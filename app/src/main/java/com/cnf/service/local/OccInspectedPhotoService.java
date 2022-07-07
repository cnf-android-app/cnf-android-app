package com.cnf.service.local;

import com.cnf.db.InspectionDatabase;
import com.cnf.domain.BlobBytes;
import com.cnf.domain.OccInspectedSpaceElementPhotoDoc;
import com.cnf.domain.PhotoDoc;

public class OccInspectedPhotoService {

  private static OccInspectedPhotoService INSTANCE = null;

  private OccInspectedPhotoService() {
  }

  public static OccInspectedPhotoService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new OccInspectedPhotoService();
    }
    return INSTANCE;
  }

  public void deleteOccInspectedPhotoBlobByte(InspectionDatabase inspectionDatabase, BlobBytes blobBytes) {
    inspectionDatabase.getBlobBytesDao().deleteBlobByte(blobBytes);
  }

  public long insertBlobBytes(InspectionDatabase inspectionDatabase, BlobBytes blobBytes) {
    return inspectionDatabase.getBlobBytesDao().insertBlobByteList(blobBytes);
  }

  public long insertPhotoDoc(InspectionDatabase inspectionDatabase, PhotoDoc photoDoc) {
    return inspectionDatabase.getPhotoDocDao().insertPhotoDoc(photoDoc);
  }

  public long insertOccInspectedSpaceElementPhotoDoc(InspectionDatabase inspectionDatabase, OccInspectedSpaceElementPhotoDoc occInspectedSpaceElementPhotoDoc) {
    return inspectionDatabase.getOccInspectedSpaceElementPhotoDocDao().insertOccInspectedSpaceElementPhotoDocList(occInspectedSpaceElementPhotoDoc);
  }

}
