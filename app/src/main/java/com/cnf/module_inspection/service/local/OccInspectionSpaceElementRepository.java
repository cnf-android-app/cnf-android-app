package com.cnf.module_inspection.service.local;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import com.cnf.module_inspection.dao.OccInspectedSpaceElementDao;
import com.cnf.module_inspection.dao.infra.CodeElementGuideDao;
import com.cnf.module_inspection.dao.infra.IntensityClassDao;
import com.cnf.module_inspection.dao.infra.OccChecklistSpaceTypeElementDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.OccInspectableStatus;
import com.cnf.module_inspection.entity.OccInspectedSpace;
import com.cnf.module_inspection.entity.OccInspectedSpaceElement;
import com.cnf.module_inspection.entity.OccInspectedSpaceElementHeavy;
import com.cnf.module_inspection.entity.OccInspectionStatusEnum;
import com.cnf.module_inspection.entity.infra.CodeElement;
import com.cnf.module_inspection.entity.infra.CodeElementGuide;
import com.cnf.module_inspection.entity.infra.CodeSetElement;
import com.cnf.module_inspection.entity.infra.CodeSource;
import com.cnf.module_inspection.entity.infra.IntensityClass;
import com.cnf.module_inspection.entity.infra.OccChecklistSpaceTypeElement;

