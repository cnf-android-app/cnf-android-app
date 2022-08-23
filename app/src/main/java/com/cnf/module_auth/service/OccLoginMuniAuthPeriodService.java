package com.cnf.module_auth.service;


import android.content.Context;
import com.cnf.module_inspection.dao.infra.LoginMuniAuthPeriodDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra.LoginMuniAuthPeriod;
import com.cnf.module_inspection.entity.infra_heavy.LoginMuniAuthPeriodHeavy;
import java.util.List;

public class OccLoginMuniAuthPeriodService {

  private LoginMuniAuthPeriodDao loginMuniAuthPeriodDao;

  private static final OccLoginMuniAuthPeriodService INSTANCE = new OccLoginMuniAuthPeriodService();

  private OccLoginMuniAuthPeriodService() {
  }

  public static OccLoginMuniAuthPeriodService getInstance(Context context) {
    INSTANCE.loginMuniAuthPeriodDao = InspectionDatabase.getInstance(context).getLoginMuniAuthPeriodDao();
    return INSTANCE;
  }


  public List<LoginMuniAuthPeriod> getLoginMuniAuthPeriodList() {
    return this.loginMuniAuthPeriodDao.selectLoginMuniAuthPeriodList();
  }


  public void insertLoginMuniAuthPeriodList(List<LoginMuniAuthPeriod> loginMuniAuthPeriodList) {
    this.loginMuniAuthPeriodDao.insertLoginMuniAuthPeriodList(loginMuniAuthPeriodList);
  }


  public void deleteAllLoginMuniAuthPeriodList() {
    this.loginMuniAuthPeriodDao.deleteAllLoginMuniAuthPeriodList();
  }

  public List<LoginMuniAuthPeriodHeavy> getLoginMuniAuthPeriodHeavyList() {
    return this.loginMuniAuthPeriodDao.selectAllLoginMuniAuthPeriodHeavyList();
  }

}
