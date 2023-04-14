package com.cnf.module_inspection.entity;

public class OccInspectableStatus extends Status {

  private final OccInspectionStatusEnum statusEnum;

  public OccInspectableStatus(OccInspectionStatusEnum enumVal) {
    statusEnum = enumVal;
  }

  public OccInspectionStatusEnum getStatusEnum() {
    return statusEnum;
  }
}