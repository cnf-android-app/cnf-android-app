package com.cnf.module_inspection.service.local;

import android.content.Context;
import androidx.room.Transaction;
import com.cnf.module_inspection.dao.BlobBytesDao;
import com.cnf.module_inspection.dao.OccInspectedSpaceElementPhotoDocDao;
import com.cnf.module_inspection.dao.PhotoDocDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.BlobBytes;
import com.cnf.module_inspection.entity.PhotoDoc;
import java.util.List;

public class OccInspectionPhotoRepository {

  private PhotoDocDao photoDocDao;
  private BlobBytesDao blobBytesDao;
  private OccInspectedSpaceElementPhotoDocDao occInspectedSpaceElementPhotoDocDao;
  private static final OccInspectionPhotoRepository INSTANCE = new OccInspectionPhotoRepository();

  private OccInspectionPhotoRepository() {
  }

  public static OccInspectionPhotoRepository getInstance(Context context) {
    INSTANCE.photoDocDao = InspectionDatabase.getInstance(context).getPhotoDocDao();
    INSTANCE.blobBytesDao = InspectionDatabase.getInstance(context).getBlobBytesDao();
    INSTANCE.occInspectedSpaceElementPhotoDocDao = InspectionDatabase.getInstance(context).getOccInspectedSpaceElementPhotoDocDao();
    return INSTANCE;
  }

  public void insertPhotoDocList(List<PhotoDoc> photoDocList) {
    this.photoDocDao.insertPhotoDoc(photoDocList);
  }

  @Transaction
  public void deleteOccInspectedPhotoBlobByte(BlobBytes blobBytes) {
    this.blobBytesDao.deleteBlobByte(blobBytes);
    PhotoDoc photoDoc = this.photoDocDao.selectOnePhotoDoc(blobBytes.getBytesId());
    this.photoDocDao.deletePhotoDoc(photoDoc);
    this.occInspectedSpaceElementPhotoDocDao.deleteOccInspectedSpaceElementPhotoDocList(photoDoc.getPhotoDocId());
  }

  public long insertPhotoDoc(PhotoDoc photoDoc) {
    return this.photoDocDao.insertPhotoDoc(photoDoc);
  }

}
