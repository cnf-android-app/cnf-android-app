package com.cnf.module_inspection.service.local;

import android.content.Context;

import com.cnf.module_inspection.dao.OccInspectedSpaceDao;
import com.cnf.module_inspection.dao.infra.OccChecklistSpaceTypeDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.OccInspectedSpace;
import com.cnf.module_inspection.entity.OccInspectedSpaceElement;
import com.cnf.module_inspection.entity.OccInspectedSpaceHeavy;

import com.cnf.module_inspection.entity.infra.OccChecklistSpaceType;
import com.cnf.module_inspection.entity.tasks.OccInspection;
import com.cnf.module_inspection.service.exception.InvalidOccInspectedSpaceException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OccInspectionSpaceRepository {

  public enum Category {FINISHED, UN_FINISH}

  private OccInspectedSpaceDao occInspectedSpaceDao;
  private OccChecklistSpaceTypeDao occChecklistSpaceTypeDao;
  private OccInspectionSpaceElementRepository occInspectionSpaceElementRepository;

  private static final OccInspectionSpaceRepository INSTANCE = new OccInspectionSpaceRepository();

  private OccInspectionSpaceRepository() {
  }

  public static OccInspectionSpaceRepository getInstance(Context context) {
    INSTANCE.occInspectedSpaceDao = InspectionDatabase.getInstance(context).getOccInspectedSpaceDao();
    INSTANCE.occChecklistSpaceTypeDao = InspectionDatabase.getInstance(context).getOccChecklistSpaceTypeDao();
    INSTANCE.occInspectionSpaceElementRepository = OccInspectionSpaceElementRepository.getInstance(context);
    return INSTANCE;
  }

  public List<OccInspectedSpaceHeavy> getOccInspectedSpaceHeavyListByInspectionId(int inspectionId) {
    return this.occInspectedSpaceDao.selectAllOccInspectedSpaceHeavyListByInspectionId(inspectionId);
  }

  public List<OccInspectedSpace> getOccInspectedSpaceListByInspectionId(int inspectionId) {
    return this.occInspectedSpaceDao.selectAllOccInspectedSpaceList(inspectionId);
  }

  public long insertOccInspectedSpace(OccInspectedSpace occInspectedSpace) {
    return occInspectedSpaceDao.insertInspectedSpace(occInspectedSpace);
  }

  public void insertOccInspectedSpaceList(List<OccInspectedSpace> occInspectedSpaceList) {
    this.occInspectedSpaceDao.insertOccInspectedSpaceList(occInspectedSpaceList);
  }

  public void createDefaultOccInspectedSpace(OccInspection occInspection) throws InvalidOccInspectedSpaceException {
    if (occInspection == null) {
      throw new InvalidOccInspectedSpaceException("occInspection", new NullPointerException());
    }

    Integer inspectionId = occInspection.getInspectionId();
    Integer userId = occInspection.getInspectorUserId();
    Integer occChecklistId = occInspection.getOccChecklistId();

    if (inspectionId == null || userId == null || occChecklistId == null) {
      throw new InvalidOccInspectedSpaceException("inspectionId or userId or occChecklistId", new NullPointerException());
    }

    List<OccChecklistSpaceType> occChecklistSpaceTypeList = occChecklistSpaceTypeDao.selectAllOccChecklistSpaceTypeList(occChecklistId);
    for (OccChecklistSpaceType occChecklistSpaceType : occChecklistSpaceTypeList) {
      OccInspectedSpace occInspectedSpace = new OccInspectedSpace();
      String inspectedSpaceId = UUID.randomUUID().toString();
      occInspectedSpace.setInspectedSpaceId(inspectedSpaceId);
      occInspectedSpace.setOccInspectionId(inspectionId);
      occInspectedSpace.setOccLocationDescriptionId(null);
      occInspectedSpace.setAddedToChecklistByUserid(userId);
      occInspectedSpace.setAddedToChecklistTS(OffsetDateTime.now().toString());
      occInspectedSpace.setOccChecklistSpaceTypeId(occChecklistSpaceType.getChecklistSpaceTypeId());

      this.insertOccInspectedSpace(occInspectedSpace);
      occInspectionSpaceElementRepository.createDefaultOccInspectedSpaceElementList(occInspectedSpace);
    }
  }

  public void updateOccInspectedSpaceLocationDescription(String occInspectedSpaceId, int occLocationDescriptionId) {
    this.occInspectedSpaceDao.updateLocationDesForOccInspectedSpace(occInspectedSpaceId, occLocationDescriptionId);
  }

  public void deleteOccInspectedSpace(OccInspectedSpace occInspectedSpace) {
    this.occInspectedSpaceDao.deleteOccInspectedSpace(occInspectedSpace);
  }

  public Map<Category, List<OccInspectedSpaceHeavy>> getOccInspectedSpaceHeavyListMap(List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList) {
    List<OccInspectedSpaceHeavy> unFinishOccInspectedSpaceHeavyList = new ArrayList<>();
    List<OccInspectedSpaceHeavy> finishedOccInspectedSpaceHeavyList = new ArrayList<>();
    Map<Category, List<OccInspectedSpaceHeavy>> map = new HashMap<>();
    map.put(Category.FINISHED, finishedOccInspectedSpaceHeavyList);
    map.put(Category.UN_FINISH, unFinishOccInspectedSpaceHeavyList);
    if (occInspectedSpaceHeavyList == null) {
      return map;
    }
    for (OccInspectedSpaceHeavy occInspectedSpaceHeavy : occInspectedSpaceHeavyList) {
      OccInspectedSpace occInspectedSpace = occInspectedSpaceHeavy.getOccInspectedSpace();
      if (occInspectedSpace == null || occInspectedSpace.getInspectedSpaceId() == null) {
        continue;
      }
      if (occInspectedSpace.getOccLocationDescriptionId() == null) {
        unFinishOccInspectedSpaceHeavyList.add(occInspectedSpaceHeavy);
        continue;
      }
      String inspectedSpaceId = occInspectedSpace.getInspectedSpaceId();
      List<OccInspectedSpaceElement> occInspectedSpaceElementList = occInspectionSpaceElementRepository.getOccInspectedSpaceElementList(inspectedSpaceId);
      if (occInspectionSpaceElementRepository.isAllInspectedSpaceElementComplete(occInspectedSpaceElementList)) {
        finishedOccInspectedSpaceHeavyList.add(occInspectedSpaceHeavy);
      } else {
        unFinishOccInspectedSpaceHeavyList.add(occInspectedSpaceHeavy);
      }
    }
    return map;
  }

  public boolean isAllInspectedSpaceComplete(List<OccInspectedSpace> occInspectedSpaceList) {
    if (occInspectedSpaceList == null || occInspectedSpaceList.size() == 0) {
      return true;
    }
    for (OccInspectedSpace occInspectedSpace: occInspectedSpaceList) {
      String inspectedSpaceId = occInspectedSpace.getInspectedSpaceId();
      if (inspectedSpaceId == null){
        continue;
      }
      List<OccInspectedSpaceElement> occInspectedSpaceElementList = occInspectionSpaceElementRepository.getOccInspectedSpaceElementList(inspectedSpaceId);
      if (!occInspectionSpaceElementRepository.isAllInspectedSpaceElementComplete(occInspectedSpaceElementList)) {
        return false;
      }
    }
    return true;
  }
}
