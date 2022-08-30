package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.infra.OccLocationDescriptionDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra.OccLocationDescription;
import java.util.List;

public class OccLocationDescriptionRepository {

  private OccLocationDescriptionDao occChecklistSpaceTypeElementDao;

  private static final OccLocationDescriptionRepository INSTANCE = new OccLocationDescriptionRepository();

  private OccLocationDescriptionRepository() {
  }

  public static OccLocationDescriptionRepository getInstance(Context context) {
    INSTANCE.occChecklistSpaceTypeElementDao = InspectionDatabase.getInstance(context).getOccLocationDescriptionDao();
    return INSTANCE;
  }

  public List<OccLocationDescription> getOccLocationDescriptionListFromSQLite() {
    return this.occChecklistSpaceTypeElementDao.selectAllOccLocationDescriptionList();
  }
}
