package com.cnf.module_inspection.entity.infra_heavy;

import androidx.room.Embedded;
import com.cnf.module_inspection.entity.infra.OccCheckList;
import com.cnf.module_inspection.entity.infra.OccChecklistSpaceType;
import com.cnf.module_inspection.entity.infra.OccSpaceType;
import lombok.Data;

@Data
public class OccChecklistSpaceTypeHeavy {

  @Embedded
  private OccChecklistSpaceType occChecklistSpaceType;
  @Embedded
  private OccCheckList occCheckList;
  @Embedded
  private OccSpaceType occSpaceType;

  public OccChecklistSpaceType getOccChecklistSpaceType() {
    return occChecklistSpaceType;
  }

  public void setOccChecklistSpaceType(OccChecklistSpaceType occChecklistSpaceType) {
    this.occChecklistSpaceType = occChecklistSpaceType;
  }

  public OccCheckList getOccCheckList() {
    return occCheckList;
  }

  public void setOccCheckList(OccCheckList occCheckList) {
    this.occCheckList = occCheckList;
  }

  public OccSpaceType getOccSpaceType() {
    return occSpaceType;
  }

  public void setOccSpaceType(OccSpaceType occSpaceType) {
    this.occSpaceType = occSpaceType;
  }

  @Override
  public String toString() {
    return "OccChecklistSpaceTypeHeavy{" +
        "occChecklistSpaceType=" + occChecklistSpaceType +
        ", occCheckList=" + occCheckList +
        ", occSpaceType=" + occSpaceType +
        '}';
  }
}
