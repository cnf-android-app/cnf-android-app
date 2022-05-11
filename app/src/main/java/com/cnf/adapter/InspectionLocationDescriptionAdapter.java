package com.cnf.adapter;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.domain.OccInspectedSpace;
import com.cnf.domain.OccLocationDescription;
import com.cnf.fragment.InspectionAddSpaceFragment;
import com.cnf.service.InspectionActivityService;

import java.time.OffsetDateTime;
import java.util.List;

public class InspectionLocationDescriptionAdapter extends RecyclerView.Adapter<InspectionLocationDescriptionAdapter.LinearViewHolder> {

    private Fragment fragment;
    private int CSTId;
    private Context context;
    private List<OccLocationDescription> occLocationDescriptionList;

    private InspectionActivityService inspectionActivityService;

    public InspectionLocationDescriptionAdapter(Fragment fragment, int CSTId, Context context, List<OccLocationDescription> occLocationDescriptionList) {
        this.CSTId = CSTId;
        this.context = context;
        this.fragment = fragment;
        this.occLocationDescriptionList = occLocationDescriptionList;
        this.inspectionActivityService = new InspectionActivityService(context);
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_inspection_location_description, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        OccLocationDescription occLocationDescription = occLocationDescriptionList.get(position);
        String locationDescription = occLocationDescription.getDescription();
        int locationDescriptionId = occLocationDescription.getLocationdescriptionid();

        SharedPreferences sp = context.getSharedPreferences("user_session", context.MODE_PRIVATE);
        int userId = sp.getInt("user_id", 0);
        int muniId = sp.getInt("muni_id", 0);
        int inspectionId = fragment.getActivity().getIntent().getIntExtra("inspectionId", 0);

        View itemView = holder.itemView;
        TextView descriptionTV = itemView.findViewById(R.id.tv_inspection_location_description);
        descriptionTV.setText(locationDescription);

        Button loadBtn = itemView.findViewById(R.id.btn_inspection_location_description_create);
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                OccInspectedSpace occInspectedSpace = new OccInspectedSpace(inspectionId, locationDescriptionId, userId, OffsetDateTime.now().toString(), CSTId);
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        inspectionActivityService.addInspectedSpace(occInspectedSpace);
                    }
                };
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("=======================================================");
                System.out.println("inspectionId: " + inspectionId);
                System.out.println("CSTId: " + CSTId);
                System.out.println("user_id:" + userId);
                System.out.println("muni_id:" + muniId);
                System.out.println("=======================================================");
                InspectionAddSpaceFragment inspectionAddSpaceFragment = new InspectionAddSpaceFragment();
                fragment.getFragmentManager().beginTransaction().replace(R.id.fl_inspection_container, inspectionAddSpaceFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return occLocationDescriptionList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
