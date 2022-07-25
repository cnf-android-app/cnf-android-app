package com.cnf.service.local;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.cnf.db.InspectionDatabase;
import com.cnf.domain.OccInspectedSpace;
import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.domain.OccInspectedSpaceHeavy;

import com.cnf.domain.infra.OccChecklistSpaceType;
import com.cnf.domain.infra_heavy.OccInspectionDispatchHeavy;
import com.cnf.domain.tasks.OccInspection;
import com.cnf.service.exception.CreateOccInspectedSpaceElementListNullPointerException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OccInspectedSpaceService {

  private static OccInspectedSpaceService INSTANCE = null;

  private OccInspectedSpaceService() {
  }

  public static OccInspectedSpaceService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new OccInspectedSpaceService();
    }
    return INSTANCE;
  }

  public List<OccInspectedSpaceHeavy> getOccInspectedSpaceHeavyListByInspectionId(InspectionDatabase inspectionDatabase, int inspectionId) {
    List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList = inspectionDatabase.getOccInspectedSpaceDao().selectAllOccInspectedSpaceHeavyListByInspectionId(inspectionId);
    if (occInspectedSpaceHeavyList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Occ Inspected Space Heavy List: %s", occInspectedSpaceHeavyList));
    return occInspectedSpaceHeavyList;
  }

  public List<OccInspectedSpace> getOccInspectedSpaceListByInspectionId(InspectionDatabase inspectionDatabase, int inspectionId) {
    List<OccInspectedSpace> occInspectedSpaceList = inspectionDatabase.getOccInspectedSpaceDao().selectAllOccInspectedSpaceList(inspectionId);
    if (occInspectedSpaceList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Occ Inspected Space Heavy List: %s", occInspectedSpaceList));
    return occInspectedSpaceList;
  }

  public long insertInspectedSpace(InspectionDatabase inspectionDatabase, OccInspectedSpace occInspectedSpace) {
    return inspectionDatabase.getOccInspectedSpaceDao().insertInspectedSpace(occInspectedSpace);
  }

  public void createDefaultOccInspectedSpace(InspectionDatabase inspectionDatabase, OccInspection occInspection) {
    Integer inspectionId = occInspection.getInspectionId();
    Integer userId = occInspection.getInspectorUserId();
    Integer occChecklistId = occInspection.getOccChecklistId();

    List<OccChecklistSpaceType> occChecklistSpaceTypeList = inspectionDatabase.getOccChecklistSpaceTypeDao().selectAllOccChecklistSpaceTypeList(occChecklistId);
    for (OccChecklistSpaceType occChecklistSpaceType : occChecklistSpaceTypeList) {
      OccInspectedSpace occInspectedSpace = new OccInspectedSpace();
      String inspectedSpaceId = UUID.randomUUID().toString();
      occInspectedSpace.setInspectedSpaceId(inspectedSpaceId);
      occInspectedSpace.setOccInspectionId(inspectionId);
      occInspectedSpace.setOccLocationDescriptionId(null);
      occInspectedSpace.setAddedToChecklistByUserid(userId);
      occInspectedSpace.setAddedToChecklistTS(OffsetDateTime.now().toString());
      occInspectedSpace.setOccChecklistSpaceTypeId(occChecklistSpaceType.getChecklistSpaceTypeId());

      insertInspectedSpace(inspectionDatabase, occInspectedSpace);
      occInspectedSpace.setInspectedSpaceId(inspectedSpaceId);
      //todo
      OccInspectionSpaceElementService.getInstance().createDefaultOccInspectedSpaceElementList(inspectionDatabase, occInspectedSpace);
    }
  }

  public void updateOccInspectedSpaceLocationDescription(InspectionDatabase inspectionDatabase, String occInspectedSpaceId, int occLocationDescriptionId) {
    inspectionDatabase.getOccInspectedSpaceDao().updateLocationDesForOccInspectedSpace(occInspectedSpaceId, occLocationDescriptionId);
  }

  public Map<String, List<OccInspectedSpaceHeavy>> getOccInspectedSpaceHeavyListMap(InspectionDatabase inspectionDatabase, List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList) {
    List<OccInspectedSpaceHeavy> unFinishOccInspectedSpaceHeavyList = new ArrayList<>();
    List<OccInspectedSpaceHeavy> finishedOccInspectedSpaceHeavyList = new ArrayList<>();
    for (OccInspectedSpaceHeavy occInspectedSpaceHeavy : occInspectedSpaceHeavyList) {
      if (occInspectedSpaceHeavy.getOccInspectedSpace().getOccLocationDescriptionId() == null) {
        unFinishOccInspectedSpaceHeavyList.add(occInspectedSpaceHeavy);
        continue;
      }
      String inspectedSpaceId = occInspectedSpaceHeavy.getOccInspectedSpace().getInspectedSpaceId();

      List<OccInspectedSpaceElement> occInspectedSpaceElementList = OccInspectionSpaceElementService.getInstance()
          .getOccInspectedSpaceElementList(inspectionDatabase, inspectedSpaceId);
      if (!OccInspectionSpaceElementService.getInstance().isAllInspectedSpaceElementComplete(occInspectedSpaceElementList)) {
        unFinishOccInspectedSpaceHeavyList.add(occInspectedSpaceHeavy);
      } else {
        finishedOccInspectedSpaceHeavyList.add(occInspectedSpaceHeavy);
      }
    }
    Map<String, List<OccInspectedSpaceHeavy>> map = new HashMap<>();
    map.put("FINISHED", finishedOccInspectedSpaceHeavyList);
    map.put("UNFINISH", unFinishOccInspectedSpaceHeavyList);
    return map;
  }
}
