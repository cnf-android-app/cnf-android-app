package com.cnf.fragment;

import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_ID_KEY;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_MUNICIPALITY_CODE;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnf.InspectionActivity;
import com.cnf.InspectionContainerActivity;
import com.cnf.R;
import com.cnf.adapter.InspectionOccInspectedSpaceAdapter;
import com.cnf.adapter.InspectionOccInspectedSpaceElementAdapter.SavePass;
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.domain.OccInspectedSpaceHeavy;
import com.cnf.domain.infra_heavy.OccInspectionDispatchHeavy;
import com.cnf.domain.tasks.OccInspection;
import com.cnf.service.local.OccInspectedSpaceService;

import com.cnf.service.local.OccInspectionDispatchService;
import com.cnf.service.local.OccInspectionService;
import com.cnf.service.local.OccInspectionSpaceElementService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import java.util.Map;

public class InspectionSelectOccInspectedSpaceFragment extends Fragment {

  private final Handler textHandler = new Handler();

  private FloatingActionButton btnAddSpace;

  private InspectionSelectOccChecklistSpaceTypeFragment inspectionSelectOccChecklistSpaceTypeFragment;
  private RecyclerView rvOccInspectedSpace;

  private List<OccInspectedSpaceHeavy> allOccInspectedSpaceHeavyList;
  private List<OccInspectedSpaceHeavy> unFinishOccInspectedSpaceHeavyList;
  private List<OccInspectedSpaceHeavy> finishedOccInspectedSpaceHeavyList;

  private InspectionOccInspectedSpaceAdapter unFinishOccInspectedSpaceAdapter;
  private InspectionOccInspectedSpaceAdapter finishedOccInspectedSpaceAdapter;

  private OccInspectedSpaceService occInspectedSpaceService;
  private OccInspectionSpaceElementService occInspectionSpaceElementService;
  private OccInspectionDispatchService occInspectionDispatchService;
  private OccInspectionService occInspectionService;
  private InspectionDatabase inspectionDB;

  private RadioButton rBtnUnFinish;
  private RadioButton rBtnFinished;
  private RadioGroup radioGroup;
  private TextView tvNavTitle;

  private Toolbar toolbar;

  private Integer inspectionId;
  private Integer muniCode;
  private SharedPreferences sp;

  private TextView tvIsCompletedIndicator;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.inspectionDB = Room.databaseBuilder(getActivity(), InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();
    this.occInspectedSpaceService = OccInspectedSpaceService.getInstance();
    this.occInspectionSpaceElementService = OccInspectionSpaceElementService.getInstance();
    this.occInspectionDispatchService = OccInspectionDispatchService.getInstance();
    this.occInspectionService = OccInspectionService.getInstance();
    this.inspectionId = getActivity().getIntent().getIntExtra(INTENT_EXTRA_INSPECTION_ID_KEY, -1);
    this.sp = getActivity().getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    this.muniCode = sp.getInt(SP_KEY_MUNICIPALITY_CODE, -1);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_inspection_add_space, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    this.tvNavTitle = getActivity().findViewById(R.id.tv_occ_inspection_container_nav_title);
    this.toolbar = getActivity().findViewById(R.id.tb_occ_inspection_container_nav);
    this.tvNavTitle.setText("INSPECTED SPACE");
    toolbar.setNavigationOnClickListener(v -> {
      Intent intent = new Intent(getActivity(), InspectionActivity.class);
      startActivity(intent);
    });
    this.radioGroup = view.findViewById(R.id.rg_inspected_space_is_finish_or_not);

    this.rBtnUnFinish = view.findViewById(R.id.rb_inspected_space_un_finish);
    this.rBtnFinished = view.findViewById(R.id.rb_inspected_space_finished);

    this.rvOccInspectedSpace = view.findViewById(R.id.rv_occ_inspected_space);
    this.rvOccInspectedSpace.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    this.btnAddSpace = view.findViewById(R.id.btn_add_space);

    this.tvIsCompletedIndicator = view.findViewById(R.id.tv_inspection_space_is_completed_indicator);

    configAfterClickOnUnFinishBtn();

    this.btnAddSpace.setOnClickListener(v -> {

      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      builder.setTitle("Options");
      builder.setMessage("Do you want to add all required spaces automatically or individual space manually?");
      builder.setPositiveButton("Automatically", (dialog, which) -> {
        new Thread(new AutoGenerateInspectedSpace()).start();
      });
      builder.setNegativeButton("Manually", (dialog, which) -> {
        this.inspectionSelectOccChecklistSpaceTypeFragment = new InspectionSelectOccChecklistSpaceTypeFragment();
        getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccChecklistSpaceTypeFragment).commit();
      });
      AlertDialog alertDialog = builder.create();
      alertDialog.show();
    });

