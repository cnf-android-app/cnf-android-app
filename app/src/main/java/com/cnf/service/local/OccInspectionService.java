package com.cnf.service.local;

import static android.content.ContentValues.TAG;

import android.util.Log;
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.tasks.OccInspection;
import java.util.ArrayList;
import java.util.List;

public class OccInspectionService {

  private static OccInspectionService INSTANCE = null;

  private OccInspectionService() {
  }

  public static OccInspectionService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new OccInspectionService();
    }
    return INSTANCE;
  }

  public OccInspection getOccInspection(InspectionDatabase inspectionDatabase, int occInspectionId) {
    OccInspection occInspection = inspectionDatabase.getOccInspectionDao().selectOccInspection(occInspectionId);
    Log.d(TAG, String.format("Local Occ Inspection: %s", occInspection));
    return occInspection;
  }

  public void markOccInspectionInitialized(InspectionDatabase inspectionDatabase, int occInspectionId) {
    inspectionDatabase.getOccInspectionDao().markOccInspectionInitialized(occInspectionId);
  }
}
