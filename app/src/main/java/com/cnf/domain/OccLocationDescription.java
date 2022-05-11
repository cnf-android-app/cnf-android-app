package com.cnf.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity
public class OccLocationDescription {

  @PrimaryKey(autoGenerate = false)
  private Integer locationdescriptionid;
  private String description;
  private Integer buildingfloorno;

  public Integer getLocationdescriptionid() {
    return locationdescriptionid;
  }

  public void setLocationdescriptionid(Integer locationdescriptionid) {
    this.locationdescriptionid = locationdescriptionid;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getBuildingfloorno() {
    return buildingfloorno;
  }

  public void setBuildingfloorno(Integer buildingfloorno) {
    this.buildingfloorno = buildingfloorno;
  }
}
