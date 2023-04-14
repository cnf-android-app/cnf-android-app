package com.cnf.module_inspection.service.local;


import android.content.Context;
import com.cnf.module_inspection.dao.dispatch.OccInspectionDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.tasks.OccInspection;
import java.util.List;

public class OccInspectionRepository {

  private OccInspectionDao occInspectionDao;

  private static final OccInspectionRepository INSTANCE = new OccInspectionRepository();

  private OccInspectionRepository() {
  }

  public static OccInspectionRepository getInstance(Context context) {
    INSTANCE.occInspectionDao = InspectionDatabase.getInstance(context).getOccInspectionDao();
    return INSTANCE;
  }

  public void insertOccInspectionList(List<OccInspection> occInspectionList) {
    this.occInspectionDao.insertOccInspectionList(occInspectionList);
  }

}
