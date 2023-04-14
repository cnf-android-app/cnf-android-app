package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.infra.IntensityClassDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra.IntensityClass;
import java.util.List;

public class IntensityRepository {

  private IntensityClassDao intensityClassDao;

  private static final IntensityRepository INSTANCE = new IntensityRepository();

  private IntensityRepository() {
  }

  public static IntensityRepository getInstance(Context context) {
    INSTANCE.intensityClassDao = InspectionDatabase.getInstance(context).getIntensityClassDao();
    return INSTANCE;
  }

  public void insertIntensityClassList(List<IntensityClass> intensityClassList) {
    this.intensityClassDao.insertIntensityClassList(intensityClassList);
  }

  public void deleteAllIntensityClassList() {
    this.intensityClassDao.deleteAllIntensityClassList();
  }

}
