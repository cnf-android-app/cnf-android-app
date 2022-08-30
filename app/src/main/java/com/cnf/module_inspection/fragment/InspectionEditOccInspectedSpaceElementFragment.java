package com.cnf.module_inspection.fragment;

import static com.cnf.utils.AppConstants.FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE_CATEGORY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_GUIDE_ID_NAME;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ID_NAME;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnf.module_auth.activity.HomeActivity;
import com.cnf.R;
import com.cnf.module_inspection.async.LoadOccInspectedSpaceElementTask;
import com.cnf.module_inspection.async.PassAllOccInspectedSpaceElementTask;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InspectionEditOccInspectedSpaceElementFragment extends Fragment {

  private static final String TITLE = "INSPECTED SPACE ELEMENT";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_inspection_occ_inspected_space_element, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    if (getActivity() == null) {
      return;
    }

    String inspectedSpaceId = getActivity().getIntent().getStringExtra(INTENT_EXTRA_INSPECTED_SPACE_ID_NAME);
    int codeElementGuideId = getActivity().getIntent().getIntExtra(INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_GUIDE_ID_NAME, -1);

    RecyclerView rvOccInspectedSpaceElement = getActivity().findViewById(R.id.rv_inspection_inspected_space_elements);
    FloatingActionButton btnFinish = getActivity().findViewById(R.id.btn_inspection_occ_inspected_space_elements_finish);
    FloatingActionButton btnBatchPass = getActivity().findViewById(R.id.btn_inspection_occ_inspected_space_elements_batch_pass);
    Toolbar toolbar = getActivity().findViewById(R.id.tb_occ_inspection_container_nav);
    TextView tvNavTitle = getActivity().findViewById(R.id.tv_occ_inspection_container_nav_title);
    EditText etSearch = getActivity().findViewById(R.id.et_inspection_inspected_space_elements_search);

    rvOccInspectedSpaceElement.setLayoutManager(new LinearLayoutManager(getActivity()));
    tvNavTitle.setText(TITLE);

    etSearch.setOnFocusChangeListener((v, hasFocus) -> {
      if (!hasFocus) {
        hideKeyboard(v);
      }
    });

    toolbar.setNavigationOnClickListener(v -> {
      Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE_CATEGORY);
      if (f == null) {
        f = new InspectionSelectOccInspectedSpaceElementCategoryFragment();
      }
      getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, f).commit();
    });

    btnFinish.setOnClickListener(v -> new AlertDialog
        .Builder(getActivity())
        .setTitle("Confirm")
        .setMessage("Do you want to go back to inspection main page?")
        .setPositiveButton("Yes", (dialog, which) -> {
          Intent intent = new Intent(getActivity(), HomeActivity.class);
          startActivity(intent);
        })
        .setNegativeButton("No", (dialog, which) -> {
        })
        .create()
        .show());

    btnBatchPass.setOnClickListener(v -> new Builder(getActivity())
        .setTitle("Confirm")
        .setMessage("Do you want to pass all?")
        .setPositiveButton("Yes", (dialog, which) -> {
          PassAllOccInspectedSpaceElementTask task = new PassAllOccInspectedSpaceElementTask(inspectedSpaceId, codeElementGuideId, InspectionEditOccInspectedSpaceElementFragment.this);
          task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        })
        .setNegativeButton("No", (dialog, which) -> {
        })
        .create()
        .show());

    LoadOccInspectedSpaceElementTask task = new LoadOccInspectedSpaceElementTask(inspectedSpaceId, codeElementGuideId, InspectionEditOccInspectedSpaceElementFragment.this);
    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }


  public void hideKeyboard(View view) {
    if (getActivity() == null) {
      return;
    }
    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }
}