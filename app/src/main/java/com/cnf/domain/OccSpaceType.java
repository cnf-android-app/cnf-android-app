package com.cnf.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity
public class OccSpaceType {

    @PrimaryKey(autoGenerate = false)
    private Integer spacetypeid;
    private String spacetitle;
    private String description;
    private Boolean required;

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

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}
