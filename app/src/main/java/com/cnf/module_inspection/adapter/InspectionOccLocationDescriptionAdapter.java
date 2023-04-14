package com.cnf.module_inspection.adapter;

import androidx.fragment.app.Fragment;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.module_inspection.adapter.InspectionOccLocationDescriptionAdapter.OccLocationDescriptionHolder;
import com.cnf.module_inspection.async.CreateOccInspectedSpaceTask;
import com.cnf.module_inspection.entity.infra.OccLocationDescription;
import java.util.List;

public class InspectionOccLocationDescriptionAdapter extends RecyclerView.Adapter<OccLocationDescriptionHolder> {

  private final Fragment fragment;
  private List<OccLocationDescription> occLocationDescriptionList;

  public InspectionOccLocationDescriptionAdapter(Fragment fragment, List<OccLocationDescription> occLocationDescriptionList) {
    this.fragment = fragment;
    this.occLocationDescriptionList = occLocationDescriptionList;
  }

  @NonNull
  @Override
  public OccLocationDescriptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new OccLocationDescriptionHolder(LayoutInflater.from(fragment.getActivity()).inflate(R.layout.layout_inspection_occ_location_description_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull OccLocationDescriptionHolder holder, int position) {
    OccLocationDescription occLocationDescription = occLocationDescriptionList.get(position);
    String locationDescription = occLocationDescription.getDescription();
    holder.tvDescription.setText(locationDescription);
    holder.btnCreate.setOnClickListener(view -> {
      CreateOccInspectedSpaceTask task = new CreateOccInspectedSpaceTask(occLocationDescription.getLocationDescriptionId(), fragment);
      task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    });
  }

  @Override
  public int getItemCount() {
    return occLocationDescriptionList.size();
  }

  static class OccLocationDescriptionHolder extends RecyclerView.ViewHolder {

    TextView tvDescription;
    Button btnCreate;

    public OccLocationDescriptionHolder(@NonNull View itemView) {
      super(itemView);
      tvDescription = itemView.findViewById(R.id.tv_inspection_occ_location_description);
      btnCreate = itemView.findViewById(R.id.btn_inspection_occ_location_description_create);
    }
  }

  public void setOccLocationDescriptionList(List<OccLocationDescription> occLocationDescriptionList) {
    this.occLocationDescriptionList = occLocationDescriptionList;
  }
}
