package com.cnf.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OccInspectedSpaceElementPhotoDoc {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "photodoc_photodocid")
    private Integer photoDocId;
    @ColumnInfo(name = "inspectedspaceelement_elementid")
    private Integer inspectedSpaceElementId;

    public OccInspectedSpaceElementPhotoDoc(Integer photoDocId, Integer inspectedSpaceElementId) {
        this.photoDocId = photoDocId;
        this.inspectedSpaceElementId = inspectedSpaceElementId;
    }

    public Integer getPhotoDocId() {
        return photoDocId;
    }

    public void setPhotoDocId(Integer photoDocId) {
        this.photoDocId = photoDocId;
    }

    public Integer getInspectedSpaceElementId() {
        return inspectedSpaceElementId;
    }

    public void setInspectedSpaceElementId(Integer inspectedSpaceElementId) {
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
