package com.cnf.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cnf.InspectionActivity;
import com.cnf.adapter.InspectionSpaceTypeAdapter;
import com.cnf.R;
import com.cnf.domain.OccChecklistSpaceTypeHeavy;
import com.cnf.service.api.InspectionActivityService;

import java.util.List;

public class InspectionSelectSpaceTypeFragment extends Fragment {

    private Button cancelBtn;
    private Button backBtn;
    private List<OccChecklistSpaceTypeHeavy> occChecklistSpaceTypeHeavyList;
    private InspectionActivityService inspectionActivityService;
    private RecyclerView inspection_space_type_list_rv;

    public InspectionSelectSpaceTypeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inspection_select_space_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inspectionActivityService = InspectionActivityService.getInstance(getActivity());
        Thread t = new Thread() {
            @Override
            public void run() {
                occChecklistSpaceTypeHeavyList = inspectionActivityService.getOccChecklistSpaceTypeHeavyList();
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inspection_space_type_list_rv = getActivity().findViewById(R.id.rv_space_type_list);
        inspection_space_type_list_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        InspectionSpaceTypeAdapter inspectionSpaceTypeAdapter = new InspectionSpaceTypeAdapter(InspectionSelectSpaceTypeFragment.this, getActivity(), occChecklistSpaceTypeHeavyList);
        inspection_space_type_list_rv.setAdapter(inspectionSpaceTypeAdapter);

        backBtn = view.findViewById(R.id.btn_select_space_back);
        cancelBtn = view.findViewById(R.id.btn_select_space_cancel);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fl_inspection_container, new InspectionAddSpaceFragment()).commit();
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