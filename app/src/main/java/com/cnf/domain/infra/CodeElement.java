package com.cnf.domain.infra;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class CodeElement implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "elementid")
    private Integer elementId;
    @ColumnInfo(name = "codesource_sourceid")
    private Integer codeSourceId;
    @ColumnInfo(name = "ordchapterno")
    private Integer ordChapterNo;
    @ColumnInfo(name = "ordchaptertitle")
    private String ordChapterTitle;
    @ColumnInfo(name = "ordsecnum")
    private String ordSecNum;
    @ColumnInfo(name = "ordsectitle")
    private String ordSecTitle;
    @ColumnInfo(name = "ordsubsecnum")
    private String ordSubSecNum;
    @ColumnInfo(name = "ordsubsectitle")
    private String ordSubSecTitle;
    @ColumnInfo(name = "ordtechnicaltext")
    private String ordTechnicalText;
    @ColumnInfo(name = "ordhumanfriendlytext")
    private String ordHumanFriendlyText;
    @ColumnInfo(name = "resourceurl")
    private String resourceUrl;
    @ColumnInfo(name = "guideentryid")
    private Integer guideEntryId;
    @ColumnInfo(name = "code_element_notes")
    private String notes;
    @ColumnInfo(name = "legacyid")
    private Integer legacyId;
    @ColumnInfo(name = "ordsubsubsecnum")
    private String ordSubSubSecNum;
    @ColumnInfo(name = "useinjectedvalues")
    private Boolean useInjectedValues;
    @ColumnInfo(name = "lastupdatedts")
    private String lastUpdatedTS;
    @ColumnInfo(name = "createdby_userid")
    private Integer createdByUserId;
    @ColumnInfo(name = "lastupdatedby_userid")
    private Integer lastUpdatedByUserId;
    @ColumnInfo(name = "deactivatedts")
    private String deactivatedTS;
    @ColumnInfo(name = "deactivatedby_userid")
    private Integer deactivatedByUserId;
    @ColumnInfo(name = "createdts")
    private String createdTS;

    public Integer getElementId() {
        return elementId;
    }

    public void setElementId(Integer elementId) {
        this.elementId = elementId;
    }

    public Integer getCodeSourceId() {
        return codeSourceId;
    }

    public void setCodeSourceId(Integer codeSourceId) {
        this.codeSourceId = codeSourceId;
    }

    public Integer getOrdChapterNo() {
        return ordChapterNo;
    }

    public void setOrdChapterNo(Integer ordChapterNo) {
        this.ordChapterNo = ordChapterNo;
    }

    public String getOrdChapterTitle() {
        return ordChapterTitle;
    }

    public void setOrdChapterTitle(String ordChapterTitle) {
        this.ordChapterTitle = ordChapterTitle;
    }

    public String getOrdSecNum() {
        return ordSecNum;
    }

    public void setOrdSecNum(String ordSecNum) {
        this.ordSecNum = ordSecNum;
    }

    public String getOrdSecTitle() {
        return ordSecTitle;
    }

    public void setOrdSecTitle(String ordSecTitle) {
        this.ordSecTitle = ordSecTitle;
    }

    public String getOrdSubSecNum() {
        return ordSubSecNum;
    }

    public void setOrdSubSecNum(String ordSubSecNum) {
        this.ordSubSecNum = ordSubSecNum;
    }

    public String getOrdSubSecTitle() {
        return ordSubSecTitle;
    }

    public void setOrdSubSecTitle(String ordSubSecTitle) {
        this.ordSubSecTitle = ordSubSecTitle;
    }

    public String getOrdTechnicalText() {
        return ordTechnicalText;
    }

    public void setOrdTechnicalText(String ordTechnicalText) {
        this.ordTechnicalText = ordTechnicalText;
    }

    public String getOrdHumanFriendlyText() {
        return ordHumanFriendlyText;
    }

    public void setOrdHumanFriendlyText(String ordHumanFriendlyText) {
        this.ordHumanFriendlyText = ordHumanFriendlyText;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public Integer getGuideEntryId() {
        return guideEntryId;
    }

    public void setGuideEntryId(Integer guideEntryId) {
        this.guideEntryId = guideEntryId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(Integer legacyId) {
        this.legacyId = legacyId;
    }

    public String getOrdSubSubSecNum() {
        return ordSubSubSecNum;
    }

    public void setOrdSubSubSecNum(String ordSubSubSecNum) {
        this.ordSubSubSecNum = ordSubSubSecNum;
    }

    public Boolean getUseInjectedValues() {
        return useInjectedValues;
    }

    public void setUseInjectedValues(Boolean useInjectedValues) {
        this.useInjectedValues = useInjectedValues;
    }

    public String getLastUpdatedTS() {
        return lastUpdatedTS;
    }

    public void setLastUpdatedTS(String lastUpdatedTS) {
        this.lastUpdatedTS = lastUpdatedTS;
    }

    public Integer getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Integer createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Integer getLastUpdatedByUserId() {
        return lastUpdatedByUserId;
    }

    public void setLastUpdatedByUserId(Integer lastUpdatedByUserId) {
        this.lastUpdatedByUserId = lastUpdatedByUserId;
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

    public String getCreatedTS() {
        return createdTS;
    }

    public void setCreatedTS(String createdTS) {
        this.createdTS = createdTS;
    }

    @Override
    public String toString() {
        return "CodeElement{" +
                "elementId=" + elementId +
                ", codeSourceId=" + codeSourceId +
                ", ordChapterNo=" + ordChapterNo +
                ", ordChapterTitle='" + ordChapterTitle + '\'' +
                ", ordSecNum='" + ordSecNum + '\'' +
                ", ordSecTitle='" + ordSecTitle + '\'' +
                ", ordSubSecNum='" + ordSubSecNum + '\'' +
                ", ordSubSecTitle='" + ordSubSecTitle + '\'' +
                ", ordTechnicalText='" + ordTechnicalText + '\'' +
                ", ordHumanFriendlyText='" + ordHumanFriendlyText + '\'' +
                ", resourceUrl='" + resourceUrl + '\'' +
                ", guideEntryId=" + guideEntryId +
                ", notes='" + notes + '\'' +
                ", legacyId=" + legacyId +
                ", ordSubSubSecNum='" + ordSubSubSecNum + '\'' +
                ", useInjectedValues=" + useInjectedValues +
                ", lastUpdatedTS='" + lastUpdatedTS + '\'' +
                ", createdByUserId=" + createdByUserId +
                ", lastUpdatedByUserId=" + lastUpdatedByUserId +
                ", deactivatedTS='" + deactivatedTS + '\'' +
                ", deactivatedByUserId=" + deactivatedByUserId +
                ", createdTS='" + createdTS + '\'' +
                '}';
    }
}
