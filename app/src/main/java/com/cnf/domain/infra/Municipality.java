package com.cnf.domain.infra;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Municipality {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "municode")
    private Integer municode;
    @ColumnInfo(name = "muniname")
    private String muniName;
    @ColumnInfo(name = "address_street")
    private String addressStreet;
    @ColumnInfo(name = "address_city")
    private String addressCity;
    @ColumnInfo(name = "address_state")
    private String addressState;
    @ColumnInfo(name = "address_zip")
    private String addressZip;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "fax")
    private String fax;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "population")
    private Integer population;
    @ColumnInfo(name = "activeinprogram")
    private Boolean activeInProgram;
    @ColumnInfo(name = "defaultcodeset")
    private Integer defaultCodeSet;
    @ColumnInfo(name = "occpermitissuingsource_sourceid")
    private Integer occPermitIssuingSourceId;
    @ColumnInfo(name = "novprintstyle_styleid")
    private Integer novPrintStyleId;
    @ColumnInfo(name = "profile_profileid")
    private Integer profileId;
    @ColumnInfo(name = "enablecodeenforcement")
    private Boolean enableCodeEnforcement;
    @ColumnInfo(name = "enableoccupancy")
    private Boolean enableOccupancy;
    @ColumnInfo(name = "enablepublicceactionreqsub")
    private Boolean enablePublicCEActionReqSub;
    @ColumnInfo(name = "enablepublicceactionreqinfo")
    private Boolean enablePublicCEActionReqInfo;
    @ColumnInfo(name = "enablepublicoccpermitapp")
    private Boolean enablePublicOccPermitApp;
    @ColumnInfo(name = "enablepublicoccinspectodo")
    private Boolean enablePublicOccInspectionTODOs;
    @ColumnInfo(name = "munimanager_userid")
    private Integer muniManagerUserId;
    @ColumnInfo(name = "office_propertyid")
    private Integer officePropertyId;
    @ColumnInfo(name = "municipality_notes")
    private String notes;
    @ColumnInfo(name = "lastupdatedts")
    private String lastUpdatedTS;
    @ColumnInfo(name = "lastupdated_userid")
    private Integer lastUpdatedUserId;
    @ColumnInfo(name = "primarystaffcontact_userid")
    private Integer primaryStaffContactUserId;
    @ColumnInfo(name = "defaultoccperiod")
    private Integer defaultOccPeriod;
    @ColumnInfo(name = "officeparcel_parcelid")
    private Integer officeParcelId;

    public Integer getMunicode() {
        return municode;
    }

    public void setMunicode(Integer municode) {
        this.municode = municode;
    }

    public String getMuniName() {
        return muniName;
    }

    public void setMuniName(String muniName) {
        this.muniName = muniName;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public String getAddressZip() {
        return addressZip;
    }

    public void setAddressZip(String addressZip) {
        this.addressZip = addressZip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Boolean getActiveInProgram() {
        return activeInProgram;
    }

    public void setActiveInProgram(Boolean activeInProgram) {
        this.activeInProgram = activeInProgram;
    }

    public Integer getDefaultCodeSet() {
        return defaultCodeSet;
    }

    public void setDefaultCodeSet(Integer defaultCodeSet) {
        this.defaultCodeSet = defaultCodeSet;
    }

    public Integer getOccPermitIssuingSourceId() {
        return occPermitIssuingSourceId;
    }

    public void setOccPermitIssuingSourceId(Integer occPermitIssuingSourceId) {
        this.occPermitIssuingSourceId = occPermitIssuingSourceId;
    }

    public Integer getNovPrintStyleId() {
        return novPrintStyleId;
    }

    public void setNovPrintStyleId(Integer novPrintStyleId) {
        this.novPrintStyleId = novPrintStyleId;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Boolean getEnableCodeEnforcement() {
        return enableCodeEnforcement;
    }

    public void setEnableCodeEnforcement(Boolean enableCodeEnforcement) {
        this.enableCodeEnforcement = enableCodeEnforcement;
    }

    public Boolean getEnableOccupancy() {
        return enableOccupancy;
    }

    public void setEnableOccupancy(Boolean enableOccupancy) {
        this.enableOccupancy = enableOccupancy;
    }

    public Boolean getEnablePublicCEActionReqSub() {
        return enablePublicCEActionReqSub;
    }

    public void setEnablePublicCEActionReqSub(Boolean enablePublicCEActionReqSub) {
        this.enablePublicCEActionReqSub = enablePublicCEActionReqSub;
    }

    public Boolean getEnablePublicCEActionReqInfo() {
        return enablePublicCEActionReqInfo;
    }

    public void setEnablePublicCEActionReqInfo(Boolean enablePublicCEActionReqInfo) {
        this.enablePublicCEActionReqInfo = enablePublicCEActionReqInfo;
    }

    public Boolean getEnablePublicOccPermitApp() {
        return enablePublicOccPermitApp;
    }

    public void setEnablePublicOccPermitApp(Boolean enablePublicOccPermitApp) {
        this.enablePublicOccPermitApp = enablePublicOccPermitApp;
    }

    public Boolean getEnablePublicOccInspectionTODOs() {
        return enablePublicOccInspectionTODOs;
    }

    public void setEnablePublicOccInspectionTODOs(Boolean enablePublicOccInspectionTODOs) {
        this.enablePublicOccInspectionTODOs = enablePublicOccInspectionTODOs;
    }

    public Integer getMuniManagerUserId() {
        return muniManagerUserId;
    }

    public void setMuniManagerUserId(Integer muniManagerUserId) {
        this.muniManagerUserId = muniManagerUserId;
    }

    public Integer getOfficePropertyId() {
        return officePropertyId;
    }

    public void setOfficePropertyId(Integer officePropertyId) {
        this.officePropertyId = officePropertyId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLastUpdatedTS() {
        return lastUpdatedTS;
    }

    public void setLastUpdatedTS(String lastUpdatedTS) {
        this.lastUpdatedTS = lastUpdatedTS;
    }

    public Integer getLastUpdatedUserId() {
        return lastUpdatedUserId;
    }

    public void setLastUpdatedUserId(Integer lastUpdatedUserId) {
        this.lastUpdatedUserId = lastUpdatedUserId;
    }

    public Integer getPrimaryStaffContactUserId() {
        return primaryStaffContactUserId;
    }

    public void setPrimaryStaffContactUserId(Integer primaryStaffContactUserId) {
        this.primaryStaffContactUserId = primaryStaffContactUserId;
    }

    public Integer getDefaultOccPeriod() {
        return defaultOccPeriod;
    }

    public void setDefaultOccPeriod(Integer defaultOccPeriod) {
        this.defaultOccPeriod = defaultOccPeriod;
    }

    public Integer getOfficeParcelId() {
        return officeParcelId;
    }

    public void setOfficeParcelId(Integer officeParcelId) {
        this.officeParcelId = officeParcelId;
    }

    @Override
    public String toString() {
        return "Municipality{" +
                "municode=" + municode +
                ", muniName='" + muniName + '\'' +
                ", addressStreet='" + addressStreet + '\'' +
                ", addressCity='" + addressCity + '\'' +
                ", addressState='" + addressState + '\'' +
                ", addressZip='" + addressZip + '\'' +
                ", phone='" + phone + '\'' +
                ", fax='" + fax + '\'' +
                ", email='" + email + '\'' +
                ", population=" + population +
                ", activeInProgram=" + activeInProgram +
                ", defaultCodeSet=" + defaultCodeSet +
                ", occPermitIssuingSourceId=" + occPermitIssuingSourceId +
                ", novPrintStyleId=" + novPrintStyleId +
                ", profileId=" + profileId +
                ", enableCodeEnforcement=" + enableCodeEnforcement +
                ", enableOccupancy=" + enableOccupancy +
                ", enablePublicCEActionReqSub=" + enablePublicCEActionReqSub +
                ", enablePublicCEActionReqInfo=" + enablePublicCEActionReqInfo +
                ", enablePublicOccPermitApp=" + enablePublicOccPermitApp +
                ", enablePublicOccInspectionTODOs=" + enablePublicOccInspectionTODOs +
                ", muniManagerUserId=" + muniManagerUserId +
                ", officePropertyId=" + officePropertyId +
                ", notes='" + notes + '\'' +
                ", lastUpdatedTS='" + lastUpdatedTS + '\'' +
                ", lastUpdatedUserId=" + lastUpdatedUserId +
                ", primaryStaffContactUserId=" + primaryStaffContactUserId +
                ", defaultOccPeriod=" + defaultOccPeriod +
                ", officeParcelId=" + officeParcelId +
                '}';
    }
}
