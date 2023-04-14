package com.cnf.module_inspection.fragment;

import static com.cnf.utils.AppConstants.FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_CHECKLIST_ID_KEY;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnf.R;
import com.cnf.module_inspection.async.LoadOccChecklistSpaceTypeTask;

public class InspectionSelectOccChecklistSpaceTypeFragment extends Fragment {

  private final static String INSPECTION_OCC_CHECKLIST_SPACE_TYPE = "INSPECTED SPACE";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_inspection_select_occ_checklist_space_type, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (getActivity() == null) {
      return;
    }

    int checklistId = getActivity().getIntent().getIntExtra(INTENT_EXTRA_INSPECTION_CHECKLIST_ID_KEY, -1);
    Toolbar toolbar = getActivity().findViewById(R.id.tb_occ_inspection_container_nav);
    TextView tvNavTitle = getActivity().findViewById(R.id.tv_occ_inspection_container_nav_title);
    RecyclerView rvOccChecklistSpaceType = getActivity().findViewById(R.id.rv_space_type_list);
    tvNavTitle.setText(INSPECTION_OCC_CHECKLIST_SPACE_TYPE);
    rvOccChecklistSpaceType.setLayoutManager(new LinearLayoutManager(getActivity()));
    toolbar.setNavigationOnClickListener(v -> {
      Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE);
      if (f == null) {
        f = new InspectionSelectOccInspectedSpaceFragment();
      }
      getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, f, FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE).commit();
    });
    LoadOccChecklistSpaceTypeTask task = new LoadOccChecklistSpaceTypeTask(checklistId, InspectionSelectOccChecklistSpaceTypeFragment.this);
    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

}