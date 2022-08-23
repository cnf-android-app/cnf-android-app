package com.cnf.module_inspection.fragment;

import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_CHECKLIST_ID_KEY;

import android.os.Bundle;
import android.app.Fragment;

import android.os.Handler;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cnf.module_inspection.adapter.InspectionOccChecklistSpaceTypeAdapter;
import com.cnf.R;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra_heavy.OccChecklistSpaceTypeHeavy;
import com.cnf.module_inspection.service.local.OccInspectionChecklistSpaceTypeRepository;
import com.cnf.module_inspection.service.local.OccInspectionInfraService;

import java.util.List;

public class InspectionSelectOccChecklistSpaceTypeFragment extends Fragment {

  private final Handler textHandler = new Handler();

  private Button cancelBtn;
  private Button backBtn;
  private List<OccChecklistSpaceTypeHeavy> occChecklistSpaceTypeHeavyList;
  private OccInspectionInfraService occInspectionInfraService;
  private OccInspectionChecklistSpaceTypeRepository occInspectionChecklistSpaceTypeRepository;
  private RecyclerView inspection_space_type_list_rv;
  private InspectionDatabase inspectionDB;

  private Integer checklistId;

  private Toolbar toolbar;

  private Integer inspectionId;
  private TextView tvNavTitle;


  public InspectionSelectOccChecklistSpaceTypeFragment() {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.inspectionDB = Room.databaseBuilder(getActivity(), InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();
    this.occInspectionInfraService = OccInspectionInfraService.getInstance(getActivity());
    this.occInspectionChecklistSpaceTypeRepository = OccInspectionChecklistSpaceTypeRepository.getInstance(getActivity());
    this.checklistId = getActivity().getIntent().getIntExtra(INTENT_EXTRA_INSPECTION_CHECKLIST_ID_KEY, -1);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_inspection_select_occ_checklist_space_type, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.toolbar = getActivity().findViewById(R.id.tb_occ_inspection_container_nav);
    this.tvNavTitle = getActivity().findViewById(R.id.tv_occ_inspection_container_nav_title);
    this.tvNavTitle.setText("SELECT A SPACE");
    toolbar.setNavigationOnClickListener(v -> {
      InspectionSelectOccInspectedSpaceFragment inspectionSelectOccInspectedSpaceFragment = new InspectionSelectOccInspectedSpaceFragment();
      getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccInspectedSpaceFragment).commit();
    });
    new Thread(new LoadOccChecklistSpaceTypeHeavy()).start();
  }

  class LoadOccChecklistSpaceTypeHeavy implements Runnable {

    @Override
    public void run() {
      occChecklistSpaceTypeHeavyList = occInspectionChecklistSpaceTypeRepository.getOccChecklistSpaceTypeHeavyListFromSQLite(checklistId);
      textHandler.post(new Runnable() {
        @Override
        public void run() {
          inspection_space_type_list_rv = getActivity().findViewById(R.id.rv_space_type_list);
          inspection_space_type_list_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
          InspectionOccChecklistSpaceTypeAdapter inspectionOccChecklistSpaceTypeAdapter = new InspectionOccChecklistSpaceTypeAdapter(InspectionSelectOccChecklistSpaceTypeFragment.this, getActivity(),
              occChecklistSpaceTypeHeavyList);
          inspection_space_type_list_rv.setAdapter(inspectionOccChecklistSpaceTypeAdapter);
        }
      });
    }
  }
}