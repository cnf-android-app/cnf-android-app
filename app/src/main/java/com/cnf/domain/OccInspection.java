package com.cnf.domain;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class OccInspection {

  private Integer inspectionId;
  private Integer occperiod_periodid;
  private Integer inspector_userid;
  private Integer publicaccesscc;
  private boolean enablepacc;
  private String notespreinspection;
  private Integer thirdpartyinspector_personid;
  private LocalDateTime thirdpartyinspectorapprovalts;
  private Integer thirdpartyinspectorapprovalby;
  private Integer maxoccupantsallowed;
  private Integer numbedrooms;
  private Integer numbathrooms;
  private Integer occchecklist_checklistlistid;
  private OffsetDateTime effectivedate;
  private OffsetDateTime createdts;
  private Integer followupto_inspectionid;
  private LocalDateTime timestart;
  private LocalDateTime timeend;
  private Integer determination_detid;
  private Integer determinationby_userid;
  private LocalDateTime determinationts;
  private String remarks;
  private String generalcomments;
  private LocalDateTime deactivatedts;
  private Integer deactivatedby_userid;
  private Integer createdby_userid;
  private LocalDateTime lastupdatedts;
  private Integer lastupdatedby_userid;
  private Integer cause_causeid;
}
