package com.cnf.domain;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.cnf.domain.infra.OccChecklistSpaceType;
import com.cnf.domain.infra.OccLocationDescription;
import com.cnf.domain.infra.OccSpaceType;

import java.util.List;
import lombok.Data;

@Data
@Entity
public class OccInspectedSpaceHeavy {

  @Embedded
  private OccInspectedSpace occInspectedSpace;
  @Embedded
  private OccChecklistSpaceType occChecklistSpaceType;
  @Embedded
  private OccSpaceType occSpaceType;
  @Embedded
  private OccLocationDescription occLocationDescription;

  @Ignore
  private List<OccInspectedSpaceElement> occInspectedSpaceElementList;

  public OccInspectedSpace getOccInspectedSpace() {
    return occInspectedSpace;
  }

  public void setOccInspectedSpace(OccInspectedSpace occInspectedSpace) {
    this.occInspectedSpace = occInspectedSpace;
  }

  public OccChecklistSpaceType getOccChecklistSpaceType() {
    return occChecklistSpaceType;
  }

  public void setOccChecklistSpaceType(OccChecklistSpaceType occChecklistSpaceType) {
    this.occChecklistSpaceType = occChecklistSpaceType;
  }

  public OccSpaceType getOccSpaceType() {
    return occSpaceType;
  }

  public void setOccSpaceType(OccSpaceType occSpaceType) {
    this.occSpaceType = occSpaceType;
  }

  public OccLocationDescription getOccLocationDescription() {
    return occLocationDescription;
  }

  public void setOccLocationDescription(OccLocationDescription occLocationDescription) {
    this.occLocationDescription = occLocationDescription;
  }

  public List<OccInspectedSpaceElement> getOccInspectedSpaceElementList() {
    return occInspectedSpaceElementList;
  }

  public void setOccInspectedSpaceElementList(List<OccInspectedSpaceElement> occInspectedSpaceElementList) {
    this.occInspectedSpaceElementList = occInspectedSpaceElementList;
  }

  @Override
  public String toString() {
    return "OccInspectedSpaceHeavy{" +
        "occInspectedSpace=" + occInspectedSpace +
        ", occChecklistSpaceType=" + occChecklistSpaceType +
        ", occSpaceType=" + occSpaceType +
        ", occLocationDescription=" + occLocationDescription +
        ", occInspectedSpaceElementList=" + occInspectedSpaceElementList +
        '}';
  }
}
