package com.cnf.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.domain.BlobBytes;
import com.cnf.domain.OccChecklistSpaceTypeElementHeavyDetails;
import com.cnf.domain.OccInspectedSpaceElementHeavy;
import com.cnf.fragment.InspectionAddSpaceFragment;
import com.cnf.service.InspectionActivityService;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class InspectionInspectedSpaceElementAdapter extends RecyclerView.Adapter<InspectionInspectedSpaceElementAdapter.LinearViewHolder> {


    private Activity context;
    private List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList;

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private InspectionActivityService inspectionActivityService;


    public InspectionInspectedSpaceElementAdapter(Activity context, List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList) {
        this.context = context;
        this.occInspectedSpaceElementHeavyList = occInspectedSpaceElementHeavyList;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inspection_inspected_space_elements, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy = occInspectedSpaceElementHeavyList.get(position);



        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.rv_inspection_inspected_space_elements_photos.getContext(), LinearLayoutManager.VERTICAL, false);


        Thread t = new Thread() {
            @Override
            public void run() {
                inspectionActivityService = new InspectionActivityService(holder.rv_inspection_inspected_space_elements_photos.getContext());
                occInspectedSpaceElementHeavy.setBlobBytesList(inspectionActivityService.getBlobBytesList());
                System.out.println(occInspectedSpaceElementHeavyList);
                System.out.println("Again");
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        layoutManager.setInitialPrefetchItemCount(occInspectedSpaceElementHeavy.getBlobBytesList().size());

        InspectionInspectedSpaceElementPhotoAdapter inspectionInspectedSpaceElementPhotoAdapter = new InspectionInspectedSpaceElementPhotoAdapter(occInspectedSpaceElementHeavy.getBlobBytesList());

        holder.rv_inspection_inspected_space_elements_photos.setLayoutManager(layoutManager);
        holder.rv_inspection_inspected_space_elements_photos.setAdapter(inspectionInspectedSpaceElementPhotoAdapter);
        holder.rv_inspection_inspected_space_elements_photos.setRecycledViewPool(viewPool);

        String ordSubSecTitle = occInspectedSpaceElementHeavy.getOrdsubsectitle();
        String ordTechnicalText = occInspectedSpaceElementHeavy.getOrdtechnicaltext();

        holder.ordinanceNameTV.setText(ordSubSecTitle);
        holder.ordDescriptionTV.setText(ordTechnicalText);

        boolean isExpanded = occInspectedSpaceElementHeavy.isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);



    }

    @Override
    public int getItemCount() {
        return occInspectedSpaceElementHeavyList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {
        TextView ordinanceNameTV, ordDescriptionTV, expandArrowTV;
        Button takePhotoBtn;
        RelativeLayout expandableLayout = itemView.findViewById(R.id.rt_inspection_inspected_space_element_item_expand_content);
        RecyclerView rv_inspection_inspected_space_elements_photos;


        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            ordinanceNameTV = itemView.findViewById(R.id.tv_inspection_inspected_space_element_item_ordinance_name);
            expandArrowTV = itemView.findViewById(R.id.tv_inspection_inspected_space_element_item_expand_arrow);
            ordDescriptionTV = itemView.findViewById(R.id.tv_inspection_inspected_space_elements_ordinance_description_value);
            expandableLayout = itemView.findViewById(R.id.rt_inspection_inspected_space_element_item_expand_content);
            takePhotoBtn = itemView.findViewById(R.id.btn_inspection_inspected_space_elements_take_photo);

            rv_inspection_inspected_space_elements_photos = itemView.findViewById(R.id.rv_inspection_inspected_space_elements_photos);

            expandArrowTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy = occInspectedSpaceElementHeavyList.get(getAdapterPosition());
                    occInspectedSpaceElementHeavy.setExpanded(!occInspectedSpaceElementHeavy.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            takePhotoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy = occInspectedSpaceElementHeavyList.get(getAdapterPosition());
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    System.out.println("inspectedSpaceElementId" +  occInspectedSpaceElementHeavy.getInspectedspaceelementid());

                    context.getIntent().putExtra("inspectedSpaceElementId",  occInspectedSpaceElementHeavy.getInspectedspaceelementid());

                    context.startActivityForResult(takePictureIntent, 1);



                }
            });

        }
    }

    public void updateAdapter(/* gets params like TITLES,ICONS,NAME,EMAIL,PROFILE*/){
        notifyDataSetChanged();
    }






}
