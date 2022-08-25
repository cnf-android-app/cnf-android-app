package com.cnf.module_inspection.fragment;

import static com.cnf.utils.AppConstants.FRAGMENT_INSPECTION_OCC_CHECKLIST_SPACE_TYPE;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_ID_KEY;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnf.module_auth.activity.HomeActivity;
import com.cnf.R;
import com.cnf.module_inspection.async.LoadOccInspectedSpaceTask;
import com.cnf.module_inspection.service.local.OccInspectionSpaceRepository.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InspectionSelectOccInspectedSpaceFragment extends Fragment {

  private final static String INSPECTION_SPACE_TITLE = "INSPECTED SPACE";
  private Integer inspectionId;

  public static Fragment newInstance() {
    InspectionSelectOccInspectedSpaceFragment inspectionSelectOccInspectedSpaceFragment;
    inspectionSelectOccInspectedSpaceFragment = new InspectionSelectOccInspectedSpaceFragment();
    return inspectionSelectOccInspectedSpaceFragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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

    TextView tvNavTitle = getActivity().findViewById(R.id.tv_occ_inspection_container_nav_title);
    Toolbar toolbar = getActivity().findViewById(R.id.tb_occ_inspection_container_nav);
    RadioGroup radioGroup = getActivity().findViewById(R.id.rg_inspected_space_is_finish_or_not);
    RadioButton rBtnFinished = getActivity().findViewById(R.id.rb_inspected_space_finished);
    RadioButton rBtnUnFinish = getActivity().findViewById(R.id.rb_inspected_space_un_finish);
    RecyclerView rvOccInspectedSpace = getActivity().findViewById(R.id.rv_occ_inspected_space);
    FloatingActionButton btnAddSpace = getActivity().findViewById(R.id.btn_add_space);

    tvNavTitle.setText(INSPECTION_SPACE_TITLE);
    toolbar.setNavigationOnClickListener(v -> {
      Intent intent = new Intent(getActivity(), HomeActivity.class);
      startActivity(intent);
    });
    rvOccInspectedSpace.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    btnAddSpace.setOnClickListener(v -> new AlertDialog
        .Builder(getActivity())
        .setTitle("Confirm")
        .setMessage("Do you want to add a space for inspection?")
        .setNegativeButton("Yes", (dialog, which) -> {
          Fragment fragment = getActivity().getFragmentManager().findFragmentByTag(FRAGMENT_INSPECTION_OCC_CHECKLIST_SPACE_TYPE);
          if (fragment == null) {
            fragment = new InspectionSelectOccChecklistSpaceTypeFragment();
          }
          getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, fragment, FRAGMENT_INSPECTION_OCC_CHECKLIST_SPACE_TYPE).commit();
        })
        .setPositiveButton("No ", (dialog, which) -> {

        })
        .create()
        .show());
    radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
      if (checkedId == R.id.rb_inspected_space_un_finish) {
        configAfterClickOnUnFinishBtn(rBtnUnFinish, rBtnFinished);
      } else if (checkedId == R.id.rb_inspected_space_finished) {
        configAfterClickOnFinishedBtn(rBtnUnFinish, rBtnFinished);
      }
    });
    configAfterClickOnUnFinishBtn(rBtnUnFinish, rBtnFinished);
  }

  private void configAfterClickOnUnFinishBtn(RadioButton rBtnUnFinish, RadioButton rBtnFinished) {
    rBtnUnFinish.setChecked(true);
    rBtnFinished.setChecked(false);
    rBtnUnFinish.setTextColor(getActivity().getColor(R.color.on_switch_font));
    rBtnUnFinish.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background));
    rBtnFinished.setTextColor(getActivity().getColor(R.color.off_switch_font));
    rBtnFinished.setBackground(null);
    LoadOccInspectedSpaceTask task = new LoadOccInspectedSpaceTask(inspectionId, InspectionSelectOccInspectedSpaceFragment.this, Category.UN_FINISH);
    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

  private void configAfterClickOnFinishedBtn(RadioButton rBtnUnFinish, RadioButton rBtnFinished) {
    rBtnUnFinish.setChecked(false);
    rBtnFinished.setChecked(true);
    rBtnFinished.setTextColor(getActivity().getColor(R.color.on_switch_font));
    rBtnFinished.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background));
    rBtnUnFinish.setTextColor(getActivity().getColor(R.color.off_switch_font));
    rBtnUnFinish.setBackground(null);
    LoadOccInspectedSpaceTask task = new LoadOccInspectedSpaceTask(inspectionId, InspectionSelectOccInspectedSpaceFragment.this, Category.FINISHED);
    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }
}