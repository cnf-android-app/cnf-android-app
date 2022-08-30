package com.cnf.module_inspection.adapter;

import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_GUIDE_ID_NAME;

import androidx.fragment.app.Fragment;
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

  private final Fragment fragment;
  private Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> occInspectedSpaceElementHeavyMap;
  private List<CodeElementGuide> codeElementGuideList;

  public InspectionOccInspectedSpaceElementCategoryAdapter(Fragment fragment, Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> occInspectedSpaceElementHeavyMap) {
    this.fragment = fragment;
    this.occInspectedSpaceElementHeavyMap = occInspectedSpaceElementHeavyMap;
    this.codeElementGuideList = new ArrayList<>(occInspectedSpaceElementHeavyMap.keySet());
  }

  @NonNull
  @Override
  public OccInspectedSpaceElementCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new OccInspectedSpaceElementCategoryHolder(LayoutInflater.from(this.fragment.getActivity()).inflate(R.layout.layout_inspection_occ_inspected_space_element_category_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull OccInspectedSpaceElementCategoryHolder holder, int position) {

    CodeElementGuide codeElementGuide = codeElementGuideList.get(position);
    if (codeElementGuide == null) {
      return;
    }
    OccInspectionSpaceElementRepository occInspectionSpaceElementRepository = OccInspectionSpaceElementRepository.getInstance(fragment.getContext());
    List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList = occInspectedSpaceElementHeavyMap.get(codeElementGuide);
    Map<OccInspectionStatusEnum, List<OccInspectedSpaceElementHeavy>> elementStatusMap = occInspectionSpaceElementRepository.getElementStatusMap(occInspectedSpaceElementHeavyList);

    String category = codeElementGuide.getCategory();
    List<OccInspectedSpaceElementHeavy> elementListFail = occInspectionSpaceElementRepository.getElementListFail(elementStatusMap);
    List<OccInspectedSpaceElementHeavy> elementListPass = occInspectionSpaceElementRepository.getElementListPass(elementStatusMap);
    List<OccInspectedSpaceElementHeavy> elementListNotIns = occInspectionSpaceElementRepository.getElementListNotIns(elementStatusMap);

    if (category == null
        || elementStatusMap == null
        || elementStatusMap.size() == 0
    ) {
      return;
    }

    holder.tvCategory.setText(category);
    holder.tvViolationNum.setText(String.valueOf(elementListFail.size()));
    holder.tvPassNum.setText(String.valueOf(elementListPass.size()));
    holder.tvNotInspectedNum.setText(String.valueOf(elementListNotIns.size()));

    holder.btnEdit.setOnClickListener(v -> {
      if (fragment.getActivity() == null) {
        return;
      }
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_GUIDE_ID_NAME, codeElementGuide.getGuideEntryId());
      InspectionEditOccInspectedSpaceElementFragment inspectionEditOccInspectedSpaceElementFragment = new InspectionEditOccInspectedSpaceElementFragment();
      fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionEditOccInspectedSpaceElementFragment).commit();
    });
  }

  @Override
  public int getItemCount() {
    return codeElementGuideList.size();
  }

  static class OccInspectedSpaceElementCategoryHolder extends RecyclerView.ViewHolder {

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

  public void setOccInspectedSpaceElementHeavyMap(
      Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> occInspectedSpaceElementHeavyMap) {
    this.occInspectedSpaceElementHeavyMap = occInspectedSpaceElementHeavyMap;
  }

  public void setCodeElementGuideList(List<CodeElementGuide> codeElementGuideList) {
    this.codeElementGuideList = codeElementGuideList;
  }
}
