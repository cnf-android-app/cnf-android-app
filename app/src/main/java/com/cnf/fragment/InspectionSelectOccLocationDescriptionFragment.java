package com.cnf.fragment;

import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.cnf.InspectionActivity;
import com.cnf.R;
import com.cnf.adapter.InspectionOccLocationDescriptionAdapter;
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.infra.OccLocationDescription;
import com.cnf.service.local.OccInspectionInfraService;

import java.util.List;


public class InspectionSelectOccLocationDescriptionFragment extends Fragment {

  private final Handler textHandler = new Handler();

  private List<OccLocationDescription> locationDescriptionList;
  private OccInspectionInfraService occInspectionInfraService;
  private RecyclerView inspection_location_description_rv;
  private Integer CSTId;
  private InspectionDatabase inspectionDB;
  private Integer occInspectedSpaceId;

  private TextView tvNavTitle;

  private Toolbar toolbar;

  public InspectionSelectOccLocationDescriptionFragment() {

  }


  @SuppressLint("ValidFragment")
  public InspectionSelectOccLocationDescriptionFragment(Integer CSTId, Integer occInspectedSpaceId) {
    this.CSTId = CSTId;
    this.occInspectedSpaceId = occInspectedSpaceId;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.inspectionDB = Room.databaseBuilder(getActivity(), InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();
    this.occInspectionInfraService = OccInspectionInfraService.getInstance();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    this.toolbar = getActivity().findViewById(R.id.tb_occ_inspection_container_nav);
    this.tvNavTitle = getActivity().findViewById(R.id.tv_occ_inspection_container_nav_title);
    this.tvNavTitle.setText("INSPECTED SPACE CATEGORY");
    toolbar.setNavigationOnClickListener(v -> {
      InspectionSelectOccInspectedSpaceFragment inspectionSelectOccInspectedSpaceFragment = new InspectionSelectOccInspectedSpaceFragment();
      getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccInspectedSpaceFragment).commit();
    });
    return inflater.inflate(R.layout.fragment_inspection_select_location_description, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Button backBtn = getActivity().findViewById(R.id.btn_location_description_back);
    Button cancelBtn = getActivity().findViewById(R.id.btn_location_description_cancel);
    backBtn.setOnClickListener(view1 -> getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, new InspectionSelectOccChecklistSpaceTypeFragment()).commit());
    cancelBtn.setOnClickListener(view12 -> {
      Intent intent = new Intent(getActivity(), InspectionActivity.class);
      getActivity().finish();
      startActivity(intent);
    });
    new Thread(new LoadOccLocationDescription()).start();

  }

  private class LoadOccLocationDescription implements Runnable {

    @Override
    public void run() {
      locationDescriptionList = occInspectionInfraService.getOccLocationDescriptionListFromSQLite(inspectionDB);
      textHandler.post(new Runnable() {
        @Override
        public void run() {
          inspection_location_description_rv = getActivity().findViewById(R.id.rv_inspection_location_description);
          inspection_location_description_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
          InspectionOccLocationDescriptionAdapter inspectionOccLocationDescriptionAdapter;
          if (occInspectedSpaceId == null) {
            inspectionOccLocationDescriptionAdapter = new InspectionOccLocationDescriptionAdapter(InspectionSelectOccLocationDescriptionFragment.this, CSTId, getActivity(), locationDescriptionList);
          } else {
            inspectionOccLocationDescriptionAdapter = new InspectionOccLocationDescriptionAdapter(InspectionSelectOccLocationDescriptionFragment.this, getActivity(), locationDescriptionList, occInspectedSpaceId);
          }
          inspection_location_description_rv.setAdapter(inspectionOccLocationDescriptionAdapter);
        }
      });
    }
  }
}