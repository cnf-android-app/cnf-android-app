package com.cnf.adapter;

import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_GUIDE_ID_NAME;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ID_NAME;

import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.domain.CodeElementGuide;
import com.cnf.domain.OccInspectedSpaceElementHeavy;
import com.cnf.domain.OccInspectionStatusEnum;
import com.cnf.fragment.InspectionInspectedSpaceElementFragment;
import com.cnf.service.occ.OccInspectionSpaceElementService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InspectionInspectedSpaceElementCategoryAdapter extends RecyclerView.Adapter<InspectionInspectedSpaceElementCategoryAdapter.LinearViewHolder> {

    private int inspectedSpaceId;
    private Fragment fragment;
    private Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> occInspectedSpaceElementHeavyMap;
    private OccInspectionSpaceElementService occInspectionSpaceElementService;
    private List<CodeElementGuide> codeElementGuideList;

    public InspectionInspectedSpaceElementCategoryAdapter(Activity context, Fragment fragment, Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> occInspectedSpaceElementHeavyMap, int inspectedSpaceId) {
        this.inspectedSpaceId = inspectedSpaceId;
        this.fragment = fragment;
        this.occInspectedSpaceElementHeavyMap = occInspectedSpaceElementHeavyMap;
        this.codeElementGuideList = new ArrayList<>(occInspectedSpaceElementHeavyMap.keySet());
        this.occInspectionSpaceElementService = OccInspectionSpaceElementService.getInstance(context);
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inspection_inspected_space_element_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {

        CodeElementGuide codeElementGuide = codeElementGuideList.get(position);
        List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList = occInspectedSpaceElementHeavyMap.get(codeElementGuide);
        Map<OccInspectionStatusEnum, List<OccInspectedSpaceElementHeavy>> elementStatusMap = occInspectionSpaceElementService.getElementStatusMap(occInspectedSpaceElementHeavyList);

        holder.categoryTV.setText(codeElementGuide.getCategory());
        holder.violationTV.setText(occInspectionSpaceElementService.getElementListFail(elementStatusMap).size() + "");
        holder.passTV.setText(occInspectionSpaceElementService.getElementListPass(elementStatusMap).size() + "");
        holder.notInspectedTV.setText(occInspectionSpaceElementService.getElementListNotIns(elementStatusMap).size() + "");

        holder.editBtn.setOnClickListener(v -> {
            fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_GUIDE_ID_NAME, codeElementGuide.getGuideentryid());
            InspectionInspectedSpaceElementFragment inspectionInspectedSpaceElementFragment = new InspectionInspectedSpaceElementFragment();
            fragment.getFragmentManager().beginTransaction().replace(R.id.fl_inspection_container, inspectionInspectedSpaceElementFragment).commit();
        });
    }

    @Override
    public int getItemCount() {
        return codeElementGuideList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTV, violationTV, passTV, notInspectedTV;
        ImageButton editBtn;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTV = itemView.findViewById(R.id.tv_inspection_inspected_space_element_category_item_title);
            violationTV = itemView.findViewById(R.id.tv_inspection_inspected_space_element_category_item_violation_value);
            passTV = itemView.findViewById(R.id.tv_inspection_inspected_space_element_category_item_pass_value);
            notInspectedTV = itemView.findViewById(R.id.tv_inspection_inspected_space_element_category_item_not_inspected_value);
            editBtn = itemView.findViewById(R.id.btn_inspection_inspected_space_element_category_item_edit);
        }
    }
}
