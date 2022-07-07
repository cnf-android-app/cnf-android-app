package com.cnf.domain.infra;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class CodeElementGuide implements Serializable {

  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "guideentryid")
  private Integer guideEntryId;
  @ColumnInfo(name = "category")
  private String category;
  @ColumnInfo(name = "subcategory")
  private String subCategory;
  @ColumnInfo(name = "description")
  private String description;
  @ColumnInfo(name = "enforcementguidelines")
  private String enforcementGuidelines;
  @ColumnInfo(name = "inspectionguidelines")
  private String inspectionGuidelines;
  @ColumnInfo(name = "priority")
  private Boolean priority;

  public Integer getGuideEntryId() {
    return guideEntryId;
  }

  public void setGuideEntryId(Integer guideEntryId) {
    this.guideEntryId = guideEntryId;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(String subCategory) {
    this.subCategory = subCategory;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getEnforcementGuidelines() {
    return enforcementGuidelines;
  }

  public void setEnforcementGuidelines(String enforcementGuidelines) {
    this.enforcementGuidelines = enforcementGuidelines;
  }

  public String getInspectionGuidelines() {
    return inspectionGuidelines;
  }

  public void setInspectionGuidelines(String inspectionGuidelines) {
    this.inspectionGuidelines = inspectionGuidelines;
  }

  public Boolean getPriority() {
    return priority;
  }

  public void setPriority(Boolean priority) {
    this.priority = priority;
  }

  @Override
  public String toString() {
    return "CodeElementGuide{" +
        "guideEntryId=" + guideEntryId +
        ", category='" + category + '\'' +
        ", subCategory='" + subCategory + '\'' +
        ", description='" + description + '\'' +
        ", enforcementGuidelines='" + enforcementGuidelines + '\'' +
        ", inspectionGuidelines='" + inspectionGuidelines + '\'' +
        ", priority=" + priority +
        '}';
  }
}
