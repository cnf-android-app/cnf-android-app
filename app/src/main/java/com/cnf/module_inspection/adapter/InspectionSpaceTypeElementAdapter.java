package com.cnf.module_inspection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.module_inspection.entity.infra_heavy.OccChecklistSpaceTypeElementHeavy;

import java.util.List;

public class InspectionSpaceTypeElementAdapter extends RecyclerView.Adapter<InspectionSpaceTypeElementAdapter.LinearViewHolder> {


    private Context context;
    private List<OccChecklistSpaceTypeElementHeavy> occChecklistSpaceTypeElementHeavyList;


    public InspectionSpaceTypeElementAdapter(Context context, List<OccChecklistSpaceTypeElementHeavy> occChecklistSpaceTypeElementHeavyList) {
        this.context = context;
        this.occChecklistSpaceTypeElementHeavyList = occChecklistSpaceTypeElementHeavyList;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_inspection_occ_checklist_space_type_element_details_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        OccChecklistSpaceTypeElementHeavy occChecklistSpaceTypeElementHeavy = occChecklistSpaceTypeElementHeavyList.get(position);

        String elementid = String.valueOf(occChecklistSpaceTypeElementHeavy.getCodeElement().getElementId());
        String ordsecnum = occChecklistSpaceTypeElementHeavy.getCodeElement().getOrdSecNum();
        String ordsubsectitle = occChecklistSpaceTypeElementHeavy.getCodeElement().getOrdSubSecTitle();
        String ordtechnicaltext = occChecklistSpaceTypeElementHeavy.getCodeElement().getOrdTechnicalText();

        View itemView = holder.itemView;

        TextView elementidTV = itemView.findViewById(R.id.tv_inspection_space_type_details_element_id_value);
        elementidTV.setText(elementid);

        TextView ordsecnumTV = itemView.findViewById(R.id.tv_inspection_space_type_details_element_Ordinance_num_value);
        ordsecnumTV.setText(ordsecnum);

        TextView ordsubsectitleTV = itemView.findViewById(R.id.tv_inspection_space_type_details_element_Ordinance_title_value);
        ordsubsectitleTV.setText(ordsubsectitle);

        TextView ordtechnicaltextTV = itemView.findViewById(R.id.tv_inspection_space_type_details_element_Ordinance_text_value);
        ordtechnicaltextTV.setText(ordtechnicaltext);

    }


    @Override
    public int getItemCount() {
        return occChecklistSpaceTypeElementHeavyList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
