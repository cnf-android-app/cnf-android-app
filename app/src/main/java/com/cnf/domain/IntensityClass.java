package com.cnf.domain;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity
@Data
public class IntensityClass {

    @PrimaryKey(autoGenerate = false)
    private Integer classid;
    private String title;
    private Integer muni_municode;
    private Integer numericrating;
    private String schemaname;
    private Boolean active;
    private Integer icon_iconid;

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMuni_municode() {
        return muni_municode;
    }

    public void setMuni_municode(Integer muni_municode) {
        this.muni_municode = muni_municode;
    }

    public Integer getNumericrating() {
        return numericrating;
    }

    public void setNumericrating(Integer numericrating) {
        this.numericrating = numericrating;
    }

    public String getSchemaname() {
        return schemaname;
    }

    public void setSchemaname(String schemaname) {
        this.schemaname = schemaname;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getIcon_iconid() {
        return icon_iconid;
    }

    public void setIcon_iconid(Integer icon_iconid) {
        this.icon_iconid = icon_iconid;
    }

    @Override
    public String toString() {
        return numericrating == null ? title : numericrating  + "-" + title;
    }
}
