package com.cnf.domain;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;


@Dao
public class OccInspectedSpaceElementHeavy {


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


    private Integer spaceelementid;
    private Integer codeelement_id;
    private Boolean required;
    private Integer checklistspacetype_typeid;


    private Integer elementid;
    private Integer codesource_sourceid;
    private Integer ordchapterno;
    private String ordchaptertitle;
    private String ordsecnum;
    private String ordsectitle;
    private String ordsubsecnum;
    private String ordsubsectitle;
    private String ordtechnicaltext;
    private String ordhumanfriendlytext;
    private String resourceurl;
    private Integer guideentryid;
    private Integer legacyid;
    private String ordsubsubsecnum;
    private Boolean useinjectedvalues;
    private String lastupdatedts;
    private Integer createdby_userid;
    private Integer lastupdatedby_userid;
    private String deactivatedts;
    private Integer deactivatedby_userid;
    private String createdts;

    @Ignore
    private boolean expanded;

    @Ignore
    List<BlobBytes> blobBytesList;

    public List<BlobBytes> getBlobBytesList() {
        return blobBytesList;
    }

    public void setBlobBytesList(List<BlobBytes> blobBytesList) {
        this.blobBytesList = blobBytesList;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
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

    public Integer getSpaceelementid() {
        return spaceelementid;
    }

    public void setSpaceelementid(Integer spaceelementid) {
        this.spaceelementid = spaceelementid;
    }

    public Integer getCodeelement_id() {
        return codeelement_id;
    }

    public void setCodeelement_id(Integer codeelement_id) {
        this.codeelement_id = codeelement_id;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getChecklistspacetype_typeid() {
        return checklistspacetype_typeid;
    }

    public void setChecklistspacetype_typeid(Integer checklistspacetype_typeid) {
        this.checklistspacetype_typeid = checklistspacetype_typeid;
    }

    public Integer getElementid() {
        return elementid;
    }

    public void setElementid(Integer elementid) {
        this.elementid = elementid;
    }

    public Integer getCodesource_sourceid() {
        return codesource_sourceid;
    }

    public void setCodesource_sourceid(Integer codesource_sourceid) {
        this.codesource_sourceid = codesource_sourceid;
    }

    public Integer getOrdchapterno() {
        return ordchapterno;
    }

    public void setOrdchapterno(Integer ordchapterno) {
        this.ordchapterno = ordchapterno;
    }

    public String getOrdchaptertitle() {
        return ordchaptertitle;
    }

    public void setOrdchaptertitle(String ordchaptertitle) {
        this.ordchaptertitle = ordchaptertitle;
    }

    public String getOrdsecnum() {
        return ordsecnum;
    }

    public void setOrdsecnum(String ordsecnum) {
        this.ordsecnum = ordsecnum;
    }

    public String getOrdsectitle() {
        return ordsectitle;
    }

    public void setOrdsectitle(String ordsectitle) {
        this.ordsectitle = ordsectitle;
    }

    public String getOrdsubsecnum() {
        return ordsubsecnum;
    }

    public void setOrdsubsecnum(String ordsubsecnum) {
        this.ordsubsecnum = ordsubsecnum;
    }

    public String getOrdsubsectitle() {
        return ordsubsectitle;
    }

    public void setOrdsubsectitle(String ordsubsectitle) {
        this.ordsubsectitle = ordsubsectitle;
    }

    public String getOrdtechnicaltext() {
        return ordtechnicaltext;
    }

    public void setOrdtechnicaltext(String ordtechnicaltext) {
        this.ordtechnicaltext = ordtechnicaltext;
    }

    public String getOrdhumanfriendlytext() {
        return ordhumanfriendlytext;
    }

    public void setOrdhumanfriendlytext(String ordhumanfriendlytext) {
        this.ordhumanfriendlytext = ordhumanfriendlytext;
    }

    public String getResourceurl() {
        return resourceurl;
    }

    public void setResourceurl(String resourceurl) {
        this.resourceurl = resourceurl;
    }

    public Integer getGuideentryid() {
        return guideentryid;
    }

    public void setGuideentryid(Integer guideentryid) {
        this.guideentryid = guideentryid;
    }

    public Integer getLegacyid() {
        return legacyid;
    }

    public void setLegacyid(Integer legacyid) {
        this.legacyid = legacyid;
    }

    public String getOrdsubsubsecnum() {
        return ordsubsubsecnum;
    }

    public void setOrdsubsubsecnum(String ordsubsubsecnum) {
        this.ordsubsubsecnum = ordsubsubsecnum;
    }

    public Boolean getUseinjectedvalues() {
        return useinjectedvalues;
    }

    public void setUseinjectedvalues(Boolean useinjectedvalues) {
        this.useinjectedvalues = useinjectedvalues;
    }

    public String getLastupdatedts() {
        return lastupdatedts;
    }

    public void setLastupdatedts(String lastupdatedts) {
        this.lastupdatedts = lastupdatedts;
    }

    public Integer getCreatedby_userid() {
        return createdby_userid;
    }

    public void setCreatedby_userid(Integer createdby_userid) {
        this.createdby_userid = createdby_userid;
    }

    public Integer getLastupdatedby_userid() {
        return lastupdatedby_userid;
    }

    public void setLastupdatedby_userid(Integer lastupdatedby_userid) {
        this.lastupdatedby_userid = lastupdatedby_userid;
    }

    public String getDeactivatedts() {
        return deactivatedts;
    }

    public void setDeactivatedts(String deactivatedts) {
        this.deactivatedts = deactivatedts;
    }

    public Integer getDeactivatedby_userid() {
        return deactivatedby_userid;
    }

    public void setDeactivatedby_userid(Integer deactivatedby_userid) {
        this.deactivatedby_userid = deactivatedby_userid;
    }

    public String getCreatedts() {
        return createdts;
    }

    public void setCreatedts(String createdts) {
        this.createdts = createdts;
    }

    @Override
    public String toString() {
        return "OccInspectedSpaceElementHeavy{" +
                "inspectedspaceelementid=" + inspectedspaceelementid +
                ", notes='" + notes + '\'' +
                ", locationdescription_id=" + locationdescription_id +
                ", lastinspectedby_userid=" + lastinspectedby_userid +
                ", lastinspectedts='" + lastinspectedts + '\'' +
                ", compliancegrantedby_userid=" + compliancegrantedby_userid +
                ", compliancegrantedts='" + compliancegrantedts + '\'' +
                ", inspectedspace_inspectedspaceid=" + inspectedspace_inspectedspaceid +
                ", overriderequiredflagnotinspected_userid=" + overriderequiredflagnotinspected_userid +
                ", occchecklistspacetypeelement_elementid=" + occchecklistspacetypeelement_elementid +
                ", failureseverity_intensityclassid=" + failureseverity_intensityclassid +
                ", migratetocecaseonfail=" + migratetocecaseonfail +
                ", spaceelementid=" + spaceelementid +
                ", codeelement_id=" + codeelement_id +
                ", required=" + required +
                ", checklistspacetype_typeid=" + checklistspacetype_typeid +
                ", elementid=" + elementid +
                ", codesource_sourceid=" + codesource_sourceid +
                ", ordchapterno=" + ordchapterno +
                ", ordchaptertitle='" + ordchaptertitle + '\'' +
                ", ordsecnum='" + ordsecnum + '\'' +
                ", ordsectitle='" + ordsectitle + '\'' +
                ", ordsubsecnum='" + ordsubsecnum + '\'' +
                ", ordsubsectitle='" + ordsubsectitle + '\'' +
                ", ordtechnicaltext='" + ordtechnicaltext + '\'' +
                ", ordhumanfriendlytext='" + ordhumanfriendlytext + '\'' +
                ", resourceurl='" + resourceurl + '\'' +
                ", guideentryid=" + guideentryid +
                ", legacyid=" + legacyid +
                ", ordsubsubsecnum='" + ordsubsubsecnum + '\'' +
                ", useinjectedvalues=" + useinjectedvalues +
                ", lastupdatedts='" + lastupdatedts + '\'' +
                ", createdby_userid=" + createdby_userid +
                ", lastupdatedby_userid=" + lastupdatedby_userid +
                ", deactivatedts='" + deactivatedts + '\'' +
                ", deactivatedby_userid=" + deactivatedby_userid +
                ", createdts='" + createdts + '\'' +
                '}';
    }
};
