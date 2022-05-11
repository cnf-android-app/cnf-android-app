package com.cnf.adapter;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.domain.OccChecklistSpaceTypeHeavy;
import com.cnf.domain.OccInspectedSpace;
import com.cnf.domain.OccInspectedSpaceElementHeavy;
import com.cnf.domain.OccInspectedSpaceHeavy;
import com.cnf.fragment.InspectionAddSpaceFragment;
import com.cnf.fragment.InspectionInspectedSpaceElementsFragment;
import com.cnf.service.InspectionActivityService;

import java.util.List;

public class InspectedSpaceAdapter extends RecyclerView.Adapter<InspectedSpaceAdapter.LinearViewHolder> {

    private List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList;
    private Context context;
    private InspectionActivityService inspectionActivityService;
    private Fragment fragment;

    public InspectedSpaceAdapter(List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList, Context context, Fragment fragment) {
        this.occInspectedSpaceHeavyList = occInspectedSpaceHeavyList;
        this.context = context;
        this.fragment = fragment;
        this.inspectionActivityService = new InspectionActivityService(context);
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InspectedSpaceAdapter.LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_inspected_space_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        OccInspectedSpaceHeavy occInspectedSpaceHeavy = occInspectedSpaceHeavyList.get(position);
        Integer inspectedspaceid = occInspectedSpaceHeavy.getInspectedspaceid();
        String spaceType = occInspectedSpaceHeavy.getSpacetitle();
        String description = occInspectedSpaceHeavy.getDescription();


        View itemView = holder.itemView;
        TextView inspectedSpaceTitleTV = itemView.findViewById(R.id.tv_inspection_inspected_space_item_name);
        TextView inspectedDesTV = itemView.findViewById(R.id.tv_inspection_inspected_space_item_description);
        Button editBtn = itemView.findViewById(R.id.btn_inspection_inspected_space_item_edit);

        inspectedSpaceTitleTV.setText(spaceType);
        inspectedDesTV.setText(description);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        System.out.println("inspectedspaceid : " + inspectedspaceid);
                        inspectionActivityService.createDefaultAndAddOccInspectedSpaceElementList(inspectedspaceid);
                        List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList = inspectionActivityService.getOccInspectedSpaceElementHeavyList(inspectedspaceid);
                        System.out.println(occInspectedSpaceElementHeavyList);

                    }
                };
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //System.out.println("inspectedspaceid : " + inspectedspaceid);
                InspectionInspectedSpaceElementsFragment inspectionInspectedSpaceElementsFragment = new InspectionInspectedSpaceElementsFragment(inspectedspaceid);
                fragment.getFragmentManager().beginTransaction().replace(R.id.fl_inspection_container, inspectionInspectedSpaceElementsFragment).commit();
            }

        });



    }

    @Override
    public int getItemCount() {
        return occInspectedSpaceHeavyList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
