package com.cnf.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity
public class CodeElementGuide {

    @PrimaryKey(autoGenerate = false)
    private Integer guideentryid;
    private String category;
    private String subcategory;
    private String description;
    private String enforcementguidelines;
    private String inspectionguidelines;
    private Boolean priority;
    private Integer icon_iconid;

    public Integer getGuideentryid() {
        return guideentryid;
    }

    public void setGuideentryid(Integer guideentryid) {
        this.guideentryid = guideentryid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnforcementguidelines() {
        return enforcementguidelines;
    }

    public void setEnforcementguidelines(String enforcementguidelines) {
        this.enforcementguidelines = enforcementguidelines;
    }

    public String getInspectionguidelines() {
        return inspectionguidelines;
    }

    public void setInspectionguidelines(String inspectionguidelines) {
        this.inspectionguidelines = inspectionguidelines;
    }

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    public Integer getIcon_iconid() {
        return icon_iconid;
    }

    public void setIcon_iconid(Integer icon_iconid) {
        this.icon_iconid = icon_iconid;
    }
}
