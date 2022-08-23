package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.infra.OccSpaceTypeDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra.OccSpaceType;
import java.util.List;

public class OccSpaceTypeRepository {

  private OccSpaceTypeDao occSpaceTypeDao;

  private static final OccSpaceTypeRepository INSTANCE = new OccSpaceTypeRepository();

  private OccSpaceTypeRepository() {
  }

  public static OccSpaceTypeRepository getInstance(Context context) {
    INSTANCE.occSpaceTypeDao = InspectionDatabase.getInstance(context).getOccSpaceTypeDao();
    return INSTANCE;
  }

  public List<OccSpaceType> getOccSpaceTypeListFromSQLite() {
    return this.occSpaceTypeDao.selectAllOccSpaceTypeList();
  }

  public void insertOccSpaceTypeList(List<OccSpaceType> occSpaceTypeList) {
    this.occSpaceTypeDao.insertOccSpaceTypeList(occSpaceTypeList);
  }

  public void deleteAllOccSpaceTypeList() {
    this.occSpaceTypeDao.deleteAllOccSpaceTypeList();
  }

}
