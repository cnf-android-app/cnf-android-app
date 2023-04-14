package com.cnf.module_inspection.dto;

import com.cnf.module_inspection.entity.BlobBytes;
import com.cnf.module_inspection.entity.PhotoDoc;

public class PhotoDto {

    private PhotoDoc photoDoc;
    private BlobBytes blobBytes;

    public PhotoDto(PhotoDoc photoDoc, BlobBytes blobBytes) {
        this.photoDoc = photoDoc;
        this.blobBytes = blobBytes;
    }

    public PhotoDoc getPhotoDoc() {
        return photoDoc;
    }

    public void setPhotoDoc(PhotoDoc photoDoc) {
        this.photoDoc = photoDoc;
    }

    public BlobBytes getBlobBytes() {
        return blobBytes;
    }

    public void setBlobBytes(BlobBytes blobBytes) {
        this.blobBytes = blobBytes;
    }

    @Override
    public String toString() {
        return "PhotoDto{" +
            "photoDoc=" + photoDoc +
            ", blobBytes=" + blobBytes +
            '}';
    }
}
