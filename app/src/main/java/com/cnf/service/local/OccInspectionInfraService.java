package com.cnf.service.local;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.cnf.db.InspectionDatabase;
import com.cnf.domain.BlobBytes;
import com.cnf.domain.infra.CodeSetElement;
import com.cnf.domain.infra.OccCheckList;
import com.cnf.domain.infra.OccInspectionInfra;
import com.cnf.domain.infra_heavy.OccChecklistSpaceTypeElementHeavy;
import com.cnf.domain.infra_heavy.OccChecklistSpaceTypeHeavy;
import com.cnf.domain.OccInspectedSpace;
import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.domain.PhotoDoc;
import com.cnf.domain.infra.CodeElement;
import com.cnf.domain.infra.CodeElementGuide;
import com.cnf.domain.infra.CodeSource;
import com.cnf.domain.infra.IntensityClass;
import com.cnf.domain.infra.Municipality;
import com.cnf.domain.infra.OccChecklistSpaceType;
import com.cnf.domain.infra.OccChecklistSpaceTypeElement;
import com.cnf.domain.infra.OccLocationDescription;
import com.cnf.domain.infra.OccSpaceType;
import com.cnf.dto.OccInspectedSpaceDTO;
import com.cnf.dto.OccInspectedSpaceElementDTO;
import com.cnf.dto.PhotoDto;
import com.cnf.dto.UploadDTO;
import com.cnf.service.exception.OccInspectionCopyNullPointerException;

import java.util.ArrayList;
import java.util.List;

public class OccInspectionInfraService {

  private static OccInspectionInfraService INSTANCE = null;

  private OccInspectionInfraService() {
  }

