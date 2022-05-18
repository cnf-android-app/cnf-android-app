package com.cnf.fragment;

import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ID_NAME;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.InspectionActivity;
import com.cnf.R;
import com.cnf.adapter.InspectionInspectedSpaceElementCategoryAdapter;
import com.cnf.domain.CodeElementGuide;
import com.cnf.domain.OccInspectedSpaceElementHeavy;
import com.cnf.service.occ.OccInspectionSpaceElementService;

import java.util.List;
import java.util.Map;

public class InspectionInspectedSpaceElementCategoryFragment extends Fragment {

    private Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> occInspectedSpaceElementHeavyMap;
    private RecyclerView rv_inspection_inspected_space_element_category;
    private int inspectedSpaceId;

    private Button cancelBtn;
    private Button backBtn;

    public InspectionInspectedSpaceElementCategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inspection_inspected_space_element_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inspectedSpaceId = this.getActivity().getIntent().getIntExtra(INTENT_EXTRA_INSPECTED_SPACE_ID_NAME, 0);
        Thread t = new Thread() {
            @Override
            public void run() {
                occInspectedSpaceElementHeavyMap = OccInspectionSpaceElementService.getInstance(getActivity()).getOccInspectedSpaceElementHeavyMap(inspectedSpaceId);
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rv_inspection_inspected_space_element_category = getActivity().findViewById(R.id.rv_inspection_inspected_space_element_category);
        rv_inspection_inspected_space_element_category.setLayoutManager(new LinearLayoutManager(getActivity()));
        InspectionInspectedSpaceElementCategoryAdapter inspectionInspectedSpaceElementCategoryAdapter = new InspectionInspectedSpaceElementCategoryAdapter(getActivity(), this, occInspectedSpaceElementHeavyMap, inspectedSpaceId);
        rv_inspection_inspected_space_element_category.setAdapter(inspectionInspectedSpaceElementCategoryAdapter);

        cancelBtn = getActivity().findViewById(R.id.btn_inspection_inspected_space_element_category_cancel);
        backBtn = getActivity().findViewById(R.id.btn_inspection_inspected_space_element_category_back);

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
                getFragmentManager().beginTransaction().replace(R.id.fl_inspection_container, new InspectionAddSpaceFragment()).commit();
            }
        });
    }
}