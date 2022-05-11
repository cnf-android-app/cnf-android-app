package com.cnf.domain;


import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class MuniLogin {

  private Integer muni_municode;
  private Integer userid;
  private boolean defaultmuni;
  private OffsetDateTime accessgranteddatestart;
  private OffsetDateTime accessgranteddatestop;
  private OffsetDateTime codeofficerstartdate;
  private OffsetDateTime codeofficerstopdate;
  private OffsetDateTime staffstartdate;
  private OffsetDateTime staffstopdate;
  private OffsetDateTime sysadminstartdate;
  private OffsetDateTime sysadminstopdate;
  private OffsetDateTime supportstartdate;
  private OffsetDateTime supportstopdate;
  private Integer codeofficerassignmentorder;
  private Integer staffassignmentorder;
  private Integer sysadminassignmentorder;
  private Integer supportassignmentorder;
  private Integer bypasscodeofficerassignmentorder;
  private Integer bypassstaffassignmentorder;
  private Integer bypasssysadminassignmentorder;
  private Integer bypasssupportassignmentorder;
  private OffsetDateTime recorddeactivatedts;
  private String userrole;
  private Integer muniloginrecordid;
  private OffsetDateTime recordcreatedts;
  private String badgenumber;
  private String orinumber;
  private Integer defaultcecase_caseid;

  public Integer getMuni_municode() {
    return muni_municode;
  }

  public void setMuni_municode(Integer muni_municode) {
    this.muni_municode = muni_municode;
  }

  public Integer getUserid() {
    return userid;
  }

  public void setUserid(Integer userid) {
    this.userid = userid;
  }

  public boolean isDefaultmuni() {
    return defaultmuni;
  }

  public void setDefaultmuni(boolean defaultmuni) {
    this.defaultmuni = defaultmuni;
  }

  public OffsetDateTime getAccessgranteddatestart() {
    return accessgranteddatestart;
  }

  public void setAccessgranteddatestart(OffsetDateTime accessgranteddatestart) {
    this.accessgranteddatestart = accessgranteddatestart;
  }

  public OffsetDateTime getAccessgranteddatestop() {
    return accessgranteddatestop;
  }

  public void setAccessgranteddatestop(OffsetDateTime accessgranteddatestop) {
    this.accessgranteddatestop = accessgranteddatestop;
  }

  public OffsetDateTime getCodeofficerstartdate() {
    return codeofficerstartdate;
  }

  public void setCodeofficerstartdate(OffsetDateTime codeofficerstartdate) {
    this.codeofficerstartdate = codeofficerstartdate;
  }

  public OffsetDateTime getCodeofficerstopdate() {
    return codeofficerstopdate;
  }

  public void setCodeofficerstopdate(OffsetDateTime codeofficerstopdate) {
    this.codeofficerstopdate = codeofficerstopdate;
  }

  public OffsetDateTime getStaffstartdate() {
    return staffstartdate;
  }

  public void setStaffstartdate(OffsetDateTime staffstartdate) {
    this.staffstartdate = staffstartdate;
  }

  public OffsetDateTime getStaffstopdate() {
    return staffstopdate;
  }

  public void setStaffstopdate(OffsetDateTime staffstopdate) {
    this.staffstopdate = staffstopdate;
  }

  public OffsetDateTime getSysadminstartdate() {
    return sysadminstartdate;
  }

  public void setSysadminstartdate(OffsetDateTime sysadminstartdate) {
    this.sysadminstartdate = sysadminstartdate;
  }

  public OffsetDateTime getSysadminstopdate() {
    return sysadminstopdate;
  }

  public void setSysadminstopdate(OffsetDateTime sysadminstopdate) {
    this.sysadminstopdate = sysadminstopdate;
  }

  public OffsetDateTime getSupportstartdate() {
    return supportstartdate;
  }

  public void setSupportstartdate(OffsetDateTime supportstartdate) {
    this.supportstartdate = supportstartdate;
  }

  public OffsetDateTime getSupportstopdate() {
    return supportstopdate;
  }

  public void setSupportstopdate(OffsetDateTime supportstopdate) {
    this.supportstopdate = supportstopdate;
  }

  public Integer getCodeofficerassignmentorder() {
    return codeofficerassignmentorder;
  }

  public void setCodeofficerassignmentorder(Integer codeofficerassignmentorder) {
    this.codeofficerassignmentorder = codeofficerassignmentorder;
  }

  public Integer getStaffassignmentorder() {
    return staffassignmentorder;
  }

  public void setStaffassignmentorder(Integer staffassignmentorder) {
    this.staffassignmentorder = staffassignmentorder;
  }

  public Integer getSysadminassignmentorder() {
    return sysadminassignmentorder;
  }

  public void setSysadminassignmentorder(Integer sysadminassignmentorder) {
    this.sysadminassignmentorder = sysadminassignmentorder;
  }

  public Integer getSupportassignmentorder() {
    return supportassignmentorder;
  }

  public void setSupportassignmentorder(Integer supportassignmentorder) {
    this.supportassignmentorder = supportassignmentorder;
  }

  public Integer getBypasscodeofficerassignmentorder() {
    return bypasscodeofficerassignmentorder;
  }

  public void setBypasscodeofficerassignmentorder(Integer bypasscodeofficerassignmentorder) {
    this.bypasscodeofficerassignmentorder = bypasscodeofficerassignmentorder;
  }

  public Integer getBypassstaffassignmentorder() {
    return bypassstaffassignmentorder;
  }

  public void setBypassstaffassignmentorder(Integer bypassstaffassignmentorder) {
    this.bypassstaffassignmentorder = bypassstaffassignmentorder;
  }

  public Integer getBypasssysadminassignmentorder() {
    return bypasssysadminassignmentorder;
  }

  public void setBypasssysadminassignmentorder(Integer bypasssysadminassignmentorder) {
    this.bypasssysadminassignmentorder = bypasssysadminassignmentorder;
  }

  public Integer getBypasssupportassignmentorder() {
    return bypasssupportassignmentorder;
  }

  public void setBypasssupportassignmentorder(Integer bypasssupportassignmentorder) {
    this.bypasssupportassignmentorder = bypasssupportassignmentorder;
  }

  public OffsetDateTime getRecorddeactivatedts() {
    return recorddeactivatedts;
  }

  public void setRecorddeactivatedts(OffsetDateTime recorddeactivatedts) {
    this.recorddeactivatedts = recorddeactivatedts;
  }

  public String getUserrole() {
    return userrole;
  }

  public void setUserrole(String userrole) {
    this.userrole = userrole;
  }

  public Integer getMuniloginrecordid() {
    return muniloginrecordid;
  }

  public void setMuniloginrecordid(Integer muniloginrecordid) {
    this.muniloginrecordid = muniloginrecordid;
  }

  public OffsetDateTime getRecordcreatedts() {
    return recordcreatedts;
  }

  public void setRecordcreatedts(OffsetDateTime recordcreatedts) {
    this.recordcreatedts = recordcreatedts;
  }

  public String getBadgenumber() {
    return badgenumber;
  }

  public void setBadgenumber(String badgenumber) {
    this.badgenumber = badgenumber;
  }

  public String getOrinumber() {
    return orinumber;
  }

  public void setOrinumber(String orinumber) {
    this.orinumber = orinumber;
  }

  public Integer getDefaultcecase_caseid() {
    return defaultcecase_caseid;
  }

  public void setDefaultcecase_caseid(Integer defaultcecase_caseid) {
    this.defaultcecase_caseid = defaultcecase_caseid;
  }
}
