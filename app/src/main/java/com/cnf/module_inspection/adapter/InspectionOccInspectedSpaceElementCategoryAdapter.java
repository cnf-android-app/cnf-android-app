package com.cnf.module_inspection.adapter;

import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_GUIDE_ID_NAME;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.module_inspection.adapter.InspectionOccInspectedSpaceElementCategoryAdapter.OccInspectedSpaceElementCategoryHolder;
import com.cnf.module_inspection.entity.OccInspectedSpaceElementHeavy;
import com.cnf.module_inspection.entity.OccInspectionStatusEnum;
import com.cnf.module_inspection.entity.infra.CodeElementGuide;
import com.cnf.module_inspection.fragment.InspectionEditOccInspectedSpaceElementFragment;
import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InspectionOccInspectedSpaceElementCategoryAdapter extends RecyclerView.Adapter<OccInspectedSpaceElementCategoryHolder> {

    private Fragment fragment;
    private Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> occInspectedSpaceElementHeavyMap;
    private OccInspectionSpaceElementRepository occInspectionSpaceElementRepository;
    private List<CodeElementGuide> codeElementGuideList;
    private boolean isFinished;

    public InspectionOccInspectedSpaceElementCategoryAdapter( Fragment fragment, Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> occInspectedSpaceElementHeavyMap, boolean isFinished) {
        this.fragment = fragment;
        this.occInspectedSpaceElementHeavyMap = occInspectedSpaceElementHeavyMap;
        this.isFinished = isFinished;
        this.occInspectionSpaceElementRepository = OccInspectionSpaceElementRepository.getInstance(fragment.getContext());
        this.codeElementGuideList = new ArrayList<>(occInspectedSpaceElementHeavyMap.keySet());
    }

    @NonNull
    @Override
    public OccInspectedSpaceElementCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OccInspectedSpaceElementCategoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inspection_occ_inspected_space_element_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OccInspectedSpaceElementCategoryHolder holder, int position) {
        CodeElementGuide codeElementGuide = codeElementGuideList.get(position);

        List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList = occInspectedSpaceElementHeavyMap.get(codeElementGuide);
        Map<OccInspectionStatusEnum, List<OccInspectedSpaceElementHeavy>> elementStatusMap = occInspectionSpaceElementRepository.getElementStatusMap(occInspectedSpaceElementHeavyList);

        // TODO How to build the title of category
        holder.tvCategory.setText(codeElementGuide.getCategory());

        if (isFinished) {
            holder.rlOccInspectedSpaceElementCategory.setBackgroundResource(R.drawable.layout_bg_blue);
        }

        holder.tvViolationNum.setText(occInspectionSpaceElementRepository.getElementListFail(elementStatusMap).size() + "");
        holder.tvPassNum.setText(occInspectionSpaceElementRepository.getElementListPass(elementStatusMap).size() + "");
        holder.tvNotInspectedNum.setText(occInspectionSpaceElementRepository.getElementListNotIns(elementStatusMap).size() + "");

        holder.btnEdit.setOnClickListener(v -> {
            fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_GUIDE_ID_NAME, codeElementGuide.getGuideEntryId());
            InspectionEditOccInspectedSpaceElementFragment inspectionEditOccInspectedSpaceElementFragment = new InspectionEditOccInspectedSpaceElementFragment();
            fragment.getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionEditOccInspectedSpaceElementFragment).commit();
        });
    }

    @Override
    public int getItemCount() {
        return codeElementGuideList.size();
    }

    class OccInspectedSpaceElementCategoryHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvViolationNum, tvPassNum, tvNotInspectedNum;
        ImageButton btnEdit;
        RelativeLayout rlOccInspectedSpaceElementCategory;

        public OccInspectedSpaceElementCategoryHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tv_inspection_inspected_space_element_category_item_title);
            tvViolationNum = itemView.findViewById(R.id.tv_inspection_inspected_space_element_category_item_violation_value);
            tvPassNum = itemView.findViewById(R.id.tv_inspection_inspected_space_element_category_item_pass_value);
            tvNotInspectedNum = itemView.findViewById(R.id.tv_inspection_inspected_space_element_category_item_not_inspected_value);
            btnEdit = itemView.findViewById(R.id.btn_inspection_inspected_space_element_category_item_edit);
            rlOccInspectedSpaceElementCategory = itemView.findViewById(R.id.rl_inspection_inspected_space_element_category_item);
        }
    }
}
