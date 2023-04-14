package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.infra.OccCheckListDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra.OccCheckList;
import java.util.List;

public class OccChecklistRepository {

  private OccCheckListDao occCheckListDao;

  private static final OccChecklistRepository INSTANCE = new OccChecklistRepository();

  private OccChecklistRepository() {
  }

  public static OccChecklistRepository getInstance(Context context) {
    INSTANCE.occCheckListDao = InspectionDatabase.getInstance(context).getOccCheckListDao();
    return INSTANCE;
  }

  public void insertOccCheckListList(List<OccCheckList> occCheckListList) {
    this.occCheckListDao.insertOccCheckListList(occCheckListList);
  }

  public void deleteAllOccCheckListList() {
    this.occCheckListDao.deleteAllOccCheckListList();
  }

}
