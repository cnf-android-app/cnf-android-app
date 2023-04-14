package com.cnf.module_inspection.fragment;

import static com.cnf.utils.AppConstants.FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ID_NAME;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.cnf.R;
import com.cnf.module_inspection.async.LoadOccInspectedSpaceElementCategoryTask;

import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository.Category;

public class InspectionSelectOccInspectedSpaceElementCategoryFragment extends Fragment {

  private final static String INSPECTION_SPACE_CATEGORY_TITLE = "INSPECTED SPACE CATEGORY";


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_inspection_inspected_space_element_category, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (getActivity() == null) {
      return;
    }
    String inspectedSpaceId = this.getActivity().getIntent().getStringExtra(INTENT_EXTRA_INSPECTED_SPACE_ID_NAME);
    RecyclerView rvOccInspectedSpaceElementCategory = getActivity().findViewById(R.id.rv_occ_inspected_space_element_category);

    RadioGroup radioGroup = getActivity().findViewById(R.id.rg_occ_inspected_space_element_category_is_finish_or_not);
    Toolbar toolbar = getActivity().findViewById(R.id.tb_occ_inspection_container_nav);
    TextView tvNavTitle = getActivity().findViewById(R.id.tv_occ_inspection_container_nav_title);
    RadioButton rBtnUnFinish = getActivity().findViewById(R.id.rb_occ_inspected_space_element_category_un_finish);
    RadioButton rBtnFinished = getActivity().findViewById(R.id.rb_occ_inspected_space_element_category_finished);

    tvNavTitle.setText(INSPECTION_SPACE_CATEGORY_TITLE);
    rvOccInspectedSpaceElementCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
    toolbar.setNavigationOnClickListener(v -> {
      Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE);
      if (fragment == null) {
        fragment = new InspectionSelectOccInspectedSpaceFragment();
      }
      getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, fragment, FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE).commit();
    });

    radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
      if (checkedId == R.id.rb_occ_inspected_space_element_category_finished) {
        configAfterClickOnFinishedBtn(rBtnUnFinish, rBtnFinished, inspectedSpaceId);
      } else if (checkedId == R.id.rb_occ_inspected_space_element_category_un_finish) {
        configAfterClickOnUnFinishBtn(rBtnUnFinish, rBtnFinished, inspectedSpaceId);
      }
    });

    if (radioGroup.getCheckedRadioButtonId() == R.id.rb_occ_inspected_space_element_category_finished) {
      configAfterClickOnFinishedBtn(rBtnUnFinish, rBtnFinished, inspectedSpaceId);
    } else {
      configAfterClickOnUnFinishBtn(rBtnUnFinish, rBtnFinished, inspectedSpaceId);
    }
  }

  private void configAfterClickOnUnFinishBtn(RadioButton rBtnUnFinish, RadioButton rBtnFinished, String inspectedSpaceId) {
    if (getActivity() == null) {
      return;
    }
    rBtnUnFinish.setChecked(true);
    rBtnFinished.setChecked(false);
    rBtnUnFinish.setTextColor(getActivity().getColor(R.color.on_switch_font));
    rBtnUnFinish.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background));
    rBtnFinished.setTextColor(getActivity().getColor(R.color.off_switch_font));
    rBtnFinished.setBackground(null);
    LoadOccInspectedSpaceElementCategoryTask task = new LoadOccInspectedSpaceElementCategoryTask(inspectedSpaceId, InspectionSelectOccInspectedSpaceElementCategoryFragment.this, Category.UN_FINISH);
    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

  private void configAfterClickOnFinishedBtn(RadioButton rBtnUnFinish, RadioButton rBtnFinished, String inspectedSpaceId) {
    if (getActivity() == null) {
      return;
    }
    rBtnUnFinish.setChecked(false);
    rBtnFinished.setChecked(true);
    rBtnFinished.setTextColor(getActivity().getColor(R.color.on_switch_font));
    rBtnFinished.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background));
    rBtnUnFinish.setTextColor(getActivity().getColor(R.color.off_switch_font));
    rBtnUnFinish.setBackground(null);
    LoadOccInspectedSpaceElementCategoryTask task = new LoadOccInspectedSpaceElementCategoryTask(inspectedSpaceId, InspectionSelectOccInspectedSpaceElementCategoryFragment.this, Category.FINISHED);
    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

}