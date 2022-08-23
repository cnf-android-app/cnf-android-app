package com.cnf.module_inspection.fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;
import static com.cnf.utils.AppConstants.INTENSITY_SCHEMA_VIOLATION_SEVERITY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_GUIDE_ID_NAME;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ID_NAME;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_USER_ID;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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

import androidx.room.Room;
import com.cnf.module_auth.activity.HomeActivity;
import com.cnf.R;
import com.cnf.module_inspection.adapter.InspectionOccInspectedSpaceElementAdapter;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.BlobBytes;
import com.cnf.module_inspection.entity.OccInspectedSpaceElementHeavy;
import com.cnf.module_inspection.entity.infra.IntensityClass;
import com.cnf.module_inspection.service.local.BlobBytesRepository;
import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;


public class InspectionEditOccInspectedSpaceElementFragment extends Fragment {

  private final Handler textHandler = new Handler();

  private ProgressDialog progressDialog;

  private OccInspectionSpaceElementRepository occInspectionSpaceElementRepository;
  private BlobBytesRepository blobBytesRepository;
  private InspectionOccInspectedSpaceElementAdapter inspectionOccInspectedSpaceElementAdapter;


  private List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList;
  private List<IntensityClass> intensityClassList;

  private RecyclerView rvOccInspectedSpaceElement;
  private FloatingActionButton btnFinish;
  private FloatingActionButton btnBatchPass;
  private TextView tvNavTitle;
  private Toolbar toolbar;
  private EditText etSearch;

  private String inspectedSpaceId;
  private int codeElementGuideId;
  private int userId;
  private SharedPreferences sp;

  public InspectionEditOccInspectedSpaceElementFragment() {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.occInspectionSpaceElementRepository = OccInspectionSpaceElementRepository.getInstance(getActivity());
    this.blobBytesRepository = BlobBytesRepository.getInstance(getActivity());
    this.inspectedSpaceId = this.getActivity().getIntent().getStringExtra(INTENT_EXTRA_INSPECTED_SPACE_ID_NAME);
    this.codeElementGuideId = this.getActivity().getIntent().getIntExtra(INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_GUIDE_ID_NAME, -1);
    this.sp = this.getActivity().getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, MODE_PRIVATE);
    this.userId = sp.getInt(SP_KEY_USER_ID, 0);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_inspection_occ_inspected_space_element, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    this.rvOccInspectedSpaceElement = getActivity().findViewById(R.id.rv_inspection_inspected_space_elements);
    this.rvOccInspectedSpaceElement.setLayoutManager(new LinearLayoutManager(getActivity()));

    this.btnFinish = getActivity().findViewById(R.id.btn_inspection_occ_inspected_space_elements_finish);
    this.btnBatchPass = getActivity().findViewById(R.id.btn_inspection_occ_inspected_space_elements_batch_pass);
    this.toolbar = getActivity().findViewById(R.id.tb_occ_inspection_container_nav);
    this.tvNavTitle = getActivity().findViewById(R.id.tv_occ_inspection_container_nav_title);

    this.tvNavTitle.setText("INSPECTED SPACE ELEMENT");
    this.progressDialog = new ProgressDialog(getActivity());

    this.etSearch = getActivity().findViewById(R.id.et_inspection_inspected_space_elements_search);

    etSearch.setOnFocusChangeListener((v, hasFocus) -> {
      if (!hasFocus) {
        hideKeyboard(v);
      }
    });

    this.etSearch.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        inspectionOccInspectedSpaceElementAdapter.getFilter().filter(s.toString());
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

