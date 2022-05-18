package com.cnf.domain;

public class OccInspectableStatus
        extends Status {

    private final OccInspectionStatusEnum statusEnum;

    public OccInspectableStatus(OccInspectionStatusEnum enumVal) {
        statusEnum = enumVal;
    }


    /**
     * @return the statusEnum
     */
    public OccInspectionStatusEnum getStatusEnum() {
        return statusEnum;
    }


}