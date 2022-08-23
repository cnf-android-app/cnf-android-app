package com.cnf.module_notification.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class InspectionDispatchNotification {

  @PrimaryKey(autoGenerate = true)
  @NonNull
  private Integer notificationID;
  private String inspectorId;
  private String inspectorName;
  private String inspectionId;
  private String inspectionDispatchId;
  private String email;
  private String createTS;
  private Boolean isNotified;
  private Boolean isArchived;

  public InspectionDispatchNotification() {
  }

  @NonNull
  public Integer getNotificationID() {
    return notificationID;
  }

  public void setNotificationID(@NonNull Integer notificationID) {
    this.notificationID = notificationID;
  }

  public String getInspectorId() {
    return inspectorId;
  }

  public void setInspectorId(String inspectorId) {
    this.inspectorId = inspectorId;
  }

  public String getInspectorName() {
    return inspectorName;
  }

  public void setInspectorName(String inspectorName) {
    this.inspectorName = inspectorName;
  }

  public String getInspectionId() {
    return inspectionId;
  }

  public void setInspectionId(String inspectionId) {
    this.inspectionId = inspectionId;
  }

  public String getInspectionDispatchId() {
    return inspectionDispatchId;
  }

  public void setInspectionDispatchId(String inspectionDispatchId) {
    this.inspectionDispatchId = inspectionDispatchId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCreateTS() {
    return createTS;
  }

  public void setCreateTS(String createTS) {
    this.createTS = createTS;
  }

  public Boolean getNotified() {
    return isNotified;
  }

  public void setNotified(Boolean notified) {
    isNotified = notified;
  }

  public Boolean getArchived() {
    return isArchived;
  }

  public void setArchived(Boolean archived) {
    isArchived = archived;
  }

  @Override
  public String toString() {
    return "InspectionDispatchDTO{" +
        "notificationID=" + notificationID +
        ", inspectorId='" + inspectorId + '\'' +
        ", inspectorName='" + inspectorName + '\'' +
        ", inspectionId='" + inspectionId + '\'' +
        ", inspectionDispatchId='" + inspectionDispatchId + '\'' +
        ", email='" + email + '\'' +
        ", createTS='" + createTS + '\'' +
        ", isNotified=" + isNotified +
        ", isArchived=" + isArchived +
        '}';
  }
}
