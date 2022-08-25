package com.cnf.module_inspection.entity;

public class OccInspectedSpaceStatus {

  private int finishedInspectedSpaceElementCount;
  private int unFinishInspectedSpaceElementCount;

  public OccInspectedSpaceStatus(int finishedInspectedSpaceElementCount, int unFinishInspectedSpaceElementCount) {
    this.finishedInspectedSpaceElementCount = finishedInspectedSpaceElementCount;
    this.unFinishInspectedSpaceElementCount = unFinishInspectedSpaceElementCount;
  }

  public int getFinishedInspectedSpaceElementCount() {
    return finishedInspectedSpaceElementCount;
  }

  public void setFinishedInspectedSpaceElementCount(int finishedInspectedSpaceElementCount) {
    this.finishedInspectedSpaceElementCount = finishedInspectedSpaceElementCount;
  }

  public int getUnFinishInspectedSpaceElementCount() {
    return unFinishInspectedSpaceElementCount;
  }

  public void setUnFinishInspectedSpaceElementCount(int unFinishInspectedSpaceElementCount) {
    this.unFinishInspectedSpaceElementCount = unFinishInspectedSpaceElementCount;
  }

  @Override
  public String toString() {
    return "InspectedSpaceStatus{" +
        "finishedInspectedSpaceElementCount=" + finishedInspectedSpaceElementCount +
        ", unFinishInspectedSpaceElementCount=" + unFinishInspectedSpaceElementCount +
        '}';
  }
}
