package com.cnf.domain;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity
public class OccInspectedSpaceElementPhotoDoc {

    @PrimaryKey(autoGenerate = false)
    private Integer photodoc_photodocid;
    private Integer inspectedspaceelement_elementid;

    public OccInspectedSpaceElementPhotoDoc(Integer photodoc_photodocid, Integer inspectedspaceelement_elementid) {
        this.photodoc_photodocid = photodoc_photodocid;
        this.inspectedspaceelement_elementid = inspectedspaceelement_elementid;
    }

    public Integer getPhotodoc_photodocid() {
        return photodoc_photodocid;
    }

    public void setPhotodoc_photodocid(Integer photodoc_photodocid) {
        this.photodoc_photodocid = photodoc_photodocid;
    }

    public Integer getInspectedspaceelement_elementid() {
        return inspectedspaceelement_elementid;
    }

    public void setInspectedspaceelement_elementid(Integer inspectedspaceelement_elementid) {
        this.inspectedspaceelement_elementid = inspectedspaceelement_elementid;
    }


}
