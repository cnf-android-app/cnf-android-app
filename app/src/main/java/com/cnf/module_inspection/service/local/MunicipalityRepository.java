package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.infra.MunicipalityDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra.Municipality;
import java.util.List;

public class MunicipalityRepository {

  private MunicipalityDao municipalityDao;

  private static final MunicipalityRepository INSTANCE = new MunicipalityRepository();

  private MunicipalityRepository() {
  }

  public static MunicipalityRepository getInstance(Context context) {
    INSTANCE.municipalityDao = InspectionDatabase.getInstance(context).getMunicipalityDao();
    return INSTANCE;
  }

  public void insertMunicipalityList(List<Municipality> municipalityList) {
    this.municipalityDao.insertMunicipalityList(municipalityList);
  }

  public void deleteAllMunicipalityList() {
    this.municipalityDao.deleteAllMunicipalityList();
  }
}
