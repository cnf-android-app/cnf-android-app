package com.cnf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.domain.OccChecklistSpaceTypeElementHeavyDetails;

import java.util.List;

public class InspectionSpaceTypeElementAdapter extends RecyclerView.Adapter<InspectionSpaceTypeElementAdapter.LinearViewHolder> {


    private Context context;
    private List<OccChecklistSpaceTypeElementHeavyDetails> occChecklistSpaceTypeElementHeavyDetailsList;


    public InspectionSpaceTypeElementAdapter(Context context, List<OccChecklistSpaceTypeElementHeavyDetails> occChecklistSpaceTypeElementHeavyDetailsList) {
        this.context = context;
        this.occChecklistSpaceTypeElementHeavyDetailsList = occChecklistSpaceTypeElementHeavyDetailsList;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_inspection_space_type_details_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        OccChecklistSpaceTypeElementHeavyDetails occChecklistSpaceTypeElementHeavyDetails = occChecklistSpaceTypeElementHeavyDetailsList.get(position);

        String elementid = String.valueOf(occChecklistSpaceTypeElementHeavyDetails.getElementid());
        String ordsecnum = occChecklistSpaceTypeElementHeavyDetails.getOrdsecnum();
        String ordsubsectitle = occChecklistSpaceTypeElementHeavyDetails.getOrdsubsectitle();
        String ordtechnicaltext = occChecklistSpaceTypeElementHeavyDetails.getOrdtechnicaltext();

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
        return occChecklistSpaceTypeElementHeavyDetailsList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
