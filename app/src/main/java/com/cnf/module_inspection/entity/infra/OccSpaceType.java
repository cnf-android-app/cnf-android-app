package com.cnf.module_inspection.entity.infra;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OccSpaceType {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "spacetypeid")
    private Integer spaceTypeId;
    @ColumnInfo(name = "spacetitle")
    private String spaceTitle;
    @ColumnInfo(name = "occ_space_type_description")
    private String description;

    public Integer getSpaceTypeId() {
        return spaceTypeId;
    }

    public void setSpaceTypeId(Integer spaceTypeId) {
        this.spaceTypeId = spaceTypeId;
    }

    public String getSpaceTitle() {
        return spaceTitle;
    }

    public void setSpaceTitle(String spaceTitle) {
        this.spaceTitle = spaceTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "OccSpaceType{" +
            "spaceTypeId=" + spaceTypeId +
            ", spaceTitle='" + spaceTitle + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
