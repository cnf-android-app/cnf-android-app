package com.cnf.module_inspection.entity.infra;

import java.util.List;

public class OccInspectionInfra {

  private List<Municipality> municipalityList;
  private List<CodeSource> codeSourceList;
  private List<CodeElement> codeElementList;
  private List<CodeSetElement> codeSetElementList;
  private List<CodeElementGuide> codeElementGuideList;
  private List<OccCheckList> occCheckListList;
  private List<OccChecklistSpaceType> occChecklistSpaceTypeList;
  private List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList;
  private List<OccSpaceType> occSpaceTypeList;
  private List<OccLocationDescription> occLocationDescriptionList;
  private List<IntensityClass> intensityClassList;

  public List<Municipality> getMunicipalityList() {
    return municipalityList;
  }

  public void setMunicipalityList(List<Municipality> municipalityList) {
    this.municipalityList = municipalityList;
  }

  public List<CodeSource> getCodeSourceList() {
    return codeSourceList;
  }

  public void setCodeSourceList(List<CodeSource> codeSourceList) {
    this.codeSourceList = codeSourceList;
  }

  public List<CodeElement> getCodeElementList() {
    return codeElementList;
  }

  public void setCodeElementList(List<CodeElement> codeElementList) {
    this.codeElementList = codeElementList;
  }

  public List<CodeSetElement> getCodeSetElementList() {
    return codeSetElementList;
  }

  public void setCodeSetElementList(List<CodeSetElement> codeSetElementList) {
    this.codeSetElementList = codeSetElementList;
  }

  public List<CodeElementGuide> getCodeElementGuideList() {
    return codeElementGuideList;
  }

  public void setCodeElementGuideList(List<CodeElementGuide> codeElementGuideList) {
    this.codeElementGuideList = codeElementGuideList;
  }

  public List<OccCheckList> getOccCheckListList() {
    return occCheckListList;
  }

  public void setOccCheckListList(List<OccCheckList> occCheckListList) {
    this.occCheckListList = occCheckListList;
  }

  public List<OccChecklistSpaceType> getOccChecklistSpaceTypeList() {
    return occChecklistSpaceTypeList;
  }

  public void setOccChecklistSpaceTypeList(List<OccChecklistSpaceType> occChecklistSpaceTypeList) {
    this.occChecklistSpaceTypeList = occChecklistSpaceTypeList;
  }

  public List<OccChecklistSpaceTypeElement> getOccChecklistSpaceTypeElementList() {
    return occChecklistSpaceTypeElementList;
  }

  public void setOccChecklistSpaceTypeElementList(List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList) {
    this.occChecklistSpaceTypeElementList = occChecklistSpaceTypeElementList;
  }

  public List<OccSpaceType> getOccSpaceTypeList() {
    return occSpaceTypeList;
  }

  public void setOccSpaceTypeList(List<OccSpaceType> occSpaceTypeList) {
    this.occSpaceTypeList = occSpaceTypeList;
  }

  public List<OccLocationDescription> getOccLocationDescriptionList() {
    return occLocationDescriptionList;
  }

  public void setOccLocationDescriptionList(List<OccLocationDescription> occLocationDescriptionList) {
    this.occLocationDescriptionList = occLocationDescriptionList;
  }

  public List<IntensityClass> getIntensityClassList() {
    return intensityClassList;
  }

  public void setIntensityClassList(List<IntensityClass> intensityClassList) {
    this.intensityClassList = intensityClassList;
  }

  @Override
  public String toString() {
    return "OccInspectionInfra{" +
        "municipalityList=" + municipalityList +
        ", codeSourceList=" + codeSourceList +
        ", codeElementList=" + codeElementList +
        ", codeSetElementList=" + codeSetElementList +
        ", codeElementGuideList=" + codeElementGuideList +
        ", occCheckListList=" + occCheckListList +
        ", occChecklistSpaceTypeList=" + occChecklistSpaceTypeList +
        ", occChecklistSpaceTypeElementList=" + occChecklistSpaceTypeElementList +
        ", occSpaceTypeList=" + occSpaceTypeList +
        ", occLocationDescriptionList=" + occLocationDescriptionList +
        ", intensityClassList=" + intensityClassList +
        '}';
  }
}
