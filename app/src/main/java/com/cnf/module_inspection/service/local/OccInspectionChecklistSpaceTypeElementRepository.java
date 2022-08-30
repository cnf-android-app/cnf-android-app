package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.infra.OccChecklistSpaceTypeElementDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra.OccChecklistSpaceTypeElement;
import com.cnf.module_inspection.entity.infra_heavy.OccChecklistSpaceTypeElementHeavy;
import java.util.List;

public class OccInspectionChecklistSpaceTypeElementRepository {

  private OccChecklistSpaceTypeElementDao occChecklistSpaceTypeElementDao;

  private static final OccInspectionChecklistSpaceTypeElementRepository INSTANCE = new OccInspectionChecklistSpaceTypeElementRepository();

  private OccInspectionChecklistSpaceTypeElementRepository() {
  }

  public static OccInspectionChecklistSpaceTypeElementRepository getInstance(Context context) {
    INSTANCE.occChecklistSpaceTypeElementDao = InspectionDatabase.getInstance(context).getOccChecklistSpaceTypeElementDao();
    return INSTANCE;
  }

  public List<OccChecklistSpaceTypeElementHeavy> getOccChecklistSpaceTypeElementHeavyList(Integer checklistSpaceTypeId) {
    return this.occChecklistSpaceTypeElementDao.selectAllOccChecklistSpaceTypeElementListDetailsByCSTId(checklistSpaceTypeId);
  }

  public void insertOccChecklistSpaceTypeElementList(List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList) {
    this.occChecklistSpaceTypeElementDao.insertOccChecklistSpaceTypeElementList(occChecklistSpaceTypeElementList);
  }

  public void deleteAllOccChecklistSpaceTypeElementList() {
    this.occChecklistSpaceTypeElementDao.deleteAllOccChecklistSpaceTypeElementList();
  }

}
