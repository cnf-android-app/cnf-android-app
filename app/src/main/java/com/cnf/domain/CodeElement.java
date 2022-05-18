package com.cnf.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
@Entity
public class CodeElement {

    @PrimaryKey(autoGenerate = false)
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
    @ColumnInfo(name = "code_element_notes")
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
