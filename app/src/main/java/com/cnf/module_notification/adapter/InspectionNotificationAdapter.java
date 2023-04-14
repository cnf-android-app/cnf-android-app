package com.cnf.module_notification.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cnf.R;
import com.cnf.module_notification.entity.InspectionDispatchNotification;
import com.cnf.module_notification.adapter.InspectionNotificationAdapter.OccInspectionNotificationHolder;
import com.cnf.module_notification.interfaces.OnItemClickListener;
import com.loopeer.itemtouchhelperextension.Extension;
import java.util.List;

public class InspectionNotificationAdapter extends RecyclerView.Adapter<OccInspectionNotificationHolder> {

  private List<InspectionDispatchNotification> inspectionDispatchNotificationList;
  private final OnItemClickListener<InspectionDispatchNotification> onItemClickListener;

  public InspectionNotificationAdapter(List<InspectionDispatchNotification> inspectionDispatchNotificationList, OnItemClickListener<InspectionDispatchNotification> onItemClickListener) {
    this.inspectionDispatchNotificationList = inspectionDispatchNotificationList;
    this.onItemClickListener = onItemClickListener;
  }

  @NonNull
  @Override
  public OccInspectionNotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new OccInspectionNotificationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inspection_occ_notification_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull OccInspectionNotificationHolder holder, int position) {
    InspectionDispatchNotification inspectionDispatchNotification = inspectionDispatchNotificationList.get(position);
    // TODO: NOTIFICATION CONTENT
    holder.tvNotificationInspectionId.setText("Dispatch Id: " + inspectionDispatchNotification.getInspectionId());
    holder.tvNotificationCreatedTS.setText(inspectionDispatchNotification.getCreateTS());
    holder.tvNotificationContent.setText(String.format("You have a new inspection(ID: %s) dispatch.", inspectionDispatchNotification.getInspectionId()));
    holder.tvNotificationItemDelete.setText(inspectionDispatchNotification.getArchived() ? "UNDO" : "ARCHIVE");
    holder.tvNotificationItemDelete.setOnClickListener(v -> onItemClickListener.onItemClick(inspectionDispatchNotification));
  }

  @Override
  public int getItemCount() {
    return inspectionDispatchNotificationList.size();
  }

  public static class OccInspectionNotificationHolder extends RecyclerView.ViewHolder implements Extension {

    public TextView tvNotificationInspectionId, tvNotificationCreatedTS, tvNotificationContent, tvNotificationItemDelete;

    public OccInspectionNotificationHolder(@NonNull View itemView) {
      super(itemView);
      tvNotificationInspectionId = itemView.findViewById(R.id.inspection_occ_notification_item_dispatchId);
      tvNotificationCreatedTS = itemView.findViewById(R.id.inspection_occ_notification_item_createdTS);
      tvNotificationContent = itemView.findViewById(R.id.inspection_occ_notification_item_content);
      tvNotificationItemDelete = itemView.findViewById(R.id.tv_item_delete);
    }

    @Override
    public float getActionWidth() {
      return tvNotificationContent.getWidth();
    }
  }

  public void setInspectionDispatchNotificationList(List<InspectionDispatchNotification> inspectionDispatchNotificationList) {
    this.inspectionDispatchNotificationList = inspectionDispatchNotificationList;
  }
}
