package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.tasks.OccInspectionTasks;

public class OccInspectionTaskRepository {

  private static final OccInspectionTaskRepository INSTANCE = new OccInspectionTaskRepository();
  private InspectionDatabase inspectionDatabase;

  private OccInspectionTaskRepository() {
  }

  public static OccInspectionTaskRepository getInstance(Context context) {
    INSTANCE.inspectionDatabase = InspectionDatabase.getInstance(context);
    return INSTANCE;
  }

  public void deleteOccInspectionTask() {
    inspectionDatabase.runInTransaction(() -> {
      inspectionDatabase.getOccInspectionDispatchDao().deleteAllOccInspectionDispatchList();
      inspectionDatabase.getCECaseDao().deleteAllCECaseList();
      inspectionDatabase.getPropertyDao().deleteAllPropertyList();
      inspectionDatabase.getLoginDao().deleteAllLoginList();
      inspectionDatabase.getPersonDao().deleteAllPersonList();
    });

  }

  public void insertOccInspectionTask(OccInspectionTasks occInspectionTasks) {
    inspectionDatabase.runInTransaction(() -> {
      inspectionDatabase.getOccInspectionDispatchDao().insertOccInspectionDispatchList(occInspectionTasks.getOccInspectionDispatchList());
      inspectionDatabase.getOccInspectionDao().insertOccInspectionList(occInspectionTasks.getOccInspectionList());
      inspectionDatabase.getCECaseDao().insertCECaseList(occInspectionTasks.getCeCaseList());
      inspectionDatabase.getPropertyDao().insertPropertyList(occInspectionTasks.getPropertyList());
      inspectionDatabase.getLoginDao().insertLoginList(occInspectionTasks.getLoginList());
      inspectionDatabase.getPersonDao().insertPersonList(occInspectionTasks.getPersonList());

      inspectionDatabase.getOccInspectedSpaceDao().insertOccInspectedSpaceList(occInspectionTasks.getOccInspectedSpaceList());
      inspectionDatabase.getOccInspectedSpaceElementDao().insertOccInspectedSpaceElementList(occInspectionTasks.getOccInspectedSpaceElementList());
      inspectionDatabase.getBlobBytesDao().insertBlobByteList(occInspectionTasks.getBlobBytesList());
      inspectionDatabase.getPhotoDocDao().insertPhotoDoc(occInspectionTasks.getPhotoDocList());
      inspectionDatabase.getOccInspectedSpaceElementPhotoDocDao().insertOccInspectedSpaceElementPhotoDocList(occInspectionTasks.getOccInspectedSpaceElementPhotoDocList());
    });
  }

}
