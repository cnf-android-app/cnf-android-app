package com.cnf.module_auth.service;

import android.content.Context;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra.OccInspectionInfra;

public class OccInspectionSystemInitializationService {

  private InspectionDatabase inspectionDatabase;

  private static final OccInspectionSystemInitializationService INSTANCE = new OccInspectionSystemInitializationService();

  private OccInspectionSystemInitializationService() {
  }

  public static OccInspectionSystemInitializationService getInstance(Context context) {
    INSTANCE.inspectionDatabase = InspectionDatabase.getInstance(context);
    return INSTANCE;
  }

  public void initializeSystem(OccInspectionInfra occInspectionInfra) {

    inspectionDatabase.runInTransaction(() -> {
      inspectionDatabase.clearAllTables();

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

    });
  }

}
