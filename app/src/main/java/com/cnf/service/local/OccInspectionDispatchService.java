package com.cnf.service.local;

import static android.content.ContentValues.TAG;

import android.util.Log;
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.OccInspectedSpace;
import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.domain.OccInspectedSpaceHeavy;
import com.cnf.domain.infra_heavy.OccInspectionDispatchHeavy;
import com.cnf.domain.tasks.CECase;
import com.cnf.domain.tasks.Login;
import com.cnf.domain.tasks.OccInspection;
import com.cnf.domain.tasks.OccInspectionDispatch;
import com.cnf.domain.tasks.OccInspectionTasks;
import com.cnf.domain.tasks.OccPeriod;
import com.cnf.domain.tasks.Person;
import com.cnf.domain.tasks.Property;
import com.cnf.domain.tasks.PropertyUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OccInspectionDispatchService {

  private static OccInspectionDispatchService INSTANCE = null;

  private OccInspectionDispatchService() {
  }

  public static OccInspectionDispatchService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new OccInspectionDispatchService();
    }
    return INSTANCE;
  }

  /*
   * --------------------------------  OccInspectionDispatch Class   -------------------------------------------
   */

  public List<OccInspectionDispatch> getOccInspectionDispatchList(InspectionDatabase inspectionDatabase) {
    List<OccInspectionDispatch> occInspectionDispatchList = inspectionDatabase.getOccInspectionDispatchDao().selectOccInspectionDispatchList();
    if (occInspectionDispatchList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local OccInspectionDispatch List: %s", occInspectionDispatchList));
    return occInspectionDispatchList;
  }

  public void insertOccInspectionDispatchList(InspectionDatabase inspectionDatabase, List<OccInspectionDispatch> occInspectionDispatchList) {
    if (occInspectionDispatchList == null) {
      return;
    }
    inspectionDatabase.getOccInspectionDispatchDao().insertOccInspectionDispatchList(occInspectionDispatchList);
  }

  private void deleteAllOccInspectionDispatchList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getOccInspectionDispatchDao().deleteAllOccInspectionDispatchList();
  }

  public void updateOccInspectionDispatch(InspectionDatabase inspectionDatabase, OccInspectionDispatch occInspectionDispatch) {
    inspectionDatabase.getOccInspectionDispatchDao().updateOccInspectionDispatch(occInspectionDispatch);
  }

  /*
   * --------------------------------  OccInspection Class   -------------------------------------------
   */

  public List<OccInspection> getOccInspectionList(InspectionDatabase inspectionDatabase) {
    List<OccInspection> occInspectionList = inspectionDatabase.getOccInspectionDao().selectOccInspectionList();
    if (occInspectionList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Occ Inspection List: %s", occInspectionList));
    return occInspectionList;
  }

  public void insertOccInspectionList(InspectionDatabase inspectionDatabase, List<OccInspection> occInspectionList) {
    if (occInspectionList == null) {
      return;
    }
    inspectionDatabase.getOccInspectionDao().insertOccInspectionList(occInspectionList);
  }

  private void deleteAllOccInspectionList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getOccInspectionDao().deleteAllOccInspectionList();
  }

  /*
   * --------------------------------  CECase Class   -------------------------------------------
   */

  public List<CECase> getCECaseList(InspectionDatabase inspectionDatabase) {
    List<CECase> ceCaseList = inspectionDatabase.getCECaseDao().selectCECaseList();
    if (ceCaseList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local CECase List: %s", ceCaseList));
    return ceCaseList;
  }

  public void insertCECaseList(InspectionDatabase inspectionDatabase, List<CECase> ceCaseList) {
    if (ceCaseList == null) {
      return;
    }
    inspectionDatabase.getCECaseDao().insertCECaseList(ceCaseList);
  }

  private void deleteAllCECaseList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getCECaseDao().deleteAllCECaseList();
  }

  /*
   * --------------------------------  Property Class   -------------------------------------------
   */

  public List<Property> getPropertyList(InspectionDatabase inspectionDatabase) {
    List<Property> propertyList = inspectionDatabase.getPropertyDao().selectPropertyList();
    if (propertyList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Property List: %s", propertyList));
    return propertyList;
  }

  public void insertPropertyList(InspectionDatabase inspectionDatabase, List<Property> propertyList) {
    if (propertyList == null) {
      return;
    }
    inspectionDatabase.getPropertyDao().insertPropertyList(propertyList);
  }

  private void deleteAllPropertyList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getPropertyDao().deleteAllPropertyList();
  }

  /*
   * --------------------------------  OccPeriod Class   -------------------------------------------
   */

  public List<OccPeriod> getOccPeriodList(InspectionDatabase inspectionDatabase) {
    List<OccPeriod> occPeriodList = inspectionDatabase.getOccPeriodDao().selectOccPeriodList();
    if (occPeriodList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local OccPeriod List: %s", occPeriodList));
    return occPeriodList;
  }

  public void insertOccPeriodList(InspectionDatabase inspectionDatabase, List<OccPeriod> occPeriodList) {
    if (occPeriodList == null) {
      return;
    }
    inspectionDatabase.getOccPeriodDao().insertOccPeriodList(occPeriodList);
  }

  private void deleteAllOccPeriodList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getOccPeriodDao().deleteAllOccPeriodList();
  }

  /*
   * --------------------------------  PropertyUnit Class   -------------------------------------------
   */

  public List<PropertyUnit> getPropertyUnitList(InspectionDatabase inspectionDatabase) {
    List<PropertyUnit> propertyUnitList = inspectionDatabase.getPropertyUnitDao().selectPropertyUnitList();
    if (propertyUnitList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local PropertyUnit List: %s", propertyUnitList));
    return propertyUnitList;
  }

  public void insertPropertyUnitList(InspectionDatabase inspectionDatabase, List<PropertyUnit> propertyUnitList) {
    if (propertyUnitList == null) {
      return;
    }
    inspectionDatabase.getPropertyUnitDao().insertPropertyUnitList(propertyUnitList);
  }

  private void deleteAllPropertyUnitList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getPropertyUnitDao().deleteAllPropertyUnitList();
  }

  /*
   * --------------------------------  Login Class   -------------------------------------------
   */

  public List<Login> getLoginList(InspectionDatabase inspectionDatabase) {
    List<Login> loginList = inspectionDatabase.getLoginDao().selectLoginList();
    if (loginList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Login List: %s", loginList));
    return loginList;
  }

  public void insertLoginList(InspectionDatabase inspectionDatabase, List<Login> loginList) {
    if (loginList == null) {
      return;
    }
    inspectionDatabase.getLoginDao().insertLoginList(loginList);
  }

  private void deleteAllLoginList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getLoginDao().deleteAllLoginList();
  }

  /*
   * --------------------------------  Person Class   -------------------------------------------
   */

  public List<Person> getPersonList(InspectionDatabase inspectionDatabase) {
    List<Person> personList = inspectionDatabase.getPersonDao().selectPersonList();
    if (personList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Person List: %s", personList));
    return personList;
  }

  public void insertPersonList(InspectionDatabase inspectionDatabase, List<Person> personList) {
    if (personList == null) {
      return;
    }
    inspectionDatabase.getPersonDao().insertPersonList(personList);
  }

  private void deleteAllPersonList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getPersonDao().deleteAllPersonList();
  }


  /*
   * --------------------------------  End    -------------------------------------------
   */


  public void deleteOccInspectionTask(InspectionDatabase inspectionDatabase) {
    deleteAllOccInspectionDispatchList(inspectionDatabase);
    deleteAllOccInspectionList(inspectionDatabase);
    deleteAllCECaseList(inspectionDatabase);
    deleteAllPropertyList(inspectionDatabase);
    deleteAllOccPeriodList(inspectionDatabase);
    deleteAllPropertyUnitList(inspectionDatabase);
    deleteAllLoginList(inspectionDatabase);
    deleteAllPersonList(inspectionDatabase);
  }

  public void insertOccInspectionTask(InspectionDatabase inspectionDatabase, OccInspectionTasks occInspectionTasks) {
    insertOccInspectionDispatchList(inspectionDatabase, occInspectionTasks.getOccInspectionDispatchList());
    insertOccInspectionList(inspectionDatabase, occInspectionTasks.getOccInspectionList());
    insertCECaseList(inspectionDatabase, occInspectionTasks.getCeCaseList());
    insertPropertyList(inspectionDatabase, occInspectionTasks.getPropertyList());
    insertLoginList(inspectionDatabase, occInspectionTasks.getLoginList());
    insertPersonList(inspectionDatabase, occInspectionTasks.getPersonList());
  }

  public List<OccInspectionDispatchHeavy> getUnSynchronizeInspectionDispatchHeavy(InspectionDatabase inspectionDatabase,int muniCode) {
    List<OccInspectionDispatchHeavy> occInspectionDispatchHeavyList = inspectionDatabase.getOccInspectionDispatchDao().selectUnSynchronizeInspectionDispatchHeavy(muniCode);
    System.out.println("!!NO Sync :" + occInspectionDispatchHeavyList);
    return occInspectionDispatchHeavyList;
  }

  public List<OccInspectionDispatchHeavy> getSynchronizedInspectionDispatchHeavy(InspectionDatabase inspectionDatabase,int muniCode) {
    List<OccInspectionDispatchHeavy> occInspectionDispatchHeavyList = inspectionDatabase.getOccInspectionDispatchDao().selectSynchronizedInspectionDispatchHeavy(muniCode);
    System.out.println("!!Sync :" + occInspectionDispatchHeavyList);
    return occInspectionDispatchHeavyList;
  }

  public Map<String,List<OccInspectionDispatchHeavy>> getOccInspectionDispatchHeavyListMap (InspectionDatabase inspectionDatabase, List<OccInspectionDispatchHeavy> occInspectionDispatchHeavyList) {
    List<OccInspectionDispatchHeavy> unFinishInspectionDispatchHeavyList = new ArrayList<>();
    List<OccInspectionDispatchHeavy> finishedInspectionDispatchHeavyList = new ArrayList<>();
    for (OccInspectionDispatchHeavy occInspectionDispatchHeavy : occInspectionDispatchHeavyList) {
      Integer inspectionId = occInspectionDispatchHeavy.getOccInspection().getInspectionId();
      List<OccInspectedSpace> occInspectedSpaceList = OccInspectedSpaceService.getInstance().getOccInspectedSpaceListByInspectionId(inspectionDatabase, inspectionId);
      boolean isFinished = true;
      inspectedSpaceListFinish: for (OccInspectedSpace occInspectedSpace : occInspectedSpaceList) {
        List<OccInspectedSpaceElement> occInspectedSpaceElementList = OccInspectionSpaceElementService.getInstance()
            .getOccInspectedSpaceElementList(inspectionDatabase, occInspectedSpace.getInspectedSpaceId());
        if (!OccInspectionSpaceElementService.getInstance().isAllInspectedSpaceElementComplete(occInspectedSpaceElementList)){
          isFinished = false;
          break inspectedSpaceListFinish;
        }
      }
      if (isFinished) {
        finishedInspectionDispatchHeavyList.add(occInspectionDispatchHeavy);
      } else {
        unFinishInspectionDispatchHeavyList.add(occInspectionDispatchHeavy);
      }
    }
    Map<String,List<OccInspectionDispatchHeavy>> map = new HashMap<>();
    map.put("FINISHED", finishedInspectionDispatchHeavyList);
    map.put("UNFINISH", unFinishInspectionDispatchHeavyList);
    return map;
  }
}
