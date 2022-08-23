package com.cnf.module_inspection.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.UUID;

@Entity
public class OccInspectedSpaceElement {

  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "inspectedspaceelementid")
  @NonNull
  private String inspectedSpaceElementId = UUID.randomUUID().toString();
  @ColumnInfo(name = "notes")
  private String notes;
  @ColumnInfo(name = "locationdescription_id")
  private Integer locationDescriptionId;
  @ColumnInfo(name = "lastinspectedby_userid")
  private Integer lastInspectedByUserId;
  @ColumnInfo(name = "lastinspectedts")
  private String lastInspectedTS;
  @ColumnInfo(name = "compliancegrantedby_userid")
  private Integer complianceGrantedByUserId;
  @ColumnInfo(name = "compliancegrantedts")
  private String complianceGrantedTS;
  @ColumnInfo(name = "inspectedspace_inspectedspaceid")
  private String inspectedSpaceId;
  @ColumnInfo(name = "overriderequiredflagnotinspected_userid")
  private Integer overrideRequiredFlagNotInspectedUserid;
  @ColumnInfo(name = "occchecklistspacetypeelement_elementid")
  private Integer occChecklistSpaceTypeElementId;
  @ColumnInfo(name = "failureseverity_intensityclassid")
  private Integer failureSeverityIntensityClassId;
  @ColumnInfo(name = "migratetocecaseonfail")
  private Boolean migrateToCECaseOnFail;
  @ColumnInfo(name = "transferredts")
  private String transferredTS;//
  @ColumnInfo(name = "transferredby_userid")
  private Integer transferredByUserId;//
  @ColumnInfo(name = "transferredtocecase_caseid")
  private Integer transferredToCECaseId;//

  @Ignore
  private OccInspectableStatus status;

  @Ignore
  private boolean isToUseDefaultDescription;

  public OccInspectedSpaceElement() {
  }

  private OccInspectedSpaceElement(Builder builder) {
    this.inspectedSpaceElementId = builder.inspectedSpaceElementId;
    this.notes = builder.notes;
    this.locationDescriptionId = builder.locationDescriptionId;
    this.lastInspectedByUserId = builder.lastInspectedByUserId;
    this.lastInspectedTS = builder.lastInspectedTS;
    this.complianceGrantedByUserId = builder.complianceGrantedByUserId;
    this.complianceGrantedTS = builder.complianceGrantedTS;
    this.inspectedSpaceId = builder.inspectedSpaceId;
    this.overrideRequiredFlagNotInspectedUserid = builder.overrideRequiredFlagNotInspectedUserid;
    this.occChecklistSpaceTypeElementId = builder.occChecklistSpaceTypeElementId;
    this.failureSeverityIntensityClassId = builder.failureSeverityIntensityClassId;
    this.migrateToCECaseOnFail = builder.migrateToCECaseOnFail;
    this.transferredTS = builder.transferredTS;
    this.transferredByUserId = builder.transferredByUserId;
    this.transferredToCECaseId = builder.transferredToCECaseId;
    this.status = builder.status;
  }

  public static class Builder {

    private String inspectedSpaceElementId;
    private String notes;
    private Integer locationDescriptionId;
    private Integer lastInspectedByUserId;
    private String lastInspectedTS;
    private Integer complianceGrantedByUserId;
    private String complianceGrantedTS;
    private String inspectedSpaceId;
    private Integer overrideRequiredFlagNotInspectedUserid;
    private Integer occChecklistSpaceTypeElementId;
    private Integer failureSeverityIntensityClassId;
    private Boolean migrateToCECaseOnFail;
    private String transferredTS;
    private Integer transferredByUserId;
    private Integer transferredToCECaseId;
    private OccInspectableStatus status;

    public Builder inspectedSpaceElementId(String inspectedSpaceElementId) {
      this.inspectedSpaceElementId = inspectedSpaceElementId;
      return this;
    }

    public Builder notes(String notes) {
      this.notes = notes;
      return this;
    }

    public Builder locationDescriptionId(Integer locationDescriptionId) {
      this.locationDescriptionId = locationDescriptionId;
      return this;
    }

    public Builder lastInspectedByUserId(Integer lastInspectedByUserId) {
      this.lastInspectedByUserId = lastInspectedByUserId;
      return this;
    }

    public Builder lastInspectedTS(String lastInspectedTS) {
      this.lastInspectedTS = lastInspectedTS;
      return this;
    }

    public Builder complianceGrantedByUserId(Integer complianceGrantedByUserId) {
      this.complianceGrantedByUserId = complianceGrantedByUserId;
      return this;

    }

    public Builder complianceGrantedTS(String complianceGrantedTS) {
      this.complianceGrantedTS = complianceGrantedTS;
      return this;
    }

    public Builder inspectedSpaceId(String inspectedSpaceId) {
      this.inspectedSpaceId = inspectedSpaceId;
      return this;
    }

    public Builder overrideRequiredFlagNotInspectedUserid(Integer overrideRequiredFlagNotInspectedUserid) {
      this.overrideRequiredFlagNotInspectedUserid = overrideRequiredFlagNotInspectedUserid;
      return this;
    }

    public Builder occChecklistSpaceTypeElementId(Integer occChecklistSpaceTypeElementId) {
      this.occChecklistSpaceTypeElementId = occChecklistSpaceTypeElementId;
      return this;
    }

    public Builder failureSeverityIntensityClassId(Integer failureSeverityIntensityClassId) {
      this.failureSeverityIntensityClassId = failureSeverityIntensityClassId;
      return this;
    }

    public Builder migrateToCECaseOnFail(Boolean migrateToCECaseOnFail) {
      this.migrateToCECaseOnFail = migrateToCECaseOnFail;
      return this;
    }

    public Builder transferredTS(String transferredTS) {
      this.transferredTS = transferredTS;
      return this;
    }

    public Builder transferredByUserId(Integer transferredByUserId) {
      this.transferredByUserId = transferredByUserId;
      return this;
    }

    public Builder transferredToCECaseId(Integer transferredToCECaseId) {
      this.transferredToCECaseId = transferredToCECaseId;
      return this;
    }

    public Builder status(OccInspectableStatus status) {
      this.status = status;
      return this;
    }


    public OccInspectedSpaceElement build() {
      return new OccInspectedSpaceElement(this);
    }
  }

  public String getInspectedSpaceElementId() {
    return inspectedSpaceElementId;
  }

  public void setInspectedSpaceElementId(String inspectedSpaceElementId) {
    this.inspectedSpaceElementId = inspectedSpaceElementId;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Integer getLocationDescriptionId() {
    return locationDescriptionId;
  }

  public void setLocationDescriptionId(Integer locationDescriptionId) {
    this.locationDescriptionId = locationDescriptionId;
  }

  public Integer getLastInspectedByUserId() {
    return lastInspectedByUserId;
  }

  public void setLastInspectedByUserId(Integer lastInspectedByUserId) {
    this.lastInspectedByUserId = lastInspectedByUserId;
  }

  public String getLastInspectedTS() {
    return lastInspectedTS;
  }

  public void setLastInspectedTS(String lastInspectedTS) {
    this.lastInspectedTS = lastInspectedTS;
  }

  public Integer getComplianceGrantedByUserId() {
    return complianceGrantedByUserId;
  }

  public void setComplianceGrantedByUserId(Integer complianceGrantedByUserId) {
    this.complianceGrantedByUserId = complianceGrantedByUserId;
  }

  public String getComplianceGrantedTS() {
    return complianceGrantedTS;
  }

  public void setComplianceGrantedTS(String complianceGrantedTS) {
    this.complianceGrantedTS = complianceGrantedTS;
  }

  public String getInspectedSpaceId() {
    return inspectedSpaceId;
  }

  public void setInspectedSpaceId(String inspectedSpaceId) {
    this.inspectedSpaceId = inspectedSpaceId;
  }

  public Integer getOverrideRequiredFlagNotInspectedUserid() {
    return overrideRequiredFlagNotInspectedUserid;
  }

  public void setOverrideRequiredFlagNotInspectedUserid(Integer overrideRequiredFlagNotInspectedUserid) {
    this.overrideRequiredFlagNotInspectedUserid = overrideRequiredFlagNotInspectedUserid;
  }

  public Integer getOccChecklistSpaceTypeElementId() {
    return occChecklistSpaceTypeElementId;
  }

  public void setOccChecklistSpaceTypeElementId(Integer occChecklistSpaceTypeElementId) {
    this.occChecklistSpaceTypeElementId = occChecklistSpaceTypeElementId;
  }


  public Integer getFailureSeverityIntensityClassId() {
    return failureSeverityIntensityClassId;
  }

  public void setFailureSeverityIntensityClassId(Integer failureSeverityIntensityClassId) {
    this.failureSeverityIntensityClassId = failureSeverityIntensityClassId;
  }

  public Boolean getMigrateToCECaseOnFail() {
    return migrateToCECaseOnFail;
  }

  public void setMigrateToCECaseOnFail(Boolean migrateToCECaseOnFail) {
    this.migrateToCECaseOnFail = migrateToCECaseOnFail;
  }

  public String getTransferredTS() {
    return transferredTS;
  }

  public void setTransferredTS(String transferredTS) {
    this.transferredTS = transferredTS;
  }

  public Integer getTransferredByUserId() {
    return transferredByUserId;
  }

  public void setTransferredByUserId(Integer transferredByUserId) {
    this.transferredByUserId = transferredByUserId;
  }

  public Integer getTransferredToCECaseId() {
    return transferredToCECaseId;
  }

  public void setTransferredToCECaseId(Integer transferredToCECaseId) {
    this.transferredToCECaseId = transferredToCECaseId;
  }

  public OccInspectableStatus getStatus() {
    return status;
  }

  public void setStatus(OccInspectableStatus status) {
    this.status = status;
  }

  public boolean isToUseDefaultDescription() {
    return isToUseDefaultDescription;
  }

  public void setToUseDefaultDescription(boolean toUseDefaultDescription) {
    isToUseDefaultDescription = toUseDefaultDescription;
  }
}
