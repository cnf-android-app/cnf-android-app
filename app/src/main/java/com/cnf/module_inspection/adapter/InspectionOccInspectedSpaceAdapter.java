package com.cnf.module_inspection.adapter;

import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ID_NAME;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.module_inspection.adapter.InspectionOccInspectedSpaceAdapter.OccInspectedSpaceHolder;
import com.cnf.module_inspection.entity.OccInspectedSpace;
import com.cnf.module_inspection.entity.OccInspectedSpaceElement;
import com.cnf.module_inspection.entity.OccInspectedSpaceHeavy;
import com.cnf.module_inspection.entity.OccInspectedSpaceStatus;
import com.cnf.module_inspection.entity.infra.OccChecklistSpaceType;
import com.cnf.module_inspection.entity.infra.OccLocationDescription;
import com.cnf.module_inspection.entity.infra.OccSpaceType;
import com.cnf.module_inspection.fragment.InspectionSelectOccInspectedSpaceElementCategoryFragment;
import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository;

import java.util.List;

public class InspectionOccInspectedSpaceAdapter extends RecyclerView.Adapter<OccInspectedSpaceHolder> {

  private List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList;
  private final Fragment fragment;

  private final static String IS_REQUIRED = "(Required)";
  private final static String NOT_REQUIRED = "(Not Required)";

  public InspectionOccInspectedSpaceAdapter(List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList, Fragment fragment) {
    this.occInspectedSpaceHeavyList = occInspectedSpaceHeavyList;
    this.fragment = fragment;
  }

  @NonNull
  @Override
  public OccInspectedSpaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new OccInspectedSpaceHolder(LayoutInflater.from(this.fragment.getActivity()).inflate(R.layout.layout_inspected_space_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull OccInspectedSpaceHolder holder, int position) {

    OccInspectedSpaceHeavy occInspectedSpaceHeavy = this.occInspectedSpaceHeavyList.get(position);
    if (occInspectedSpaceHeavy == null) {
      return;
    }

    OccInspectedSpace occInspectedSpace = occInspectedSpaceHeavy.getOccInspectedSpace();
    OccChecklistSpaceType occChecklistSpaceType = occInspectedSpaceHeavy.getOccChecklistSpaceType();
    List<OccInspectedSpaceElement> occInspectedSpaceElementList = occInspectedSpaceHeavy.getOccInspectedSpaceElementList();
    OccSpaceType occSpaceType = occInspectedSpaceHeavy.getOccSpaceType();
    OccLocationDescription occLocationDescription = occInspectedSpaceHeavy.getOccLocationDescription();

    if (occInspectedSpace == null || occInspectedSpaceElementList == null) {
      return;
    }

    String completeStatus = calculateStatus(occInspectedSpaceHeavy);

    String inspectedSpaceTypeTile = null;
    String inspectedLocationDescription = null;
    String requiredStatus = null;

    if (occChecklistSpaceType != null) {
      inspectedSpaceTypeTile = occSpaceType.getSpaceTitle();
    }

    if (occLocationDescription != null) {
      inspectedLocationDescription = occLocationDescription.getDescription();
    }

    if (occChecklistSpaceType.getRequired() != null) {
      requiredStatus = occChecklistSpaceType.getRequired() ? IS_REQUIRED : NOT_REQUIRED;
    }

    holder.tvOccInspectedSpaceCompleteStatus.setText(completeStatus);
    holder.tvOccInspectedSpaceTypeTitle.setText(inspectedSpaceTypeTile);
    holder.tvOccInspectedSpaceLocationDescription.setText(inspectedLocationDescription);
    holder.tvOccInspectedSpaceIsRequired.setText(requiredStatus);

    holder.clOccInspectedSpaceItem.setOnClickListener(view -> {
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_INSPECTED_SPACE_ID_NAME, occInspectedSpace.getInspectedSpaceId());
      InspectionSelectOccInspectedSpaceElementCategoryFragment inspectionSelectOccInspectedSpaceElementCategoryFragment = new InspectionSelectOccInspectedSpaceElementCategoryFragment();
      fragment.getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccInspectedSpaceElementCategoryFragment).commit();
    });
  }

  @Override
  public int getItemCount() {
    return occInspectedSpaceHeavyList.size();
  }

  static class OccInspectedSpaceHolder extends RecyclerView.ViewHolder {

    TextView tvOccInspectedSpaceTypeTitle, tvOccInspectedSpaceLocationDescription, tvOccInspectedSpaceCompleteStatus, tvOccInspectedSpaceIsRequired;
    ConstraintLayout clOccInspectedSpaceItem;

    public OccInspectedSpaceHolder(@NonNull View itemView) {
      super(itemView);
      tvOccInspectedSpaceTypeTitle = itemView.findViewById(R.id.tv_occ_inspected_space_item_title);
      tvOccInspectedSpaceLocationDescription = itemView.findViewById(R.id.tv_occ_inspected_space_item_location_description);
      tvOccInspectedSpaceCompleteStatus = itemView.findViewById(R.id.tv_occ_inspected_space_item_status);
      tvOccInspectedSpaceIsRequired = itemView.findViewById(R.id.tv_occ_inspected_space_item_is_required);
      clOccInspectedSpaceItem = itemView.findViewById(R.id.cl_occ_inspected_space_item);
    }
  }

  public void setOccInspectedSpaceHeavyList(List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList) {
    this.occInspectedSpaceHeavyList = occInspectedSpaceHeavyList;
  }

  private String calculateStatus(OccInspectedSpaceHeavy occInspectedSpaceHeavy) {
    OccInspectedSpaceStatus occInspectedSpaceStatus = OccInspectionSpaceElementRepository.getInstance(fragment.getActivity()).getOccInspectedSpaceStatus(occInspectedSpaceHeavy);
    int totalInspectedSpaceElement = occInspectedSpaceStatus.getFinishedInspectedSpaceElementCount() + occInspectedSpaceStatus.getUnFinishInspectedSpaceElementCount();
    int completedInspectedSpaceElement = occInspectedSpaceStatus.getFinishedInspectedSpaceElementCount();
    return String.format("%s / %s", completedInspectedSpaceElement, totalInspectedSpaceElement);
  }
}
