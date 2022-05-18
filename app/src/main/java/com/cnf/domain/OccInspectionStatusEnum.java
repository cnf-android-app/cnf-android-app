package com.cnf.domain;

public enum OccInspectionStatusEnum {
    NOTINSPECTED("Not inspected", 0, "inspectionStatusIcon_notinspected"),
    PASS("Passed", 1, "inspectionStatusIcon_pass"),
    VIOLATION("Violated", 2, "inspectionStatusIcon_fail");

    private final String label;
    private final int phaseOrder;
    private final String iconPropertyLookup;

    private OccInspectionStatusEnum(String label, int ord, String iconLkup){
        this.label = label;
        this.phaseOrder = ord;
        this.iconPropertyLookup = iconLkup;
    }

    public String getLabel(){
        return label;
    }

    public int getOrder(){
        return phaseOrder;
    }

    public String getIconPropertyLookup(){
        return iconPropertyLookup;
    }
}
