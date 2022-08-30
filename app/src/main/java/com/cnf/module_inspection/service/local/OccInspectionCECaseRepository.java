package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.dispatch.CECaseDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.tasks.CECase;
import java.util.List;

public class OccInspectionCECaseRepository {

  private CECaseDao ceCaseDao;
  private static final OccInspectionCECaseRepository INSTANCE = new OccInspectionCECaseRepository();

  private OccInspectionCECaseRepository() {
  }

  public static OccInspectionCECaseRepository getInstance(Context context) {
    INSTANCE.ceCaseDao = InspectionDatabase.getInstance(context).getCECaseDao();
    return INSTANCE;
  }

  public List<CECase> getCECaseList() {
    return this.ceCaseDao.selectCECaseList();
  }

  public void insertCECaseList(List<CECase> ceCaseList) {
    this.ceCaseDao.insertCECaseList(ceCaseList);
  }

  public void deleteAllCECaseList() {
    this.ceCaseDao.deleteAllCECaseList();
  }

}
