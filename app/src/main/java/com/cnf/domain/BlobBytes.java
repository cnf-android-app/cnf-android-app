package com.cnf.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

import lombok.Data;

@Data
@Entity
public class BlobBytes {
    @PrimaryKey(autoGenerate = true)
    private Integer bytesid;
    private String createdts;
    private byte[] blob;
    private Integer uploadedby_userid;
    private String filename;

    public BlobBytes(String createdts, byte[] blob, Integer uploadedby_userid, String filename) {
        this.createdts = createdts;
        this.blob = blob;
        this.uploadedby_userid = uploadedby_userid;
        this.filename = filename;
    }

    public Integer getBytesid() {
        return bytesid;
    }

    public void setBytesid(Integer bytesid) {
        this.bytesid = bytesid;
    }

    public String getCreatedts() {
        return createdts;
    }

    public void setCreatedts(String createdts) {
        this.createdts = createdts;
    }

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    public Integer getUploadedby_userid() {
        return uploadedby_userid;
    }

    public void setUploadedby_userid(Integer uploadedby_userid) {
        this.uploadedby_userid = uploadedby_userid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "BlobBytes{" +
                "bytesid=" + bytesid +
                ", createdts='" + createdts + '\'' +
                ", blob=" + Arrays.toString(blob) +
                ", uploadedby_userid=" + uploadedby_userid +
                ", filename='" + filename + '\'' +
                '}';
    }
};
