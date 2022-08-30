package com.cnf.module_inspection.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
    @ForeignKey(entity = OccInspectedSpaceElement.class,
        parentColumns = "inspectedspaceelementid",
        childColumns = "inspectedspaceelement_elementid",
        onDelete = CASCADE),
    @ForeignKey(entity = PhotoDoc.class,
        parentColumns = "photodocid",
        childColumns = "photodoc_photodocid",
        onDelete = CASCADE)})
public class OccInspectedSpaceElementPhotoDoc {

  @ColumnInfo(name = "photodoc_photodocid")
  @PrimaryKey
  @NonNull
  private String photoDocId;
  @ColumnInfo(name = "inspectedspaceelement_elementid")
  private String inspectedSpaceElementId;

  public OccInspectedSpaceElementPhotoDoc(String photoDocId, String inspectedSpaceElementId) {
    this.photoDocId = photoDocId;
    this.inspectedSpaceElementId = inspectedSpaceElementId;
  }

  public String getPhotoDocId() {
    return photoDocId;
  }

  public void setPhotoDocId(String photoDocId) {
    this.photoDocId = photoDocId;
  }

  public String getInspectedSpaceElementId() {
    return inspectedSpaceElementId;
  }

  public void setInspectedSpaceElementId(String inspectedSpaceElementId) {
    this.inspectedSpaceElementId = inspectedSpaceElementId;
  }

  @Override
  public String toString() {
    return "OccInspectedSpaceElementPhotoDoc{" +
        "photoDocId=" + photoDocId +
        ", inspectedSpaceElementId=" + inspectedSpaceElementId +
        '}';
  }
}
