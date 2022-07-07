package com.cnf.service.local;

import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;

import android.content.Context;

import androidx.room.Room;

import com.cnf.db.InspectionDatabase;
import com.cnf.domain.BlobBytes;
import com.cnf.domain.OccInspectableStatus;
import com.cnf.domain.OccInspectedSpace;
import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.domain.OccInspectedSpaceElementHeavy;
import com.cnf.domain.OccInspectedSpaceHeavy;
import com.cnf.domain.OccInspectionStatusEnum;
import com.cnf.domain.infra.CodeElement;
import com.cnf.domain.infra.CodeElementGuide;
import com.cnf.domain.infra.CodeSetElement;
import com.cnf.domain.infra.CodeSource;
import com.cnf.domain.infra.IntensityClass;
import com.cnf.domain.infra.OccChecklistSpaceTypeElement;

import com.cnf.service.exception.CreateOccInspectedSpaceElementListNullPointerException;
import com.cnf.service.exception.OccInspectedSpaceElementNullPointerException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OccInspectionSpaceElementService {

  private final static String FMT_CH = "ch.";
  private final static String FMT_SPACE = " ";
  private final static String FMT_PAREN_L = "(";
  private final static String FMT_PAREN_R = ")";
  private final static String FMT_DASH = "-";
  private final static String FMT_COLON = ":";
  private final static String FMT_SECTION_ENTITY = "§";

  private static OccInspectionSpaceElementService INSTANCE = null;

  private OccInspectionSpaceElementService() {

  }

  public static OccInspectionSpaceElementService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new OccInspectionSpaceElementService();
    }
    return INSTANCE;
  }

  public OccInspectedSpaceElementHeavy inspectionAction_configureElementForNotInspected(OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy, int userId) {
    OccInspectedSpaceElement oise = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
    oise.setComplianceGrantedByUserId(null);
    oise.setComplianceGrantedTS(null);
    oise.setLastInspectedTS(null);
    oise.setLastInspectedByUserId(null);
    return occInspectedSpaceElementHeavy;
  }

  public OccInspectedSpaceElementHeavy inspectionAction_configureElementForCompliance(OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy, int userId) {
    OccInspectedSpaceElement oise = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
    oise.setComplianceGrantedByUserId(userId);
    oise.setComplianceGrantedTS(OffsetDateTime.now().toString());
    oise.setLastInspectedTS(OffsetDateTime.now().toString());
    oise.setLastInspectedByUserId(userId);
    return occInspectedSpaceElementHeavy;
  }

  // TODO how to user userDefFindOnFail
  public OccInspectedSpaceElementHeavy inspectionAction_configureElementForInspectionNoCompliance(OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy, int userId, boolean useDefFindOnFail) {
    OccInspectedSpaceElement oise = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
    CodeSetElement codeSetElement = occInspectedSpaceElementHeavy.getCodeSetElement();
    oise.setComplianceGrantedByUserId(null);
    oise.setComplianceGrantedTS(null);
    oise.setLastInspectedTS(OffsetDateTime.now().toString());
    oise.setLastInspectedByUserId(userId);
    if (useDefFindOnFail) {
      StringBuilder sb = new StringBuilder();
      if (oise.getNotes() != null) {
        sb.append(oise.getNotes());
        sb.append("/n");
      }
      if (codeSetElement.getDefaultViolationDescription() != null) {
        sb.append(codeSetElement.getDefaultViolationDescription());
      }
      oise.setNotes(sb.toString());
    }
    return occInspectedSpaceElementHeavy;
  }

  public List<OccInspectedSpaceElement> createDefaultOccInspectedSpaceElementList(InspectionDatabase inspectionDatabase, OccInspectedSpace occInspectedSpace) {
    if (occInspectedSpace == null) {
      return new ArrayList<>();
    }

    Integer inspectedSpaceId = occInspectedSpace.getInspectedSpaceId();
    Integer occChecklistSpaceTypeId = occInspectedSpace.getOccChecklistSpaceTypeId();

    // Make sure no duplicates
    List<OccInspectedSpaceElement> occInspectedSpaceElementList = inspectionDatabase
        .getOccInspectedSpaceElementDao()
        .selectAllOccInspectedSpaceElementList(inspectedSpaceId);

    if (occInspectedSpaceElementList != null && occInspectedSpaceElementList.size() != 0) {
      return occInspectedSpaceElementList;
    }

    // Create Default OccInspectedSpaceElement List
    occInspectedSpaceElementList = new ArrayList<>();

    // Load Occ Checklist Space Type Element List BY Occ Checklist Space Type
    List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList = inspectionDatabase
        .getOccChecklistSpaceTypeElementDao()
        .selectAllOccChecklistSpaceTypeElementListByCSTId(occChecklistSpaceTypeId);

    // Create Occ Inspected Space Type Element BY Occ Checklist Space Type Element
    for (OccChecklistSpaceTypeElement occChecklistSpaceTypeElement : occChecklistSpaceTypeElementList) {
      OccInspectedSpaceElement e = new OccInspectedSpaceElement();
      e.setInspectedSpaceElementId(null);
      e.setNotes(null);
      e.setLocationDescriptionId(occInspectedSpace.getOccLocationDescriptionId());
      e.setLastInspectedByUserId(null);
      e.setLastInspectedTS(null);
      e.setComplianceGrantedByUserId(null);
      e.setComplianceGrantedTS(null);
      e.setInspectedSpaceId(occInspectedSpace.getInspectedSpaceId());
      e.setOverrideRequiredFlagNotInspectedUserid(null);
      e.setOccChecklistSpaceTypeElementId(occChecklistSpaceTypeElement.getSpaceElementId());
      e.setFailureSeverityIntensityClassId(null);
      e.setMigrateToCECaseOnFail(null);
      e.setTransferredTS(null);
      e.setTransferredByUserId(null);
      e.setTransferredToCECaseId(null);
      e.setStatus(new OccInspectableStatus(OccInspectionStatusEnum.NOTINSPECTED));
      occInspectedSpaceElementList.add(e);
    }

    inspectionDatabase.getOccInspectedSpaceElementDao().insertOccInspectedSpaceElementList(occInspectedSpaceElementList);
    return occInspectedSpaceElementList;
  }

  public boolean isAllInspectedSpaceElementComplete(List<OccInspectedSpaceElement> occInspectedSpaceElementList) {
    occInspectedSpaceElementList = configureOccInspectedSpaceElementListStatus(occInspectedSpaceElementList);
    for (OccInspectedSpaceElement o : occInspectedSpaceElementList) {
      if (isInspectedSpaceElementNotInspected(o)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAllInspectedSpaceElementHeavyComplete(List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList) {
    occInspectedSpaceElementHeavyList = configureOccInspectedSpaceElementHeavyListStatus(occInspectedSpaceElementHeavyList);
    for (OccInspectedSpaceElementHeavy o : occInspectedSpaceElementHeavyList) {
      if (isInspectedSpaceElementNotInspected(o)) {
        return false;
      }
    }
    return true;
  }

  public List<OccInspectedSpaceElementHeavy> configureOccInspectedSpaceElementHeavyListStatus(List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList) {
    for (OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy : occInspectedSpaceElementHeavyList) {
      configureOccInspectedSpaceElementStatus(occInspectedSpaceElementHeavy.getOccInspectedSpaceElement());
    }
    return occInspectedSpaceElementHeavyList;
  }

  public List<OccInspectedSpaceElement> configureOccInspectedSpaceElementListStatus(List<OccInspectedSpaceElement> occInspectedSpaceElementList) {
    for (OccInspectedSpaceElement occInspectedSpaceElement : occInspectedSpaceElementList) {
      configureOccInspectedSpaceElementStatus(occInspectedSpaceElement);
    }
    return occInspectedSpaceElementList;
  }

  public OccInspectedSpaceElement configureOccInspectedSpaceElementStatus(OccInspectedSpaceElement occInspectedSpaceElement) {
    if (occInspectedSpaceElement == null) {
      return null;
    }
    if (occInspectedSpaceElement.getLastInspectedByUserId() != null && occInspectedSpaceElement.getComplianceGrantedTS() == null) {
      occInspectedSpaceElement.setStatus(new OccInspectableStatus(OccInspectionStatusEnum.VIOLATION));
      return occInspectedSpaceElement;
    }
    if (occInspectedSpaceElement.getLastInspectedByUserId() != null && occInspectedSpaceElement.getComplianceGrantedTS() != null) {
      occInspectedSpaceElement.setStatus(new OccInspectableStatus(OccInspectionStatusEnum.PASS));
      return occInspectedSpaceElement;
    }
    occInspectedSpaceElement.setStatus(new OccInspectableStatus(OccInspectionStatusEnum.NOTINSPECTED));
    return occInspectedSpaceElement;
  }

  public boolean isInspectedSpaceElementPass(OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
    OccInspectedSpaceElement e = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
    return isInspectedSpaceElementPass(e);
  }

  public boolean isInspectedSpaceElementNotInspected(OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
    OccInspectedSpaceElement e = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
    return isInspectedSpaceElementNotInspected(e);
  }

  public boolean isInspectedSpaceElementViolation(OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
    OccInspectedSpaceElement e = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
    return isInspectedSpaceElementViolation(e);
  }

  public boolean isInspectedSpaceElementPass(OccInspectedSpaceElement occInspectedSpaceElement) {
    if (OccInspectionStatusEnum.PASS.equals(occInspectedSpaceElement.getStatus().getStatusEnum())) {
      return true;
    }
    return false;
  }

  public boolean isInspectedSpaceElementNotInspected(OccInspectedSpaceElement occInspectedSpaceElement) {
    if (OccInspectionStatusEnum.NOTINSPECTED.equals(occInspectedSpaceElement.getStatus().getStatusEnum())) {
      return true;
    }
    return false;
  }

  public boolean isInspectedSpaceElementViolation(OccInspectedSpaceElement occInspectedSpaceElement) {
    if (OccInspectionStatusEnum.VIOLATION.equals(occInspectedSpaceElement.getStatus().getStatusEnum())) {
      return true;
    }
    return false;
  }

  public Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> getOccInspectedSpaceElementHeavyMap(InspectionDatabase inspectionDatabase, int inspectedSpaceId) {
    return inspectionDatabase.getOccInspectedSpaceElementDao()
        .selectAllOccInspectedSpaceElementHeavyMap(inspectedSpaceId);
  }

  public Map<OccInspectionStatusEnum, List<OccInspectedSpaceElementHeavy>> getElementStatusMap(List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList) {
    Map<OccInspectionStatusEnum, List<OccInspectedSpaceElementHeavy>> elementStatusMap = new HashMap<>();
    elementStatusMap.put(OccInspectionStatusEnum.PASS, new ArrayList<>());
    elementStatusMap.put(OccInspectionStatusEnum.VIOLATION, new ArrayList<>());
    elementStatusMap.put(OccInspectionStatusEnum.NOTINSPECTED, new ArrayList<>());
    for (OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy : occInspectedSpaceElementHeavyList) {
      OccInspectedSpaceElement e = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
      configureOccInspectedSpaceElementStatus(e);
      switch (e.getStatus().getStatusEnum()) {
        case VIOLATION:
          elementStatusMap.get(OccInspectionStatusEnum.VIOLATION).add(occInspectedSpaceElementHeavy);
          break;
        case NOTINSPECTED:
          elementStatusMap.get(OccInspectionStatusEnum.NOTINSPECTED).add(occInspectedSpaceElementHeavy);
          break;
        case PASS:
          elementStatusMap.get(OccInspectionStatusEnum.PASS).add(occInspectedSpaceElementHeavy);
          break;
      }
    }
    return elementStatusMap;
  }

  public List<OccInspectedSpaceElementHeavy> getElementListPass(Map<OccInspectionStatusEnum, List<OccInspectedSpaceElementHeavy>> elementStatusMap) {
    if (elementStatusMap != null) {
      return elementStatusMap.get(OccInspectionStatusEnum.PASS);
    }
    return new ArrayList<>();
  }

  public List<OccInspectedSpaceElementHeavy> getElementListFail(Map<OccInspectionStatusEnum, List<OccInspectedSpaceElementHeavy>> elementStatusMap) {
    if (elementStatusMap != null) {
      return elementStatusMap.get(OccInspectionStatusEnum.VIOLATION);
    }
    return new ArrayList<>();
  }

  public List<OccInspectedSpaceElementHeavy> getElementListNotIns(Map<OccInspectionStatusEnum, List<OccInspectedSpaceElementHeavy>> elementStatusMap) {
    if (elementStatusMap != null) {
      return elementStatusMap.get(OccInspectionStatusEnum.NOTINSPECTED);
    }
    return new ArrayList<>();
  }

  public List<OccInspectedSpaceElementHeavy> getOccInspectedSpaceElementHeavyList(InspectionDatabase inspectionDatabase, int inspectedSpaceElementGuideId, int inspectedSpaceId) {
    return inspectionDatabase.getOccInspectedSpaceElementDao().selectAllOccInspectedSpaceElementHeavyList(inspectedSpaceElementGuideId, inspectedSpaceId);
  }

  public List<OccInspectedSpaceElement> getOccInspectedSpaceElementList(InspectionDatabase inspectionDatabase, int inspectedSpaceId) {
    return inspectionDatabase.getOccInspectedSpaceElementDao().selectAllOccInspectedSpaceElementList(inspectedSpaceId);
  }

  public Map<String, Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>>> getOccInspectedSpaceElementHeavyStatusMap(InspectionDatabase inspectionDatabase,
      Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> occInspectedSpaceElementHeavyMap) {
    Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> unFinishOccInspectedSpaceElementHeavyMap = new HashMap<>();
    Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> finishedOccInspectedSpaceElementHeavyMap = new HashMap<>();
    for (Entry<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> entry : occInspectedSpaceElementHeavyMap.entrySet()) {
      CodeElementGuide codeElementGuide = entry.getKey();
      List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList = entry.getValue();
      boolean isFinished = true;
      if (!OccInspectionSpaceElementService.getInstance().isAllInspectedSpaceElementHeavyComplete(occInspectedSpaceElementHeavyList)) {
        unFinishOccInspectedSpaceElementHeavyMap.put(codeElementGuide, occInspectedSpaceElementHeavyList);
        isFinished = false;
      }

      if (isFinished) {
        finishedOccInspectedSpaceElementHeavyMap.put(codeElementGuide, occInspectedSpaceElementHeavyList);
      }
    }
    Map<String, Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>>> map = new HashMap<>();
    map.put("FINISHED", finishedOccInspectedSpaceElementHeavyMap);
    map.put("UNFINISH", unFinishOccInspectedSpaceElementHeavyMap);
    return map;
  }

  public List<IntensityClass> selectAllIntensityClassListBySchemaLabel(InspectionDatabase inspectionDatabase, String schemaLabel) {
    if (schemaLabel == null || schemaLabel.length() == 0) {
      return new ArrayList<>();
    }
    return inspectionDatabase.getIntensityClassDao().selectAllIntensityClassListBySchemaLabel(schemaLabel);
  }

  public void updateOccInspectedSpaceElement(InspectionDatabase inspectionDatabase, OccInspectedSpaceElement oise) {
    if (oise == null) {
      return;
    }
    inspectionDatabase.getOccInspectedSpaceElementDao().updateOccInspectedSpaceElementList(oise);
  }


  public List<BlobBytes> getInspectedPhotoBlobBytesList(InspectionDatabase inspectionDatabase, int inspectedSpaceElementId) {
    return inspectionDatabase.getBlobBytesDao().selectBlobByteListByInspectedElementId(inspectedSpaceElementId);
  }


  public List<OccInspectedSpaceElementHeavy> getOccInspectedSpaceElementHeavyList(InspectionDatabase inspectionDatabase, int inspectedSpaceId) {
    return inspectionDatabase.getOccInspectedSpaceElementDao().selectAllOccInspectedSpaceElementHeavyList(inspectedSpaceId);
  }


  // TODO CHECK THE LOGIC FROM ERIC
  public String getOrdinanceHeaderSectionNumber(OccInspectedSpaceElementHeavy e) {
    StringBuilder sb = new StringBuilder();
    CodeElement ce = e.getCodeElement();
    if (e != null) {
      // Prefix with source and year
      CodeSource codeSource = e.getCodeSource();
      if (codeSource != null) {
        sb.append(codeSource.getName());
        if (codeSource.getYear() != 0) {
          sb.append(FMT_PAREN_L);
          sb.append(codeSource.getYear());
          sb.append(FMT_PAREN_R);
        }
        sb.append(FMT_SPACE);
      }
    } // element not null
    return sb.toString();
  }

  // TODO CHECK THE LOGIC FROM ERIC
  public String getOrdinanceHeaderSectionTitle(OccInspectedSpaceElementHeavy e) {
    StringBuilder sb = new StringBuilder();
    CodeElement ce = e.getCodeElement();
    if (e != null) {
      // If we have a standard IPMC listing, the chapter and section
      // numbers are actually in the sub-section number, so just use that number
      // such as 302.1.1: Chapter 3, section 2, subsec 1, subsubsec 1
      // check if section number is in sub-section number
      if (checkForPureIRCRecursiveListing(ce)) {
        sb.append(FMT_SECTION_ENTITY);
        sb.append(FMT_SPACE);
        if (ce.getOrdSubSecNum() != null
            && !ce.getOrdSubSecNum().equals("''")) {
          // eg 302.1.1
          sb.append(ce.getOrdSubSecNum());
        } else { // we don't have a recursive sub-sub sec number, so use sub-sec number
          sb.append(ce.getOrdSubSecNum());
          sb.append(FMT_COLON);
          sb.append(FMT_SPACE);
        }
        // now insert the titles
        // NOTE we are not using chapter titles in the standard IRC
        // they are implied by the section and subsection titles

        if (ce.getOrdSecTitle() != null) {
          sb.append(ce.getOrdSecTitle());
          sb.append(FMT_SPACE);
          sb.append(FMT_DASH);
          sb.append(FMT_SPACE);
        }
        if (ce.getOrdSubSecTitle() != null) {
          sb.append(ce.getOrdSubSecTitle());
        }
      } else { // We do not have a pure IRC, so piece the header together straight across
        // we have a non-zero chapter number
        if (ce.getOrdChapterNo() != 0) {
          sb.append(FMT_CH);
          sb.append(ce.getOrdChapterNo());
          if (ce.getOrdChapterTitle() != null && !ce.getOrdChapterTitle().equals("''")) {
            sb.append(FMT_COLON);
            sb.append(ce.getOrdChapterTitle());
          }
        }
        // setup our section
        // if we have a section number, put it in
        if (ce.getOrdSecNum() != null && !ce.getOrdSecNum().equals("''")) {
          sb.append(FMT_SPACE);
          sb.append(FMT_SECTION_ENTITY);
          sb.append(FMT_SPACE);
          sb.append(ce.getOrdSecNum());
          // if we have a sub sec number, it follows in parents (a)
          if (ce.getOrdSubSecNum() != null && !ce.getOrdSubSecNum().equals("''")) {
            sb.append(FMT_PAREN_L);
            sb.append(ce.getOrdSubSecNum());
            sb.append(FMT_PAREN_R);
            // if we have a sub sub sec number, it follows in parents (1)
            if (ce.getOrdSubSecNum() != null && !ce.getOrdSubSecNum().equals("''")) {
              sb.append(FMT_PAREN_L);
              sb.append(ce.getOrdSubSecNum());
              sb.append(FMT_PAREN_R);
            }
          }
        } // done with section numbers, now for section titles

        if (ce.getOrdSecTitle() != null && !ce.getOrdSecTitle().equals("''")) {
          sb.append(FMT_SPACE);
          sb.append(ce.getOrdSecTitle());
        }
        if (ce.getOrdSubSecTitle() != null && !ce.getOrdSubSecTitle().equals("''")) {
          sb.append(FMT_SPACE);
          sb.append(FMT_DASH);
          sb.append(FMT_SPACE);
          sb.append(ce.getOrdSubSecTitle());
        }
      } // end outer if check on standard IRC
    }
    return sb.toString();
  }

  private boolean checkForPureIRCRecursiveListing(CodeElement ce) {

    boolean isPureIRCFormat = true;

    // nulls or zero for ch, sec, and subsec violates purity
    if (ce.getOrdChapterNo() == 0
        || ce.getOrdSecNum() == null
        || ce.getOrdSubSecNum() == null) {
      return false;
    }

    // no ch, sec, or sub-sec titles violates purity
    if (ce.getOrdChapterTitle() == null
        || ce.getOrdSecTitle() == null
        || ce.getOrdSubSecTitle() == null) {
      return false;
    }

    // chapter No. should be in our section #
    if (!ce.getOrdSecNum().contains(String.valueOf(ce.getOrdChapterNo()))) {
      return false;
    }

    // sec number should be in our sub-section #
    if (!ce.getOrdSubSecNum().contains(ce.getOrdSecNum())) {
      return false;
    }

    // if there is a sub-sub number, section should be in there, too
    if (ce.getOrdSubSecNum() != null && !ce.getOrdSubSecNum().equals("''")) {
      if (!ce.getOrdSubSecNum().contains(ce.getOrdSubSecNum())) {
        return false;
      }
    }

    return isPureIRCFormat;
  }
}
