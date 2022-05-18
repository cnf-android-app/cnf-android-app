package com.cnf.fragment;

import static android.app.Activity.RESULT_OK;

import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_GUIDE_ID_NAME;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ID_NAME;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cnf.InspectionActivity;
import com.cnf.R;
import com.cnf.adapter.InspectionInspectedSpaceElementAdapter;
import com.cnf.domain.BlobBytes;
import com.cnf.domain.OccInspectedSpaceElementHeavy;
import com.cnf.service.api.InspectionActivityService;
import com.cnf.service.occ.OccInspectionSpaceElementService;

import java.util.List;


public class InspectionInspectedSpaceElementFragment extends Fragment {


    private int inspectedSpaceId;
    private int codeElementGuideId;
    private OccInspectionSpaceElementService occInspectionSpaceElementService;
    private RecyclerView rv_inspection_inspected_space_elements;
    private List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList;
    private InspectionInspectedSpaceElementAdapter inspectionInspectedSpaceElementAdapter;

    private Button backBtn;
    private Button cancelBtn;


    public InspectionInspectedSpaceElementFragment() {
        this.occInspectionSpaceElementService = OccInspectionSpaceElementService.getInstance(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inspection_inspected_space_element, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.inspectedSpaceId = this.getActivity().getIntent().getIntExtra(INTENT_EXTRA_INSPECTED_SPACE_ID_NAME, 0);
        this.codeElementGuideId = this.getActivity().getIntent().getIntExtra(INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_GUIDE_ID_NAME, 0);
        Thread t = new Thread() {
            @Override
            public void run() {
                occInspectedSpaceElementHeavyList = occInspectionSpaceElementService.getOccInspectedSpaceElementHeavyList(codeElementGuideId, inspectedSpaceId);
                occInspectedSpaceElementHeavyList = occInspectionSpaceElementService.configureOccInspectedSpaceElementListStatus(occInspectedSpaceElementHeavyList);
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
        inspectionInspectedSpaceElementAdapter = new InspectionInspectedSpaceElementAdapter(getActivity(), this, occInspectedSpaceElementHeavyList);
        rv_inspection_inspected_space_elements.setAdapter(inspectionInspectedSpaceElementAdapter);

        backBtn = getActivity().findViewById(R.id.btn_inspection_inspected_space_elements_back);
        cancelBtn = getActivity().findViewById(R.id.btn_inspection_inspected_space_elements_cancel);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InspectionActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fl_inspection_container, new InspectionInspectedSpaceElementCategoryFragment()).commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Thread t = new Thread() {
            @Override
            public void run() {
                for (OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy : occInspectedSpaceElementHeavyList) {
                    List<BlobBytes> blobBytesList = OccInspectionSpaceElementService.getInstance(getActivity()).getInspectedPhotoBlobBytesList(occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getInspectedspaceelementid());
                    occInspectedSpaceElementHeavy.setBlobBytesList(blobBytesList);
                }
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inspectionInspectedSpaceElementAdapter.setOccInspectedSpaceElementHeavyList(occInspectedSpaceElementHeavyList);
        rv_inspection_inspected_space_elements.getAdapter().notifyDataSetChanged();
    }

}