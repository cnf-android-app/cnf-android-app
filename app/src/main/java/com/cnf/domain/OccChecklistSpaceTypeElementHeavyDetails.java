package com.cnf.domain;

import lombok.Data;

@Data
public class OccChecklistSpaceTypeElementHeavyDetails {

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
    private String notes;
    private Integer legacyid;
    private String ordsubsubsecnum;
    private Boolean useinjectedvalues;
    private String lastupdatedts;
    private Integer createdby_userid;
    private Integer lastupdatedby_userid;
    private String deactivatedts;
    private Integer deactivatedby_userid;
    private String createdts;

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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
}
