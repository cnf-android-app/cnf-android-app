package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.infra.OccChecklistSpaceTypeDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra.OccChecklistSpaceType;
import com.cnf.module_inspection.entity.infra_heavy.OccChecklistSpaceTypeHeavy;
import java.util.List;

public class OccInspectionChecklistSpaceTypeRepository {

  private OccChecklistSpaceTypeDao occChecklistSpaceTypeDao;

  private static final OccInspectionChecklistSpaceTypeRepository INSTANCE = new OccInspectionChecklistSpaceTypeRepository();

  private OccInspectionChecklistSpaceTypeRepository() {
  }

  public static OccInspectionChecklistSpaceTypeRepository getInstance(Context context) {
    INSTANCE.occChecklistSpaceTypeDao = InspectionDatabase.getInstance(context).getOccChecklistSpaceTypeDao();
    return INSTANCE;
  }

  public List<OccChecklistSpaceTypeHeavy> getOccChecklistSpaceTypeHeavyList(int checklistId) {
    return this.occChecklistSpaceTypeDao.selectAllOccChecklistSpaceTypeHeavyList(checklistId);
  }

  public void insertOccChecklistSpaceTypeList(List<OccChecklistSpaceType> occChecklistSpaceTypeList) {
    this.occChecklistSpaceTypeDao.insertOccChecklistSpaceTypeList(occChecklistSpaceTypeList);
  }

  public void deleteAllOccChecklistSpaceTypeList() {
    this.occChecklistSpaceTypeDao.deleteAllOccChecklistSpaceTypeList();
  }

}
