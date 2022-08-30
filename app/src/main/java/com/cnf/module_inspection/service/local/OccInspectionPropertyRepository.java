package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.dispatch.PropertyDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.tasks.Property;
import java.util.List;

public class OccInspectionPropertyRepository {

  private PropertyDao propertyDao;
  private static final OccInspectionPropertyRepository INSTANCE = new OccInspectionPropertyRepository();

  private OccInspectionPropertyRepository() {
  }

  public static OccInspectionPropertyRepository getInstance(Context context) {
    INSTANCE.propertyDao = InspectionDatabase.getInstance(context).getPropertyDao();
    return INSTANCE;
  }

  public List<Property> getPropertyList() {
    return this.propertyDao.selectPropertyList();
  }

  public void insertPropertyList(List<Property> propertyList) {
    this.propertyDao.insertPropertyList(propertyList);
  }

  public void deleteAllPropertyList() {
    this.propertyDao.deleteAllPropertyList();
  }

}
