package com.cnf.fragment;

import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_ID_KEY;

import android.app.Fragment;
import android.content.Intent;
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
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.domain.OccInspectedSpaceHeavy;
import com.cnf.service.local.OccInspectedSpaceService;

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
  private InspectionDatabase inspectionDB;

  private RadioButton rBtnUnFinish;
  private RadioButton rBtnFinished;
  private RadioGroup radioGroup;
  private TextView tvNavTitle;

  private Toolbar toolbar;

  private Integer inspectionId;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.inspectionDB = Room.databaseBuilder(getActivity(), InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();
    this.occInspectedSpaceService = OccInspectedSpaceService.getInstance();
    this.occInspectionSpaceElementService = OccInspectionSpaceElementService.getInstance();

    this.inspectionId = getActivity().getIntent().getIntExtra(INTENT_EXTRA_INSPECTION_ID_KEY, -1);

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

    configAfterClickOnUnFinishBtn();

    this.btnAddSpace.setOnClickListener(v -> {
      this.inspectionSelectOccChecklistSpaceTypeFragment = new InspectionSelectOccChecklistSpaceTypeFragment();
      getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccChecklistSpaceTypeFragment).commit();
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


  private void configAfterClickOnUnFinishBtn () {
    rvOccInspectedSpace.setAdapter(unFinishOccInspectedSpaceAdapter);
    rBtnUnFinish.setChecked(true);
    rBtnFinished.setChecked(false);
    rBtnUnFinish.setTextColor(getActivity().getColor(R.color.on_switch_font));
    rBtnUnFinish.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background));
    rBtnFinished.setTextColor(getActivity().getColor(R.color.off_switch_font));
    rBtnFinished.setBackground(null);
  }

  private void configAfterClickOnFinishedBtn () {
    rvOccInspectedSpace.setAdapter(finishedOccInspectedSpaceAdapter);
    rBtnUnFinish.setChecked(false);
    rBtnFinished.setChecked(true);
    rBtnFinished.setTextColor(getActivity().getColor(R.color.on_switch_font));
    rBtnFinished.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background));
    rBtnUnFinish.setTextColor(getActivity().getColor(R.color.off_switch_font));
    rBtnUnFinish.setBackground(null);
  }

  private class LoadOccInspectedSpace implements Runnable {

    @Override
    public void run() {
      allOccInspectedSpaceHeavyList = occInspectedSpaceService.getOccInspectedSpaceHeavyListByInspectionId(inspectionDB, inspectionId);
      for (OccInspectedSpaceHeavy occInspectedSpaceHeavy : allOccInspectedSpaceHeavyList) {
        Integer inspectedSpaceId = occInspectedSpaceHeavy.getOccInspectedSpace().getInspectedSpaceId();
        List<OccInspectedSpaceElement> occInspectedSpaceElementList = occInspectionSpaceElementService.getOccInspectedSpaceElementList(inspectionDB, inspectedSpaceId);
        occInspectedSpaceHeavy.setOccInspectedSpaceElementList(occInspectedSpaceElementList);
      }

      Map<String, List<OccInspectedSpaceHeavy>> occInspectedSpaceHeavyListMap = occInspectedSpaceService.getOccInspectedSpaceHeavyListMap(inspectionDB, allOccInspectedSpaceHeavyList);
      unFinishOccInspectedSpaceHeavyList = occInspectedSpaceHeavyListMap.get("UNFINISH");
      finishedOccInspectedSpaceHeavyList = occInspectedSpaceHeavyListMap.get("FINISHED");
      finishedOccInspectedSpaceAdapter = new InspectionOccInspectedSpaceAdapter(finishedOccInspectedSpaceHeavyList, getActivity(), InspectionSelectOccInspectedSpaceFragment.this);
      unFinishOccInspectedSpaceAdapter = new InspectionOccInspectedSpaceAdapter(unFinishOccInspectedSpaceHeavyList, getActivity(), InspectionSelectOccInspectedSpaceFragment.this);
      textHandler.post(() -> {
        rvOccInspectedSpace.setAdapter(unFinishOccInspectedSpaceAdapter);
      });
    }
  }
}