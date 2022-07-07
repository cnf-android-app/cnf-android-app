package com.cnf.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;


@Entity
public class PhotoDoc {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "photodocid")
    private Integer photoDocId;
    @ColumnInfo(name = "photodocdescription")
    private String photoDocDescription;
    @ColumnInfo(name = "photodoccommitted")
    private boolean photoDocCommitted;
    @ColumnInfo(name = "blobbytes_bytesid")
    private Integer blobBytesId;
    @ColumnInfo(name = "muni_municode")
    private Integer municode;
    @ColumnInfo(name = "blobtype_typeid")
    private Integer blobTypeId;
    @ColumnInfo(name = "metadatamap")
    private byte[] metaDataMap;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "createdts")
    private String createdTS;
    @ColumnInfo(name = "createdby_userid")
    private Integer createdByUserid;
    @ColumnInfo(name = "lastupdatedts")
    private String lastUpdatedTS;
    @ColumnInfo(name = "lastupdatedby_userid")
    private Integer lastUpdatedByUserId;
    @ColumnInfo(name = "deactivatedts")
    private String deActivatedTS;
    @ColumnInfo(name = "deactivatedby_userid")
    private Integer deActivatedByUserUd;
    @ColumnInfo(name = "dateofrecord")
    private String dateOfRecord;
    @ColumnInfo(name = "courtdocument")
    private Integer courtDocument;

    public PhotoDoc() {
    }

    public PhotoDoc(Integer photoDocId, String photoDocDescription, boolean photoDocCommitted, Integer blobBytesId, Integer municode, Integer blobTypeId, byte[] metaDataMap, String title,
        String createdTS, Integer createdByUserid, String lastUpdatedTS, Integer lastUpdatedByUserId, String deActivatedTS, Integer deActivatedByUserUd, String dateOfRecord,
        Integer courtDocument) {
        this.photoDocId = photoDocId;
        this.photoDocDescription = photoDocDescription;
        this.photoDocCommitted = photoDocCommitted;
        this.blobBytesId = blobBytesId;
        this.municode = municode;
        this.blobTypeId = blobTypeId;
        this.metaDataMap = metaDataMap;
        this.title = title;
        this.createdTS = createdTS;
        this.createdByUserid = createdByUserid;
        this.lastUpdatedTS = lastUpdatedTS;
        this.lastUpdatedByUserId = lastUpdatedByUserId;
        this.deActivatedTS = deActivatedTS;
        this.deActivatedByUserUd = deActivatedByUserUd;
        this.dateOfRecord = dateOfRecord;
        this.courtDocument = courtDocument;
    }

    public Integer getPhotoDocId() {
        return photoDocId;
    }

    public void setPhotoDocId(Integer photoDocId) {
        this.photoDocId = photoDocId;
    }

    public String getPhotoDocDescription() {
        return photoDocDescription;
    }

    public void setPhotoDocDescription(String photoDocDescription) {
        this.photoDocDescription = photoDocDescription;
    }

    public boolean isPhotoDocCommitted() {
        return photoDocCommitted;
    }

    public void setPhotoDocCommitted(boolean photoDocCommitted) {
        this.photoDocCommitted = photoDocCommitted;
    }

    public Integer getBlobBytesId() {
        return blobBytesId;
    }

    public void setBlobBytesId(Integer blobBytesId) {
        this.blobBytesId = blobBytesId;
    }

    public Integer getMunicode() {
        return municode;
    }

    public void setMunicode(Integer municode) {
        this.municode = municode;
    }

    public Integer getBlobTypeId() {
        return blobTypeId;
    }

    public void setBlobTypeId(Integer blobTypeId) {
        this.blobTypeId = blobTypeId;
    }

    public byte[] getMetaDataMap() {
        return metaDataMap;
    }

    public void setMetaDataMap(byte[] metaDataMap) {
        this.metaDataMap = metaDataMap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedTS() {
        return createdTS;
    }

    public void setCreatedTS(String createdTS) {
        this.createdTS = createdTS;
    }

    public Integer getCreatedByUserid() {
        return createdByUserid;
    }

    public void setCreatedByUserid(Integer createdByUserid) {
        this.createdByUserid = createdByUserid;
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

    public Integer getDeActivatedByUserUd() {
        return deActivatedByUserUd;
    }

    public void setDeActivatedByUserUd(Integer deActivatedByUserUd) {
        this.deActivatedByUserUd = deActivatedByUserUd;
    }

    public String getDateOfRecord() {
        return dateOfRecord;
    }

    public void setDateOfRecord(String dateOfRecord) {
        this.dateOfRecord = dateOfRecord;
    }

    public Integer getCourtDocument() {
        return courtDocument;
    }

    public void setCourtDocument(Integer courtDocument) {
        this.courtDocument = courtDocument;
    }

    @Override
    public String toString() {
        return "PhotoDoc{" +
            "photoDocId=" + photoDocId +
            ", photoDocDescription='" + photoDocDescription + '\'' +
            ", photoDocCommitted=" + photoDocCommitted +
            ", blobBytesId=" + blobBytesId +
            ", municode=" + municode +
            ", blobTypeId=" + blobTypeId +
            ", metaDataMap=" + Arrays.toString(metaDataMap) +
            ", title='" + title + '\'' +
            ", createdTS='" + createdTS + '\'' +
            ", createdByUserid=" + createdByUserid +
            ", lastUpdatedTS='" + lastUpdatedTS + '\'' +
            ", lastUpdatedByUserId=" + lastUpdatedByUserId +
            ", deActivatedTS='" + deActivatedTS + '\'' +
            ", deActivatedByUserUd=" + deActivatedByUserUd +
            ", dateOfRecord='" + dateOfRecord + '\'' +
            ", courtDocument=" + courtDocument +
            '}';
    }
}
