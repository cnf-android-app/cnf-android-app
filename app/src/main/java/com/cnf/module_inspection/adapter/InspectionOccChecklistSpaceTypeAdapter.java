package com.cnf.module_inspection.adapter;

import static com.cnf.utils.AppConstants.FRAGMENT_INSPECTION_OCC_CHECKLIST_SPACE_TYPE_DETAILS;
import static com.cnf.utils.AppConstants.FRAGMENT_INSPECTION_OCC_LOCATION_DESCRIPTION;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_CHECKLIST_SPACE_TYPE_ID_KEY;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.module_inspection.adapter.InspectionOccChecklistSpaceTypeAdapter.OccChecklistSpaceTypeHolder;
import com.cnf.module_inspection.entity.infra.OccCheckList;
import com.cnf.module_inspection.entity.infra.OccChecklistSpaceType;
import com.cnf.module_inspection.entity.infra.OccSpaceType;
import com.cnf.module_inspection.entity.infra_heavy.OccChecklistSpaceTypeHeavy;
import com.cnf.module_inspection.fragment.InspectionSelectOccLocationDescriptionFragment;
import com.cnf.module_inspection.fragment.InspectionSpaceTypeDetailsFragment;

import java.util.List;
import java.util.Locale;

public class InspectionOccChecklistSpaceTypeAdapter extends RecyclerView.Adapter<OccChecklistSpaceTypeHolder> {

  private final Fragment fragment;
  private List<OccChecklistSpaceTypeHeavy> occChecklistSpaceTypeHeavyList;

  public InspectionOccChecklistSpaceTypeAdapter(Fragment fragment, List<OccChecklistSpaceTypeHeavy> occChecklistSpaceTypeHeavyList) {
    this.fragment = fragment;
    this.occChecklistSpaceTypeHeavyList = occChecklistSpaceTypeHeavyList;
  }

  @NonNull
  @Override
  public OccChecklistSpaceTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new OccChecklistSpaceTypeHolder(LayoutInflater.from(fragment.getActivity()).inflate(R.layout.layout_inspection_occ_checklist_space_type_item, parent, false));
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

    String occChecklistSpaceTypeTitle = occSpaceType.getSpaceTitle().toUpperCase(Locale.ROOT);
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
      if (fragment.getActivity() == null) {
        return;
      }
      Fragment f = fragment.getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_INSPECTION_OCC_CHECKLIST_SPACE_TYPE_DETAILS);
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_INSPECTION_CHECKLIST_SPACE_TYPE_ID_KEY, occChecklistSpaceType.getChecklistSpaceTypeId());
      if (f == null) {
        f = new InspectionSpaceTypeDetailsFragment();
      }
      fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, f, FRAGMENT_INSPECTION_OCC_CHECKLIST_SPACE_TYPE_DETAILS).commit();
    });

    holder.btnSelect.setOnClickListener(view -> {
      if (fragment.getActivity() == null) {
        return;
      }
      Fragment f = fragment.getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_INSPECTION_OCC_LOCATION_DESCRIPTION);
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_INSPECTION_CHECKLIST_SPACE_TYPE_ID_KEY, occChecklistSpaceType.getChecklistSpaceTypeId());
      if (f == null) {
        f = new InspectionSelectOccLocationDescriptionFragment();
      }
      fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, f, FRAGMENT_INSPECTION_OCC_LOCATION_DESCRIPTION).commit();
    });
  }

  @Override
  public int getItemCount() {
    return occChecklistSpaceTypeHeavyList.size();
  }

  static class OccChecklistSpaceTypeHolder extends RecyclerView.ViewHolder {

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

  public void setOccChecklistSpaceTypeHeavyList(List<OccChecklistSpaceTypeHeavy> occChecklistSpaceTypeHeavyList) {
    this.occChecklistSpaceTypeHeavyList = occChecklistSpaceTypeHeavyList;
  }
}
