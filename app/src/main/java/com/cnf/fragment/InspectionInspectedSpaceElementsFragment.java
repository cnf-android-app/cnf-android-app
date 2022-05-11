package com.cnf.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.app.Fragment;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnf.R;
import com.cnf.adapter.InspectionInspectedSpaceElementAdapter;
import com.cnf.domain.BlobBytes;
import com.cnf.domain.OccInspectedSpaceElementHeavy;
import com.cnf.service.InspectionActivityService;

import java.util.List;


public class InspectionInspectedSpaceElementsFragment extends Fragment {

    private List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList;
    private InspectionActivityService inspectionActivityService;
    private int inspectedSpaceId;
    private RecyclerView rv_inspection_inspected_space_elements;


    public InspectionInspectedSpaceElementsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public InspectionInspectedSpaceElementsFragment(int inspectedSpaceId) {
        this.inspectedSpaceId = inspectedSpaceId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inspection_inspected_space_elements, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Thread t = new Thread() {
            @Override
            public void run() {
                inspectionActivityService = new InspectionActivityService(getActivity());
                occInspectedSpaceElementHeavyList = inspectionActivityService.getOccInspectedSpaceElementHeavyList(inspectedSpaceId);
                for (OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy : occInspectedSpaceElementHeavyList) {
                    List<BlobBytes> blobBytesList = new InspectionActivityService(getActivity()).getBlobBytesList();
                    occInspectedSpaceElementHeavy.setBlobBytesList(blobBytesList);
                }
                System.out.println(occInspectedSpaceElementHeavyList);
                System.out.println("UPDATEINGINGINGIGNI");
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rv_inspection_inspected_space_elements = getActivity().findViewById(R.id.rv_inspection_inspected_space_elements);
        rv_inspection_inspected_space_elements.setLayoutManager(new LinearLayoutManager(getActivity()));
        InspectionInspectedSpaceElementAdapter inspectionInspectedSpaceElementAdapter = new InspectionInspectedSpaceElementAdapter(getActivity(), occInspectedSpaceElementHeavyList);
        rv_inspection_inspected_space_elements.setAdapter(inspectionInspectedSpaceElementAdapter);

    }
}