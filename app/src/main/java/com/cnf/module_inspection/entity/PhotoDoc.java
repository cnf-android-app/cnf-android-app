package com.cnf.module_inspection.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.UUID;

//@Entity(foreignKeys =
//@ForeignKey(entity = BlobBytes.class,
//    parentColumns = "bytesid",
//    childColumns = "blobtype_typeid",
//    onDelete = CASCADE))
@Entity
public class PhotoDoc {

  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "photodocid")
  @NonNull
  private String photoDocId = UUID.randomUUID().toString();
  @ColumnInfo(name = "photodocdescription")
  private String photoDocDescription;
  @ColumnInfo(name = "photodoccommitted")
  private boolean photoDocCommitted;
  @ColumnInfo(name = "blobbytes_bytesid")
  private String blobBytesId;
  @ColumnInfo(name = "muni_municode")
  private Integer municode;
  @ColumnInfo(name = "blobtype_typeid")
  private Integer blobTypeId;
  @ColumnInfo(name = "metadatamap")
  private String metaDataMap;
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
  private Boolean courtDocument;

  public PhotoDoc() {

  }


  public String getPhotoDocId() {
    return photoDocId;
  }

  public void setPhotoDocId(String photoDocId) {
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

  public String getBlobBytesId() {
    return blobBytesId;
  }

  public void setBlobBytesId(String blobBytesId) {
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


  public String getMetaDataMap() {
    return metaDataMap;
  }

  public void setMetaDataMap(String metaDataMap) {
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

  public Boolean getCourtDocument() {
    return courtDocument;
  }

  public void setCourtDocument(Boolean courtDocument) {
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
        ", metaDataMap='" + metaDataMap + '\'' +
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
