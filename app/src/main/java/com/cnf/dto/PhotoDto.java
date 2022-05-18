package com.cnf.dto;

import com.cnf.domain.BlobBytes;
import com.cnf.domain.PhotoDoc;

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
}
