package com.cnf.module_inspection.fragment;

import static com.cnf.utils.AppConstants.FRAGMENT_INSPECTION_OCC_CHECKLIST_SPACE_TYPE;

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
import com.cnf.module_inspection.async.LoadOccLocationDescriptionTask;

public class InspectionSelectOccLocationDescriptionFragment extends Fragment {

  private final static String INSPECTION_OCC_LOCATION = "SELECT SPACE LOCATION";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_inspection_select_location_description, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (getActivity() == null) {
      return;
    }
    Toolbar toolbar = getActivity().findViewById(R.id.tb_occ_inspection_container_nav);
    TextView tvNavTitle = getActivity().findViewById(R.id.tv_occ_inspection_container_nav_title);
    RecyclerView recyclerView = getActivity().findViewById(R.id.rv_inspection_location_description);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    tvNavTitle.setText(INSPECTION_OCC_LOCATION);
    toolbar.setNavigationOnClickListener(v -> {
      Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_INSPECTION_OCC_CHECKLIST_SPACE_TYPE);
      if (f == null) {
        f = new InspectionSelectOccChecklistSpaceTypeFragment();
      }
      getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, f, FRAGMENT_INSPECTION_OCC_CHECKLIST_SPACE_TYPE).commit();
    });
    LoadOccLocationDescriptionTask task = new LoadOccLocationDescriptionTask(InspectionSelectOccLocationDescriptionFragment.this);
    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }
}