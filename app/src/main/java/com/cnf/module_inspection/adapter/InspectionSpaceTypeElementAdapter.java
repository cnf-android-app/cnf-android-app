package com.cnf.module_inspection.adapter;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.module_inspection.adapter.InspectionSpaceTypeElementAdapter.InspectionSpaceTypeElementViewHolder;
import com.cnf.module_inspection.entity.infra_heavy.OccChecklistSpaceTypeElementHeavy;

import java.util.List;

public class InspectionSpaceTypeElementAdapter extends RecyclerView.Adapter<InspectionSpaceTypeElementViewHolder> {

  private final Fragment fragment;
  private List<OccChecklistSpaceTypeElementHeavy> occChecklistSpaceTypeElementHeavyList;

  public InspectionSpaceTypeElementAdapter(Fragment fragment, List<OccChecklistSpaceTypeElementHeavy> occChecklistSpaceTypeElementHeavyList) {
    this.fragment = fragment;
    this.occChecklistSpaceTypeElementHeavyList = occChecklistSpaceTypeElementHeavyList;
  }

  @NonNull
  @Override
  public InspectionSpaceTypeElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new InspectionSpaceTypeElementViewHolder(LayoutInflater.from(fragment.getActivity()).inflate(R.layout.layout_inspection_occ_checklist_space_type_element_details_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull InspectionSpaceTypeElementViewHolder holder, int position) {
    OccChecklistSpaceTypeElementHeavy occChecklistSpaceTypeElementHeavy = occChecklistSpaceTypeElementHeavyList.get(position);
    String elementId = String.valueOf(occChecklistSpaceTypeElementHeavy.getCodeElement().getElementId());
    String ordSecNum = occChecklistSpaceTypeElementHeavy.getCodeElement().getOrdSecNum();
    String ordSubSecTitle = occChecklistSpaceTypeElementHeavy.getCodeElement().getOrdSubSecTitle();
    String ordTechnicalText = occChecklistSpaceTypeElementHeavy.getCodeElement().getOrdTechnicalText();

    holder.tvElementId.setText(elementId);
    holder.tvOrdSecNum.setText(ordSecNum);
    holder.tvOrdSubSecTitle.setText(ordSubSecTitle);
    holder.tvOrdTechnicalText.setText(ordTechnicalText);
  }


  @Override
  public int getItemCount() {
    return occChecklistSpaceTypeElementHeavyList.size();
  }

  static class InspectionSpaceTypeElementViewHolder extends RecyclerView.ViewHolder {

    TextView tvElementId, tvOrdSecNum, tvOrdSubSecTitle, tvOrdTechnicalText;

    public InspectionSpaceTypeElementViewHolder(@NonNull View itemView) {
      super(itemView);
      tvElementId = itemView.findViewById(R.id.tv_inspection_space_type_details_element_id_value);
      tvOrdSecNum = itemView.findViewById(R.id.tv_inspection_space_type_details_element_Ordinance_num_value);
      tvOrdSubSecTitle = itemView.findViewById(R.id.tv_inspection_space_type_details_element_Ordinance_title_value);
      tvOrdTechnicalText = itemView.findViewById(R.id.tv_inspection_space_type_details_element_Ordinance_text_value);
    }
  }

  public void setOccChecklistSpaceTypeElementHeavyList(List<OccChecklistSpaceTypeElementHeavy> occChecklistSpaceTypeElementHeavyList) {
    this.occChecklistSpaceTypeElementHeavyList = occChecklistSpaceTypeElementHeavyList;
  }
}
