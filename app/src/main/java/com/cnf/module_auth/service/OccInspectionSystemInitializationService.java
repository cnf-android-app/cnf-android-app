package com.cnf.module_auth.service;

import android.content.Context;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra.LoginMuniAuthPeriod;
import com.cnf.module_inspection.entity.infra.OccInspectionInfra;
import com.cnf.module_inspection.entity.tasks.OccInspectionTasks;
import java.util.List;

public class OccInspectionSystemInitializationService {

  private InspectionDatabase inspectionDatabase;

  private static final OccInspectionSystemInitializationService INSTANCE = new OccInspectionSystemInitializationService();

  private OccInspectionSystemInitializationService() {
  }

  public static OccInspectionSystemInitializationService getInstance(Context context) {
    INSTANCE.inspectionDatabase = InspectionDatabase.getInstance(context);
    return INSTANCE;
  }

  public void initializeSystem(OccInspectionInfra occInspectionInfra, List<LoginMuniAuthPeriod> loginMuniAuthPeriodList, List<OccInspectionTasks> occInspectionTasksList) {
    inspectionDatabase.runInTransaction(new Runnable() {
      @Override
      public void run() {

        inspectionDatabase.getCodeSourceDao().deleteAllCodeSourceList();
        inspectionDatabase.getCodeElementDao().deleteAllCodeElementList();
        inspectionDatabase.getCodeSetElementDao().deleteAllCodeSetElementList();
        inspectionDatabase.getCodeElementGuideDao().deleteAllCodeElementGuideList();
        inspectionDatabase.getOccCheckListDao().deleteAllOccCheckListList();
        inspectionDatabase.getOccChecklistSpaceTypeDao().deleteAllOccChecklistSpaceTypeList();
        inspectionDatabase.getOccChecklistSpaceTypeElementDao().deleteAllOccChecklistSpaceTypeElementList();
        inspectionDatabase.getOccSpaceTypeDao().deleteAllOccSpaceTypeList();
        inspectionDatabase.getOccLocationDescriptionDao().deleteAllOccLocationDescriptionList();
        inspectionDatabase.getIntensityClassDao().deleteAllIntensityClassList();
        inspectionDatabase.getMunicipalityDao().deleteAllMunicipalityList();

        inspectionDatabase.getLoginMuniAuthPeriodDao().deleteAllLoginMuniAuthPeriodList();

        inspectionDatabase.getOccInspectionDispatchDao().deleteAllOccInspectionDispatchList();
        inspectionDatabase.getCECaseDao().deleteAllCECaseList();
        inspectionDatabase.getPropertyDao().deleteAllPropertyList();
        inspectionDatabase.getLoginDao().deleteAllLoginList();
        inspectionDatabase.getPersonDao().deleteAllPersonList();

        inspectionDatabase.getCodeSourceDao().insertCodeSourceList(occInspectionInfra.getCodeSourceList());
        inspectionDatabase.getCodeElementDao().insertCodeElementList(occInspectionInfra.getCodeElementList());
        inspectionDatabase.getCodeSetElementDao().insertCodeSetElementList(occInspectionInfra.getCodeSetElementList());
        inspectionDatabase.getCodeElementGuideDao().insertCodeElementGuideList(occInspectionInfra.getCodeElementGuideList());
        inspectionDatabase.getOccCheckListDao().insertOccCheckListList(occInspectionInfra.getOccCheckListList());
        inspectionDatabase.getOccChecklistSpaceTypeDao().insertOccChecklistSpaceTypeList(occInspectionInfra.getOccChecklistSpaceTypeList());
        inspectionDatabase.getOccChecklistSpaceTypeElementDao().insertOccChecklistSpaceTypeElementList(occInspectionInfra.getOccChecklistSpaceTypeElementList());
        inspectionDatabase.getOccSpaceTypeDao().insertOccSpaceTypeList(occInspectionInfra.getOccSpaceTypeList());
        inspectionDatabase.getOccLocationDescriptionDao().insertOccLocationDescriptionList(occInspectionInfra.getOccLocationDescriptionList());
        inspectionDatabase.getIntensityClassDao().insertIntensityClassList(occInspectionInfra.getIntensityClassList());
        inspectionDatabase.getMunicipalityDao().insertMunicipalityList(occInspectionInfra.getMunicipalityList());

        inspectionDatabase.getLoginMuniAuthPeriodDao().insertLoginMuniAuthPeriodList(loginMuniAuthPeriodList);

        for (OccInspectionTasks occInspectionTasks : occInspectionTasksList) {
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
        }
      }
    });
  }
}
