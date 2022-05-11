package com.cnf.domain;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
@Dao
public class OccInspectedSpaceElement {

    @PrimaryKey(autoGenerate = true)
    private Integer inspectedspaceelementid;
    private String notes;
    private Integer locationdescription_id;
    private Integer lastinspectedby_userid;
    private String lastinspectedts;
    private Integer compliancegrantedby_userid;
    private String compliancegrantedts;
    private Integer inspectedspace_inspectedspaceid;
    private Integer overriderequiredflagnotinspected_userid;
    private Integer occchecklistspacetypeelement_elementid;
    private Integer failureseverity_intensityclassid;
    private Boolean migratetocecaseonfail;

    public OccInspectedSpaceElement(String notes, Integer locationdescription_id, Integer lastinspectedby_userid, String lastinspectedts, Integer compliancegrantedby_userid, String compliancegrantedts, Integer inspectedspace_inspectedspaceid, Integer overriderequiredflagnotinspected_userid, Integer occchecklistspacetypeelement_elementid, Integer failureseverity_intensityclassid, Boolean migratetocecaseonfail) {
        this.notes = notes;
        this.locationdescription_id = locationdescription_id;
        this.lastinspectedby_userid = lastinspectedby_userid;
        this.lastinspectedts = lastinspectedts;
        this.compliancegrantedby_userid = compliancegrantedby_userid;
        this.compliancegrantedts = compliancegrantedts;
        this.inspectedspace_inspectedspaceid = inspectedspace_inspectedspaceid;
        this.overriderequiredflagnotinspected_userid = overriderequiredflagnotinspected_userid;
        this.occchecklistspacetypeelement_elementid = occchecklistspacetypeelement_elementid;
        this.failureseverity_intensityclassid = failureseverity_intensityclassid;
        this.migratetocecaseonfail = migratetocecaseonfail;
    }

    public Integer getInspectedspaceelementid() {
        return inspectedspaceelementid;
    }

    public void setInspectedspaceelementid(Integer inspectedspaceelementid) {
        this.inspectedspaceelementid = inspectedspaceelementid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getLocationdescription_id() {
        return locationdescription_id;
    }

    public void setLocationdescription_id(Integer locationdescription_id) {
        this.locationdescription_id = locationdescription_id;
    }

    public Integer getLastinspectedby_userid() {
        return lastinspectedby_userid;
    }

    public void setLastinspectedby_userid(Integer lastinspectedby_userid) {
        this.lastinspectedby_userid = lastinspectedby_userid;
    }

    public String getLastinspectedts() {
        return lastinspectedts;
    }

    public void setLastinspectedts(String lastinspectedts) {
        this.lastinspectedts = lastinspectedts;
    }

    public Integer getCompliancegrantedby_userid() {
        return compliancegrantedby_userid;
    }

    public void setCompliancegrantedby_userid(Integer compliancegrantedby_userid) {
        this.compliancegrantedby_userid = compliancegrantedby_userid;
    }

    public String getCompliancegrantedts() {
        return compliancegrantedts;
    }

    public void setCompliancegrantedts(String compliancegrantedts) {
        this.compliancegrantedts = compliancegrantedts;
    }

    public Integer getInspectedspace_inspectedspaceid() {
        return inspectedspace_inspectedspaceid;
    }

    public void setInspectedspace_inspectedspaceid(Integer inspectedspace_inspectedspaceid) {
        this.inspectedspace_inspectedspaceid = inspectedspace_inspectedspaceid;
    }

    public Integer getOverriderequiredflagnotinspected_userid() {
        return overriderequiredflagnotinspected_userid;
    }

    public void setOverriderequiredflagnotinspected_userid(Integer overriderequiredflagnotinspected_userid) {
        this.overriderequiredflagnotinspected_userid = overriderequiredflagnotinspected_userid;
    }

    public Integer getOccchecklistspacetypeelement_elementid() {
        return occchecklistspacetypeelement_elementid;
    }

    public void setOccchecklistspacetypeelement_elementid(Integer occchecklistspacetypeelement_elementid) {
        this.occchecklistspacetypeelement_elementid = occchecklistspacetypeelement_elementid;
    }

    public Integer getFailureseverity_intensityclassid() {
        return failureseverity_intensityclassid;
    }

    public void setFailureseverity_intensityclassid(Integer failureseverity_intensityclassid) {
        this.failureseverity_intensityclassid = failureseverity_intensityclassid;
    }

    public Boolean getMigratetocecaseonfail() {
        return migratetocecaseonfail;
    }

    public void setMigratetocecaseonfail(Boolean migratetocecaseonfail) {
        this.migratetocecaseonfail = migratetocecaseonfail;
    }
};
