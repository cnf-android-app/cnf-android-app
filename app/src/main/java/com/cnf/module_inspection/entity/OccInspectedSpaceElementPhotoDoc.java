package com.cnf.module_inspection.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OccInspectedSpaceElementPhotoDoc {


    @ColumnInfo(name = "photodoc_photodocid")
    @PrimaryKey
    @NonNull
    private String photoDocId;
    @ColumnInfo(name = "inspectedspaceelement_elementid")
    private String inspectedSpaceElementId;

    public OccInspectedSpaceElementPhotoDoc(String photoDocId, String inspectedSpaceElementId) {
        this.photoDocId = photoDocId;
        this.inspectedSpaceElementId = inspectedSpaceElementId;
    }

    public String getPhotoDocId() {
        return photoDocId;
    }

    public void setPhotoDocId(String photoDocId) {
        this.photoDocId = photoDocId;
    }

    public String getInspectedSpaceElementId() {
        return inspectedSpaceElementId;
    }

    public void setInspectedSpaceElementId(String inspectedSpaceElementId) {
        this.inspectedSpaceElementId = inspectedSpaceElementId;
    }

    @Override
    public String toString() {
        return "OccInspectedSpaceElementPhotoDoc{" +
            "photoDocId=" + photoDocId +
            ", inspectedSpaceElementId=" + inspectedSpaceElementId +
            '}';
    }
}
