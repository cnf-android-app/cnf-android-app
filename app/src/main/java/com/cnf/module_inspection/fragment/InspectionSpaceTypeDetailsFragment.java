package com.cnf.module_inspection.fragment;

import static com.cnf.utils.AppConstants.FRAGMENT_INSPECTION_OCC_CHECKLIST_SPACE_TYPE;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_CHECKLIST_SPACE_TYPE_ID_KEY;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.module_inspection.async.LoadOccChecklistSpaceTypeElementTask;

public class InspectionSpaceTypeDetailsFragment extends Fragment {

  private final static String INSPECTION_CHECKLIST_SPACE_ELEMENTS_TITLE = "CHECKLIST SPACE ELEMENTS";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_inspection_select_occ_checklist_space_type_element_details, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (getActivity() == null) {
      return;
    }
    int checklistSpaceTypeId = getActivity().getIntent().getIntExtra(INTENT_EXTRA_INSPECTION_CHECKLIST_SPACE_TYPE_ID_KEY, -1);
    Toolbar toolbar = getActivity().findViewById(R.id.tb_occ_inspection_container_nav);
    TextView tvNavTitle = getActivity().findViewById(R.id.tv_occ_inspection_container_nav_title);
    RecyclerView rvOccChecklistSpaceTypeElement = getActivity().findViewById(R.id.rv_occ_checklist_space_type_element_detail);

    tvNavTitle.setText(INSPECTION_CHECKLIST_SPACE_ELEMENTS_TITLE);
    rvOccChecklistSpaceTypeElement.setLayoutManager(new LinearLayoutManager(getActivity()));

    toolbar.setNavigationOnClickListener(v -> {
      Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_INSPECTION_OCC_CHECKLIST_SPACE_TYPE);
      if (f == null) {
        f = new InspectionSelectOccChecklistSpaceTypeFragment();
      }
      getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, f, FRAGMENT_INSPECTION_OCC_CHECKLIST_SPACE_TYPE).commit();
    });

    LoadOccChecklistSpaceTypeElementTask task = new LoadOccChecklistSpaceTypeElementTask(checklistSpaceTypeId, InspectionSpaceTypeDetailsFragment.this);
    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }
}