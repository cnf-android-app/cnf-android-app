package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.dispatch.LoginDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.tasks.Login;
import java.util.List;

public class OccInspectionLoginRepository {

  private LoginDao loginDao;

  private static final OccInspectionLoginRepository INSTANCE = new OccInspectionLoginRepository();

  private OccInspectionLoginRepository() {
  }

  public static OccInspectionLoginRepository getInstance(Context context) {
    INSTANCE.loginDao = InspectionDatabase.getInstance(context).getLoginDao();
    return INSTANCE;
  }

  public List<Login> getLoginList() {
    return this.loginDao.selectLoginList();
  }

  public void insertLoginList(List<Login> loginList) {
    this.loginDao.insertLoginList(loginList);
  }

  public void deleteAllLoginList() {
    this.loginDao.deleteAllLoginList();
  }

}
