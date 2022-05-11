package com.cnf.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.domain.OccChecklistSpaceTypeHeavy;
import com.cnf.fragment.InspectionSelectLocationDescriptionFragment;
import com.cnf.fragment.InspectionSelectSpaceTypeFragment;
import com.cnf.fragment.InspectionSpaceTypeDetailsFragment;

import java.util.List;

public class InspectionSpaceTypeAdapter extends RecyclerView.Adapter<InspectionSpaceTypeAdapter.LinearViewHolder> {

    private InspectionSelectSpaceTypeFragment fragment;
    private Context context;
    private List<OccChecklistSpaceTypeHeavy> occChecklistSpaceTypeHeavyList;
    private InspectionSpaceTypeDetailsFragment inspectionSpaceTypeDetailsFragment;
    private InspectionSelectLocationDescriptionFragment inspectionSelectLocationDescriptionFragment;

    public InspectionSpaceTypeAdapter(InspectionSelectSpaceTypeFragment fragment, Context context, List<OccChecklistSpaceTypeHeavy> occChecklistSpaceTypeHeavyList) {
        this.fragment = fragment;
        this.context = context;
        this.occChecklistSpaceTypeHeavyList = occChecklistSpaceTypeHeavyList;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_inspection_space_type_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        OccChecklistSpaceTypeHeavy occChecklistSpaceTypeHeavy = occChecklistSpaceTypeHeavyList.get(position);
        String checklistSpaceTypeId = String.valueOf(occChecklistSpaceTypeHeavy.getChecklistspacetypeid());
        String spaceTypeId = String.valueOf(occChecklistSpaceTypeHeavy.getSpacetype_typeid());
        String checklistId = String.valueOf(occChecklistSpaceTypeHeavy.getChecklist_id());
        String required = occChecklistSpaceTypeHeavy.getRequired() ? "True" : "False";
        String spaceTitle = occChecklistSpaceTypeHeavy.getSpacetitle();

        View itemView = holder.itemView;
        TextView spaceTitleTV = itemView.findViewById(R.id.tv_inspection_space_type_name_value);
        spaceTitleTV.setText(spaceTitle);
        TextView checklistSpaceTypeTV = itemView.findViewById(R.id.tv_inspection_checklist_space_type_id_value);
        checklistSpaceTypeTV.setText(checklistSpaceTypeId);
        TextView spaceTypeIdTV = itemView.findViewById(R.id.tv_inspection_space_type_id_value);
        spaceTypeIdTV.setText(spaceTypeId);
        TextView checklistIdTV = itemView.findViewById(R.id.tv_inspection_space_type_checklist_id_value);
        checklistIdTV.setText(checklistId);
        TextView requiredTV = itemView.findViewById(R.id.tv_inspection_space_type_required_value);
        requiredTV.setText(required);
        Button detailBtn = itemView.findViewById(R.id.btn_inspection_space_type_details);
        Button selectBtn = itemView.findViewById(R.id.btn_inspection_space_type_select);

        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inspectionSpaceTypeDetailsFragment = new InspectionSpaceTypeDetailsFragment(occChecklistSpaceTypeHeavy.getChecklistspacetypeid());
                fragment.getFragmentManager().beginTransaction().replace(R.id.fl_inspection_container, inspectionSpaceTypeDetailsFragment).commit();
            }
        });

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inspectionSelectLocationDescriptionFragment = new InspectionSelectLocationDescriptionFragment(occChecklistSpaceTypeHeavy.getChecklistspacetypeid());
                fragment.getFragmentManager().beginTransaction().replace(R.id.fl_inspection_container, inspectionSelectLocationDescriptionFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return occChecklistSpaceTypeHeavyList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
