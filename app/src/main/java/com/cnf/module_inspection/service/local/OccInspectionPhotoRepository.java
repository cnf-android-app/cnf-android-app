package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.PhotoDocDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.BlobBytes;
import com.cnf.module_inspection.entity.PhotoDoc;
import java.util.List;

public class OccInspectionPhotoRepository {

  private PhotoDocDao photoDocDao;
  private BlobBytesRepository blobBytesRepository;
  private OccInspectedSpaceElementPhotoDocRepository occInspectedSpaceElementPhotoDocRepository;
  private static final OccInspectionPhotoRepository INSTANCE = new OccInspectionPhotoRepository();

  private OccInspectionPhotoRepository() {
  }

  public static OccInspectionPhotoRepository getInstance(Context context) {
    INSTANCE.photoDocDao = InspectionDatabase.getInstance(context).getPhotoDocDao();
    INSTANCE.blobBytesRepository = BlobBytesRepository.getInstance(context);
    INSTANCE.occInspectedSpaceElementPhotoDocRepository = OccInspectedSpaceElementPhotoDocRepository.getInstance(context);
    return INSTANCE;
  }

  public void insertPhotoDocList(List<PhotoDoc> photoDocList) {
    this.photoDocDao.insertPhotoDoc(photoDocList);
  }

  public void deleteOccInspectedPhotoBlobByte(BlobBytes blobBytes) {
    this.blobBytesRepository.deleteBlobByte(blobBytes);
    PhotoDoc photoDoc = this.photoDocDao.selectOnePhotoDoc(blobBytes.getBytesId());
    this.photoDocDao.deletePhotoDoc(photoDoc);
    this.occInspectedSpaceElementPhotoDocRepository.deleteOccInspectedSpaceElementPhotoDocList(photoDoc.getPhotoDocId());
  }

  public long insertPhotoDoc(PhotoDoc photoDoc) {
    return this.photoDocDao.insertPhotoDoc(photoDoc);
  }

  public List<PhotoDoc> selectAllPhotoDocList(String occInspectedSpaceTypeElementId) {
    return this.photoDocDao.selectAllPhotoDocListByOccInspectedSpaceTypeElementId(occInspectedSpaceTypeElementId);
  }

  public void deletePhotoDoc(PhotoDoc photoDoc) {
    this.photoDocDao.deletePhotoDoc(photoDoc);
  }

}
