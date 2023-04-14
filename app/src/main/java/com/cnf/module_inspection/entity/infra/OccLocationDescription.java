package com.cnf.module_inspection.entity.infra;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OccLocationDescription {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "locationdescriptionid")
    private Integer locationDescriptionId;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "buildingfloorno")
    private Integer buildingFloorNo;

    public Integer getLocationDescriptionId() {
        return locationDescriptionId;
    }

    public void setLocationDescriptionId(Integer locationDescriptionId) {
        this.locationDescriptionId = locationDescriptionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBuildingFloorNo() {
        return buildingFloorNo;
    }

    public void setBuildingFloorNo(Integer buildingFloorNo) {
        this.buildingFloorNo = buildingFloorNo;
    }

    @Override
    public String toString() {
        return "OccLocationDescription{" +
                "locationDescriptionId=" + locationDescriptionId +
                ", description='" + description + '\'' +
                ", buildingFloorNo=" + buildingFloorNo +
                '}';
    }
}
