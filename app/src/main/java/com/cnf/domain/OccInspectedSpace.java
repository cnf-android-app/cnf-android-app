package com.cnf.domain;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
public class OccInspectedSpace {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "inspectedspaceid")
  private Integer inspectedSpaceId;
  @ColumnInfo(name = "occinspection_inspectionid")
  private Integer occInspectionId;
  @ColumnInfo(name = "occlocationdescription_descid")
  private Integer occLocationDescriptionId;
  @ColumnInfo(name = "addedtochecklistby_userid")
  private Integer addedToChecklistByUserid;
  @ColumnInfo(name = "addedtochecklistts")
  private String addedToChecklistTS;
  @ColumnInfo(name = "occchecklistspacetype_chklstspctypid")
  private Integer occChecklistSpaceTypeId;

  public OccInspectedSpace() {
  }

  public OccInspectedSpace(Integer inspectedSpaceId, Integer occInspectionId, Integer occLocationDescriptionId, Integer addedToChecklistByUserid, String addedToChecklistTS,
      Integer occChecklistSpaceTypeId) {
    this.inspectedSpaceId = inspectedSpaceId;
    this.occInspectionId = occInspectionId;
    this.occLocationDescriptionId = occLocationDescriptionId;
    this.addedToChecklistByUserid = addedToChecklistByUserid;
    this.addedToChecklistTS = addedToChecklistTS;
    this.occChecklistSpaceTypeId = occChecklistSpaceTypeId;
  }

  public Integer getInspectedSpaceId() {
    return inspectedSpaceId;
  }

  public void setInspectedSpaceId(Integer inspectedSpaceId) {
    this.inspectedSpaceId = inspectedSpaceId;
  }

  public Integer getOccInspectionId() {
    return occInspectionId;
  }

  public void setOccInspectionId(Integer occInspectionId) {
    this.occInspectionId = occInspectionId;
  }

  public Integer getOccLocationDescriptionId() {
    return occLocationDescriptionId;
  }

  public void setOccLocationDescriptionId(Integer occLocationDescriptionId) {
    this.occLocationDescriptionId = occLocationDescriptionId;
  }

  public Integer getAddedToChecklistByUserid() {
    return addedToChecklistByUserid;
  }

  public void setAddedToChecklistByUserid(Integer addedToChecklistByUserid) {
    this.addedToChecklistByUserid = addedToChecklistByUserid;
  }

  public String getAddedToChecklistTS() {
    return addedToChecklistTS;
  }

  public void setAddedToChecklistTS(String addedToChecklistTS) {
    this.addedToChecklistTS = addedToChecklistTS;
  }

  public Integer getOccChecklistSpaceTypeId() {
    return occChecklistSpaceTypeId;
  }

  public void setOccChecklistSpaceTypeId(Integer occChecklistSpaceTypeId) {
    this.occChecklistSpaceTypeId = occChecklistSpaceTypeId;
  }

  @Override
  public String toString() {
    return "OccInspectedSpace{" +
        "inspectedSpaceId=" + inspectedSpaceId +
        ", occInspectionId=" + occInspectionId +
        ", occLocationDescriptionId=" + occLocationDescriptionId +
        ", addedToChecklistByUserid=" + addedToChecklistByUserid +
        ", addedToChecklistTS='" + addedToChecklistTS + '\'' +
        ", occChecklistSpaceTypeId=" + occChecklistSpaceTypeId +
        '}';
  }
}
