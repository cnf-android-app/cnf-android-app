package com.cnf.dto;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class InspectionTaskDTO {

    @PrimaryKey(autoGenerate = false)
    private Integer inspectionId;
    private String firstName;
    private String lastName;
    private String address;
    private String unitNumber;
    private String title;
    private String description;
    private String createdTS;
    private String synchronizationTS;
    private Integer periodId;
    private boolean uploaded;


    public Integer getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Integer inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedTS() {
        return createdTS;
    }

    public void setCreatedTS(String createdTS) {
        this.createdTS = createdTS;
    }

    public String getSynchronizationTS() {
        return synchronizationTS;
    }

    public void setSynchronizationTS(String synchronizationTS) {
        this.synchronizationTS = synchronizationTS;
    }

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    @Override
    public String toString() {
        return "InspectionTaskDTO{" +
                "inspectionId=" + inspectionId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", unitNumber='" + unitNumber + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdTS='" + createdTS + '\'' +
                ", synchronizationTS='" + synchronizationTS + '\'' +
                ", periodId=" + periodId +
                ", uploaded=" + uploaded +
                '}';
    }
}
