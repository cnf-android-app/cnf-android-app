package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.entity.tasks.OccInspectionTasks;

public class OccInspectionTaskRepository {

  private Context context;

  private static final OccInspectionTaskRepository INSTANCE = new OccInspectionTaskRepository();

  private OccInspectionTaskRepository() {
  }

  public static OccInspectionTaskRepository getInstance(Context context) {
    INSTANCE.context = context;
    return INSTANCE;
  }

  public void deleteOccInspectionTask() {
    OccInspectionDispatchRepository.getInstance(context).deleteAllOccInspectionDispatchList();
    OccInspectionCECaseRepository.getInstance(context).deleteAllCECaseList();
    OccInspectionPropertyRepository.getInstance(context).deleteAllPropertyList();
    OccInspectionLoginRepository.getInstance(context).deleteAllLoginList();
    OccInspectionPersonRepository.getInstance(context).deleteAllPersonList();
  }

  public void insertOccInspectionTask(OccInspectionTasks occInspectionTasks) {
    OccInspectionDispatchRepository.getInstance(context).insertOccInspectionDispatchList(occInspectionTasks.getOccInspectionDispatchList());
    OccInspectionRepository.getInstance(context).insertOccInspectionList(occInspectionTasks.getOccInspectionList());
    OccInspectionCECaseRepository.getInstance(context).insertCECaseList(occInspectionTasks.getCeCaseList());
    OccInspectionPropertyRepository.getInstance(context).insertPropertyList(occInspectionTasks.getPropertyList());
    OccInspectionLoginRepository.getInstance(context).insertLoginList(occInspectionTasks.getLoginList());
    OccInspectionPersonRepository.getInstance(context).insertPersonList(occInspectionTasks.getPersonList());

    OccInspectionSpaceRepository.getInstance(context).insertOccInspectedSpaceList(occInspectionTasks.getOccInspectedSpaceList());
    OccInspectionSpaceElementRepository.getInstance(context).insertOccInspectedSpaceElementList(occInspectionTasks.getOccInspectedSpaceElementList());
    OccInspectedSpaceElementPhotoDocRepository.getInstance(context).insertOccInspectedSpaceElementPhotoDocList(occInspectionTasks.getOccInspectedSpaceElementPhotoDocList());
    OccInspectionPhotoRepository.getInstance(context).insertPhotoDocList(occInspectionTasks.getPhotoDocList());
    BlobBytesRepository.getInstance(context).insertBlobByteList(occInspectionTasks.getBlobBytesList());
  }

}
