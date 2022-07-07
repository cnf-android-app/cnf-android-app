package com.cnf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.adapter.InspectionOccChecklistSpaceTypeAdapter.OccChecklistSpaceTypeHolder;
import com.cnf.domain.infra.OccCheckList;
import com.cnf.domain.infra.OccChecklistSpaceType;
import com.cnf.domain.infra.OccSpaceType;
import com.cnf.domain.infra_heavy.OccChecklistSpaceTypeHeavy;
import com.cnf.fragment.InspectionSelectOccLocationDescriptionFragment;
import com.cnf.fragment.InspectionSelectOccChecklistSpaceTypeFragment;
import com.cnf.fragment.InspectionSpaceTypeDetailsFragment;

import java.util.List;
import java.util.Locale;

public class InspectionOccChecklistSpaceTypeAdapter extends RecyclerView.Adapter<OccChecklistSpaceTypeHolder> {

  private InspectionSelectOccChecklistSpaceTypeFragment fragment;
  private Context context;
  private List<OccChecklistSpaceTypeHeavy> occChecklistSpaceTypeHeavyList;
  private InspectionSpaceTypeDetailsFragment inspectionSpaceTypeDetailsFragment;
  private InspectionSelectOccLocationDescriptionFragment inspectionSelectOccLocationDescriptionFragment;

  public InspectionOccChecklistSpaceTypeAdapter(InspectionSelectOccChecklistSpaceTypeFragment fragment, Context context, List<OccChecklistSpaceTypeHeavy> occChecklistSpaceTypeHeavyList) {
    this.fragment = fragment;
    this.context = context;
    this.occChecklistSpaceTypeHeavyList = occChecklistSpaceTypeHeavyList;
  }

  @NonNull
  @Override
  public OccChecklistSpaceTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new OccChecklistSpaceTypeHolder(LayoutInflater.from(context).inflate(R.layout.layout_inspection_occ_checklist_space_type_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull OccChecklistSpaceTypeHolder holder, int position) {
    OccChecklistSpaceTypeHeavy occChecklistSpaceTypeHeavy = occChecklistSpaceTypeHeavyList.get(position);
    OccChecklistSpaceType occChecklistSpaceType = occChecklistSpaceTypeHeavy.getOccChecklistSpaceType();
    OccCheckList occCheckList = occChecklistSpaceTypeHeavy.getOccCheckList();
    OccSpaceType occSpaceType = occChecklistSpaceTypeHeavy.getOccSpaceType();

    if (occChecklistSpaceType == null || occCheckList == null || occSpaceType == null) {
      return;
    }

    String occChecklistSpaceTypeTitle =  occSpaceType.getSpaceTitle().toUpperCase(Locale.ROOT);
    String occChecklistSpaceTypeDescription = occSpaceType.getDescription().toUpperCase(Locale.ROOT);
    String occChecklistSpaceTypeId = String.valueOf(occChecklistSpaceType.getChecklistSpaceTypeId());
    String occChecklistSpaceTypeChecklistTitle = occCheckList.getTitle();
    String occChecklistSpaceTypeIsRequired = occChecklistSpaceType.getRequired() ? "True" : "False";

    holder.tvOccChecklistSpaceTypeTitle.setText(occChecklistSpaceTypeTitle);
    holder.tvOccChecklistSpaceTypeDescription.setText(occChecklistSpaceTypeDescription);
    holder.tvOccChecklistSpaceTypeId.setText(occChecklistSpaceTypeId);
    holder.tvOccChecklistSpaceTypeChecklistTitle.setText(occChecklistSpaceTypeChecklistTitle);
    holder.tvOccChecklistSpaceTypeIsRequired.setText(occChecklistSpaceTypeIsRequired);

    holder.btnDetail.setOnClickListener(view -> {
      inspectionSpaceTypeDetailsFragment = new InspectionSpaceTypeDetailsFragment(occChecklistSpaceType.getChecklistSpaceTypeId());
      fragment.getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSpaceTypeDetailsFragment).commit();
    });

    holder.btnSelect.setOnClickListener(view -> {
      inspectionSelectOccLocationDescriptionFragment = new InspectionSelectOccLocationDescriptionFragment(occChecklistSpaceType.getChecklistSpaceTypeId(), null);
      fragment.getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccLocationDescriptionFragment).commit();
    });
  }

  @Override
  public int getItemCount() {
    return occChecklistSpaceTypeHeavyList.size();
  }

  class OccChecklistSpaceTypeHolder extends RecyclerView.ViewHolder {

    TextView tvOccChecklistSpaceTypeTitle, tvOccChecklistSpaceTypeId, tvOccChecklistSpaceTypeChecklistTitle, tvOccChecklistSpaceTypeIsRequired, tvOccChecklistSpaceTypeDescription;
    Button btnDetail, btnSelect;

    public OccChecklistSpaceTypeHolder(@NonNull View itemView) {
      super(itemView);
      tvOccChecklistSpaceTypeTitle = itemView.findViewById(R.id.tv_inspection_occ_checklist_space_type_title);
      tvOccChecklistSpaceTypeId = itemView.findViewById(R.id.tv_inspection_occ_checklist_space_type_id_value);
      tvOccChecklistSpaceTypeChecklistTitle = itemView.findViewById(R.id.tv_inspection_occ_checklist_space_type_checklist_title_value);
      tvOccChecklistSpaceTypeIsRequired = itemView.findViewById(R.id.tv_inspection_occ_checklist_space_type_required_value);
      tvOccChecklistSpaceTypeDescription = itemView.findViewById(R.id.tv_inspection_occ_checklist_space_type_description);
      btnDetail = itemView.findViewById(R.id.btn_inspection_occ_checklist_space_type_details);
      btnSelect = itemView.findViewById(R.id.btn_inspection_occ_checklist_space_type_select);
    }
  }
}
