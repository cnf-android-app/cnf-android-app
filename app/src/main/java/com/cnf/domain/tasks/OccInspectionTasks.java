package com.cnf.domain.tasks;

import java.util.List;

public class OccInspectionTasks {

  private List<OccInspectionDispatch> occInspectionDispatchList;
  private List<OccInspection> occInspectionList;
  private List<CECase> ceCaseList;
  private List<Property> propertyList;
  private List<Login> loginList;
  private List<Person> personList;

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

  @Override
  public String toString() {
    return "OccInspectionTasks{" +
        "occInspectionDispatchList=" + occInspectionDispatchList +
        ", occInspectionList=" + occInspectionList +
        ", ceCaseList=" + ceCaseList +
        ", propertyList=" + propertyList +
        ", loginList=" + loginList +
        ", personList=" + personList +
        '}';
  }
}
