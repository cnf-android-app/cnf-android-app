package com.cnf.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

import lombok.Data;

@Data
@Entity
public class BlobBytes {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "bytesid")
  private Integer bytesId;
  @ColumnInfo(name = "createdts")
  private String createdTS;
  @ColumnInfo(name = "blob")
  private byte[] blob;
  @ColumnInfo(name = "uploadedby_userid")
  private Integer uploadedByUserId;
  @ColumnInfo(name = "filename")
  private String fileName;

  public BlobBytes(Integer bytesId, String createdTS, byte[] blob, Integer uploadedByUserId, String fileName) {
    this.bytesId = bytesId;
    this.createdTS = createdTS;
    this.blob = blob;
    this.uploadedByUserId = uploadedByUserId;
    this.fileName = fileName;
  }

  public Integer getBytesId() {
    return bytesId;
  }

  public void setBytesId(Integer bytesId) {
    this.bytesId = bytesId;
  }

  public String getCreatedTS() {
    return createdTS;
  }

  public void setCreatedTS(String createdTS) {
    this.createdTS = createdTS;
  }

  public byte[] getBlob() {
    return blob;
  }

  public void setBlob(byte[] blob) {
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
        ", blob=" + Arrays.toString(blob) +
        ", uploadedByUserId=" + uploadedByUserId +
        ", fileName='" + fileName + '\'' +
        '}';
  }
}
