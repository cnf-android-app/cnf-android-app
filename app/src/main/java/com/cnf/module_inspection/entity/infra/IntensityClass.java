package com.cnf.module_inspection.entity.infra;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Locale;

@Entity
public class IntensityClass {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "classid")
    private Integer classId;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "muni_municode")
    private Integer municode;
    @ColumnInfo(name = "numericrating")
    private Integer numericRating;
    @ColumnInfo(name = "schemaname")
    private String schemaName;
    @ColumnInfo(name = "active")
    private Boolean active;
    @ColumnInfo(name = "icon_iconid")
    private Integer iconId;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMunicode() {
        return municode;
    }

    public void setMunicode(Integer municode) {
        this.municode = municode;
    }

    public Integer getNumericRating() {
        return numericRating;
    }

    public void setNumericRating(Integer numericRating) {
        this.numericRating = numericRating;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getIconId() {
        return iconId;
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }


    @Override
    public String toString() {
        return numericRating + " - "+ title.toUpperCase(Locale.ROOT);
    }
}




