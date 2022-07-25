package com.cnf.adapter;

import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ID_NAME;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.adapter.InspectionOccInspectedSpaceAdapter.OccInspectedSpaceHolder;
import com.cnf.domain.OccInspectedSpace;
import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.domain.OccInspectedSpaceHeavy;
import com.cnf.domain.infra.OccChecklistSpaceType;
import com.cnf.domain.infra.OccLocationDescription;
import com.cnf.domain.infra.OccSpaceType;
import com.cnf.fragment.InspectionSelectOccInspectedSpaceElementCategoryFragment;
import com.cnf.fragment.InspectionSelectOccLocationDescriptionFragment;
import com.cnf.service.local.OccInspectionSpaceElementService;

import java.util.List;

public class InspectionOccInspectedSpaceAdapter extends RecyclerView.Adapter<OccInspectedSpaceHolder> {

  private List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList;
  private Context context;
  private Fragment fragment;
  private boolean isFinished;

  private OccInspectionSpaceElementService occInspectionSpaceElementService;

  private final String FINISHED_STATUS = "Finished";
  private final String UNFINISH_STATUS = "UnFinish";
  private final String IS_REQUIRED = "(Required)";
  private final String NOT_REQUIRED = "(Not Required)";

  public InspectionOccInspectedSpaceAdapter(List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList, Context context, Fragment fragment) {
    this.occInspectedSpaceHeavyList = occInspectedSpaceHeavyList;
    this.context = context;
    this.fragment = fragment;
    this.occInspectionSpaceElementService = OccInspectionSpaceElementService.getInstance();
  }

  @NonNull
  @Override
  public OccInspectedSpaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new OccInspectedSpaceHolder(LayoutInflater.from(context).inflate(R.layout.layout_inspected_space_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull OccInspectedSpaceHolder holder, int position) {

    OccInspectedSpaceHeavy occInspectedSpaceHeavy = occInspectedSpaceHeavyList.get(position);

    OccInspectedSpace occInspectedSpace = occInspectedSpaceHeavy.getOccInspectedSpace();
    OccChecklistSpaceType occChecklistSpaceType = occInspectedSpaceHeavy.getOccChecklistSpaceType();
    OccSpaceType occSpaceType = occInspectedSpaceHeavy.getOccSpaceType();
    OccLocationDescription occLocationDescription = occInspectedSpaceHeavy.getOccLocationDescription();

    List<OccInspectedSpaceElement> occInspectedSpaceElementList = occInspectedSpaceHeavy.getOccInspectedSpaceElementList();

    if (occInspectedSpaceElementList == null || occInspectedSpace == null) {
      return;
    }

    String inspectedSpaceTypeTile = null;
    String inspectedLocationDescription = null;
    String completeStatus;

//    if (occInspectionSpaceElementService.isAllInspectedSpaceElementComplete(occInspectedSpaceElementList)) {
//      completeStatus = FINISHED_STATUS;
//      holder.clOccInspectedSpaceItem.setBackgroundResource(R.drawable.layout_bg_blue);
//    } else {
//      completeStatus = UNFINISH_STATUS;
//    }

    if (isFinished) {
      completeStatus = FINISHED_STATUS;
      holder.clOccInspectedSpaceItem.setBackgroundResource(R.drawable.layout_bg_blue);
    } else {
      completeStatus = UNFINISH_STATUS;
    }

    if (occChecklistSpaceType != null) {
      inspectedSpaceTypeTile = occSpaceType.getSpaceTitle();
    }

    if (occLocationDescription != null) {
      inspectedLocationDescription = occLocationDescription.getDescription();
    }

    holder.tvOccInspectedSpaceCompleteStatus.setText(completeStatus);
    holder.tvOccInspectedSpaceTypeTitle.setText(inspectedSpaceTypeTile);
    holder.tvOccInspectedSpaceLocationDescription.setText(inspectedLocationDescription);
    holder.tvOccInspectedSpaceIsRequired.setText(occChecklistSpaceType.getRequired() ? IS_REQUIRED: NOT_REQUIRED);

    holder.clOccInspectedSpaceItem.setOnClickListener(view -> {
      if (occInspectedSpace.getOccLocationDescriptionId() == null) {
        InspectionSelectOccLocationDescriptionFragment inspectionSelectOccLocationDescriptionFragment = new InspectionSelectOccLocationDescriptionFragment(null,
            occInspectedSpace.getInspectedSpaceId());
        fragment.getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccLocationDescriptionFragment).commit();
      } else {
        fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_INSPECTED_SPACE_ID_NAME, occInspectedSpace.getInspectedSpaceId());
        InspectionSelectOccInspectedSpaceElementCategoryFragment inspectionSelectOccInspectedSpaceElementCategoryFragment = new InspectionSelectOccInspectedSpaceElementCategoryFragment();
        fragment.getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccInspectedSpaceElementCategoryFragment).commit();
      }

    });
  }

  @Override
  public int getItemCount() {
    return occInspectedSpaceHeavyList.size();
  }

  class OccInspectedSpaceHolder extends RecyclerView.ViewHolder {

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

  public boolean isFinished() {
    return isFinished;
  }

  public void setFinished(boolean finished) {
    isFinished = finished;
  }
}
