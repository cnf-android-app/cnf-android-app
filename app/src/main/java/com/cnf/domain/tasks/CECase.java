package com.cnf.domain.tasks;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CECase {

  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "caseid")
  private Integer caseId;
  @ColumnInfo(name = "cecasepubliccc")
  private Integer ceCasePubliccc;
  @ColumnInfo(name = "cecase_property_propertyid")
  private Integer propertyId;
  @ColumnInfo(name = "login_userid")
  private Integer loginUserId;
  @ColumnInfo(name = "casename")
  private String caseName;
  @ColumnInfo(name = "originationdate")
  private String originationDate;
  @ColumnInfo(name = "closingdate")
  private String closingDate;
  @ColumnInfo(name = "creationtimestamp")
  private String creationTS;
  @ColumnInfo(name = "cecase_notes")
  private String notes;
  @ColumnInfo(name = "paccenabled")
  private Boolean paccEnabled;
  @ColumnInfo(name = "allowuplinkaccess")
  private Boolean allowUpLinkAccess;
  @ColumnInfo(name = "propertyinfocase")
  private Boolean propertyInfoCase;
  @ColumnInfo(name = "personinfocase_personid")
  private Integer personInfoCasePersonId;
  @ColumnInfo(name = "bobsource_sourceid")
  private Integer bobSourceId;
  @ColumnInfo(name = "cecase_active")
  private Boolean active;
  @ColumnInfo(name = "cecase_lastupdatedby_userid")
  private Integer lastUpdatedByUserId;
  @ColumnInfo(name = "cecase_lastupdatedts")
  private String lastUpdatedTS;
  @ColumnInfo(name = "parcel_parcelkey")
  private Integer parcelKey;
  @ColumnInfo(name = "cecase_parcelunit_unitid")
  private Integer parcelUnitId;


  public Integer getCaseId() {
    return caseId;
  }

  public void setCaseId(Integer caseId) {
    this.caseId = caseId;
  }

  public Integer getCeCasePubliccc() {
    return ceCasePubliccc;
  }

  public void setCeCasePubliccc(Integer ceCasePubliccc) {
    this.ceCasePubliccc = ceCasePubliccc;
  }

  public Integer getPropertyId() {
    return propertyId;
  }

  public void setPropertyId(Integer propertyId) {
    this.propertyId = propertyId;
  }

  public Integer getLoginUserId() {
    return loginUserId;
  }

  public void setLoginUserId(Integer loginUserId) {
    this.loginUserId = loginUserId;
  }

  public String getCaseName() {
    return caseName;
  }

  public void setCaseName(String caseName) {
    this.caseName = caseName;
  }

  public String getOriginationDate() {
    return originationDate;
  }

  public void setOriginationDate(String originationDate) {
    this.originationDate = originationDate;
  }

  public String getClosingDate() {
    return closingDate;
  }

  public void setClosingDate(String closingDate) {
    this.closingDate = closingDate;
  }

  public String getCreationTS() {
    return creationTS;
  }

  public void setCreationTS(String creationTS) {
    this.creationTS = creationTS;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Boolean getPaccEnabled() {
    return paccEnabled;
  }

  public void setPaccEnabled(Boolean paccEnabled) {
    this.paccEnabled = paccEnabled;
  }

  public Boolean getAllowUpLinkAccess() {
    return allowUpLinkAccess;
  }

  public void setAllowUpLinkAccess(Boolean allowUpLinkAccess) {
    this.allowUpLinkAccess = allowUpLinkAccess;
  }

  public Boolean getPropertyInfoCase() {
    return propertyInfoCase;
  }

  public void setPropertyInfoCase(Boolean propertyInfoCase) {
    this.propertyInfoCase = propertyInfoCase;
  }

  public Integer getPersonInfoCasePersonId() {
    return personInfoCasePersonId;
  }

  public void setPersonInfoCasePersonId(Integer personInfoCasePersonId) {
    this.personInfoCasePersonId = personInfoCasePersonId;
  }

  public Integer getBobSourceId() {
    return bobSourceId;
  }

  public void setBobSourceId(Integer bobSourceId) {
    this.bobSourceId = bobSourceId;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Integer getLastUpdatedByUserId() {
    return lastUpdatedByUserId;
  }

  public void setLastUpdatedByUserId(Integer lastUpdatedByUserId) {
    this.lastUpdatedByUserId = lastUpdatedByUserId;
  }

  public String getLastUpdatedTS() {
    return lastUpdatedTS;
  }

  public void setLastUpdatedTS(String lastUpdatedTS) {
    this.lastUpdatedTS = lastUpdatedTS;
  }

  public Integer getParcelKey() {
    return parcelKey;
  }

  public void setParcelKey(Integer parcelKey) {
    this.parcelKey = parcelKey;
  }

  public Integer getParcelUnitId() {
    return parcelUnitId;
  }

  public void setParcelUnitId(Integer parcelUnitId) {
    this.parcelUnitId = parcelUnitId;
  }

  @Override
  public String toString() {
    return "CECase{" +
        "caseId=" + caseId +
        ", ceCasePubliccc=" + ceCasePubliccc +
        ", propertyId=" + propertyId +
        ", loginUserId=" + loginUserId +
        ", caseName='" + caseName + '\'' +
        ", originationDate='" + originationDate + '\'' +
        ", closingDate='" + closingDate + '\'' +
        ", creationTS='" + creationTS + '\'' +
        ", notes='" + notes + '\'' +
        ", paccEnabled=" + paccEnabled +
        ", allowUpLinkAccess=" + allowUpLinkAccess +
        ", propertyInfoCase=" + propertyInfoCase +
        ", personInfoCasePersonId=" + personInfoCasePersonId +
        ", bobSourceId=" + bobSourceId +
        ", active=" + active +
        ", lastUpdatedByUserId=" + lastUpdatedByUserId +
        ", lastUpdatedTS='" + lastUpdatedTS + '\'' +
        ", parcelKey=" + parcelKey +
        ", parcelUnitId=" + parcelUnitId +
        '}';
  }
}
