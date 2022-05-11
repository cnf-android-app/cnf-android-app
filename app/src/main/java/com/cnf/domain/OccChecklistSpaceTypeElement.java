package com.cnf.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity
public class OccChecklistSpaceTypeElement {

    @PrimaryKey(autoGenerate = false)
    private Integer spaceelementid;
    private Integer codeelement_id;
    private Boolean required;
    private Integer checklistspacetype_typeid;

    public Integer getSpaceelementid() {
        return spaceelementid;
    }

    public void setSpaceelementid(Integer spaceelementid) {
        this.spaceelementid = spaceelementid;
    }

    public Integer getCodeelement_id() {
        return codeelement_id;
    }

    public void setCodeelement_id(Integer codeelement_id) {
        this.codeelement_id = codeelement_id;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getChecklistspacetype_typeid() {
        return checklistspacetype_typeid;
    }

    public void setChecklistspacetype_typeid(Integer checklistspacetype_typeid) {
        this.checklistspacetype_typeid = checklistspacetype_typeid;
    }
}