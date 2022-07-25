package com.cnf.fragment;

import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ID_NAME;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.room.Room;
import com.cnf.R;
import com.cnf.adapter.InspectionOccInspectedSpaceElementCategoryAdapter;
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.OccInspectedSpaceElementHeavy;
import com.cnf.domain.infra.CodeElementGuide;
import com.cnf.service.local.OccInspectionSpaceElementService;

import java.util.List;
import java.util.Map;

public class InspectionSelectOccInspectedSpaceElementCategoryFragment extends Fragment {

  private final Handler textHandler = new Handler();

  private Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> occInspectedSpaceElementHeavyMap;

  private Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> unFinishOccInspectedSpaceElementHeavyMap;
  private Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> finishedOccInspectedSpaceElementHeavyMap;

  InspectionOccInspectedSpaceElementCategoryAdapter unFinishOccInspectedSpaceElementCategoryAdapter;
  InspectionOccInspectedSpaceElementCategoryAdapter finishedOccInspectedSpaceElementCategoryAdapter;

  private RecyclerView rvOccInspectedSpaceElementCategory;
  private OccInspectionSpaceElementService occInspectionSpaceElementService;
  private String inspectedSpaceId;
  private InspectionDatabase inspectionDB;

  private RadioButton rBtnUnFinish;
  private RadioButton rBtnFinished;
  private RadioGroup radioGroup;

  private TextView tvNavTitle;

  private Toolbar toolbar;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.occInspectionSpaceElementService = OccInspectionSpaceElementService.getInstance();
    this.inspectedSpaceId = this.getActivity().getIntent().getStringExtra(INTENT_EXTRA_INSPECTED_SPACE_ID_NAME);
    this.occInspectionSpaceElementService = OccInspectionSpaceElementService.getInstance();
    this.inspectionDB = Room.databaseBuilder(getActivity(), InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_inspection_inspected_space_element_category, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    this.rvOccInspectedSpaceElementCategory = getActivity().findViewById(R.id.rv_occ_inspected_space_element_category);
    this.rvOccInspectedSpaceElementCategory.setLayoutManager(new LinearLayoutManager(getActivity()));

    this.radioGroup = view.findViewById(R.id.rg_occ_inspected_space_element_category_is_finish_or_not);
    this.rBtnUnFinish = view.findViewById(R.id.rb_occ_inspected_space_element_category_un_finish);
    this.rBtnFinished = view.findViewById(R.id.rb_occ_inspected_space_element_category_finished);


    this.toolbar = getActivity().findViewById(R.id.tb_occ_inspection_container_nav);
    this.tvNavTitle = getActivity().findViewById(R.id.tv_occ_inspection_container_nav_title);
    this.tvNavTitle.setText("INSPECTED SPACE CATEGORY");
    toolbar.setNavigationOnClickListener(v -> {
      InspectionSelectOccInspectedSpaceFragment inspectionSelectOccInspectedSpaceFragment = new InspectionSelectOccInspectedSpaceFragment();
      getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccInspectedSpaceFragment).commit();
    });

    configAfterClickOnUnFinishBtn();

    this.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
      if (checkedId == R.id.rb_occ_inspected_space_element_category_un_finish) {
        configAfterClickOnUnFinishBtn();
      } else if (checkedId == R.id.rb_occ_inspected_space_element_category_finished) {
        configAfterClickOnFinishedBtn();
      }
    });

//    cancelBtn = getActivity().findViewById(R.id.btn_inspection_inspected_space_element_category_cancel);
//    backBtn = getActivity().findViewById(R.id.btn_inspection_inspected_space_element_category_back);

//    cancelBtn.setOnClickListener(v -> {
//      Intent intent = new Intent(getActivity(), InspectionActivity.class);
//      getActivity().finish();
//      startActivity(intent);
//    });
//
//    backBtn.setOnClickListener(v -> getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, new InspectionSelectOccInspectedSpaceFragment()).commit());

    new Thread(new LoadOccInspectedSpaceElementHeavyMap()).start();
  }

  private void configAfterClickOnUnFinishBtn() {
    rvOccInspectedSpaceElementCategory.setAdapter(unFinishOccInspectedSpaceElementCategoryAdapter);
    rBtnUnFinish.setChecked(true);
    rBtnFinished.setChecked(false);
    rBtnUnFinish.setTextColor(getActivity().getColor(R.color.on_switch_font));
    rBtnUnFinish.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background));
    rBtnFinished.setTextColor(getActivity().getColor(R.color.off_switch_font));
    rBtnFinished.setBackground(null);
  }

  private void configAfterClickOnFinishedBtn() {
    rvOccInspectedSpaceElementCategory.setAdapter(finishedOccInspectedSpaceElementCategoryAdapter);
    rBtnUnFinish.setChecked(false);
    rBtnFinished.setChecked(true);
    rBtnFinished.setTextColor(getActivity().getColor(R.color.on_switch_font));
    rBtnFinished.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background));
    rBtnUnFinish.setTextColor(getActivity().getColor(R.color.off_switch_font));
    rBtnUnFinish.setBackground(null);
  }

  class LoadOccInspectedSpaceElementHeavyMap implements Runnable {

    @Override
    public void run() {
      occInspectedSpaceElementHeavyMap = occInspectionSpaceElementService.getOccInspectedSpaceElementHeavyMap(inspectionDB, inspectedSpaceId);
      System.out.println();
      Map<String, Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>>> occInspectedSpaceElementHeavyStatusMap = occInspectionSpaceElementService.getOccInspectedSpaceElementHeavyStatusMap(
          inspectionDB, occInspectedSpaceElementHeavyMap);
      unFinishOccInspectedSpaceElementHeavyMap = occInspectedSpaceElementHeavyStatusMap.get("UNFINISH");
      finishedOccInspectedSpaceElementHeavyMap = occInspectedSpaceElementHeavyStatusMap.get("FINISHED");
      unFinishOccInspectedSpaceElementCategoryAdapter = new InspectionOccInspectedSpaceElementCategoryAdapter(
          InspectionSelectOccInspectedSpaceElementCategoryFragment.this, unFinishOccInspectedSpaceElementHeavyMap, false);
      finishedOccInspectedSpaceElementCategoryAdapter = new InspectionOccInspectedSpaceElementCategoryAdapter(
          InspectionSelectOccInspectedSpaceElementCategoryFragment.this, finishedOccInspectedSpaceElementHeavyMap, true);

      textHandler.post(() -> {
        rvOccInspectedSpaceElementCategory.setAdapter(unFinishOccInspectedSpaceElementCategoryAdapter);
      });
    }
  }
}