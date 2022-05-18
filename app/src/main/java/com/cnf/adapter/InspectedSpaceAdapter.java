package com.cnf.adapter;

import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ID_NAME;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.domain.OccInspectedSpaceElementHeavy;
import com.cnf.domain.OccInspectedSpaceHeavy;
import com.cnf.fragment.InspectionInspectedSpaceElementCategoryFragment;
import com.cnf.service.occ.OccInspectionSpaceElementService;

import java.util.List;

public class InspectedSpaceAdapter extends RecyclerView.Adapter<InspectedSpaceAdapter.LinearViewHolder> {

    private Context context;
    private Fragment fragment;
    private List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList;
    private List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList;

    public InspectedSpaceAdapter(List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList, Context context, Fragment fragment) {
        this.occInspectedSpaceHeavyList = occInspectedSpaceHeavyList;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InspectedSpaceAdapter.LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_inspected_space_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        OccInspectedSpaceHeavy occInspectedSpaceHeavy = occInspectedSpaceHeavyList.get(position);
        Integer inspectedSpaceId = occInspectedSpaceHeavy.getOccInspectedSpace().getInspectedspaceid();

        Thread t = new Thread() {
            @Override
            public void run() {
                Log.d("TAG", "Inspected Space Id: " + inspectedSpaceId);
                OccInspectionSpaceElementService.getInstance(context).createDefaultOccInspectedSpaceElementList(inspectedSpaceId);
                occInspectedSpaceElementHeavyList = OccInspectionSpaceElementService.getInstance(context).getOccInspectedSpaceElementHeavyList(inspectedSpaceId);
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String inspectedSpaceType = occInspectedSpaceHeavy.getOccSpaceType().getSpacetitle();
        String inspectedLocationDescription = occInspectedSpaceHeavy.getOccLocationDescription().getDescription();
        String completeStatus;
        if (OccInspectionSpaceElementService.getInstance(context).isAllInspectedSpaceElementComplete(occInspectedSpaceElementHeavyList)) {
            completeStatus = "Finished";
            //TODO CHOOSE AN APPROPREATE COLOR
            holder.inspectedSpaceLy.setBackgroundResource(R.drawable.layout_bg_yellow);
        } else {
            completeStatus = "UnFinish";
        }
        holder.inspectedSpaceItemTypeTv.setText(inspectedSpaceType);
        holder.inspectedSpaceLocationDescriptionTv.setText(inspectedLocationDescription);
        holder.inspectedSpaceCompleteStatusTv.setText(completeStatus);

        holder.inspectedSpaceLy.setOnClickListener(view -> {
            fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_INSPECTED_SPACE_ID_NAME, inspectedSpaceId);
            InspectionInspectedSpaceElementCategoryFragment inspectionInspectedSpaceElementCategoryFragment = new InspectionInspectedSpaceElementCategoryFragment();
            fragment.getFragmentManager().beginTransaction().replace(R.id.fl_inspection_container, inspectionInspectedSpaceElementCategoryFragment).commit();
        });
    }

    @Override
    public int getItemCount() {
        return occInspectedSpaceHeavyList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {
        TextView inspectedSpaceItemTypeTv, inspectedSpaceLocationDescriptionTv, inspectedSpaceCompleteStatusTv;
        RelativeLayout inspectedSpaceLy;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            inspectedSpaceItemTypeTv = itemView.findViewById(R.id.tv_inspection_inspected_space_item_type);
            inspectedSpaceLocationDescriptionTv = itemView.findViewById(R.id.tv_inspection_inspected_space_item_location_description);
            inspectedSpaceCompleteStatusTv = itemView.findViewById(R.id.tv_inspection_inspected_space_item_complete_status);
            inspectedSpaceLy = itemView.findViewById(R.id.rl_inspection_inspected_space_item);
        }
    }
}
