package com.cnf.module_inspection.entity.tasks;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PropertyUnit {

  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "unitid")
  private Integer unitId;
  @ColumnInfo(name = "unitnumber")
  private String unitNumber;
  @ColumnInfo(name = "property_propertyid")
  private Integer propertyId;
  @ColumnInfo(name = "otherknownaddress")
  private String otherKnownAddress;
  @ColumnInfo(name = "notes")
  private String notes;
  @ColumnInfo(name = "rentalintentdatestart")
  private String rentalIntentDateStart;
  @ColumnInfo(name = "rentalintentdatestop")
  private String rentalIntentDateStop;
  @ColumnInfo(name = "rentalintentlastupdatedby_userid")
  private Integer rentalIntentLastUpdatedByUserId;
  @ColumnInfo(name = "rentalnotes")
  private String rentalNotes;
  @ColumnInfo(name = "active")
  private Boolean active;
  @ColumnInfo(name = "condition_intensityclassid")
  private Integer conditionIntensityClassId;
  @ColumnInfo(name = "lastupdatedts")
  private String lastUpdatedTS;
  @ColumnInfo(name = "rental")
  private Boolean rental;

  public Integer getUnitId() {
    return unitId;
  }

  public void setUnitId(Integer unitId) {
    this.unitId = unitId;
  }

  public String getUnitNumber() {
    return unitNumber;
  }

  public void setUnitNumber(String unitNumber) {
    this.unitNumber = unitNumber;
  }

  public Integer getPropertyId() {
    return propertyId;
  }

  public void setPropertyId(Integer propertyId) {
    this.propertyId = propertyId;
  }

  public String getOtherKnownAddress() {
    return otherKnownAddress;
  }

  public void setOtherKnownAddress(String otherKnownAddress) {
    this.otherKnownAddress = otherKnownAddress;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getRentalIntentDateStart() {
    return rentalIntentDateStart;
  }

  public void setRentalIntentDateStart(String rentalIntentDateStart) {
    this.rentalIntentDateStart = rentalIntentDateStart;
  }

  public String getRentalIntentDateStop() {
    return rentalIntentDateStop;
  }

  public void setRentalIntentDateStop(String rentalIntentDateStop) {
    this.rentalIntentDateStop = rentalIntentDateStop;
  }

  public Integer getRentalIntentLastUpdatedByUserId() {
    return rentalIntentLastUpdatedByUserId;
  }

  public void setRentalIntentLastUpdatedByUserId(Integer rentalIntentLastUpdatedByUserId) {
    this.rentalIntentLastUpdatedByUserId = rentalIntentLastUpdatedByUserId;
  }

  public String getRentalNotes() {
    return rentalNotes;
  }

  public void setRentalNotes(String rentalNotes) {
    this.rentalNotes = rentalNotes;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Integer getConditionIntensityClassId() {
    return conditionIntensityClassId;
  }

  public void setConditionIntensityClassId(Integer conditionIntensityClassId) {
    this.conditionIntensityClassId = conditionIntensityClassId;
  }

  public String getLastUpdatedTS() {
    return lastUpdatedTS;
  }

  public void setLastUpdatedTS(String lastUpdatedTS) {
    this.lastUpdatedTS = lastUpdatedTS;
  }

  public Boolean getRental() {
    return rental;
  }

  public void setRental(Boolean rental) {
    this.rental = rental;
  }

  @Override
  public String toString() {
    return "PropertyUnit{" +
        "unitId=" + unitId +
        ", unitNumber='" + unitNumber + '\'' +
        ", propertyId=" + propertyId +
        ", otherKnownAddress='" + otherKnownAddress + '\'' +
        ", notes='" + notes + '\'' +
        ", rentalIntentDateStart='" + rentalIntentDateStart + '\'' +
        ", rentalIntentDateStop='" + rentalIntentDateStop + '\'' +
        ", rentalIntentLastUpdatedByUserId=" + rentalIntentLastUpdatedByUserId +
        ", rentalNotes='" + rentalNotes + '\'' +
        ", active=" + active +
        ", conditionIntensityClassId=" + conditionIntensityClassId +
        ", lastUpdatedTS='" + lastUpdatedTS + '\'' +
        ", rental=" + rental +
        '}';
  }
}
