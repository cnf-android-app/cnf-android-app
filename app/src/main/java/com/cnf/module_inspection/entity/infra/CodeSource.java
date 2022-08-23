package com.cnf.module_inspection.entity.infra;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CodeSource {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "sourceid")
    private Integer sourceId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "year")
    private Integer year;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "isactive")
    private Boolean isActive;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "code_source_notes")
    private String notes;

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "CodeSource{" +
                "sourceId=" + sourceId +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", url='" + url + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
