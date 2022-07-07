package com.cnf.service.local;

import static android.content.ContentValues.TAG;

import android.util.Log;
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.infra.LoginMuniAuthPeriod;
import com.cnf.domain.infra_heavy.LoginMuniAuthPeriodHeavy;
import com.cnf.service.exception.OccInspectionCopyNullPointerException;
import com.cnf.service.exception.OccLoginMuniAuthPeriodNullPointerException;
import java.util.ArrayList;
import java.util.List;

public class OccLoginMuniAuthPeriodService {

  private static OccLoginMuniAuthPeriodService INSTANCE = null;

  private OccLoginMuniAuthPeriodService() {
  }

  public static OccLoginMuniAuthPeriodService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new OccLoginMuniAuthPeriodService();
    }
    return INSTANCE;
  }

  /*
   * --------------------------------  LoginMuniAuthPeriod  -------------------------------------------
   */

  public List<LoginMuniAuthPeriod> getLoginMuniAuthPeriodList(InspectionDatabase inspectionDatabase) {
    List<LoginMuniAuthPeriod> loginMuniAuthPeriodList = inspectionDatabase.getLoginMuniAuthPeriodDao().selectLoginMuniAuthPeriodList();
    if (loginMuniAuthPeriodList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local LoginMuniAuthPeriod List: %s", loginMuniAuthPeriodList));
    return loginMuniAuthPeriodList;
  }


  public void insertLoginMuniAuthPeriodList(InspectionDatabase inspectionDatabase, List<LoginMuniAuthPeriod> loginMuniAuthPeriodList) throws OccLoginMuniAuthPeriodNullPointerException {
    if (loginMuniAuthPeriodList == null) {
      throw new OccLoginMuniAuthPeriodNullPointerException("Occ Login Municipality Auth Period Null Pointer Error");
    }
    inspectionDatabase.getLoginMuniAuthPeriodDao().insertLoginMuniAuthPeriodList(loginMuniAuthPeriodList);
  }


  public void deleteAllLoginMuniAuthPeriodList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getLoginMuniAuthPeriodDao().deleteAllLoginMuniAuthPeriodList();
  }

  public List<LoginMuniAuthPeriodHeavy> getLoginMuniAuthPeriodHeavyList(InspectionDatabase inspectionDatabase) {
    List<LoginMuniAuthPeriodHeavy> loginMuniAuthPeriodHeavyList = inspectionDatabase.getLoginMuniAuthPeriodDao().selectAllLoginMuniAuthPeriodHeavyList();
    if (loginMuniAuthPeriodHeavyList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local LoginMuniAuthPeriodHeavy List: %s", loginMuniAuthPeriodHeavyList));
    return loginMuniAuthPeriodHeavyList;
  }

}
