package com.cnf.domain.tasks;

import com.cnf.domain.BlobBytes;
import com.cnf.domain.OccInspectedSpace;
import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.domain.OccInspectedSpaceElementPhotoDoc;
import com.cnf.domain.PhotoDoc;
import java.util.List;

public class OccInspectionTasks {

  private List<OccInspectionDispatch> occInspectionDispatchList;
  private List<OccInspection> occInspectionList;
  private List<CECase> ceCaseList;
  private List<Property> propertyList;
  private List<Login> loginList;
  private List<Person> personList;
  private List<OccInspectedSpace> occInspectedSpaceList;
  private List<OccInspectedSpaceElement> occInspectedSpaceElementList;
  private List<OccInspectedSpaceElementPhotoDoc> occInspectedSpaceElementPhotoDocList;
  private List<PhotoDoc> photoDocList;
  private List<BlobBytes> blobBytesList;

  public List<OccInspectionDispatch> getOccInspectionDispatchList() {
    return occInspectionDispatchList;
  }

  public void setOccInspectionDispatchList(List<OccInspectionDispatch> occInspectionDispatchList) {
    this.occInspectionDispatchList = occInspectionDispatchList;
  }

  public List<OccInspection> getOccInspectionList() {
    return occInspectionList;
  }

  public void setOccInspectionList(List<OccInspection> occInspectionList) {
    this.occInspectionList = occInspectionList;
  }

  public List<CECase> getCeCaseList() {
    return ceCaseList;
  }

  public void setCeCaseList(List<CECase> ceCaseList) {
    this.ceCaseList = ceCaseList;
  }

  public List<Property> getPropertyList() {
    return propertyList;
  }

  public void setPropertyList(List<Property> propertyList) {
    this.propertyList = propertyList;
  }

  public List<Login> getLoginList() {
    return loginList;
  }

  public void setLoginList(List<Login> loginList) {
    this.loginList = loginList;
  }

  public List<Person> getPersonList() {
    return personList;
  }

  public void setPersonList(List<Person> personList) {
    this.personList = personList;
  }

  public List<OccInspectedSpace> getOccInspectedSpaceList() {
    return occInspectedSpaceList;
  }

  public void setOccInspectedSpaceList(List<OccInspectedSpace> occInspectedSpaceList) {
    this.occInspectedSpaceList = occInspectedSpaceList;
  }

  public List<OccInspectedSpaceElement> getOccInspectedSpaceElementList() {
    return occInspectedSpaceElementList;
  }

  public void setOccInspectedSpaceElementList(List<OccInspectedSpaceElement> occInspectedSpaceElementList) {
    this.occInspectedSpaceElementList = occInspectedSpaceElementList;
  }

  public List<OccInspectedSpaceElementPhotoDoc> getOccInspectedSpaceElementPhotoDocList() {
    return occInspectedSpaceElementPhotoDocList;
  }

  public void setOccInspectedSpaceElementPhotoDocList(List<OccInspectedSpaceElementPhotoDoc> occInspectedSpaceElementPhotoDocList) {
    this.occInspectedSpaceElementPhotoDocList = occInspectedSpaceElementPhotoDocList;
  }

  public List<PhotoDoc> getPhotoDocList() {
    return photoDocList;
  }

  public void setPhotoDocList(List<PhotoDoc> photoDocList) {
    this.photoDocList = photoDocList;
  }

  public List<BlobBytes> getBlobBytesList() {
    return blobBytesList;
  }

  public void setBlobBytesList(List<BlobBytes> blobBytesList) {
    this.blobBytesList = blobBytesList;
  }

  @Override
  public String toString() {
    return "OccInspectionTasks{" +
        "occInspectionDispatchList=" + occInspectionDispatchList +
        ", occInspectionList=" + occInspectionList +
        ", ceCaseList=" + ceCaseList +
        ", propertyList=" + propertyList +
        ", loginList=" + loginList +
        ", personList=" + personList +
        ", occInspectedSpaceList=" + occInspectedSpaceList +
        ", occInspectedSpaceElementList=" + occInspectedSpaceElementList +
        ", occInspectedSpaceElementPhotoDocList=" + occInspectedSpaceElementPhotoDocList +
        ", photoDocList=" + photoDocList +
        ", blobBytesList=" + blobBytesList +
        '}';
  }
}
