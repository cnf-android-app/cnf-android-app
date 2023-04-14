package com.cnf.module_auth.service;

import android.content.Context;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra.LoginMuniAuthPeriod;
import com.cnf.module_inspection.entity.infra.OccInspectionInfra;
import com.cnf.module_inspection.entity.tasks.OccInspectionTasks;
import java.util.List;

public class OccMunicipalitySessionInitializationService {

  private InspectionDatabase inspectionDatabase;

  private static final OccMunicipalitySessionInitializationService INSTANCE = new OccMunicipalitySessionInitializationService();

  private OccMunicipalitySessionInitializationService() {
  }

  public static OccMunicipalitySessionInitializationService getInstance(Context context) {
    INSTANCE.inspectionDatabase = InspectionDatabase.getInstance(context);
    return INSTANCE;
  }

  public void initializeMunicipalitySession(List<LoginMuniAuthPeriod> loginMuniAuthPeriodList, List<OccInspectionTasks> occInspectionTasksList) {
    inspectionDatabase.runInTransaction(new Runnable() {
      @Override
      public void run() {

        inspectionDatabase.getLoginMuniAuthPeriodDao().deleteAllLoginMuniAuthPeriodList();

        inspectionDatabase.getOccInspectionDispatchDao().deleteAllOccInspectionDispatchList();
        inspectionDatabase.getCECaseDao().deleteAllCECaseList();
        inspectionDatabase.getPropertyDao().deleteAllPropertyList();
        inspectionDatabase.getLoginDao().deleteAllLoginList();
        inspectionDatabase.getPersonDao().deleteAllPersonList();

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
