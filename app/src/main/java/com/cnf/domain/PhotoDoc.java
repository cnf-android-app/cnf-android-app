package com.cnf.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

import lombok.Data;

@Data
@Entity
public class PhotoDoc {

    @PrimaryKey(autoGenerate = true)
    private Integer photodocid;
    private String photodocdescription;
    private boolean photodoccommitted;
    private Integer blobbytes_bytesid;
    private Integer muni_municode;
    private Integer blobtype_typeid;
    private byte[]  metadatamap;
    private String title;
    private Integer createdby_userid;
    private String createdts;

    public PhotoDoc(String photodocdescription, boolean photodoccommitted, Integer blobbytes_bytesid, Integer muni_municode, Integer blobtype_typeid, byte[] metadatamap, String title, Integer createdby_userid, String createdts) {
        this.photodocdescription = photodocdescription;
        this.photodoccommitted = photodoccommitted;
        this.blobbytes_bytesid = blobbytes_bytesid;
        this.muni_municode = muni_municode;
        this.blobtype_typeid = blobtype_typeid;
        this.metadatamap = metadatamap;
        this.title = title;
        this.createdby_userid = createdby_userid;
        this.createdts = createdts;
    }

    public Integer getPhotodocid() {
        return photodocid;
    }

    public void setPhotodocid(Integer photodocid) {
        this.photodocid = photodocid;
    }

    public String getPhotodocdescription() {
        return photodocdescription;
    }

    public void setPhotodocdescription(String photodocdescription) {
        this.photodocdescription = photodocdescription;
    }

    public boolean isPhotodoccommitted() {
        return photodoccommitted;
    }

    public void setPhotodoccommitted(boolean photodoccommitted) {
        this.photodoccommitted = photodoccommitted;
    }

    public Integer getBlobbytes_bytesid() {
        return blobbytes_bytesid;
    }

    public void setBlobbytes_bytesid(Integer blobbytes_bytesid) {
        this.blobbytes_bytesid = blobbytes_bytesid;
    }

    public Integer getMuni_municode() {
        return muni_municode;
    }

    public void setMuni_municode(Integer muni_municode) {
        this.muni_municode = muni_municode;
    }

    public Integer getBlobtype_typeid() {
        return blobtype_typeid;
    }

    public void setBlobtype_typeid(Integer blobtype_typeid) {
        this.blobtype_typeid = blobtype_typeid;
    }

    public byte[] getMetadatamap() {
        return metadatamap;
    }

    public void setMetadatamap(byte[] metadatamap) {
        this.metadatamap = metadatamap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCreatedby_userid() {
        return createdby_userid;
    }

    public void setCreatedby_userid(Integer createdby_userid) {
        this.createdby_userid = createdby_userid;
    }

    public String getCreatedts() {
        return createdts;
    }

    public void setCreatedts(String createdts) {
        this.createdts = createdts;
    }

    @Override
    public String toString() {
        return "PhotoDoc{" +
                "photodocid=" + photodocid +
                ", photodocdescription='" + photodocdescription + '\'' +
                ", photodoccommitted=" + photodoccommitted +
                ", blobbytes_bytesid=" + blobbytes_bytesid +
                ", muni_municode=" + muni_municode +
                ", blobtype_typeid=" + blobtype_typeid +
                ", metadatamap=" + Arrays.toString(metadatamap) +
                ", title='" + title + '\'' +
                ", createdby_userid=" + createdby_userid +
                ", createdts='" + createdts + '\'' +
                '}';
    }
}
