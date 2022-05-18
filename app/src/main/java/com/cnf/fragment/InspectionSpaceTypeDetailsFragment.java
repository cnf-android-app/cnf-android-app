package com.cnf.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;


import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.InspectionActivity;
import com.cnf.adapter.InspectionSpaceTypeElementAdapter;
import com.cnf.R;
import com.cnf.domain.OccChecklistSpaceTypeElementHeavyDetails;
import com.cnf.service.api.InspectionActivityService;

import java.util.List;


public class InspectionSpaceTypeDetailsFragment extends Fragment {

    private List<OccChecklistSpaceTypeElementHeavyDetails> occChecklistSpaceTypeElementHeavyDetailsList;
    private InspectionActivityService inspectionActivityService;
    private int CSTId;
    private RecyclerView inspection_space_type_details_list_rv;


    public InspectionSpaceTypeDetailsFragment() {
    }

    @SuppressLint("ValidFragment")
    public InspectionSpaceTypeDetailsFragment(int CSTId) {
        this.CSTId = CSTId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inspection_space_type_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Thread t = new Thread() {
            @Override
            public void run() {
                inspectionActivityService =  InspectionActivityService.getInstance(getActivity());
                occChecklistSpaceTypeElementHeavyDetailsList = inspectionActivityService.getOccChecklistSpaceTypeElementHeavyDetailsList(CSTId);
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        inspection_space_type_details_list_rv = getActivity().findViewById(R.id.rv_space_type_elements_detail);
        inspection_space_type_details_list_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        InspectionSpaceTypeElementAdapter inspectionSpaceTypeElementAdapter = new InspectionSpaceTypeElementAdapter(getActivity(), occChecklistSpaceTypeElementHeavyDetailsList);
        inspection_space_type_details_list_rv.setAdapter(inspectionSpaceTypeElementAdapter);
        Button backBtn = getActivity().findViewById(R.id.btn_select_space_details_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getFragmentManager().beginTransaction().replace(R.id.fl_inspection_container, new InspectionSelectSpaceTypeFragment()).commit();
            }
        });

        Button cancelBtn = getActivity().findViewById(R.id.btn_select_space_details_cancel);
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