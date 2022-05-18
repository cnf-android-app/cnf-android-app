package com.cnf.fragment;

import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_ID_NAME;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cnf.InspectionActivity;
import com.cnf.R;
import com.cnf.adapter.InspectedSpaceAdapter;
import com.cnf.domain.OccInspectedSpaceHeavy;
import com.cnf.service.api.InspectionActivityService;

import java.util.List;


public class InspectionAddSpaceFragment extends Fragment {

    private Button addSpaceBtn;
    private Button cancelBtn;
    private InspectionSelectSpaceTypeFragment inspectionSelectSpaceTypeFragment;
    private RecyclerView inspection_inspected_space_rv;
    private List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList;
    private InspectionActivityService inspectionActivityService;
    private InspectedSpaceAdapter inspectedSpaceAdapter;

    public InspectionAddSpaceFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.inspectionActivityService = InspectionActivityService.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inspection_add_space, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int inspectionId = getActivity().getIntent().getIntExtra(INTENT_EXTRA_INSPECTION_ID_NAME, 0);
        Thread t = new Thread() {
            @Override
            public void run() {
                occInspectedSpaceHeavyList = inspectionActivityService.getOccInspectedSpaceHeavyListByInspectionId(inspectionId);
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inspection_inspected_space_rv = getActivity().findViewById(R.id.rv_inspection_inspected_space);
        inspection_inspected_space_rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        inspectedSpaceAdapter = new InspectedSpaceAdapter(occInspectedSpaceHeavyList, getActivity(), InspectionAddSpaceFragment.this);
        inspection_inspected_space_rv.setAdapter(inspectedSpaceAdapter);


        addSpaceBtn = view.findViewById(R.id.btn_add_space);
        cancelBtn = view.findViewById(R.id.btn_add_space_cancel);

        addSpaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inspectionSelectSpaceTypeFragment = new InspectionSelectSpaceTypeFragment();
                getFragmentManager().beginTransaction().replace(R.id.fl_inspection_container, inspectionSelectSpaceTypeFragment).commit();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InspectionActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });
    }

}