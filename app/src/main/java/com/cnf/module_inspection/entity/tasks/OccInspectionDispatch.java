package com.cnf.module_inspection.entity.tasks;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OccInspectionDispatch {

  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "dispatchid")
  private Integer dispatchId;
  @ColumnInfo(name = "createdby_userid")
  private Integer createdByUserId;
  @ColumnInfo(name = "creationts")
  private String creationTS;
  @ColumnInfo(name = "dispatchnotes")
  private String dispatchNotes;
  @ColumnInfo(name = "inspection_inspectionid")
  private Integer inspectionId;
  @ColumnInfo(name = "retrievalts")
  private String retrievalTS;
  @ColumnInfo(name = "retrievedby_userid")
  private Integer retrievedByUserId;
  @ColumnInfo(name = "synchronizationts")
  private String synchronizationTS;
  @ColumnInfo(name = "synchronizationnotes")
  private String synchronizationNotes;
  @ColumnInfo(name = "occ_inspection_dispatch_municipality_municode")
  private Integer municode;
  @ColumnInfo(name = "municipalityname")
  private String municipalityName;

  public Integer getDispatchId() {
    return dispatchId;
  }

  public void setDispatchId(Integer dispatchId) {
    this.dispatchId = dispatchId;
  }

  public Integer getCreatedByUserId() {
    return createdByUserId;
  }

  public void setCreatedByUserId(Integer createdByUserId) {
    this.createdByUserId = createdByUserId;
  }

  public String getCreationTS() {
    return creationTS;
  }

  public void setCreationTS(String creationTS) {
    this.creationTS = creationTS;
  }

  public String getDispatchNotes() {
    return dispatchNotes;
  }

  public void setDispatchNotes(String dispatchNotes) {
    this.dispatchNotes = dispatchNotes;
  }

  public Integer getInspectionId() {
    return inspectionId;
  }

  public void setInspectionId(Integer inspectionId) {
    this.inspectionId = inspectionId;
  }

  public String getRetrievalTS() {
    return retrievalTS;
  }

  public void setRetrievalTS(String retrievalTS) {
    this.retrievalTS = retrievalTS;
  }

  public Integer getRetrievedByUserId() {
    return retrievedByUserId;
  }

  public void setRetrievedByUserId(Integer retrievedByUserId) {
    this.retrievedByUserId = retrievedByUserId;
  }

  public String getSynchronizationTS() {
    return synchronizationTS;
  }

  public void setSynchronizationTS(String synchronizationTS) {
    this.synchronizationTS = synchronizationTS;
  }

  public String getSynchronizationNotes() {
    return synchronizationNotes;
  }

  public void setSynchronizationNotes(String synchronizationNotes) {
    this.synchronizationNotes = synchronizationNotes;
  }

  public Integer getMunicode() {
    return municode;
  }

  public void setMunicode(Integer municode) {
    this.municode = municode;
  }

  public String getMunicipalityName() {
    return municipalityName;
  }

  public void setMunicipalityName(String municipalityName) {
    this.municipalityName = municipalityName;
  }

  @Override
  public String toString() {
    return "OccInspectionDispatch{" +
        "dispatchId=" + dispatchId +
        ", createdByUserId=" + createdByUserId +
        ", creationTS='" + creationTS + '\'' +
        ", dispatchNotes='" + dispatchNotes + '\'' +
        ", inspectionId=" + inspectionId +
        ", retrievalTS='" + retrievalTS + '\'' +
        ", retrievedByUserId=" + retrievedByUserId +
        ", synchronizationTS='" + synchronizationTS + '\'' +
        ", synchronizationNotes='" + synchronizationNotes + '\'' +
        ", municode=" + municode +
        ", municipalityName='" + municipalityName + '\'' +
        '}';
  }
}
