package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.dispatch.OccInspectionDispatchDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.OccInspectedSpace;
import com.cnf.module_inspection.entity.infra_heavy.OccInspectionDispatchHeavy;
import com.cnf.module_inspection.entity.tasks.OccInspection;
import com.cnf.module_inspection.entity.tasks.OccInspectionDispatch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OccInspectionDispatchRepository {

  public enum Category {FINISHED, UN_FINISH, SYNCHRONIZED}

  private OccInspectionDispatchDao occInspectionDispatchDao;
  private OccInspectionSpaceRepository occInspectionSpaceRepository;

  private static final OccInspectionDispatchRepository INSTANCE = new OccInspectionDispatchRepository();

  private OccInspectionDispatchRepository() {
  }

  public static OccInspectionDispatchRepository getInstance(Context context) {
    INSTANCE.occInspectionDispatchDao = InspectionDatabase.getInstance(context).getOccInspectionDispatchDao();
    INSTANCE.occInspectionSpaceRepository = OccInspectionSpaceRepository.getInstance(context);
    return INSTANCE;
  }

  public void insertOccInspectionDispatchList(List<OccInspectionDispatch> occInspectionDispatchList) {
    this.occInspectionDispatchDao.insertOccInspectionDispatchList(occInspectionDispatchList);
  }

  public void deleteAllOccInspectionDispatchList() {
    this.occInspectionDispatchDao.deleteAllOccInspectionDispatchList();
  }

  public void updateOccInspectionDispatch(OccInspectionDispatch occInspectionDispatch) {
    this.occInspectionDispatchDao.updateOccInspectionDispatch(occInspectionDispatch);
  }

  public List<OccInspectionDispatchHeavy> getUnSynchronizeInspectionDispatchHeavyList(int muniCode) {
    return this.occInspectionDispatchDao.selectUnSynchronizeInspectionDispatchHeavy(muniCode);
  }

  public List<OccInspectionDispatchHeavy> getSynchronizedInspectionDispatchHeavyList(int muniCode) {
    return this.occInspectionDispatchDao.selectSynchronizedInspectionDispatchHeavy(muniCode);
  }

  public Map<Category, List<OccInspectionDispatchHeavy>> getOccInspectionDispatchHeavyListMap(List<OccInspectionDispatchHeavy> occInspectionDispatchHeavyList) {
    List<OccInspectionDispatchHeavy> unFinishInspectionDispatchHeavyList = new ArrayList<>();
    List<OccInspectionDispatchHeavy> finishedInspectionDispatchHeavyList = new ArrayList<>();
    Map<Category, List<OccInspectionDispatchHeavy>> map = new HashMap<>();
    map.put(Category.FINISHED, finishedInspectionDispatchHeavyList);
    map.put(Category.UN_FINISH, unFinishInspectionDispatchHeavyList);
    if (occInspectionDispatchHeavyList == null) {
      return map;
    }
    for (OccInspectionDispatchHeavy occInspectionDispatchHeavy : occInspectionDispatchHeavyList) {
      OccInspection occInspection = occInspectionDispatchHeavy.getOccInspection();
      if (occInspection == null || occInspection.getInspectionId() == null) {
        continue;
      }
      Integer inspectionId = occInspection.getInspectionId();
      List<OccInspectedSpace> occInspectedSpaceList = this.occInspectionSpaceRepository.getOccInspectedSpaceListByInspectionId(inspectionId);
      if (this.occInspectionSpaceRepository.isAllInspectedSpaceComplete(occInspectedSpaceList)) {
        finishedInspectionDispatchHeavyList.add(occInspectionDispatchHeavy);
      } else {
        unFinishInspectionDispatchHeavyList.add(occInspectionDispatchHeavy);
      }
    }
    return map;
  }
}
