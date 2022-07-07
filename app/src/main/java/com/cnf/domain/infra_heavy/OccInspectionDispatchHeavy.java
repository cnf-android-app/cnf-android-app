package com.cnf.domain.infra_heavy;

import androidx.room.Embedded;
import androidx.room.Entity;
import com.cnf.domain.infra.OccCheckList;
import com.cnf.domain.tasks.CECase;
import com.cnf.domain.tasks.Login;
import com.cnf.domain.tasks.OccInspection;
import com.cnf.domain.tasks.OccInspectionDispatch;
import com.cnf.domain.tasks.OccPeriod;
import com.cnf.domain.tasks.Person;
import com.cnf.domain.tasks.Property;
import com.cnf.domain.tasks.PropertyUnit;

@Entity
public class OccInspectionDispatchHeavy {

  @Embedded
  private OccInspectionDispatch occInspectionDispatch;
  @Embedded
  private OccInspection occInspection;
  @Embedded
  private CECase ceCase;
  @Embedded
  private Property property;
  @Embedded
  private Login login;
  @Embedded
  private Person person;
  @Embedded
  private OccCheckList occCheckList;

  public OccInspectionDispatch getOccInspectionDispatch() {
    return occInspectionDispatch;
  }

  public void setOccInspectionDispatch(OccInspectionDispatch occInspectionDispatch) {
    this.occInspectionDispatch = occInspectionDispatch;
  }

  public OccInspection getOccInspection() {
    return occInspection;
  }

  public void setOccInspection(OccInspection occInspection) {
    this.occInspection = occInspection;
  }

  public CECase getCeCase() {
    return ceCase;
  }

  public void setCeCase(CECase ceCase) {
    this.ceCase = ceCase;
  }

  public Property getProperty() {
    return property;
  }

  public void setProperty(Property property) {
    this.property = property;
  }

  public Login getLogin() {
    return login;
  }

  public void setLogin(Login login) {
    this.login = login;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public OccCheckList getOccCheckList() {
    return occCheckList;
  }

  public void setOccCheckList(OccCheckList occCheckList) {
    this.occCheckList = occCheckList;
  }

  @Override
  public String toString() {
    return "OccInspectionDispatchHeavy{" +
        "occInspectionDispatch=" + occInspectionDispatch +
        ", occInspection=" + occInspection +
        ", ceCase=" + ceCase +
        ", property=" + property +
        ", login=" + login +
        ", person=" + person +
        ", occCheckList=" + occCheckList +
        '}';
  }
}
