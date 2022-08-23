package com.cnf.module_inspection.service.local;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import com.cnf.module_inspection.dao.infra.CodeSourceDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.BlobBytes;
import com.cnf.module_inspection.entity.infra.CodeSetElement;
import com.cnf.module_inspection.entity.infra.OccCheckList;
import com.cnf.module_inspection.entity.infra.OccInspectionInfra;
import com.cnf.module_inspection.entity.infra_heavy.OccChecklistSpaceTypeElementHeavy;
import com.cnf.module_inspection.entity.infra_heavy.OccChecklistSpaceTypeHeavy;
import com.cnf.module_inspection.entity.OccInspectedSpace;
import com.cnf.module_inspection.entity.OccInspectedSpaceElement;
import com.cnf.module_inspection.entity.PhotoDoc;
import com.cnf.module_inspection.entity.infra.CodeElement;
import com.cnf.module_inspection.entity.infra.CodeElementGuide;
import com.cnf.module_inspection.entity.infra.CodeSource;
import com.cnf.module_inspection.entity.infra.IntensityClass;
import com.cnf.module_inspection.entity.infra.Municipality;
import com.cnf.module_inspection.entity.infra.OccChecklistSpaceType;
import com.cnf.module_inspection.entity.infra.OccChecklistSpaceTypeElement;
import com.cnf.module_inspection.entity.infra.OccLocationDescription;
import com.cnf.module_inspection.entity.infra.OccSpaceType;
import com.cnf.module_inspection.dto.OccInspectedSpaceDTO;
import com.cnf.module_inspection.dto.OccInspectedSpaceElementDTO;
import com.cnf.module_inspection.dto.PhotoDto;
import com.cnf.module_inspection.dto.UploadDTO;
import com.cnf.module_inspection.service.exception.OccInspectionCopyNullPointerException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OccInspectionInfraService {
  
  private CodeSourceRepository codeSourceRepository;
  private CodeElementRepository codeElementRepository;
  private CodeSetElementRepository codeSetElementRepository;
  private CodeElementGuideRepository codeElementGuideRepository;
  private OccChecklistRepository occChecklistRepository;
  private OccInspectionChecklistSpaceTypeRepository occInspectionChecklistSpaceTypeRepository;
  private OccInspectionChecklistSpaceTypeElementRepository occInspectionChecklistSpaceTypeElementRepository;
  private OccSpaceTypeRepository occSpaceTypeRepository;
  private OccLocationDescriptionRepository occLocationDescriptionRepository;
  private IntensityRepository intensityRepository;
  private MunicipalityRepository municipalityRepository;

  private OccInspectionSpaceRepository occInspectionSpaceRepository;
  private OccInspectionSpaceElementRepository occInspectionSpaceElementRepository;
  private OccInspectionPhotoRepository occInspectionPhotoRepository;
  private BlobBytesRepository blobBytesRepository;
  private OccInspectedSpaceElementPhotoDocRepository occInspectedSpaceElementPhotoDocRepository;


  private static final OccInspectionInfraService INSTANCE = new OccInspectionInfraService();

  private OccInspectionInfraService() {
  }

  public static OccInspectionInfraService getInstance(Context context) {
    INSTANCE.codeSourceRepository = CodeSourceRepository.getInstance(context);
    INSTANCE.codeElementRepository = CodeElementRepository.getInstance(context);
    INSTANCE.codeSetElementRepository = CodeSetElementRepository.getInstance(context);
    INSTANCE.codeElementGuideRepository = CodeElementGuideRepository.getInstance(context);
    INSTANCE.occChecklistRepository = OccChecklistRepository.getInstance(context);
    INSTANCE.occInspectionChecklistSpaceTypeRepository = OccInspectionChecklistSpaceTypeRepository.getInstance(context);
    INSTANCE.occInspectionChecklistSpaceTypeElementRepository = OccInspectionChecklistSpaceTypeElementRepository.getInstance(context);
    INSTANCE.occSpaceTypeRepository = OccSpaceTypeRepository.getInstance(context);
    INSTANCE.occLocationDescriptionRepository = OccLocationDescriptionRepository.getInstance(context);
    INSTANCE.intensityRepository = IntensityRepository.getInstance(context);
    INSTANCE.municipalityRepository = MunicipalityRepository.getInstance(context);

    INSTANCE.occInspectionSpaceRepository = OccInspectionSpaceRepository.getInstance(context);
    INSTANCE.occInspectionSpaceElementRepository = OccInspectionSpaceElementRepository.getInstance(context);
    INSTANCE.occInspectionPhotoRepository = OccInspectionPhotoRepository.getInstance(context);
    INSTANCE.blobBytesRepository = BlobBytesRepository.getInstance(context);
    INSTANCE.occInspectedSpaceElementPhotoDocRepository = OccInspectedSpaceElementPhotoDocRepository.getInstance(context);

    return INSTANCE;
  }
  
  
  public void deleteOccInspectionInfraFromSQLite() {
    codeSourceRepository.deleteAllCodeSourceList();
    codeElementRepository.deleteAllCodeElementList();
    codeSetElementRepository.deleteAllCodeSetElementList();
    codeElementGuideRepository.deleteAllCodeElementGuideList();
    occChecklistRepository.deleteAllOccCheckListList();
    occInspectionChecklistSpaceTypeRepository.deleteAllOccChecklistSpaceTypeList();
    occInspectionChecklistSpaceTypeElementRepository.deleteAllOccChecklistSpaceTypeElementList();
    occSpaceTypeRepository.deleteAllOccSpaceTypeList();
    occLocationDescriptionRepository.deleteAllOccLocationDescriptionList();
    intensityRepository.deleteAllIntensityClassList();
    municipalityRepository.deleteAllMunicipalityList();
  }

  public void insertOccInspectionInfraToSQLite( OccInspectionInfra occInspectionInfra) throws OccInspectionCopyNullPointerException {
    if (occInspectionInfra == null) {
      throw new OccInspectionCopyNullPointerException("Occ Inspection Copy Null Pointer Error");
    }

    codeSourceRepository.insertCodeSourceList(occInspectionInfra.getCodeSourceList());
    codeElementRepository.insertCodeElementList(occInspectionInfra.getCodeElementList());
    codeSetElementRepository.insertCodeSetElementList(occInspectionInfra.getCodeSetElementList());
    codeElementGuideRepository.insertCodeElementGuideList(occInspectionInfra.getCodeElementGuideList());
    occChecklistRepository.insertOccCheckListList(occInspectionInfra.getOccCheckListList());
    occInspectionChecklistSpaceTypeRepository.insertOccChecklistSpaceTypeList(occInspectionInfra.getOccChecklistSpaceTypeList());
    occInspectionChecklistSpaceTypeElementRepository.insertOccChecklistSpaceTypeElementList(occInspectionInfra.getOccChecklistSpaceTypeElementList());
    occSpaceTypeRepository.insertOccSpaceTypeList(occInspectionInfra.getOccSpaceTypeList());
    occLocationDescriptionRepository.insertOccLocationDescriptionList(occInspectionInfra.getOccLocationDescriptionList());
    intensityRepository.insertIntensityClassList(occInspectionInfra.getIntensityClassList());
    municipalityRepository.insertMunicipalityList(occInspectionInfra.getMunicipalityList());

  }

  public UploadDTO getUploadDTO(Integer inspectionId ) {
    List<OccInspectedSpace> occInspectedSpaceList = occInspectionSpaceRepository.getOccInspectedSpaceListByInspectionId(inspectionId);
    List<OccInspectedSpaceDTO> occInspectedSpaceDTOList = new ArrayList<>();
    for (OccInspectedSpace occInspectedSpace : occInspectedSpaceList) {
      String inspectedSpaceId = occInspectedSpace.getInspectedSpaceId();
      List<OccInspectedSpaceElement> occInspectedSpaceElementList = occInspectionSpaceElementRepository.getOccInspectedSpaceElementList(inspectedSpaceId);
      List<OccInspectedSpaceElementDTO> occInspectedSpaceElementDTOList = new ArrayList<>();
      for (OccInspectedSpaceElement occInspectedSpaceElement : occInspectedSpaceElementList) {
        String inspectedSpaceElementId = occInspectedSpaceElement.getInspectedSpaceElementId();
        List<PhotoDto> photoDtoList = new ArrayList<>();
        List<PhotoDoc> photoDocList = occInspectionPhotoRepository.selectAllPhotoDocList(inspectedSpaceElementId);
        for (PhotoDoc photoDoc : photoDocList) {
          String blobBytesId = photoDoc.getBlobBytesId();
          BlobBytes blobBytes = blobBytesRepository.getBlobByte(blobBytesId);
          if (blobBytes != null) {
            blobBytesRepository.deleteBlobByte(blobBytes);
          }
          occInspectionPhotoRepository.deletePhotoDoc(photoDoc);
          if (!isNumeric(blobBytesId)) {
            blobBytes.setBytesId("-1");
            photoDoc.setBlobBytesId("-1");
          }
          String photoDocId = photoDoc.getPhotoDocId();
          if (!isNumeric(photoDocId)) {
            photoDoc.setPhotoDocId("-1");
          }
          photoDtoList.add(new PhotoDto(photoDoc, blobBytes));
        }
        occInspectionSpaceElementRepository.deleteOccInspectedSpaceElement(occInspectedSpaceElement);
        if (!isNumeric(inspectedSpaceElementId)) {
          occInspectedSpaceElement.setInspectedSpaceElementId("-1");
          occInspectedSpaceElement.setInspectedSpaceId("-1");
        }
        occInspectedSpaceElementDTOList.add(new OccInspectedSpaceElementDTO(occInspectedSpaceElement, photoDtoList));
      }
      occInspectionSpaceRepository.deleteOccInspectedSpace(occInspectedSpace);
      if (!isNumeric(inspectedSpaceId)) {
        occInspectedSpace.setInspectedSpaceId("-1");
      }
      occInspectedSpaceDTOList.add(new OccInspectedSpaceDTO(occInspectedSpace, occInspectedSpaceElementDTOList));
    }
    UploadDTO uploadDTO = new UploadDTO(inspectionId, occInspectedSpaceDTOList);
    return uploadDTO;
  }

  private boolean isNumeric(String str) {
    Pattern pattern = Pattern.compile("[0-9]*");
    Matcher isNum = pattern.matcher(str);
    if (!isNum.matches()) {
      return false;
    }
    return true;
  }

}
