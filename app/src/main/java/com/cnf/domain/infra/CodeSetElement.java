package com.cnf.domain.infra;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity
public class CodeSetElement implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "codesetelementid")
    private Integer codeSetElementId;
    @ColumnInfo(name = "codeset_codesetid")
    private Integer codeSetId;
    @ColumnInfo(name = "codelement_elementid")
    private Integer codeElementId;
    @ColumnInfo(name = "elementmaxpenalty")
    private Double elementMaxPenalty;
    @ColumnInfo(name = "elementminpenalty")
    private Double elementMinPenalt;
    @ColumnInfo(name = "elementnormpenalty")
    private Double elementNormPenalty;
    @ColumnInfo(name = "penaltynotes")
    private String penaltyNotes;
    @ColumnInfo(name = "normdaystocomply")
    private Integer normDaysToComply;
    @ColumnInfo(name = "daystocomplynotes")
    private String daysToComplyNotes;
    @ColumnInfo(name = "munispecificnotes")
    private String muniSpecificNotes;
    @ColumnInfo(name = "defaultseverityclass_classid")
    private Integer defaultSeverityClassId;
    @ColumnInfo(name = "fee_feeid")
    private Integer feeId;
    @ColumnInfo(name = "defaultviolationdescription")
    private String defaultViolationDescription;
    @ColumnInfo(name = "code_set_element_createdts")
    private String createdTS;
    @ColumnInfo(name = "code_set_element_createdby_userid")
    private Integer createdByUserId;
    @ColumnInfo(name = "code_set_element_lastupdatedts")
    private String lastUpdatedTS;
    @ColumnInfo(name = "code_set_element_lastupdatedby_userid")
    private Integer lastUpdatedByUserId;
    @ColumnInfo(name = "code_set_element_deactivatedts")
    private String deActivatedTS;
    @ColumnInfo(name = "code_set_element_deactivatedby_userid")
    private Integer deActivatedByUserid;

    public Integer getCodeSetElementId() {
        return codeSetElementId;
    }

    public void setCodeSetElementId(Integer codeSetElementId) {
        this.codeSetElementId = codeSetElementId;
    }

    public Integer getCodeSetId() {
        return codeSetId;
    }

    public void setCodeSetId(Integer codeSetId) {
        this.codeSetId = codeSetId;
    }

    public Integer getCodeElementId() {
        return codeElementId;
    }

    public void setCodeElementId(Integer codeElementId) {
        this.codeElementId = codeElementId;
    }

    public Double getElementMaxPenalty() {
        return elementMaxPenalty;
    }

    public void setElementMaxPenalty(Double elementMaxPenalty) {
        this.elementMaxPenalty = elementMaxPenalty;
    }

    public Double getElementMinPenalt() {
        return elementMinPenalt;
    }

    public void setElementMinPenalt(Double elementMinPenalt) {
        this.elementMinPenalt = elementMinPenalt;
    }

    public Double getElementNormPenalty() {
        return elementNormPenalty;
    }

    public void setElementNormPenalty(Double elementNormPenalty) {
        this.elementNormPenalty = elementNormPenalty;
    }

    public String getPenaltyNotes() {
        return penaltyNotes;
    }

    public void setPenaltyNotes(String penaltyNotes) {
        this.penaltyNotes = penaltyNotes;
    }

    public Integer getNormDaysToComply() {
        return normDaysToComply;
    }

    public void setNormDaysToComply(Integer normDaysToComply) {
        this.normDaysToComply = normDaysToComply;
    }

    public String getDaysToComplyNotes() {
        return daysToComplyNotes;
    }

    public void setDaysToComplyNotes(String daysToComplyNotes) {
        this.daysToComplyNotes = daysToComplyNotes;
    }

    public String getMuniSpecificNotes() {
        return muniSpecificNotes;
    }

    public void setMuniSpecificNotes(String muniSpecificNotes) {
        this.muniSpecificNotes = muniSpecificNotes;
    }

    public Integer getDefaultSeverityClassId() {
        return defaultSeverityClassId;
    }

    public void setDefaultSeverityClassId(Integer defaultSeverityClassId) {
        this.defaultSeverityClassId = defaultSeverityClassId;
    }

    public Integer getFeeId() {
        return feeId;
    }

    public void setFeeId(Integer feeId) {
        this.feeId = feeId;
    }

    public String getDefaultViolationDescription() {
        return defaultViolationDescription;
    }

    public void setDefaultViolationDescription(String defaultViolationDescription) {
        this.defaultViolationDescription = defaultViolationDescription;
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

    public String getDeActivatedTS() {
        return deActivatedTS;
    }

    public void setDeActivatedTS(String deActivatedTS) {
        this.deActivatedTS = deActivatedTS;
    }

    public Integer getDeActivatedByUserid() {
        return deActivatedByUserid;
    }

    public void setDeActivatedByUserid(Integer deActivatedByUserid) {
        this.deActivatedByUserid = deActivatedByUserid;
    }

    @Override
    public String toString() {
        return "CodeSetElement{" +
            "codeSetElementId=" + codeSetElementId +
            ", codeSetId=" + codeSetId +
            ", codeElementId=" + codeElementId +
            ", elementMaxPenalty=" + elementMaxPenalty +
            ", elementMinPenalt=" + elementMinPenalt +
            ", elementNormPenalty=" + elementNormPenalty +
            ", penaltyNotes='" + penaltyNotes + '\'' +
            ", normDaysToComply=" + normDaysToComply +
            ", daysToComplyNotes='" + daysToComplyNotes + '\'' +
            ", muniSpecificNotes='" + muniSpecificNotes + '\'' +
            ", defaultSeverityClassId=" + defaultSeverityClassId +
            ", feeId=" + feeId +
            ", defaultViolationDescription='" + defaultViolationDescription + '\'' +
            ", createdTS='" + createdTS + '\'' +
            ", createdByUserId=" + createdByUserId +
            ", lastUpdatedTS='" + lastUpdatedTS + '\'' +
            ", lastUpdatedByUserId=" + lastUpdatedByUserId +
            ", deActivatedTS='" + deActivatedTS + '\'' +
            ", deActivatedByUserid=" + deActivatedByUserid +
            '}';
    }
}
