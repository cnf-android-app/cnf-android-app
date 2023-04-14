package com.cnf.module_inspection.entity.infra;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

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

  public CodeElementGuide() {
  }

  public CodeElementGuide(Integer guideEntryId, String category, String subCategory, String description, String enforcementGuidelines, String inspectionGuidelines, Boolean priority) {
    this.guideEntryId = guideEntryId;
    this.category = category;
    this.subCategory = subCategory;
    this.description = description;
    this.enforcementGuidelines = enforcementGuidelines;
    this.inspectionGuidelines = inspectionGuidelines;
    this.priority = priority;
  }

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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CodeElementGuide that = (CodeElementGuide) o;
    return Objects.equals(guideEntryId, that.guideEntryId) && Objects.equals(category, that.category) && Objects.equals(subCategory, that.subCategory)
        && Objects.equals(description, that.description) && Objects.equals(enforcementGuidelines, that.enforcementGuidelines) && Objects.equals(inspectionGuidelines,
        that.inspectionGuidelines) && Objects.equals(priority, that.priority);
  }

  @Override
  public int hashCode() {
    return Objects.hash(guideEntryId, category, subCategory, description, enforcementGuidelines, inspectionGuidelines, priority);
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
