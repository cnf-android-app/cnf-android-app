package com.cnf.domain.tasks;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OccInspection {

  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "inspectionid")
  private Integer inspectionId;
  @ColumnInfo(name = "occperiod_periodid")
  private Integer occPeriodId;
  @ColumnInfo(name = "inspector_userid")
  private Integer inspectorUserId;
  @ColumnInfo(name = "publicaccesscc")
  private Integer publicAccessCC;
  @ColumnInfo(name = "enablepacc")
  private boolean enablePACC;
  @ColumnInfo(name = "notespreinspection")
  private String notesPreInspection;
  @ColumnInfo(name = "thirdpartyinspector_personid")
  private Integer thirdPartyInspectorPersonId;
  @ColumnInfo(name = "thirdpartyinspectorapprovalts")
  private String thirdPartyInspectorApprovalTS;
  @ColumnInfo(name = "thirdpartyinspectorapprovalby")
  private Integer thirdPartyInspectorApprovalBy;
  @ColumnInfo(name = "maxoccupantsallowed")
  private Integer maxOccupantsAllowed;
  @ColumnInfo(name = "numbedrooms")
  private Integer numBedRooms;
  @ColumnInfo(name = "numbathrooms")
  private Integer numBathRooms;
  @ColumnInfo(name = "occchecklist_checklistlistid")
  private Integer occChecklistId;
  @ColumnInfo(name = "effectivedate")
  private String effectiveDate;
  @ColumnInfo(name = "createdts")
  private String createdTS;
  @ColumnInfo(name = "followupto_inspectionid")
  private Integer followUpToInspectionId;
  @ColumnInfo(name = "timestart")
  private String timeStart;
  @ColumnInfo(name = "timeend")
  private String timeEnd;
  @ColumnInfo(name = "determination_detid")
  private Integer determinationDetId;
  @ColumnInfo(name = "determinationby_userid")
  private Integer determinationByUserId;
  @ColumnInfo(name = "determinationts")
  private String determinationTS;
  @ColumnInfo(name = "remarks")
  private String remarks;
  @ColumnInfo(name = "generalcomments")
  private String generalComments;
  @ColumnInfo(name = "deactivatedts")
  private String deactivatedTS;
  @ColumnInfo(name = "deactivatedby_userid")
  private Integer deactivatedByUserId;
  @ColumnInfo(name = "occ_inspection_createdby_userid")
  private Integer createdByUserId;
  @ColumnInfo(name = "occ_inspection_lastupdatedts")
  private String lastUpdatedTS;
  @ColumnInfo(name = "occ_inspection_lastupdatedby_userid")
  private Integer lastUpdatedByUserId;
  @ColumnInfo(name = "cause_causeid")
  private Integer causeId;
  @ColumnInfo(name = "cecase_caseid")
  private Integer ceCaseId;

  private boolean isFinished;

  private boolean isInit;

  public Integer getInspectionId() {
    return inspectionId;
  }

  public void setInspectionId(Integer inspectionId) {
    this.inspectionId = inspectionId;
  }

  public Integer getOccPeriodId() {
    return occPeriodId;
  }

  public void setOccPeriodId(Integer occPeriodId) {
    this.occPeriodId = occPeriodId;
  }

  public Integer getInspectorUserId() {
    return inspectorUserId;
  }

  public void setInspectorUserId(Integer inspectorUserId) {
    this.inspectorUserId = inspectorUserId;
  }

  public Integer getPublicAccessCC() {
    return publicAccessCC;
  }

  public void setPublicAccessCC(Integer publicAccessCC) {
    this.publicAccessCC = publicAccessCC;
  }

  public boolean isEnablePACC() {
    return enablePACC;
  }

  public void setEnablePACC(boolean enablePACC) {
    this.enablePACC = enablePACC;
  }

  public String getNotesPreInspection() {
    return notesPreInspection;
  }

  public void setNotesPreInspection(String notesPreInspection) {
    this.notesPreInspection = notesPreInspection;
  }

  public Integer getThirdPartyInspectorPersonId() {
    return thirdPartyInspectorPersonId;
  }

  public void setThirdPartyInspectorPersonId(Integer thirdPartyInspectorPersonId) {
    this.thirdPartyInspectorPersonId = thirdPartyInspectorPersonId;
  }

  public String getThirdPartyInspectorApprovalTS() {
    return thirdPartyInspectorApprovalTS;
  }

  public void setThirdPartyInspectorApprovalTS(String thirdPartyInspectorApprovalTS) {
    this.thirdPartyInspectorApprovalTS = thirdPartyInspectorApprovalTS;
  }

  public Integer getThirdPartyInspectorApprovalBy() {
    return thirdPartyInspectorApprovalBy;
  }

  public void setThirdPartyInspectorApprovalBy(Integer thirdPartyInspectorApprovalBy) {
    this.thirdPartyInspectorApprovalBy = thirdPartyInspectorApprovalBy;
  }

  public Integer getMaxOccupantsAllowed() {
    return maxOccupantsAllowed;
  }

  public void setMaxOccupantsAllowed(Integer maxOccupantsAllowed) {
    this.maxOccupantsAllowed = maxOccupantsAllowed;
  }

  public Integer getNumBedRooms() {
    return numBedRooms;
  }

  public void setNumBedRooms(Integer numBedRooms) {
    this.numBedRooms = numBedRooms;
  }

  public Integer getNumBathRooms() {
    return numBathRooms;
  }

  public void setNumBathRooms(Integer numBathRooms) {
    this.numBathRooms = numBathRooms;
  }

  public Integer getOccChecklistId() {
    return occChecklistId;
  }

  public void setOccChecklistId(Integer occChecklistId) {
    this.occChecklistId = occChecklistId;
  }

  public String getEffectiveDate() {
    return effectiveDate;
  }

  public void setEffectiveDate(String effectiveDate) {
    this.effectiveDate = effectiveDate;
  }

  public String getCreatedTS() {
    return createdTS;
  }

  public void setCreatedTS(String createdTS) {
    this.createdTS = createdTS;
  }

  public Integer getFollowUpToInspectionId() {
    return followUpToInspectionId;
  }

  public void setFollowUpToInspectionId(Integer followUpToInspectionId) {
    this.followUpToInspectionId = followUpToInspectionId;
  }

  public String getTimeStart() {
    return timeStart;
  }

  public void setTimeStart(String timeStart) {
    this.timeStart = timeStart;
  }

  public String getTimeEnd() {
    return timeEnd;
  }

  public void setTimeEnd(String timeEnd) {
    this.timeEnd = timeEnd;
  }

  public Integer getDeterminationDetId() {
    return determinationDetId;
  }

  public void setDeterminationDetId(Integer determinationDetId) {
    this.determinationDetId = determinationDetId;
  }

  public Integer getDeterminationByUserId() {
    return determinationByUserId;
  }

  public void setDeterminationByUserId(Integer determinationByUserId) {
    this.determinationByUserId = determinationByUserId;
  }

  public String getDeterminationTS() {
    return determinationTS;
  }

  public void setDeterminationTS(String determinationTS) {
    this.determinationTS = determinationTS;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getGeneralComments() {
    return generalComments;
  }

  public void setGeneralComments(String generalComments) {
    this.generalComments = generalComments;
  }

  public String getDeactivatedTS() {
    return deactivatedTS;
  }

  public void setDeactivatedTS(String deactivatedTS) {
    this.deactivatedTS = deactivatedTS;
  }

  public Integer getDeactivatedByUserId() {
    return deactivatedByUserId;
  }

  public void setDeactivatedByUserId(Integer deactivatedByUserId) {
    this.deactivatedByUserId = deactivatedByUserId;
  }

  public Integer getCreatedByUserId() {
    return createdByUserId;
  }

  public void setCreatedByUserId(Integer createdByUserId) {
    this.createdByUserId = createdByUserId;
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

  public Integer getCauseId() {
    return causeId;
  }

  public void setCauseId(Integer causeId) {
    this.causeId = causeId;
  }


  public Integer getCeCaseId() {
    return ceCaseId;
  }

  public void setCeCaseId(Integer ceCaseId) {
    this.ceCaseId = ceCaseId;
  }

  public boolean isFinished() {
    return isFinished;
  }

  public void setFinished(boolean finished) {
    isFinished = finished;
  }

  public boolean isInit() {
    return isInit;
  }

  public void setInit(boolean init) {
    isInit = init;
  }

  @Override
  public String toString() {
    return "OccInspection{" +
        "inspectionId=" + inspectionId +
        ", occPeriodId=" + occPeriodId +
        ", inspectorUserId=" + inspectorUserId +
        ", publicAccessCC=" + publicAccessCC +
        ", enablePACC=" + enablePACC +
        ", notesPreInspection='" + notesPreInspection + '\'' +
        ", thirdPartyInspectorPersonId=" + thirdPartyInspectorPersonId +
        ", thirdPartyInspectorApprovalTS='" + thirdPartyInspectorApprovalTS + '\'' +
        ", thirdPartyInspectorApprovalBy=" + thirdPartyInspectorApprovalBy +
        ", maxOccupantsAllowed=" + maxOccupantsAllowed +
        ", numBedRooms=" + numBedRooms +
        ", numBathRooms=" + numBathRooms +
        ", occChecklistId=" + occChecklistId +
        ", effectiveDate='" + effectiveDate + '\'' +
        ", createdTS='" + createdTS + '\'' +
        ", followUpToInspectionId=" + followUpToInspectionId +
        ", timeStart='" + timeStart + '\'' +
        ", timeEnd='" + timeEnd + '\'' +
        ", determinationDetId=" + determinationDetId +
        ", determinationByUserId=" + determinationByUserId +
        ", determinationTS='" + determinationTS + '\'' +
        ", remarks='" + remarks + '\'' +
        ", generalComments='" + generalComments + '\'' +
        ", deactivatedTS='" + deactivatedTS + '\'' +
        ", deactivatedByUserId=" + deactivatedByUserId +
        ", createdByUserId=" + createdByUserId +
        ", lastUpdatedTS='" + lastUpdatedTS + '\'' +
        ", lastUpdatedByUserId=" + lastUpdatedByUserId +
        ", causeId=" + causeId +
        ", ceCaseId=" + ceCaseId +
        '}';
  }
}


