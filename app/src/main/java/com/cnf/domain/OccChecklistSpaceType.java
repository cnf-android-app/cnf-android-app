package com.cnf.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity
public class OccChecklistSpaceType {

    @PrimaryKey(autoGenerate = false)
    private Integer checklistspacetypeid;
    private Integer checklist_id;
    private Boolean required;
    private Integer spacetype_typeid;
    private String notes;

    public Integer getChecklistspacetypeid() {
        return checklistspacetypeid;
    }

    public void setChecklistspacetypeid(Integer checklistspacetypeid) {
        this.checklistspacetypeid = checklistspacetypeid;
    }

    public Integer getChecklist_id() {
        return checklist_id;
    }

    public void setChecklist_id(Integer checklist_id) {
        this.checklist_id = checklist_id;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getSpacetype_typeid() {
        return spacetype_typeid;
    }

    public void setSpacetype_typeid(Integer spacetype_typeid) {
        this.spacetype_typeid = spacetype_typeid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
