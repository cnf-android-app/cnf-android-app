package com.cnf.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

import java.util.UUID;
import lombok.Data;

@Data
@Entity
public class BlobBytes {

  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "bytesid")
  @NonNull
  private String bytesId = UUID.randomUUID().toString();;
  @ColumnInfo(name = "createdts")
  private String createdTS;
  @ColumnInfo(name = "blob")
  private String blob;
  @ColumnInfo(name = "uploadedby_userid")
  private Integer uploadedByUserId;
  @ColumnInfo(name = "filename")
  private String fileName;

  public BlobBytes(String bytesId, String createdTS, String blob, Integer uploadedByUserId, String fileName) {
    this.bytesId = bytesId;
    this.createdTS = createdTS;
    this.blob = blob;
    this.uploadedByUserId = uploadedByUserId;
    this.fileName = fileName;
  }

  public String getBytesId() {
    return bytesId;
  }

  public void setBytesId(String bytesId) {
    this.bytesId = bytesId;
  }

  public String getCreatedTS() {
    return createdTS;
  }

  public void setCreatedTS(String createdTS) {
    this.createdTS = createdTS;
  }

  public String getBlob() {
    return blob;
  }

  public void setBlob(String blob) {
    this.blob = blob;
  }

  public Integer getUploadedByUserId() {
    return uploadedByUserId;
  }

  public void setUploadedByUserId(Integer uploadedByUserId) {
    this.uploadedByUserId = uploadedByUserId;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public String toString() {
    return "BlobBytes{" +
        "bytesId=" + bytesId +
        ", createdTS='" + createdTS + '\'' +
        ", blob='" + blob + '\'' +
        ", uploadedByUserId=" + uploadedByUserId +
        ", fileName='" + fileName + '\'' +
        '}';
  }
}