import com.cnf.module_inspection.service.exception.InvalidOccInspectedSpaceElementException;
import com.cnf.module_inspection.service.exception.InvalidOccInspectedSpaceException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class OccInspectionSpaceElementRepository {

  public enum Category {FINISHED, UN_FINISH}

  private final static String FMT_CH = "ch.";
  private final static String FMT_SPACE = " ";
  private final static String FMT_PAREN_L = "(";
  private final static String FMT_PAREN_R = ")";
  private final static String FMT_DASH = "-";
  private final static String FMT_COLON = ":";
  private final static String FMT_SECTION_ENTITY = "§";

  private OccInspectedSpaceElementDao occInspectedSpaceElementDao;
  private OccChecklistSpaceTypeElementDao occChecklistSpaceTypeElementDao;
  private CodeElementGuideDao codeElementGuideDao;
  private IntensityClassDao intensityClassDao;

  private static final OccInspectionSpaceElementRepository INSTANCE = new OccInspectionSpaceElementRepository();

  private OccInspectionSpaceElementRepository() {
  }

  public static OccInspectionSpaceElementRepository getInstance(Context context) {
    INSTANCE.occInspectedSpaceElementDao = InspectionDatabase.getInstance(context).getOccInspectedSpaceElementDao();
    INSTANCE.occChecklistSpaceTypeElementDao = InspectionDatabase.getInstance(context).getOccChecklistSpaceTypeElementDao();
    INSTANCE.codeElementGuideDao = InspectionDatabase.getInstance(context).getCodeElementGuideDao();
    INSTANCE.intensityClassDao = InspectionDatabase.getInstance(context).getIntensityClassDao();
    return INSTANCE;
  }

  public void insertOccInspectedSpaceElementList(List<OccInspectedSpaceElement> occInspectedSpaceElementList) {
    this.occInspectedSpaceElementDao.insertOccInspectedSpaceElementList(occInspectedSpaceElementList);
  }

  public List<OccInspectedSpaceElementHeavy> getOccInspectedSpaceElementHeavyListByInspectedSpaceId(Integer inspectedSpaceElementGuideId, String inspectedSpaceId) {
    if (inspectedSpaceElementGuideId == null) {
      return this.occInspectedSpaceElementDao.selectAllUnCategoryOccInspectedSpaceElementHeavyList(inspectedSpaceId);
    }
    return this.occInspectedSpaceElementDao.selectOccInspectedSpaceElementHeavyList(inspectedSpaceElementGuideId, inspectedSpaceId);
  }

  public List<OccInspectedSpaceElement> getOccInspectedSpaceElementList(String inspectedSpaceId) {
    return this.occInspectedSpaceElementDao.selectOccInspectedSpaceElementListByInspectedSpaceId(inspectedSpaceId);
  }

  public void updateOccInspectedSpaceElement(OccInspectedSpaceElement occInspectedSpaceElement) {
    this.occInspectedSpaceElementDao.updateOccInspectedSpaceElementList(occInspectedSpaceElement);
  }

  public List<OccInspectedSpaceElementHeavy> getOccInspectedSpaceElementHeavyListByInspectedSpaceId(String inspectedSpaceId) {
    return this.occInspectedSpaceElementDao.selectOccInspectedSpaceElementHeavyList(inspectedSpaceId);
  }

  public List<IntensityClass> selectAllIntensityClassListBySchemaLabel(String schemaLabel) {
    if (schemaLabel == null || schemaLabel.length() == 0) {
      return new ArrayList<>();
    }
    return this.intensityClassDao.selectAllIntensityClassListBySchemaLabel(schemaLabel);
  }

  public void deleteOccInspectedSpaceElement(OccInspectedSpaceElement occInspectedSpaceElement) {
    this.occInspectedSpaceElementDao.deleteOccInspectedSpaceElement(occInspectedSpaceElement);
  }

  public OccInspectedSpaceElementHeavy configureElementForNotInspected(OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy, int userId) {
    OccInspectedSpaceElement oise = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
    oise.setComplianceGrantedByUserId(null);
    oise.setComplianceGrantedTS(null);
    oise.setLastInspectedTS(null);
    oise.setLastInspectedByUserId(null);
    return occInspectedSpaceElementHeavy;
  }

  public OccInspectedSpaceElementHeavy configureElementForCompliance(OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy, int userId) {
    OccInspectedSpaceElement oise = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
    oise.setComplianceGrantedByUserId(userId);
    oise.setComplianceGrantedTS(OffsetDateTime.now().toString());
    oise.setLastInspectedTS(OffsetDateTime.now().toString());
    oise.setLastInspectedByUserId(userId);
    return occInspectedSpaceElementHeavy;
  }

  // TODO how to user userDefFindOnFail
  public OccInspectedSpaceElementHeavy configureElementForInspectionNoCompliance(OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy, int userId, boolean useDefFindOnFail) {
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

  public List<OccInspectedSpaceElement> createDefaultOccInspectedSpaceElementList(OccInspectedSpace occInspectedSpace) throws InvalidOccInspectedSpaceException {

    List<OccInspectedSpaceElement> occInspectedSpaceElementList = new ArrayList<>();
    if (occInspectedSpace == null) {
      throw new InvalidOccInspectedSpaceException("Invalid occInspectedSpace", new NullPointerException());
    }

    String inspectedSpaceId = occInspectedSpace.getInspectedSpaceId();
    Integer occChecklistSpaceTypeId = occInspectedSpace.getOccChecklistSpaceTypeId();
    if (inspectedSpaceId == null || occChecklistSpaceTypeId == null) {
      throw new InvalidOccInspectedSpaceException("Invalid inspectedSpaceId or occChecklistSpaceTypeId", new NullPointerException());
    }

    List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList = occChecklistSpaceTypeElementDao.selectOccChecklistSpaceTypeElementListByCSTId(occChecklistSpaceTypeId);
    if (occChecklistSpaceTypeElementList == null || occChecklistSpaceTypeElementList.size() == 0) {
      return occInspectedSpaceElementList;
    }
    for (OccChecklistSpaceTypeElement occChecklistSpaceTypeElement : occChecklistSpaceTypeElementList) {
      OccInspectedSpaceElement e = new OccInspectedSpaceElement
          .Builder()
          .inspectedSpaceElementId(UUID.randomUUID().toString())
          .locationDescriptionId(occInspectedSpace.getOccLocationDescriptionId())
          .inspectedSpaceId(occInspectedSpace.getInspectedSpaceId())
          .occChecklistSpaceTypeElementId(occChecklistSpaceTypeElement.getSpaceElementId())
          .status(new OccInspectableStatus(OccInspectionStatusEnum.NOTINSPECTED))
          .build();
      occInspectedSpaceElementList.add(e);
    }
    this.occInspectedSpaceElementDao.insertOccInspectedSpaceElementList(occInspectedSpaceElementList);
    return occInspectedSpaceElementList;
  }

  public boolean isAllInspectedSpaceElementComplete(List<OccInspectedSpaceElement> occInspectedSpaceElementList) {
    if (occInspectedSpaceElementList == null || occInspectedSpaceElementList.size() == 0) {
      return true;
    }
    occInspectedSpaceElementList = configureOccInspectedSpaceElementListStatus(occInspectedSpaceElementList);
    for (OccInspectedSpaceElement o : occInspectedSpaceElementList) {
      try {
        if (isInspectedSpaceElementNotInspected(o)) {
          return false;
        }
      } catch (InvalidOccInspectedSpaceElementException e) {
        Log.e(TAG, e.getMessage());
        return false;
      }
    }
    return true;
  }

  public boolean isAllInspectedSpaceElementHeavyComplete(List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList) {
    if (occInspectedSpaceElementHeavyList == null || occInspectedSpaceElementHeavyList.size() == 0) {
      return true;
    }
    occInspectedSpaceElementHeavyList = configureOccInspectedSpaceElementHeavyListStatus(occInspectedSpaceElementHeavyList);
    for (OccInspectedSpaceElementHeavy o : occInspectedSpaceElementHeavyList) {
      try {
        if (isInspectedSpaceElementNotInspected(o.getOccInspectedSpaceElement())) {
          return false;
        }
      } catch (InvalidOccInspectedSpaceElementException e) {
        Log.e(TAG, e.getMessage());
        return false;
      }
    }
    return true;
  }

  public List<OccInspectedSpaceElementHeavy> configureOccInspectedSpaceElementHeavyListStatus(List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList) {
    if (occInspectedSpaceElementHeavyList == null) {
      return null;
    }
    for (OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy : occInspectedSpaceElementHeavyList) {
      configureOccInspectedSpaceElementStatus(occInspectedSpaceElementHeavy.getOccInspectedSpaceElement());
    }
    return occInspectedSpaceElementHeavyList;
  }

  public List<OccInspectedSpaceElement> configureOccInspectedSpaceElementListStatus(List<OccInspectedSpaceElement> occInspectedSpaceElementList) {
    if (occInspectedSpaceElementList == null) {
      return null;
    }
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
    try {
      return isInspectedSpaceElementPass(e);
    } catch (InvalidOccInspectedSpaceElementException ex) {
      Log.e(TAG, ex.getMessage());
      return false;
    }
  }

  public boolean isInspectedSpaceElementNotInspected(OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
    OccInspectedSpaceElement e = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
    try {
      return isInspectedSpaceElementNotInspected(e);
    } catch (InvalidOccInspectedSpaceElementException ex) {
      Log.e(TAG, ex.getMessage());
      return false;
    }
  }

  public boolean isInspectedSpaceElementViolation(OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
    OccInspectedSpaceElement e = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
    try {
      return isInspectedSpaceElementViolation(e);
    } catch (InvalidOccInspectedSpaceElementException ex) {
      Log.e(TAG, ex.getMessage());
      return false;
    }
  }

  public boolean isInspectedSpaceElementPass(OccInspectedSpaceElement occInspectedSpaceElement) throws InvalidOccInspectedSpaceElementException {
    if (occInspectedSpaceElement == null || occInspectedSpaceElement.getStatus() == null) {
      throw new InvalidOccInspectedSpaceElementException("status", new NullPointerException());
    }
    return OccInspectionStatusEnum.PASS.equals(occInspectedSpaceElement.getStatus().getStatusEnum());
  }

  public boolean isInspectedSpaceElementNotInspected(OccInspectedSpaceElement occInspectedSpaceElement) throws InvalidOccInspectedSpaceElementException {
    if (occInspectedSpaceElement == null || occInspectedSpaceElement.getStatus() == null) {
      throw new InvalidOccInspectedSpaceElementException("status", new NullPointerException());
    }
    return OccInspectionStatusEnum.NOTINSPECTED.equals(occInspectedSpaceElement.getStatus().getStatusEnum());
  }

  public boolean isInspectedSpaceElementViolation(OccInspectedSpaceElement occInspectedSpaceElement) throws InvalidOccInspectedSpaceElementException {
    if (occInspectedSpaceElement == null || occInspectedSpaceElement.getStatus() == null) {
      throw new InvalidOccInspectedSpaceElementException("status", new NullPointerException());
    }
    return OccInspectionStatusEnum.VIOLATION.equals(occInspectedSpaceElement.getStatus().getStatusEnum());
  }

  public Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> getOccInspectedSpaceElementHeavyMap(String inspectedSpaceId) {

    List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList = this.occInspectedSpaceElementDao.selectOccInspectedSpaceElementHeavyList(inspectedSpaceId);
    List<CodeElementGuide> codeElementGuideList = this.codeElementGuideDao.selectCodeElementGuideList();
    Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> map = new HashMap<>();
    Map<Integer, CodeElementGuide> codeElementGuideMap = new HashMap<>();

    if (codeElementGuideList == null || occInspectedSpaceElementHeavyList == null) {
      return map;
    }

    for (CodeElementGuide codeElementGuide : codeElementGuideList) {
      Integer guideEntryId = codeElementGuide.getGuideEntryId();
      if (guideEntryId == null) {
        continue;
      }
      codeElementGuideMap.putIfAbsent(guideEntryId, codeElementGuide);
    }

    CodeElementGuide unknownCodeElementGuide = new CodeElementGuide();
    unknownCodeElementGuide.setCategory("UnCategorized");

    for (OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy : occInspectedSpaceElementHeavyList) {
      CodeElement codeElement = occInspectedSpaceElementHeavy.getCodeElement();
      Integer guideEntryId = null;
      CodeElementGuide codeElementGuide = null;
      if (codeElement == null || (guideEntryId = codeElement.getGuideEntryId()) == null || (codeElementGuide = codeElementGuideMap.get(guideEntryId)) == null) {
        List<OccInspectedSpaceElementHeavy> list = map.get(unknownCodeElementGuide);
        if (list == null) {
          list = new ArrayList<>();
          map.put(unknownCodeElementGuide, list);
        }
        list.add(occInspectedSpaceElementHeavy);
        continue;
      }
      List<OccInspectedSpaceElementHeavy> list = map.get(codeElementGuide);
      if (list == null) {
        list = new ArrayList<>();
        map.put(codeElementGuide, list);
      }
      list.add(occInspectedSpaceElementHeavy);
    }
    return map;
  }

  public Map<OccInspectionStatusEnum, List<OccInspectedSpaceElementHeavy>> getElementStatusMap(List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList) {
    Map<OccInspectionStatusEnum, List<OccInspectedSpaceElementHeavy>> elementStatusMap = new HashMap<>();
    if (occInspectedSpaceElementHeavyList == null) {
      return elementStatusMap;
    }
    List<OccInspectedSpaceElementHeavy> passList = new ArrayList<>();
    List<OccInspectedSpaceElementHeavy> violationList = new ArrayList<>();
    List<OccInspectedSpaceElementHeavy> notInspectedList = new ArrayList<>();

    elementStatusMap.put(OccInspectionStatusEnum.PASS, passList);
    elementStatusMap.put(OccInspectionStatusEnum.VIOLATION, violationList);
    elementStatusMap.put(OccInspectionStatusEnum.NOTINSPECTED, notInspectedList);

    for (OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy : occInspectedSpaceElementHeavyList) {
      OccInspectedSpaceElement e = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
      if (e == null) {
        continue;
      }
      configureOccInspectedSpaceElementStatus(e);
      switch (e.getStatus().getStatusEnum()) {
        case VIOLATION:
          violationList.add(occInspectedSpaceElementHeavy);
          break;
        case NOTINSPECTED:
          notInspectedList.add(occInspectedSpaceElementHeavy);
          break;
        case PASS:
          passList.add(occInspectedSpaceElementHeavy);
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

  public Map<Category, Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>>> getOccInspectedSpaceElementHeavyStatusMap(
      Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> occInspectedSpaceElementHeavyMap) {

    Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> unFinishOccInspectedSpaceElementHeavyMap = new HashMap<>();
    Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> finishedOccInspectedSpaceElementHeavyMap = new HashMap<>();

    for (Entry<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> entry : occInspectedSpaceElementHeavyMap.entrySet()) {
      CodeElementGuide codeElementGuide = entry.getKey();
      List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList = entry.getValue();
      if (!isAllInspectedSpaceElementHeavyComplete(occInspectedSpaceElementHeavyList)) {
        unFinishOccInspectedSpaceElementHeavyMap.put(codeElementGuide, occInspectedSpaceElementHeavyList);
      } else {
        finishedOccInspectedSpaceElementHeavyMap.put(codeElementGuide, occInspectedSpaceElementHeavyList);
      }
    }
    Map<Category, Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>>> map = new HashMap<>();
    map.put(Category.FINISHED, finishedOccInspectedSpaceElementHeavyMap);
    map.put(Category.UN_FINISH, unFinishOccInspectedSpaceElementHeavyMap);
    return map;
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
