package com.cnf.adapter;

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
        String address = inspectionTaskDTO.getAddress() + " " + inspectionTaskDTO.getUnitnumber();
        String name = inspectionTaskDTO.getFname() + " " + inspectionTaskDTO.getLname();
        String checklist = inspectionTaskDTO.getTitle();
        String description = inspectionTaskDTO.getDescription();
        String cratedDate = null;
        cratedDate = inspectionTaskDTO.getCreatedts();

        View itemView = holder.itemView;
        TextView addressTV = (TextView) itemView.findViewById(R.id.tv_inspection_address);
        addressTV.setText(address);
        TextView nameTV = (TextView) itemView.findViewById(R.id.tv_inspection_inspector_value);
        nameTV.setText(name);
        TextView checklistTV = (TextView) itemView.findViewById(R.id.tv_inspection_checklist_value);
        checklistTV.setText(checklist);
        TextView createdDateTV = (TextView) itemView.findViewById(R.id.tv_inspection_created_date_value);
        createdDateTV.setText(cratedDate);
        TextView descriptionTV = (TextView) itemView.findViewById(R.id.tv_inspection_description_value);
        descriptionTV.setText(description);
        Button editBtn = itemView.findViewById(R.id.btn_inspection_edit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InspectionContainerActivity.class);
                intent.putExtra("inspectionId", inspectionTaskDTO.getInspectionId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return occInspectionList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
