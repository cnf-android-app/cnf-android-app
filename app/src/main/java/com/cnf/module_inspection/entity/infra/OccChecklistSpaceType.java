package com.cnf.module_inspection.entity.infra;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OccChecklistSpaceType {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "checklistspacetypeid")
    private Integer checklistSpaceTypeId;
    @ColumnInfo(name = "checklist_id")
    private Integer checklistId;
    @ColumnInfo(name = "required")
    private Boolean required;
    @ColumnInfo(name = "spacetype_typeid")
    private Integer spaceTypeId;
    @ColumnInfo(name = "notes")
    private String notes;

    public Integer getChecklistSpaceTypeId() {
        return checklistSpaceTypeId;
    }

    public void setChecklistSpaceTypeId(Integer checklistSpaceTypeId) {
        this.checklistSpaceTypeId = checklistSpaceTypeId;
    }

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getSpaceTypeId() {
        return spaceTypeId;
    }

    public void setSpaceTypeId(Integer spaceTypeId) {
        this.spaceTypeId = spaceTypeId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "OccChecklistSpaceType{" +
                "checklistSpaceTypeId=" + checklistSpaceTypeId +
                ", checklistId=" + checklistId +
                ", required=" + required +
                ", spaceTypeId=" + spaceTypeId +
                ", notes='" + notes + '\'' +
                '}';
    }
}