    this.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
      if (checkedId == R.id.rb_inspected_space_un_finish) {
        configAfterClickOnUnFinishBtn();
      } else if (checkedId == R.id.rb_inspected_space_finished) {
        configAfterClickOnFinishedBtn();
      }
    });
    new Thread(new LoadOccInspectedSpace()).start();
  }


  private void configAfterClickOnUnFinishBtn() {
    rvOccInspectedSpace.setAdapter(unFinishOccInspectedSpaceAdapter);
    rBtnUnFinish.setChecked(true);
    rBtnFinished.setChecked(false);
    rBtnUnFinish.setTextColor(getActivity().getColor(R.color.on_switch_font));
    rBtnUnFinish.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background));
    rBtnFinished.setTextColor(getActivity().getColor(R.color.off_switch_font));
    rBtnFinished.setBackground(null);
    if (unFinishOccInspectedSpaceHeavyList == null || unFinishOccInspectedSpaceHeavyList.size() == 0) {
      tvIsCompletedIndicator.setText(String.format("All inspected spaces for Inspection ID: %s have been completed", inspectionId));
      tvIsCompletedIndicator.setGravity(View.TEXT_ALIGNMENT_CENTER);
      tvIsCompletedIndicator.setVisibility(View.VISIBLE);
    } else {
      tvIsCompletedIndicator.setVisibility(View.GONE);
    }
  }

  private void configAfterClickOnFinishedBtn() {
    rvOccInspectedSpace.setAdapter(finishedOccInspectedSpaceAdapter);
    rBtnUnFinish.setChecked(false);
    rBtnFinished.setChecked(true);
    rBtnFinished.setTextColor(getActivity().getColor(R.color.on_switch_font));
    rBtnFinished.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background));
    rBtnUnFinish.setTextColor(getActivity().getColor(R.color.off_switch_font));
    rBtnUnFinish.setBackground(null);
    tvIsCompletedIndicator.setVisibility(View.GONE);
  }

  private class AutoGenerateInspectedSpace implements Runnable {

    @Override
    public void run() {
      OccInspection occInspection = occInspectionService.getOccInspection(inspectionDB, inspectionId);
      occInspectedSpaceService.createDefaultOccInspectedSpace(inspectionDB, occInspection);
      new Thread(new LoadOccInspectedSpace()).start();
    }
  }

  private class LoadOccInspectedSpace implements Runnable {

    @Override
    public void run() {
      allOccInspectedSpaceHeavyList = occInspectedSpaceService.getOccInspectedSpaceHeavyListByInspectionId(inspectionDB, inspectionId);
      for (OccInspectedSpaceHeavy occInspectedSpaceHeavy : allOccInspectedSpaceHeavyList) {
        String inspectedSpaceId = occInspectedSpaceHeavy.getOccInspectedSpace().getInspectedSpaceId();
        List<OccInspectedSpaceElement> occInspectedSpaceElementList = occInspectionSpaceElementService.getOccInspectedSpaceElementList(inspectionDB, inspectedSpaceId);
        occInspectedSpaceHeavy.setOccInspectedSpaceElementList(occInspectedSpaceElementList);
      }

      Map<String, List<OccInspectedSpaceHeavy>> occInspectedSpaceHeavyListMap = occInspectedSpaceService.getOccInspectedSpaceHeavyListMap(inspectionDB, allOccInspectedSpaceHeavyList);
      unFinishOccInspectedSpaceHeavyList = occInspectedSpaceHeavyListMap.get("UNFINISH");
      finishedOccInspectedSpaceHeavyList = occInspectedSpaceHeavyListMap.get("FINISHED");
      finishedOccInspectedSpaceAdapter = new InspectionOccInspectedSpaceAdapter(finishedOccInspectedSpaceHeavyList, getActivity(), InspectionSelectOccInspectedSpaceFragment.this);
      unFinishOccInspectedSpaceAdapter = new InspectionOccInspectedSpaceAdapter(unFinishOccInspectedSpaceHeavyList, getActivity(), InspectionSelectOccInspectedSpaceFragment.this);
      unFinishOccInspectedSpaceAdapter.setFinished(false);
      finishedOccInspectedSpaceAdapter.setFinished(true);

      if (unFinishOccInspectedSpaceHeavyList == null || unFinishOccInspectedSpaceHeavyList.size() == 0) {
        textHandler.post(() -> {
          tvIsCompletedIndicator.setText(String.format("All inspected spaces for Inspection ID: %s have been completed!", inspectionId));
          tvIsCompletedIndicator.setGravity(View.TEXT_ALIGNMENT_CENTER);
          tvIsCompletedIndicator.setVisibility(View.VISIBLE);
        });
      } else {
        textHandler.post(() -> tvIsCompletedIndicator.setVisibility(View.GONE));
      }

      textHandler.post(() -> {
        rvOccInspectedSpace.setAdapter(unFinishOccInspectedSpaceAdapter);
      });
    }
  }

}