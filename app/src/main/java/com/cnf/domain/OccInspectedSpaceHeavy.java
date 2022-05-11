package com.cnf.domain;

import androidx.room.PrimaryKey;

import lombok.Data;

@Data
public class OccInspectedSpaceHeavy {

    private Integer inspectedspaceid;
    private Integer occinspection_inspectionid;
    private Integer occlocationdescription_descid;
    private Integer addedtochecklistby_userid;
    private String addedtochecklistts;
    private Integer occchecklistspacetype_chklstspctypid;

    private String spacetitle;
    private String description;

    public OccInspectedSpaceHeavy(Integer inspectedspaceid, Integer occinspection_inspectionid, Integer occlocationdescription_descid, Integer addedtochecklistby_userid, String addedtochecklistts, Integer occchecklistspacetype_chklstspctypid, String spacetitle, String description) {
        this.inspectedspaceid = inspectedspaceid;
        this.occinspection_inspectionid = occinspection_inspectionid;
        this.occlocationdescription_descid = occlocationdescription_descid;
        this.addedtochecklistby_userid = addedtochecklistby_userid;
        this.addedtochecklistts = addedtochecklistts;
        this.occchecklistspacetype_chklstspctypid = occchecklistspacetype_chklstspctypid;
        this.spacetitle = spacetitle;
        this.description = description;
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

    public String getSpacetitle() {
        return spacetitle;
    }

    public void setSpacetitle(String spacetitle) {
        this.spacetitle = spacetitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
