package com.cnf.domain;

import lombok.Data;

@Data
public class OccChecklistSpaceTypeHeavy {

    private Integer checklistspacetypeid;
    private Integer checklist_id;
    private Boolean required;
    private Integer spacetype_typeid;
    private String notes;

    private Integer spacetypeid;
    private String spacetitle;
    private String description;

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

    public Integer getSpacetypeid() {
        return spacetypeid;
    }

    public void setSpacetypeid(Integer spacetypeid) {
        this.spacetypeid = spacetypeid;
    }

    public String getSpacetitle() {
        return spacetitle;
    }

    public void setSpacetitle(String spacetitle) {
        this.spacetitle = spacetitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "OccChecklistSpaceTypeHeavy{" +
                "checklistspacetypeid=" + checklistspacetypeid +
                ", checklist_id=" + checklist_id +
                ", required=" + required +
                ", spacetype_typeid=" + spacetype_typeid +
                ", notes='" + notes + '\'' +
                ", spacetypeid=" + spacetypeid +
                ", spacetitle='" + spacetitle + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
