package com.cnf.domain;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.cnf.domain.infra.CodeElement;
import com.cnf.domain.infra.CodeSetElement;
import com.cnf.domain.infra.CodeSource;
import com.cnf.domain.infra.OccChecklistSpaceTypeElement;

import java.util.List;

@Entity
public class OccInspectedSpaceElementHeavy {

  @Embedded
  private OccInspectedSpaceElement occInspectedSpaceElement;

  @Embedded
  private CodeElement codeElement;

  @Embedded
  private OccChecklistSpaceTypeElement occChecklistSpaceTypeElement;

  @Embedded
  private CodeSource codeSource;

  @Embedded
  private CodeSetElement codeSetElement;

  @Ignore
  private boolean passExpand;

  @Ignore
  private boolean violationExpand;

  @Ignore
  private boolean notInspectedExpand;

  @Ignore
  List<BlobBytes> blobBytesList;

  public List<BlobBytes> getBlobBytesList() {
    return blobBytesList;
  }

  public void setBlobBytesList(List<BlobBytes> blobBytesList) {
    this.blobBytesList = blobBytesList;
  }

  public boolean isPassExpand() {
    return passExpand;
  }

  public void setPassExpand(boolean passExpand) {
    if (passExpand) {
      this.violationExpand = false;
      this.notInspectedExpand = false;

    }
    this.passExpand = passExpand;
  }

  public boolean isViolationExpand() {
    return violationExpand;
  }

  public void setViolationExpand(boolean violationExpand) {
    if (violationExpand) {
      this.passExpand = false;
      this.notInspectedExpand = false;
    }
    this.violationExpand = violationExpand;
  }

  public boolean isNotInspectedExpand() {
    return notInspectedExpand;
  }

  public void setNotInspectedExpand(boolean notInspectedExpand) {
    if (notInspectedExpand) {
      this.passExpand = false;
      this.violationExpand = false;
    }
    this.notInspectedExpand = notInspectedExpand;
  }

  public OccInspectedSpaceElement getOccInspectedSpaceElement() {
    return occInspectedSpaceElement;
  }

  public void setOccInspectedSpaceElement(OccInspectedSpaceElement occInspectedSpaceElement) {
    this.occInspectedSpaceElement = occInspectedSpaceElement;
  }

  public CodeElement getCodeElement() {
    return codeElement;
  }

  public void setCodeElement(CodeElement codeElement) {
    this.codeElement = codeElement;
  }

  public OccChecklistSpaceTypeElement getOccChecklistSpaceTypeElement() {
    return occChecklistSpaceTypeElement;
  }

  public void setOccChecklistSpaceTypeElement(OccChecklistSpaceTypeElement occChecklistSpaceTypeElement) {
    this.occChecklistSpaceTypeElement = occChecklistSpaceTypeElement;
  }

  public CodeSource getCodeSource() {
    return codeSource;
  }

  public void setCodeSource(CodeSource codeSource) {
    this.codeSource = codeSource;
  }

  public CodeSetElement getCodeSetElement() {
    return codeSetElement;
  }

  public void setCodeSetElement(CodeSetElement codeSetElement) {
    this.codeSetElement = codeSetElement;
  }

}