  public static OccInspectionInfraService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new OccInspectionInfraService();
    }
    return INSTANCE;
  }

  /*
   * -------------------------------- Code Source -------------------------------------------
   */

  /**
   * To get code source list form sqlite
   *
   * @param inspectionDatabase sqlite database
   * @return code source list from sqlite
   */
  public List<CodeSource> getCodeSourceListFromSQLite(InspectionDatabase inspectionDatabase) {
    List<CodeSource> codeSourceList = inspectionDatabase.getCodeSourceDao().selectAllCodeSourceList();
    if (codeSourceList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Code Source List: %s", codeSourceList));
    return codeSourceList;
  }

  /**
   * To insert code source list to sqlite
   *
   * @param inspectionDatabase sqlite database
   * @param codeSourceList     code source list to insert
   */
  public void insertCodeSourceList(InspectionDatabase inspectionDatabase, List<CodeSource> codeSourceList) throws OccInspectionCopyNullPointerException {
    if (codeSourceList == null) {
      throw new OccInspectionCopyNullPointerException("Code source list can not be null when inserting to database");
    }
    inspectionDatabase.getCodeSourceDao().insertCodeSourceList(codeSourceList);
  }

  /**
   * @param inspectionDatabase
   */
  private void deleteAllCodeSourceList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getCodeSourceDao().deleteAllCodeSourceList();
  }

  /*
   * -------------------------------- Code Element -------------------------------------------
   */

  /**
   * To get code element list from sqlite
   *
   * @param inspectionDatabase sqlite database
   * @return code element list from sqlite
   */
  public List<CodeElement> getCodeElementListFromSQLite(InspectionDatabase inspectionDatabase) {
    List<CodeElement> codeElementList = inspectionDatabase.getCodeElementDao().selectCodeElementList();
    if (codeElementList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Code Source List: %s", codeElementList));
    return codeElementList;
  }

  /**
   * To insert code element to sqlite
   *
   * @param inspectionDatabase sqlite database
   * @param codeElementList    code element list to insert
   */
  public void insertCodeElementList(InspectionDatabase inspectionDatabase, List<CodeElement> codeElementList) throws OccInspectionCopyNullPointerException {
    if (codeElementList == null) {
      throw new OccInspectionCopyNullPointerException("Code element list can not be null when inserting to database");
    }
    inspectionDatabase.getCodeElementDao().insertCodeElementList(codeElementList);
  }

  /**
   * @param inspectionDatabase
   */
  private void deleteAllCodeElementList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getCodeElementDao().deleteAllCodeElementList();
  }

  /*
   * -------------------------------- Code Set Element -------------------------------------------
   */

  /**
   * To get code set element list from sqlite
   *
   * @param inspectionDatabase sqlite database
   * @return code set element list from sqlite
   */
  public List<CodeSetElement> getCodeSetElementListFromSQLite(InspectionDatabase inspectionDatabase) {
    List<CodeSetElement> codeSetElementList = inspectionDatabase.getCodeSetElementDao().selectCodeSetElementList();
    if (codeSetElementList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Code Source List: %s", codeSetElementList));
    return codeSetElementList;
  }

  /**
   * To insert code element to sqlite
   *
   * @param inspectionDatabase sqlite database
   * @param codeSetElementList code set element list to insert
   */
  public void insertCodeSetElementList(InspectionDatabase inspectionDatabase, List<CodeSetElement> codeSetElementList) throws OccInspectionCopyNullPointerException {
    if (codeSetElementList == null) {
      throw new OccInspectionCopyNullPointerException("Code set element list can not be null when inserting to database");
    }
    inspectionDatabase.getCodeSetElementDao().insertCodeSetElementList(codeSetElementList);
  }

  /**
   * @param inspectionDatabase
   */
  private void deleteAllCodeSetElementList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getCodeSetElementDao().deleteAllCodeSetElementList();
  }

  /*
   * -------------------------------- Code Element Guide-------------------------------------------
   */

  /**
   * @param inspectionDatabase
   * @return
   */
  public List<CodeElementGuide> getCodeElementGuideListFromSQLite(InspectionDatabase inspectionDatabase) {
    List<CodeElementGuide> codeElementGuideList = inspectionDatabase.getCodeElementGuideDao().selectCodeElementGuideList();
    if (codeElementGuideList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Code Element Guide List: %s", codeElementGuideList));
    return codeElementGuideList;
  }

  /**
   * @param inspectionDatabase
   * @param codeElementGuideList
   */
  public void insertCodeElementGuideList(InspectionDatabase inspectionDatabase, List<CodeElementGuide> codeElementGuideList) throws OccInspectionCopyNullPointerException {
    if (codeElementGuideList == null) {
      throw new OccInspectionCopyNullPointerException("Code element guide list can not be null when inserting to database");
    }
    inspectionDatabase.getCodeElementGuideDao().insertCodeElementGuideList(codeElementGuideList);
  }

  /**
   * @param inspectionDatabase
   */
  private void deleteAllCodeElementGuideList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getCodeElementGuideDao().deleteAllCodeElementGuideList();
  }

  /*
   * -------------------------------- Occ Checklist Space Type -------------------------------------------
   */

  /**
   * @param inspectionDatabase
   * @return
   */
  public List<OccChecklistSpaceType> getOccChecklistSpaceTypeListFromSQLite(InspectionDatabase inspectionDatabase) {
    List<OccChecklistSpaceType> occChecklistSpaceTypeList = inspectionDatabase.getOccChecklistSpaceTypeDao().selectAllOccChecklistSpaceTypeList();
    if (occChecklistSpaceTypeList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Occ Checklist Space Type List: %s", occChecklistSpaceTypeList));
    return occChecklistSpaceTypeList;
  }

  /**
   * @param inspectionDatabase
   * @return
   */
  public List<OccChecklistSpaceTypeHeavy> getOccChecklistSpaceTypeHeavyListFromSQLite(InspectionDatabase inspectionDatabase, int checklistId) {
    List<OccChecklistSpaceTypeHeavy> occChecklistSpaceTypeHeavyList = inspectionDatabase.getOccChecklistSpaceTypeDao().selectAllOccChecklistSpaceTypeHeavyList(checklistId);
    if (occChecklistSpaceTypeHeavyList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Occ Checklist Space Type Heavy List: %s", occChecklistSpaceTypeHeavyList));
    return occChecklistSpaceTypeHeavyList;
  }

  /**
   * @param inspectionDatabase
   * @param occChecklistSpaceTypeList
   */
  public void insertOccChecklistSpaceTypeList(InspectionDatabase inspectionDatabase, List<OccChecklistSpaceType> occChecklistSpaceTypeList) throws OccInspectionCopyNullPointerException {
    if (occChecklistSpaceTypeList == null) {
      throw new OccInspectionCopyNullPointerException("Occ checklist space type list can not be null when inserting to database");
    }
    inspectionDatabase.getOccChecklistSpaceTypeDao().insertOccChecklistSpaceTypeList(occChecklistSpaceTypeList);
  }

  /**
   * @param inspectionDatabase
   */
  private void deleteAllOccChecklistSpaceTypeList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getOccChecklistSpaceTypeDao().deleteAllOccChecklistSpaceTypeList();
  }

  /*
   * -------------------------------- Occ Checklist Space Type Element -------------------------------------------
   */

  /**
   * @param inspectionDatabase
   * @return
   */
  public List<OccChecklistSpaceTypeElement> getOccChecklistSpaceTypeElementListFromSQLite(InspectionDatabase inspectionDatabase) {
    List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList = inspectionDatabase.getOccChecklistSpaceTypeElementDao().selectAllOccChecklistSpaceTypeElementList();
    if (occChecklistSpaceTypeElementList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Occ Checklist Space Type Element List: %s", occChecklistSpaceTypeElementList));
    return occChecklistSpaceTypeElementList;
  }


  public List<OccChecklistSpaceTypeElementHeavy> getOccChecklistSpaceTypeElementHeavyDetailsList(Integer CSTId, InspectionDatabase inspectionDatabase) {
    List<OccChecklistSpaceTypeElementHeavy> occChecklistSpaceTypeElementHeavyList = inspectionDatabase.getOccChecklistSpaceTypeElementDao()
        .selectAllOccChecklistSpaceTypeElementListDetailsByCSTId(CSTId);
    if (occChecklistSpaceTypeElementHeavyList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Occ Checklist Space Type Element Heavy List: %s", occChecklistSpaceTypeElementHeavyList));
    return occChecklistSpaceTypeElementHeavyList;
  }

  /**
   * @param inspectionDatabase
   * @param occChecklistSpaceTypeElementList
   */
  public void insertOccChecklistSpaceTypeElementList(InspectionDatabase inspectionDatabase, List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList)
      throws OccInspectionCopyNullPointerException {
    if (occChecklistSpaceTypeElementList == null) {
      throw new OccInspectionCopyNullPointerException("Occ checklist space type element list can not be null when inserting to database");
    }
    inspectionDatabase.getOccChecklistSpaceTypeElementDao().insertOccChecklistSpaceTypeElementList(occChecklistSpaceTypeElementList);
  }

  /**
   * @param inspectionDatabase
   */
  private void deleteAllOccChecklistSpaceTypeElementList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getOccChecklistSpaceTypeElementDao().deleteAllOccChecklistSpaceTypeElementList();
  }

  /*
   * -------------------------------- Occ Space Type  -------------------------------------------
   */

  /**
   * @param inspectionDatabase
   * @return
   */
  public List<OccSpaceType> getOccSpaceTypeListFromSQLite(InspectionDatabase inspectionDatabase) {
    List<OccSpaceType> occSpaceTypeList = inspectionDatabase.getOccSpaceTypeDao().selectAllOccSpaceTypeList();
    if (occSpaceTypeList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Occ Space Type List: %s", occSpaceTypeList));
    return occSpaceTypeList;
  }

  /**
   * @param inspectionDatabase
   * @param occSpaceTypeList
   */
  public void insertOccSpaceTypeList(InspectionDatabase inspectionDatabase, List<OccSpaceType> occSpaceTypeList) throws OccInspectionCopyNullPointerException {
    if (occSpaceTypeList == null) {
      throw new OccInspectionCopyNullPointerException("Occ space type list can not be null when inserting to database");
    }
    inspectionDatabase.getOccSpaceTypeDao().insertOccSpaceTypeList(occSpaceTypeList);
  }

  /**
   * @param inspectionDatabase
   */
  private void deleteAllOccSpaceTypeList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getOccSpaceTypeDao().deleteAllOccSpaceTypeList();
  }

  /*
   * --------------------------------  Occ Location Description   -------------------------------------------
   */

  /**
   * @param inspectionDatabase
   * @return
   */
  public List<OccLocationDescription> getOccLocationDescriptionListFromSQLite(InspectionDatabase inspectionDatabase) {
    List<OccLocationDescription> occLocationDescriptionList = inspectionDatabase.getOccLocationDescriptionDao().selectAllOccLocationDescriptionList();
    if (occLocationDescriptionList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Occ Location Description List: %s", occLocationDescriptionList));
    return occLocationDescriptionList;
  }

  /**
   * @param inspectionDatabase
   * @param occLocationDescriptionList
   */
  public void insertOccLocationDescriptionList(InspectionDatabase inspectionDatabase, List<OccLocationDescription> occLocationDescriptionList) throws OccInspectionCopyNullPointerException {
    if (occLocationDescriptionList == null) {
      throw new OccInspectionCopyNullPointerException("Occ location description can not be null when inserting to database");
    }
    inspectionDatabase.getOccLocationDescriptionDao().insertOccLocationDescriptionList(occLocationDescriptionList);
  }

  /**
   * @param inspectionDatabase
   */
  private void deleteAllOccLocationDescriptionList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getOccLocationDescriptionDao().deleteAllOccLocationDescriptionList();
  }

  /*
   * --------------------------------  Municipality  -------------------------------------------
   */

  /**
   * @param inspectionDatabase
   * @return
   */
  public List<Municipality> getMunicipalityListFromSQLite(InspectionDatabase inspectionDatabase) {
    List<Municipality> municipalityList = inspectionDatabase.getMunicipalityDao().selectMunicipalityList();
    if (municipalityList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Municipality List: %s", municipalityList));
    return municipalityList;
  }


  /**
   * @param inspectionDatabase
   * @param municipalityList
   * @throws OccInspectionCopyNullPointerException
   */
  public void insertMunicipalityList(InspectionDatabase inspectionDatabase, List<Municipality> municipalityList) throws OccInspectionCopyNullPointerException {
    if (municipalityList == null) {
      throw new OccInspectionCopyNullPointerException("Occ location description can not be null when inserting to database");
    }
    inspectionDatabase.getMunicipalityDao().insertMunicipalityList(municipalityList);
  }


  private void deleteAllMunicipalityList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getMunicipalityDao().deleteAllMunicipalityList();
  }


  /*
   * --------------------------------  Intensity Class   -------------------------------------------
   */

  /**
   * @param inspectionDatabase
   * @return
   */
  public List<IntensityClass> getIntensityClassList(InspectionDatabase inspectionDatabase) {
    List<IntensityClass> intensityClassList = inspectionDatabase.getIntensityClassDao().selectAllIntensityClassList();
    if (intensityClassList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Intensity Class List: %s", intensityClassList));
    return intensityClassList;
  }

  /**
   * @param inspectionDatabase
   * @param intensityClassList
   */
  public void insertIntensityClassList(InspectionDatabase inspectionDatabase, List<IntensityClass> intensityClassList) throws OccInspectionCopyNullPointerException {
    if (intensityClassList == null) {
      throw new OccInspectionCopyNullPointerException("Occ intensity class list can not be null when inserting to database");
    }
    inspectionDatabase.getIntensityClassDao().insertIntensityClassList(intensityClassList);
  }

  /**
   * @param inspectionDatabase
   */
  private void deleteAllIntensityClassList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getIntensityClassDao().deleteAllIntensityClassList();
  }

  /*
   * --------------------------------  OccCheckList Class   -------------------------------------------
   */

  /**
   * @param inspectionDatabase
   * @return
   */
  public List<OccCheckList> getOccCheckListList(InspectionDatabase inspectionDatabase) {
    List<OccCheckList> occCheckListList = inspectionDatabase.getOccCheckListDao().selectAllOccCheckListList();
    if (occCheckListList == null) {
      return new ArrayList<>();
    }
    Log.d(TAG, String.format("Local Intensity Class List: %s", occCheckListList));
    return occCheckListList;
  }


  public void insertOccCheckListList(InspectionDatabase inspectionDatabase, List<OccCheckList> occCheckListList) throws OccInspectionCopyNullPointerException {
    if (occCheckListList == null) {
      throw new OccInspectionCopyNullPointerException("Occ Check List list can not be null when inserting to database");
    }
    inspectionDatabase.getOccCheckListDao().insertOccCheckListList(occCheckListList);
  }

  /**
   * @param inspectionDatabase
   */
  private void deleteAllOccCheckListList(InspectionDatabase inspectionDatabase) {
    inspectionDatabase.getOccCheckListDao().deleteAllOccCheckListList();
  }

  /*
   * -------------------------------- End   -------------------------------------------
   */

  /**
   * @param inspectionDatabase
   */
  public void deleteOccInspectionInfraFromSQLite(InspectionDatabase inspectionDatabase) {
    deleteAllCodeSourceList(inspectionDatabase);
    deleteAllCodeElementList(inspectionDatabase);
    deleteAllCodeSetElementList(inspectionDatabase);
    deleteAllCodeElementGuideList(inspectionDatabase);
    deleteAllOccCheckListList(inspectionDatabase);
    deleteAllOccChecklistSpaceTypeList(inspectionDatabase);
    deleteAllOccChecklistSpaceTypeElementList(inspectionDatabase);
    deleteAllOccSpaceTypeList(inspectionDatabase);
    deleteAllOccLocationDescriptionList(inspectionDatabase);
    deleteAllIntensityClassList(inspectionDatabase);
    deleteAllMunicipalityList(inspectionDatabase);
  }

  public void insertOccInspectionInfraToSQLite(InspectionDatabase inspectionDatabase, OccInspectionInfra occInspectionInfra) throws OccInspectionCopyNullPointerException {
    if (occInspectionInfra == null) {
      throw new OccInspectionCopyNullPointerException("Occ Inspection Copy Null Pointer Error");
    }
    insertCodeSourceList(inspectionDatabase, occInspectionInfra.getCodeSourceList());
    insertCodeElementList(inspectionDatabase, occInspectionInfra.getCodeElementList());
    insertCodeSetElementList(inspectionDatabase, occInspectionInfra.getCodeSetElementList());
    insertCodeElementGuideList(inspectionDatabase, occInspectionInfra.getCodeElementGuideList());
    insertOccCheckListList(inspectionDatabase, occInspectionInfra.getOccCheckListList());
    insertOccChecklistSpaceTypeList(inspectionDatabase, occInspectionInfra.getOccChecklistSpaceTypeList());
    insertOccChecklistSpaceTypeElementList(inspectionDatabase, occInspectionInfra.getOccChecklistSpaceTypeElementList());
    insertOccSpaceTypeList(inspectionDatabase, occInspectionInfra.getOccSpaceTypeList());
    insertOccLocationDescriptionList(inspectionDatabase, occInspectionInfra.getOccLocationDescriptionList());
    insertIntensityClassList(inspectionDatabase, occInspectionInfra.getIntensityClassList());
    insertMunicipalityList(inspectionDatabase, occInspectionInfra.getMunicipalityList());
  }

  public UploadDTO getUploadDTO(Integer inspectionId, InspectionDatabase inspectionDatabase) {
    List<OccInspectedSpace> occInspectedSpaceList = inspectionDatabase.getOccInspectedSpaceDao().selectAllOccInspectedSpaceList(inspectionId);
    List<OccInspectedSpaceDTO> occInspectedSpaceDTOList = new ArrayList<>();
    for (OccInspectedSpace occInspectedSpace : occInspectedSpaceList) {
      Integer inspectedSpaceId = occInspectedSpace.getInspectedSpaceId();
      List<OccInspectedSpaceElement> occInspectedSpaceElementList = inspectionDatabase.getOccInspectedSpaceElementDao().selectAllOccInspectedSpaceElementList(inspectedSpaceId);
      List<OccInspectedSpaceElementDTO> occInspectedSpaceElementDTOList = new ArrayList<>();
      for (OccInspectedSpaceElement occInspectedSpaceElement : occInspectedSpaceElementList) {
        Integer inspectedSpaceElementId = occInspectedSpaceElement.getInspectedSpaceElementId();
        List<PhotoDto> photoDtoList = new ArrayList<>();
        List<PhotoDoc> photoDocList = inspectionDatabase.getPhotoDocDao().selectAllPhotoDocListByOccInspectedSpaceTypeElementId(inspectedSpaceElementId);
        for (PhotoDoc photoDoc : photoDocList) {
          Integer blobBytesId = photoDoc.getBlobBytesId();
          BlobBytes blobBytes = inspectionDatabase.getBlobBytesDao().selectBlobByteById(blobBytesId);
          photoDtoList.add(new PhotoDto(photoDoc, blobBytes));
        }
        occInspectedSpaceElementDTOList.add(new OccInspectedSpaceElementDTO(occInspectedSpaceElement, photoDtoList));
      }
      occInspectedSpaceDTOList.add(new OccInspectedSpaceDTO(occInspectedSpace, occInspectedSpaceElementDTOList));
    }
    UploadDTO uploadDTO = new UploadDTO(inspectionId, occInspectedSpaceDTOList);
    return uploadDTO;
  }

}
