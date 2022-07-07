package com.cnf.domain.infra;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OccCheckList {

  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "checklistid")
  private Integer checkListId;
  @ColumnInfo(name = "title")
  private String title;
  @ColumnInfo(name = "description")
  private String description;
  @ColumnInfo(name = "muni_municode")
  private Integer municode;
  @ColumnInfo(name = "occ_checklist_active")
  private Boolean active;
  @ColumnInfo(name = "governingcodesource_sourceid")
  private Integer governingCodeSourceId;
  @ColumnInfo(name = "occ_checklist_createdts")
  private String createdTS;

  public Integer getCheckListId() {
    return checkListId;
  }

  public void setCheckListId(Integer checkListId) {
    this.checkListId = checkListId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getMunicode() {
    return municode;
  }

  public void setMunicode(Integer municode) {
    this.municode = municode;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Integer getGoverningCodeSourceId() {
    return governingCodeSourceId;
  }

  public void setGoverningCodeSourceId(Integer governingCodeSourceId) {
    this.governingCodeSourceId = governingCodeSourceId;
  }

  public String getCreatedTS() {
    return createdTS;
  }

  public void setCreatedTS(String createdTS) {
    this.createdTS = createdTS;
  }

  @Override
  public String toString() {
    return "OccCheckList{" +
        "checkListId=" + checkListId +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", municode=" + municode +
        ", active=" + active +
        ", governingCodeSourceId=" + governingCodeSourceId +
        ", createdTS='" + createdTS + '\'' +
        '}';
  }
}