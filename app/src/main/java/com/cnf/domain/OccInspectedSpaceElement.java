package com.cnf.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class OccInspectedSpaceElement {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "inspectedspaceelementid")
    private Integer inspectedSpaceElementId;
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
    private Integer inspectedSpaceId;
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

    public OccInspectedSpaceElement(Integer inspectedSpaceElementId, String notes, Integer locationDescriptionId, Integer lastInspectedByUserId, String lastInspectedTS,
        Integer complianceGrantedByUserId, String complianceGrantedTS, Integer inspectedSpaceId, Integer overrideRequiredFlagNotInspectedUserid, Integer occChecklistSpaceTypeElementId,
        Integer failureSeverityIntensityClassId, Boolean migrateToCECaseOnFail, String transferredTS, Integer transferredByUserId, Integer transferredToCECaseId,
        OccInspectableStatus status) {
        this.inspectedSpaceElementId = inspectedSpaceElementId;
        this.notes = notes;
        this.locationDescriptionId = locationDescriptionId;
        this.lastInspectedByUserId = lastInspectedByUserId;
        this.lastInspectedTS = lastInspectedTS;
        this.complianceGrantedByUserId = complianceGrantedByUserId;
        this.complianceGrantedTS = complianceGrantedTS;
        this.inspectedSpaceId = inspectedSpaceId;
        this.overrideRequiredFlagNotInspectedUserid = overrideRequiredFlagNotInspectedUserid;
        this.occChecklistSpaceTypeElementId = occChecklistSpaceTypeElementId;
        this.failureSeverityIntensityClassId = failureSeverityIntensityClassId;
        this.migrateToCECaseOnFail = migrateToCECaseOnFail;
        this.transferredTS = transferredTS;
        this.transferredByUserId = transferredByUserId;
        this.transferredToCECaseId = transferredToCECaseId;
        this.status = status;
    }

    public Integer getInspectedSpaceElementId() {
        return inspectedSpaceElementId;
    }

    public void setInspectedSpaceElementId(Integer inspectedSpaceElementId) {
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

    public Integer getInspectedSpaceId() {
        return inspectedSpaceId;
    }

    public void setInspectedSpaceId(Integer inspectedSpaceId) {
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
