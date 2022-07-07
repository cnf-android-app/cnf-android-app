package com.cnf.domain.tasks;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OccPeriod {

  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "periodid")
  private Integer periodId;
  @ColumnInfo(name = "source_sourceid")
  private Integer sourceId;
  @ColumnInfo(name = "propertyunit_unitid")
  private Integer propertyUnitId;
  @ColumnInfo(name = "occ_period_createdts")
  private String createdTS;
  @ColumnInfo(name = "type_typeid")
  private Integer typeId;
  @ColumnInfo(name = "typecertifiedby_userid")
  private Integer typeCertifiedByUserId;
  @ColumnInfo(name = "typecertifiedts")
  private String typeCertifiedTS;
  @ColumnInfo(name = "startdate")
  private String startDate;
  @ColumnInfo(name = "startdatecertifiedby_userid")
  private Integer startDateCertifiedByUserId;
  @ColumnInfo(name = "startdatecertifiedts")
  private String startDateCertifiedTS;
  @ColumnInfo(name = "enddate")
  private String endDate;
  @ColumnInfo(name = "enddatecertifiedby_userid")
  private Integer endDateCertifiedByUserId;
  @ColumnInfo(name = "enddatecterifiedts")
  private String endDateCertifiedTS;
  @ColumnInfo(name = "manager_userid")
  private Integer managerUserId;
  @ColumnInfo(name = "authorizationts")
  private String authorizationTS;
  @ColumnInfo(name = "authorizedby_userid")
  private Integer authorizedByUserId;
  @ColumnInfo(name = "occ_period_notes")
  private String notes;
  @ColumnInfo(name = "occ_period_createdby_userid")
  private Integer createdByUserId;
  @ColumnInfo(name = "overrideperiodtypeconfig")
  private Boolean overridePeriodTypeConfig;
  @ColumnInfo(name = "occ_period_active")
  private Boolean active;
  @ColumnInfo(name = "occ_period_lastupdatedts")
  private String lastUpdatedTS;
  @ColumnInfo(name = "occ_period_lastupdatedby_userid")
  private Integer lastUpdatedByUserId;
  @ColumnInfo(name = "parcelunit_unitid")
  private Integer parcelUnitId;
  @ColumnInfo(name = "occ_period_deactivatedts")
  private String deActivatedTS;

  public Integer getPeriodId() {
    return periodId;
  }

  public void setPeriodId(Integer periodId) {
    this.periodId = periodId;
  }

  public Integer getSourceId() {
    return sourceId;
  }

  public void setSourceId(Integer sourceId) {
    this.sourceId = sourceId;
  }

  public Integer getPropertyUnitId() {
    return propertyUnitId;
  }

  public void setPropertyUnitId(Integer propertyUnitId) {
    this.propertyUnitId = propertyUnitId;
  }

  public String getCreatedTS() {
    return createdTS;
  }

  public void setCreatedTS(String createdTS) {
    this.createdTS = createdTS;
  }

  public Integer getTypeId() {
    return typeId;
  }

  public void setTypeId(Integer typeId) {
    this.typeId = typeId;
  }

  public Integer getTypeCertifiedByUserId() {
    return typeCertifiedByUserId;
  }

  public void setTypeCertifiedByUserId(Integer typeCertifiedByUserId) {
    this.typeCertifiedByUserId = typeCertifiedByUserId;
  }

  public String getTypeCertifiedTS() {
    return typeCertifiedTS;
  }

  public void setTypeCertifiedTS(String typeCertifiedTS) {
    this.typeCertifiedTS = typeCertifiedTS;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public Integer getStartDateCertifiedByUserId() {
    return startDateCertifiedByUserId;
  }

  public void setStartDateCertifiedByUserId(Integer startDateCertifiedByUserId) {
    this.startDateCertifiedByUserId = startDateCertifiedByUserId;
  }

  public String getStartDateCertifiedTS() {
    return startDateCertifiedTS;
  }

  public void setStartDateCertifiedTS(String startDateCertifiedTS) {
    this.startDateCertifiedTS = startDateCertifiedTS;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public Integer getEndDateCertifiedByUserId() {
    return endDateCertifiedByUserId;
  }

  public void setEndDateCertifiedByUserId(Integer endDateCertifiedByUserId) {
    this.endDateCertifiedByUserId = endDateCertifiedByUserId;
  }

  public String getEndDateCertifiedTS() {
    return endDateCertifiedTS;
  }

  public void setEndDateCertifiedTS(String endDateCertifiedTS) {
    this.endDateCertifiedTS = endDateCertifiedTS;
  }

  public Integer getManagerUserId() {
    return managerUserId;
  }

  public void setManagerUserId(Integer managerUserId) {
    this.managerUserId = managerUserId;
  }

  public String getAuthorizationTS() {
    return authorizationTS;
  }

  public void setAuthorizationTS(String authorizationTS) {
    this.authorizationTS = authorizationTS;
  }

  public Integer getAuthorizedByUserId() {
    return authorizedByUserId;
  }

  public void setAuthorizedByUserId(Integer authorizedByUserId) {
    this.authorizedByUserId = authorizedByUserId;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Integer getCreatedByUserId() {
    return createdByUserId;
  }

  public void setCreatedByUserId(Integer createdByUserId) {
    this.createdByUserId = createdByUserId;
  }

  public Boolean getOverridePeriodTypeConfig() {
    return overridePeriodTypeConfig;
  }

  public void setOverridePeriodTypeConfig(Boolean overridePeriodTypeConfig) {
    this.overridePeriodTypeConfig = overridePeriodTypeConfig;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public String getLastUpdatedTS() {
    return lastUpdatedTS;
  }

  public void setLastUpdatedTS(String lastUpdatedTS) {
    this.lastUpdatedTS = lastUpdatedTS;
  }

  public Integer getLastUpdatedByUserId() {
    return lastUpdatedByUserId;
  }

  public void setLastUpdatedByUserId(Integer lastUpdatedByUserId) {
    this.lastUpdatedByUserId = lastUpdatedByUserId;
  }

  public Integer getParcelUnitId() {
    return parcelUnitId;
  }

  public void setParcelUnitId(Integer parcelUnitId) {
    this.parcelUnitId = parcelUnitId;
  }

  public String getDeActivatedTS() {
    return deActivatedTS;
  }

  public void setDeActivatedTS(String deActivatedTS) {
    this.deActivatedTS = deActivatedTS;
  }

  @Override
  public String toString() {
    return "OccPeriod{" +
        "periodId=" + periodId +
        ", sourceId=" + sourceId +
        ", propertyUnitId=" + propertyUnitId +
        ", createdTS='" + createdTS + '\'' +
        ", typeId=" + typeId +
        ", typeCertifiedByUserId=" + typeCertifiedByUserId +
        ", typeCertifiedTS='" + typeCertifiedTS + '\'' +
        ", startDate='" + startDate + '\'' +
        ", startDateCertifiedByUserId=" + startDateCertifiedByUserId +
        ", startDateCertifiedTS='" + startDateCertifiedTS + '\'' +
        ", endDate='" + endDate + '\'' +
        ", endDateCertifiedByUserId=" + endDateCertifiedByUserId +
        ", endDateCertifiedTS='" + endDateCertifiedTS + '\'' +
        ", managerUserId=" + managerUserId +
        ", authorizationTS='" + authorizationTS + '\'' +
        ", authorizedByUserId=" + authorizedByUserId +
        ", notes='" + notes + '\'' +
        ", createdByUserId=" + createdByUserId +
        ", overridePeriodTypeConfig=" + overridePeriodTypeConfig +
        ", active=" + active +
        ", lastUpdatedTS='" + lastUpdatedTS + '\'' +
        ", lastUpdatedByUserId=" + lastUpdatedByUserId +
        ", parcelUnitId=" + parcelUnitId +
        ", deActivatedTS='" + deActivatedTS + '\'' +
        '}';
  }
}
