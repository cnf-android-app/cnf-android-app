package com.cnf.domain.infra;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LoginMuniAuthPeriod {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "muniauthperiodid")
    private Integer muniAuthPeriodId;
    @ColumnInfo(name = "muni_municode")
    private Integer muniCode;
    @ColumnInfo(name = "authuser_userid")
    private Integer userId;
    @ColumnInfo(name = "accessgranteddatestart")
    private String accessGrantedDateStart;
    @ColumnInfo(name = "accessgranteddatestop")
    private String accessGrantedDateStop;
    @ColumnInfo(name = "recorddeactivatedts")
    private String recordDeactivatedTS;
    @ColumnInfo(name = "authorizedrole")
    private String authorizedRole;
    @ColumnInfo(name = "createdts")
    private String createdTS;
    @ColumnInfo(name = "createdby_userid")
    private Integer createdByUserId;
    @ColumnInfo(name = "notes")
    private String notes;
    @ColumnInfo(name = "supportassignedby")
    private Integer supportAssignedBy;
    @ColumnInfo(name = "assignmentrank")
    private Integer assignmentRank;
    @ColumnInfo(name = "oathts")
    private String oathTS;
    @ColumnInfo(name = "oathcourtentity_entityid")
    private Integer oathCourtEntityId;

    public Integer getMuniAuthPeriodId() {
        return muniAuthPeriodId;
    }

    public void setMuniAuthPeriodId(Integer muniAuthPeriodId) {
        this.muniAuthPeriodId = muniAuthPeriodId;
    }

    public Integer getMuniCode() {
        return muniCode;
    }

    public void setMuniCode(Integer muniCode) {
        this.muniCode = muniCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccessGrantedDateStart() {
        return accessGrantedDateStart;
    }

    public void setAccessGrantedDateStart(String accessGrantedDateStart) {
        this.accessGrantedDateStart = accessGrantedDateStart;
    }

    public String getAccessGrantedDateStop() {
        return accessGrantedDateStop;
    }

    public void setAccessGrantedDateStop(String accessGrantedDateStop) {
        this.accessGrantedDateStop = accessGrantedDateStop;
    }

    public String getRecordDeactivatedTS() {
        return recordDeactivatedTS;
    }

    public void setRecordDeactivatedTS(String recordDeactivatedTS) {
        this.recordDeactivatedTS = recordDeactivatedTS;
    }

    public String getAuthorizedRole() {
        return authorizedRole;
    }

    public void setAuthorizedRole(String authorizedRole) {
        this.authorizedRole = authorizedRole;
    }

    public String getCreatedTS() {
        return createdTS;
    }

    public void setCreatedTS(String createdTS) {
        this.createdTS = createdTS;
    }

    public Integer getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Integer createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getSupportAssignedBy() {
        return supportAssignedBy;
    }

    public void setSupportAssignedBy(Integer supportAssignedBy) {
        this.supportAssignedBy = supportAssignedBy;
    }

    public Integer getAssignmentRank() {
        return assignmentRank;
    }

    public void setAssignmentRank(Integer assignmentRank) {
        this.assignmentRank = assignmentRank;
    }

    public String getOathTS() {
        return oathTS;
    }

    public void setOathTS(String oathTS) {
        this.oathTS = oathTS;
    }

    public Integer getOathCourtEntityId() {
        return oathCourtEntityId;
    }

    public void setOathCourtEntityId(Integer oathCourtEntityId) {
        this.oathCourtEntityId = oathCourtEntityId;
    }

    @Override
    public String toString() {
        return "LoginMuniAuthPeriod{" +
                "muniAuthPeriodId=" + muniAuthPeriodId +
                ", muniCode=" + muniCode +
                ", userId=" + userId +
                ", accessGrantedDateStart='" + accessGrantedDateStart + '\'' +
                ", accessGrantedDateStop='" + accessGrantedDateStop + '\'' +
                ", recordDeactivatedTS='" + recordDeactivatedTS + '\'' +
                ", authorizedRole='" + authorizedRole + '\'' +
                ", createdTS='" + createdTS + '\'' +
                ", createdByUserId=" + createdByUserId +
                ", notes='" + notes + '\'' +
                ", supportAssignedBy=" + supportAssignedBy +
                ", assignmentRank=" + assignmentRank +
                ", oathTS='" + oathTS + '\'' +
                ", oathCourtEntityId=" + oathCourtEntityId +
                '}';
    }
}
