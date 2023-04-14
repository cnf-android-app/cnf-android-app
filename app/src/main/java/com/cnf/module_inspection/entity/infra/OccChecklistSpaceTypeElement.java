package com.cnf.module_inspection.entity.infra;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OccChecklistSpaceTypeElement {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "spaceelementid")
    private Integer spaceElementId;
    @ColumnInfo(name = "occ_checklist_space_type_required")
    private Boolean required;
    @ColumnInfo(name = "checklistspacetype_typeid")
    private Integer checklistSpaceTypeId;
    @ColumnInfo(name = "codesetelement_seteleid")
    private Integer codeSetElementId;
    @ColumnInfo(name = "occ_checklist_space_type_notes")
    private String notes;

    public Integer getSpaceElementId() {
        return spaceElementId;
    }

    public void setSpaceElementId(Integer spaceElementId) {
        this.spaceElementId = spaceElementId;
    }


    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getChecklistSpaceTypeId() {
        return checklistSpaceTypeId;
    }

    public void setChecklistSpaceTypeId(Integer checklistSpaceTypeId) {
        this.checklistSpaceTypeId = checklistSpaceTypeId;
    }

    public Integer getCodeSetElementId() {
        return codeSetElementId;
    }

    public void setCodeSetElementId(Integer codeSetElementId) {
        this.codeSetElementId = codeSetElementId;
    }

    @Override
    public String toString() {
        return "OccChecklistSpaceTypeElement{" +
            "spaceElementId=" + spaceElementId +
            ", required=" + required +
            ", checklistSpaceTypeId=" + checklistSpaceTypeId +
            ", codeSetElementId=" + codeSetElementId +
            ", notes='" + notes + '\'' +
            '}';
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
