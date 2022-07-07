package com.cnf.domain.tasks;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Property {

  @PrimaryKey(autoGenerate = false)
  @ColumnInfo(name = "propertyid")
  private Integer propertyId;
  @ColumnInfo(name = "municipality_municode")
  private Integer municode;
  @ColumnInfo(name = "parid")
  private String parId;
  @ColumnInfo(name = "lotandblock")
  private String lotAndBlock;
  @ColumnInfo(name = "address")
  private String address;
  @ColumnInfo(name = "usegroup")
  private String useGroup;
  @ColumnInfo(name = "constructiontype")
  private String constructionType;
  @ColumnInfo(name = "countycode")
  private String countyCode;
  @ColumnInfo(name = "property_notes")
  private String notes;
  @ColumnInfo(name = "addr_city")
  private String city;
  @ColumnInfo(name = "addr_state")
  private String state;
  @ColumnInfo(name = "addr_zip")
  private String zip;
  @ColumnInfo(name = "ownercode")
  private String ownerCode;
  @ColumnInfo(name = "propclass")
  private String propClass;
  @ColumnInfo(name = "property_lastupdated")
  private String lastUpdated;
  @ColumnInfo(name = "property_lastupdatedby")
  private Integer lastUpdatedBy;
  @ColumnInfo(name = "locationdescription")
  private String locationDescription;
  @ColumnInfo(name = "property_bobsource_sourceid")
  private Integer bobSourceId;
  @ColumnInfo(name = "unfitdatestart")
  private String unfitDateStart;
  @ColumnInfo(name = "unfitdatestop")
  private String unfitDateStop;
  @ColumnInfo(name = "unfitby_userid")
  private Integer unfitByUserId;
  @ColumnInfo(name = "abandoneddatestart")
  private String abandonedDateStart;
  @ColumnInfo(name = "abandoneddatestop")
  private String abandonedDateStop;
  @ColumnInfo(name = "abandonedby_userid")
  private Integer abandonedByUserId;
  @ColumnInfo(name = "vacantdatestart")
  private String vacantDateStart;
  @ColumnInfo(name = "vacantdatestop")
  private String vacantDateStop;
  @ColumnInfo(name = "vacantby_userid")
  private Integer vacantByUserId;
  @ColumnInfo(name = "property_condition_intensityclassid")
  private Integer conditionIntensityClassId;
  @ColumnInfo(name = "landbankprospect_intensityclassid")
  private Integer landBankProspectIntensityClassId;
  @ColumnInfo(name = "landbankheld")
  private Boolean landBankHeld;
  @ColumnInfo(name = "property_active")
  private Boolean active;
  @ColumnInfo(name = "nonaddressable")
  private Boolean nonAddressable;
  @ColumnInfo(name = "usetype_typeid")
  private Integer useTypeId;
  @ColumnInfo(name = "property_creationts")
  private String creationTS;

  public Integer getPropertyId() {
    return propertyId;
  }

  public void setPropertyId(Integer propertyId) {
    this.propertyId = propertyId;
  }

  public Integer getMunicode() {
    return municode;
  }

  public void setMunicode(Integer municode) {
    this.municode = municode;
  }

  public String getParId() {
    return parId;
  }

  public void setParId(String parId) {
    this.parId = parId;
  }

  public String getLotAndBlock() {
    return lotAndBlock;
  }

  public void setLotAndBlock(String lotAndBlock) {
    this.lotAndBlock = lotAndBlock;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getUseGroup() {
    return useGroup;
  }

  public void setUseGroup(String useGroup) {
    this.useGroup = useGroup;
  }

  public String getConstructionType() {
    return constructionType;
  }

  public void setConstructionType(String constructionType) {
    this.constructionType = constructionType;
  }

  public String getCountyCode() {
    return countyCode;
  }

  public void setCountyCode(String countyCode) {
    this.countyCode = countyCode;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getOwnerCode() {
    return ownerCode;
  }

  public void setOwnerCode(String ownerCode) {
    this.ownerCode = ownerCode;
  }

  public String getPropClass() {
    return propClass;
  }

  public void setPropClass(String propClass) {
    this.propClass = propClass;
  }

  public String getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(String lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  public Integer getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public void setLastUpdatedBy(Integer lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  public String getLocationDescription() {
    return locationDescription;
  }

  public void setLocationDescription(String locationDescription) {
    this.locationDescription = locationDescription;
  }

  public Integer getBobSourceId() {
    return bobSourceId;
  }

  public void setBobSourceId(Integer bobSourceId) {
    this.bobSourceId = bobSourceId;
  }

  public String getUnfitDateStart() {
    return unfitDateStart;
  }

  public void setUnfitDateStart(String unfitDateStart) {
    this.unfitDateStart = unfitDateStart;
  }

  public String getUnfitDateStop() {
    return unfitDateStop;
  }

  public void setUnfitDateStop(String unfitDateStop) {
    this.unfitDateStop = unfitDateStop;
  }

  public Integer getUnfitByUserId() {
    return unfitByUserId;
  }

  public void setUnfitByUserId(Integer unfitByUserId) {
    this.unfitByUserId = unfitByUserId;
  }

  public String getAbandonedDateStart() {
    return abandonedDateStart;
  }

  public void setAbandonedDateStart(String abandonedDateStart) {
    this.abandonedDateStart = abandonedDateStart;
  }

  public String getAbandonedDateStop() {
    return abandonedDateStop;
  }

  public void setAbandonedDateStop(String abandonedDateStop) {
    this.abandonedDateStop = abandonedDateStop;
  }

  public Integer getAbandonedByUserId() {
    return abandonedByUserId;
  }

  public void setAbandonedByUserId(Integer abandonedByUserId) {
    this.abandonedByUserId = abandonedByUserId;
  }

  public String getVacantDateStart() {
    return vacantDateStart;
  }

  public void setVacantDateStart(String vacantDateStart) {
    this.vacantDateStart = vacantDateStart;
  }

  public String getVacantDateStop() {
    return vacantDateStop;
  }

  public void setVacantDateStop(String vacantDateStop) {
    this.vacantDateStop = vacantDateStop;
  }

  public Integer getVacantByUserId() {
    return vacantByUserId;
  }

  public void setVacantByUserId(Integer vacantByUserId) {
    this.vacantByUserId = vacantByUserId;
  }

  public Integer getConditionIntensityClassId() {
    return conditionIntensityClassId;
  }

  public void setConditionIntensityClassId(Integer conditionIntensityClassId) {
    this.conditionIntensityClassId = conditionIntensityClassId;
  }

  public Integer getLandBankProspectIntensityClassId() {
    return landBankProspectIntensityClassId;
  }

  public void setLandBankProspectIntensityClassId(Integer landBankProspectIntensityClassId) {
    this.landBankProspectIntensityClassId = landBankProspectIntensityClassId;
  }

  public Boolean getLandBankHeld() {
    return landBankHeld;
  }

  public void setLandBankHeld(Boolean landBankHeld) {
    this.landBankHeld = landBankHeld;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Boolean getNonAddressable() {
    return nonAddressable;
  }

  public void setNonAddressable(Boolean nonAddressable) {
    this.nonAddressable = nonAddressable;
  }

  public Integer getUseTypeId() {
    return useTypeId;
  }

  public void setUseTypeId(Integer useTypeId) {
    this.useTypeId = useTypeId;
  }

  public String getCreationTS() {
    return creationTS;
  }

  public void setCreationTS(String creationTS) {
    this.creationTS = creationTS;
  }

  @Override
  public String toString() {
    return "Property{" +
        "propertyId=" + propertyId +
        ", municode=" + municode +
        ", parId='" + parId + '\'' +
        ", lotAndBlock='" + lotAndBlock + '\'' +
        ", address='" + address + '\'' +
        ", useGroup='" + useGroup + '\'' +
        ", constructionType='" + constructionType + '\'' +
        ", countyCode='" + countyCode + '\'' +
        ", notes='" + notes + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", zip='" + zip + '\'' +
        ", ownerCode='" + ownerCode + '\'' +
        ", propClass='" + propClass + '\'' +
        ", lastUpdated='" + lastUpdated + '\'' +
        ", lastUpdatedBy=" + lastUpdatedBy +
        ", locationDescription='" + locationDescription + '\'' +
        ", bobSourceId=" + bobSourceId +
        ", unfitDateStart='" + unfitDateStart + '\'' +
        ", unfitDateStop='" + unfitDateStop + '\'' +
        ", unfitByUserId=" + unfitByUserId +
        ", abandonedDateStart='" + abandonedDateStart + '\'' +
        ", abandonedDateStop='" + abandonedDateStop + '\'' +
        ", abandonedByUserId=" + abandonedByUserId +
        ", vacantDateStart='" + vacantDateStart + '\'' +
        ", vacantDateStop='" + vacantDateStop + '\'' +
        ", vacantByUserId=" + vacantByUserId +
        ", conditionIntensityClassId=" + conditionIntensityClassId +
        ", landBankProspectIntensityClassId=" + landBankProspectIntensityClassId +
        ", landBankHeld=" + landBankHeld +
        ", active=" + active +
        ", nonAddressable=" + nonAddressable +
        ", useTypeId=" + useTypeId +
        ", creationTS='" + creationTS + '\'' +
        '}';
  }
}





