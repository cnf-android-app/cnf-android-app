package com.cnf.domain;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Dao
public class OccInspectedSpace {

    @PrimaryKey(autoGenerate = true)
    private Integer inspectedspaceid;
    private Integer occinspection_inspectionid;
    private Integer occlocationdescription_descid;
    private Integer addedtochecklistby_userid;
    private String addedtochecklistts;
    private Integer occchecklistspacetype_chklstspctypid;

    public OccInspectedSpace(Integer occinspection_inspectionid, Integer occlocationdescription_descid, Integer addedtochecklistby_userid, String addedtochecklistts, Integer occchecklistspacetype_chklstspctypid) {
        this.occinspection_inspectionid = occinspection_inspectionid;
        this.occlocationdescription_descid = occlocationdescription_descid;
        this.addedtochecklistby_userid = addedtochecklistby_userid;
        this.addedtochecklistts = addedtochecklistts;
        this.occchecklistspacetype_chklstspctypid = occchecklistspacetype_chklstspctypid;
    }

    public Integer getInspectedspaceid() {
        return inspectedspaceid;
    }

    public void setInspectedspaceid(Integer inspectedspaceid) {
        this.inspectedspaceid = inspectedspaceid;
    }

    public Integer getOccinspection_inspectionid() {
        return occinspection_inspectionid;
    }

    public void setOccinspection_inspectionid(Integer occinspection_inspectionid) {
        this.occinspection_inspectionid = occinspection_inspectionid;
    }

    public Integer getOcclocationdescription_descid() {
        return occlocationdescription_descid;
    }

    public void setOcclocationdescription_descid(Integer occlocationdescription_descid) {
        this.occlocationdescription_descid = occlocationdescription_descid;
    }

    public Integer getAddedtochecklistby_userid() {
        return addedtochecklistby_userid;
    }

    public void setAddedtochecklistby_userid(Integer addedtochecklistby_userid) {
        this.addedtochecklistby_userid = addedtochecklistby_userid;
    }

    public String getAddedtochecklistts() {
        return addedtochecklistts;
    }

    public void setAddedtochecklistts(String addedtochecklistts) {
        this.addedtochecklistts = addedtochecklistts;
    }

    public Integer getOccchecklistspacetype_chklstspctypid() {
        return occchecklistspacetype_chklstspctypid;
    }

    public void setOccchecklistspacetype_chklstspctypid(Integer occchecklistspacetype_chklstspctypid) {
        this.occchecklistspacetype_chklstspctypid = occchecklistspacetype_chklstspctypid;
    }

    @Override
    public String toString() {
        return "OccInspectedSpace{" +
                "inspectedspaceid=" + inspectedspaceid +
                ", occinspection_inspectionid=" + occinspection_inspectionid +
                ", occlocationdescription_descid=" + occlocationdescription_descid +
                ", addedtochecklistby_userid=" + addedtochecklistby_userid +
                ", addedtochecklistts='" + addedtochecklistts + '\'' +
                ", occchecklistspacetype_chklstspctypid=" + occchecklistspacetype_chklstspctypid +
                '}';
    }
};