    this.toolbar.setNavigationOnClickListener(v -> {
      InspectionSelectOccInspectedSpaceElementCategoryFragment inspectionSelectOccInspectedSpaceElementCategoryFragment = new InspectionSelectOccInspectedSpaceElementCategoryFragment();
      getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccInspectedSpaceElementCategoryFragment).commit();
    });

    this.btnFinish.setOnClickListener(v -> {
      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      builder.setTitle("Confirm");
      builder.setMessage("Do you want to go back to inspection main page?");
      builder.setPositiveButton("Yes", (dialog, which) -> {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
      });
      builder.setNegativeButton("No", (dialog, which) -> {
      });
      AlertDialog alertDialog = builder.create();
      alertDialog.show();
    });

    this.btnBatchPass.setOnClickListener(v -> {
      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      builder.setTitle("Confirm");
      builder.setMessage("Do you want to pass all?");
      builder.setPositiveButton("Yes", (dialog, which) -> {
        new Thread(new PassAll()).start();
      });
      builder.setNegativeButton("No", (dialog, which) -> {
      });
      AlertDialog alertDialog = builder.create();
      alertDialog.show();
    });

    new Thread(new LoadOccInspectedSpaceElementHeavy()).start();
  }

  class PassAll implements Runnable {

    @Override
    public void run() {
      for (OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy : occInspectedSpaceElementHeavyList) {
        occInspectedSpaceElementHeavy = occInspectionSpaceElementRepository.configureElementForCompliance(occInspectedSpaceElementHeavy, userId);
        occInspectionSpaceElementRepository.configureOccInspectedSpaceElementStatus(occInspectedSpaceElementHeavy.getOccInspectedSpaceElement());
        occInspectionSpaceElementRepository.updateOccInspectedSpaceElement(occInspectedSpaceElementHeavy.getOccInspectedSpaceElement());
        textHandler.post(() -> {
          inspectionOccInspectedSpaceElementAdapter.setOccInspectedSpaceElementHeavyList(occInspectedSpaceElementHeavyList);
          rvOccInspectedSpaceElement.getAdapter().notifyDataSetChanged();
        });
      }
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    new Thread(new ReLoadOccInspectedSpaceElementHeavy()).start();
  }

  class LoadOccInspectedSpaceElementHeavy implements Runnable {

    @Override
    public void run() {
      textHandler.post(new Runnable() {
        @Override
        public void run() {
          progressDialog.setMessage("Loading");
          progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
          progressDialog.setIndeterminate(true);
          progressDialog.show();
        }
      });

      occInspectedSpaceElementHeavyList = occInspectionSpaceElementRepository.getOccInspectedSpaceElementHeavyListByInspectedSpaceId(codeElementGuideId, inspectedSpaceId);
      occInspectedSpaceElementHeavyList = occInspectionSpaceElementRepository.configureOccInspectedSpaceElementHeavyListStatus(occInspectedSpaceElementHeavyList);
      intensityClassList = occInspectionSpaceElementRepository.selectAllIntensityClassListBySchemaLabel(INTENSITY_SCHEMA_VIOLATION_SEVERITY);
      for (OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy : occInspectedSpaceElementHeavyList) {
        List<BlobBytes> blobBytesList = blobBytesRepository.getInspectedPhotoBlobBytesList(
            occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getInspectedSpaceElementId());
        occInspectedSpaceElementHeavy.setBlobBytesList(blobBytesList);
      }
      textHandler.post(() -> {
        inspectionOccInspectedSpaceElementAdapter = new InspectionOccInspectedSpaceElementAdapter(getActivity(), InspectionEditOccInspectedSpaceElementFragment.this, occInspectedSpaceElementHeavyList,
            intensityClassList);
        rvOccInspectedSpaceElement.setAdapter(inspectionOccInspectedSpaceElementAdapter);
        progressDialog.dismiss();
      });
    }
  }

  class ReLoadOccInspectedSpaceElementHeavy implements Runnable {

    @Override
    public void run() {
      if (occInspectedSpaceElementHeavyList == null) {
        return;
      }
      for (OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy : occInspectedSpaceElementHeavyList) {
        List<BlobBytes> blobBytesList = blobBytesRepository.getInspectedPhotoBlobBytesList(
            occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getInspectedSpaceElementId());
        occInspectedSpaceElementHeavy.setBlobBytesList(blobBytesList);
      }
      textHandler.post(() -> {
        inspectionOccInspectedSpaceElementAdapter.setOccInspectedSpaceElementHeavyList(occInspectedSpaceElementHeavyList);
        rvOccInspectedSpaceElement.getAdapter().notifyDataSetChanged();
      });
    }
  }

  public void hideKeyboard(View view) {
    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }
}