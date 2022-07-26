package com.cnf.fragment;

import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;

import android.annotation.SuppressLint;
import android.os.Bundle;


import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.cnf.adapter.InspectionSpaceTypeElementAdapter;
import com.cnf.R;
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.infra_heavy.OccChecklistSpaceTypeElementHeavy;
import com.cnf.service.local.OccInspectionInfraService;

import java.util.List;


public class InspectionSpaceTypeDetailsFragment extends Fragment {

  private final Handler handler = new Handler();

  private List<OccChecklistSpaceTypeElementHeavy> occChecklistSpaceTypeElementHeavyList;
  private OccInspectionInfraService occInspectionInfraService;
  private InspectionDatabase inspectionDB;

  private int checklistSpaceTypeId;

  private RecyclerView rvOccChecklistSpaceTypeElement;
  private TextView tvNavTitle;
  private Toolbar toolbar;

  public InspectionSpaceTypeDetailsFragment() {
  }

  @SuppressLint("ValidFragment")
  public InspectionSpaceTypeDetailsFragment(int checklistSpaceTypeId) {
    this.checklistSpaceTypeId = checklistSpaceTypeId;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.inspectionDB = Room.databaseBuilder(getActivity(), InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();
    this.occInspectionInfraService = OccInspectionInfraService.getInstance();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_inspection_select_occ_checklist_space_type_element_details, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    this.toolbar = getActivity().findViewById(R.id.tb_occ_inspection_container_nav);
    this.tvNavTitle = getActivity().findViewById(R.id.tv_occ_inspection_container_nav_title);
    this.tvNavTitle.setText("CHECKLIST SPACE ELEMENTS");

    this.toolbar.setNavigationOnClickListener(v -> {
      InspectionSelectOccChecklistSpaceTypeFragment inspectionSelectOccChecklistSpaceTypeFragment = new InspectionSelectOccChecklistSpaceTypeFragment();
      getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccChecklistSpaceTypeFragment).commit();
    });

    this.rvOccChecklistSpaceTypeElement = getActivity().findViewById(R.id.rv_occ_checklist_space_type_element_detail);
    this.rvOccChecklistSpaceTypeElement.setLayoutManager(new LinearLayoutManager(getActivity()));

    new Thread(new LoadOccChecklistSpaceTypeElementHeavyList()).start();
  }

  class LoadOccChecklistSpaceTypeElementHeavyList implements Runnable {

    @Override
    public void run() {
      occChecklistSpaceTypeElementHeavyList = occInspectionInfraService.getOccChecklistSpaceTypeElementHeavyDetailsList(checklistSpaceTypeId, inspectionDB);
      handler.post(new Runnable() {
        @Override
        public void run() {
          InspectionSpaceTypeElementAdapter inspectionSpaceTypeElementAdapter = new InspectionSpaceTypeElementAdapter(getActivity(), occChecklistSpaceTypeElementHeavyList);
          rvOccChecklistSpaceTypeElement.setAdapter(inspectionSpaceTypeElementAdapter);
        }
      });
    }
  }
}