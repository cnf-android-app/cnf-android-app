package com.cnf.adapter;

import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_ID_NAME;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.InspectionContainerActivity;
import com.cnf.R;
import com.cnf.dto.InspectionTaskDTO;

import java.util.List;

public class InspectionAdapter extends RecyclerView.Adapter<InspectionAdapter.LinearViewHolder> {

    private Context context;
    private List<InspectionTaskDTO> occInspectionList;

    public InspectionAdapter(Context context, List<InspectionTaskDTO> occInspectionList) {
        this.context = context;
        this.occInspectionList = occInspectionList;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_inspection_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        InspectionTaskDTO inspectionTaskDTO = occInspectionList.get(position);
        String inspectionId = "Inspection ID: " + inspectionTaskDTO.getInspectionId();
        String address = inspectionTaskDTO.getAddress() + " " + inspectionTaskDTO.getUnitnumber();
        String name = inspectionTaskDTO.getFname() + " " + inspectionTaskDTO.getLname();
        String checklist = inspectionTaskDTO.getTitle();
        String description = inspectionTaskDTO.getDescription();
        String cratedDate = inspectionTaskDTO.getCreatedts();
        holder.inspectionIdTv.setText(inspectionId);
        holder.addressTv.setText(address);
        holder.nameTv.setText(name);
        holder.checklistTv.setText(checklist);
        holder.createdDateTv.setText(cratedDate);
        holder.descriptionTv.setText(description);
        holder.editBtn.setOnClickListener(view -> {
            Intent intent = new Intent(context, InspectionContainerActivity.class);
            intent.putExtra(INTENT_EXTRA_INSPECTION_ID_NAME, inspectionTaskDTO.getInspectionId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return occInspectionList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {
        TextView inspectionIdTv, addressTv, nameTv, checklistTv, createdDateTv, descriptionTv;
        Button editBtn;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            inspectionIdTv = itemView.findViewById(R.id.tv_inspection_inspectionId);
            addressTv = itemView.findViewById(R.id.tv_inspection_address_value);
            nameTv = itemView.findViewById(R.id.tv_inspection_inspector_value);
            checklistTv = itemView.findViewById(R.id.tv_inspection_checklist_value);
            createdDateTv = itemView.findViewById(R.id.tv_inspection_created_date_value);
            descriptionTv = itemView.findViewById(R.id.tv_inspection_description_value);
            editBtn = itemView.findViewById(R.id.btn_inspection_edit);
        }
    }
}
