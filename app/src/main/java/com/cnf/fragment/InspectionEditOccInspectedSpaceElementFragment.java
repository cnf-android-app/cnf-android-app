package com.cnf.fragment;

import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;
import static com.cnf.utils.AppConstants.INTENSITY_SCHEMA_VIOLATION_SEVERITY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_GUIDE_ID_NAME;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ID_NAME;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import android.os.Handler;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.room.Room;
import com.cnf.InspectionActivity;
import com.cnf.R;
import com.cnf.adapter.InspectionOccInspectedSpaceElementAdapter;
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.BlobBytes;
import com.cnf.domain.OccInspectedSpaceElementHeavy;
import com.cnf.domain.infra.IntensityClass;
import com.cnf.service.local.OccInspectionSpaceElementService;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;


public class InspectionEditOccInspectedSpaceElementFragment extends Fragment {

  private final Handler textHandler = new Handler();

  private ProgressDialog progressDialog;

  private OccInspectionSpaceElementService occInspectionSpaceElementService;
  private InspectionOccInspectedSpaceElementAdapter inspectionOccInspectedSpaceElementAdapter;
  private InspectionDatabase inspectionDB;

  private List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList;
  private List<IntensityClass> intensityClassList;

  private RecyclerView rvOccInspectedSpaceElement;
  private FloatingActionButton btnFinish;
  private TextView tvNavTitle;
  private Toolbar toolbar;

  private int inspectedSpaceId;
  private int codeElementGuideId;

  public InspectionEditOccInspectedSpaceElementFragment() {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.inspectionDB = Room.databaseBuilder(getActivity(), InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();
    this.occInspectionSpaceElementService = OccInspectionSpaceElementService.getInstance();
    this.inspectedSpaceId = this.getActivity().getIntent().getIntExtra(INTENT_EXTRA_INSPECTED_SPACE_ID_NAME, -1);
    this.codeElementGuideId = this.getActivity().getIntent().getIntExtra(INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_GUIDE_ID_NAME, -1);
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
    this.toolbar = getActivity().findViewById(R.id.tb_occ_inspection_container_nav);
    this.tvNavTitle = getActivity().findViewById(R.id.tv_occ_inspection_container_nav_title);

    this.tvNavTitle.setText("INSPECTED SPACE ELEMENT");
    this.progressDialog = new ProgressDialog(getActivity());

    this.toolbar.setNavigationOnClickListener(v -> {
      InspectionSelectOccInspectedSpaceElementCategoryFragment inspectionSelectOccInspectedSpaceElementCategoryFragment = new InspectionSelectOccInspectedSpaceElementCategoryFragment();
      getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccInspectedSpaceElementCategoryFragment).commit();
    });

    this.btnFinish.setOnClickListener(v -> {
      Intent intent = new Intent(getActivity(), InspectionActivity.class);
      getActivity().finish();
      startActivity(intent);
    });

    new Thread(new LoadOccInspectedSpaceElementHeavy()).start();
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
      occInspectedSpaceElementHeavyList = occInspectionSpaceElementService.getOccInspectedSpaceElementHeavyList(inspectionDB, codeElementGuideId, inspectedSpaceId);
      occInspectedSpaceElementHeavyList = occInspectionSpaceElementService.configureOccInspectedSpaceElementHeavyListStatus(occInspectedSpaceElementHeavyList);
      intensityClassList = occInspectionSpaceElementService.selectAllIntensityClassListBySchemaLabel(inspectionDB, INTENSITY_SCHEMA_VIOLATION_SEVERITY);
      for (OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy : occInspectedSpaceElementHeavyList) {
        List<BlobBytes> blobBytesList = occInspectionSpaceElementService.getInspectedPhotoBlobBytesList(inspectionDB,
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
        List<BlobBytes> blobBytesList = occInspectionSpaceElementService.getInspectedPhotoBlobBytesList(inspectionDB,
            occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getInspectedSpaceElementId());
        occInspectedSpaceElementHeavy.setBlobBytesList(blobBytesList);
      }
      textHandler.post(() -> {
        inspectionOccInspectedSpaceElementAdapter.setOccInspectedSpaceElementHeavyList(occInspectedSpaceElementHeavyList);
        rvOccInspectedSpaceElement.getAdapter().notifyDataSetChanged();
      });
    }
  }
}