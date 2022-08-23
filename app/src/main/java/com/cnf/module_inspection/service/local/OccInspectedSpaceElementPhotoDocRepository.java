package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.OccInspectedSpaceElementPhotoDocDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.OccInspectedSpaceElementPhotoDoc;
import java.util.List;

public class OccInspectedSpaceElementPhotoDocRepository {

  private OccInspectedSpaceElementPhotoDocDao occInspectedSpaceElementPhotoDocDao;

  private static final OccInspectedSpaceElementPhotoDocRepository INSTANCE = new OccInspectedSpaceElementPhotoDocRepository();

  private OccInspectedSpaceElementPhotoDocRepository() {
  }

  public static OccInspectedSpaceElementPhotoDocRepository getInstance(Context context) {
    INSTANCE.occInspectedSpaceElementPhotoDocDao = InspectionDatabase.getInstance(context).getOccInspectedSpaceElementPhotoDocDao();
    return INSTANCE;
  }

  public void insertOccInspectedSpaceElementPhotoDocList(List<OccInspectedSpaceElementPhotoDoc> occInspectedSpaceElementPhotoDocList) {
    this.occInspectedSpaceElementPhotoDocDao.insertOccInspectedSpaceElementPhotoDocList(occInspectedSpaceElementPhotoDocList);
  }

  public long insertOccInspectedSpaceElementPhotoDoc(OccInspectedSpaceElementPhotoDoc occInspectedSpaceElementPhotoDoc) {
    return this.occInspectedSpaceElementPhotoDocDao.insertOccInspectedSpaceElementPhotoDocList(occInspectedSpaceElementPhotoDoc);
  }

  public void deleteOccInspectedSpaceElementPhotoDocList(String photoDocId) {
    this.occInspectedSpaceElementPhotoDocDao.deleteOccInspectedSpaceElementPhotoDocList(photoDocId);
  }
}
