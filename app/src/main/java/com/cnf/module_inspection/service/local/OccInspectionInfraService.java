package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.BlobBytes;
import com.cnf.module_inspection.entity.infra.OccInspectionInfra;
import com.cnf.module_inspection.entity.OccInspectedSpace;
import com.cnf.module_inspection.entity.OccInspectedSpaceElement;
import com.cnf.module_inspection.entity.PhotoDoc;
import com.cnf.module_inspection.dto.OccInspectedSpaceDTO;
import com.cnf.module_inspection.dto.OccInspectedSpaceElementDTO;
import com.cnf.module_inspection.dto.PhotoDto;
import com.cnf.module_inspection.dto.UploadDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OccInspectionInfraService {

  private static final OccInspectionInfraService INSTANCE = new OccInspectionInfraService();
  private InspectionDatabase inspectionDatabase;

  private OccInspectionInfraService() {
  }

  public static OccInspectionInfraService getInstance(Context context) {
    INSTANCE.inspectionDatabase = InspectionDatabase.getInstance(context);
    return INSTANCE;
  }

  public void deleteOccInspectionInfraFromDatabase() {
    inspectionDatabase.runInTransaction(() -> {
      inspectionDatabase.getCodeSourceDao().deleteAllCodeSourceList();
      inspectionDatabase.getCodeElementDao().deleteAllCodeElementList();
      inspectionDatabase.getCodeSetElementDao().deleteAllCodeSetElementList();
      inspectionDatabase.getCodeElementGuideDao().deleteAllCodeElementGuideList();
      inspectionDatabase.getOccCheckListDao().deleteAllOccCheckListList();
      inspectionDatabase.getOccChecklistSpaceTypeDao().deleteAllOccChecklistSpaceTypeList();
      inspectionDatabase.getOccChecklistSpaceTypeElementDao().deleteAllOccChecklistSpaceTypeElementList();
      inspectionDatabase.getOccSpaceTypeDao().deleteAllOccSpaceTypeList();
      inspectionDatabase.getOccLocationDescriptionDao().deleteAllOccLocationDescriptionList();
      inspectionDatabase.getIntensityClassDao().deleteAllIntensityClassList();
      inspectionDatabase.getMunicipalityDao().deleteAllMunicipalityList();
    });
  }

  public void insertOccInspectionInfraToDatabase(OccInspectionInfra occInspectionInfra) {
    inspectionDatabase.runInTransaction(() -> {
      inspectionDatabase.getCodeSourceDao().insertCodeSourceList(occInspectionInfra.getCodeSourceList());
      inspectionDatabase.getCodeElementDao().insertCodeElementList(occInspectionInfra.getCodeElementList());
      inspectionDatabase.getCodeSetElementDao().insertCodeSetElementList(occInspectionInfra.getCodeSetElementList());
      inspectionDatabase.getCodeElementGuideDao().insertCodeElementGuideList(occInspectionInfra.getCodeElementGuideList());
      inspectionDatabase.getOccCheckListDao().insertOccCheckListList(occInspectionInfra.getOccCheckListList());
      inspectionDatabase.getOccChecklistSpaceTypeDao().insertOccChecklistSpaceTypeList(occInspectionInfra.getOccChecklistSpaceTypeList());
      inspectionDatabase.getOccChecklistSpaceTypeElementDao().insertOccChecklistSpaceTypeElementList(occInspectionInfra.getOccChecklistSpaceTypeElementList());
      inspectionDatabase.getOccSpaceTypeDao().insertOccSpaceTypeList(occInspectionInfra.getOccSpaceTypeList());
      inspectionDatabase.getOccLocationDescriptionDao().insertOccLocationDescriptionList(occInspectionInfra.getOccLocationDescriptionList());
      inspectionDatabase.getIntensityClassDao().insertIntensityClassList(occInspectionInfra.getIntensityClassList());
      inspectionDatabase.getMunicipalityDao().insertMunicipalityList(occInspectionInfra.getMunicipalityList());
    });
  }

  public UploadDTO getUploadDTO(Integer inspectionId) {
    UploadDTO uploadDTO = inspectionDatabase.runInTransaction(() -> {
      List<OccInspectedSpace> occInspectedSpaceList = inspectionDatabase.getOccInspectedSpaceDao().selectAllOccInspectedSpaceList(inspectionId);
      List<OccInspectedSpaceDTO> occInspectedSpaceDTOList = new ArrayList<>();
      for (OccInspectedSpace occInspectedSpace : occInspectedSpaceList) {
        String inspectedSpaceId = occInspectedSpace.getInspectedSpaceId();
        List<OccInspectedSpaceElement> occInspectedSpaceElementList = inspectionDatabase.getOccInspectedSpaceElementDao().selectOccInspectedSpaceElementListByInspectedSpaceId(inspectedSpaceId);
        List<OccInspectedSpaceElementDTO> occInspectedSpaceElementDTOList = new ArrayList<>();
        for (OccInspectedSpaceElement occInspectedSpaceElement : occInspectedSpaceElementList) {
          String inspectedSpaceElementId = occInspectedSpaceElement.getInspectedSpaceElementId();
          List<PhotoDto> photoDtoList = new ArrayList<>();
          List<PhotoDoc> photoDocList = inspectionDatabase.getPhotoDocDao().selectAllPhotoDocListByOccInspectedSpaceTypeElementId(inspectedSpaceElementId);
          for (PhotoDoc photoDoc : photoDocList) {
            String blobBytesId = photoDoc.getBlobBytesId();
            BlobBytes blobBytes = inspectionDatabase.getBlobBytesDao().selectBlobByteById(blobBytesId);
            if (blobBytes != null) {
              inspectionDatabase.getBlobBytesDao().deleteBlobByte(blobBytes);
            }
            inspectionDatabase.getPhotoDocDao().deletePhotoDoc(photoDoc);
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
          inspectionDatabase.getOccInspectedSpaceElementDao().deleteOccInspectedSpaceElement(occInspectedSpaceElement);
          if (!isNumeric(inspectedSpaceElementId)) {
            occInspectedSpaceElement.setInspectedSpaceElementId("-1");
            occInspectedSpaceElement.setInspectedSpaceId("-1");
          }
          occInspectedSpaceElementDTOList.add(new OccInspectedSpaceElementDTO(occInspectedSpaceElement, photoDtoList));
        }
        inspectionDatabase.getOccInspectedSpaceDao().deleteOccInspectedSpace(occInspectedSpace);
        if (!isNumeric(inspectedSpaceId)) {
          occInspectedSpace.setInspectedSpaceId("-1");
        }
        occInspectedSpaceDTOList.add(new OccInspectedSpaceDTO(occInspectedSpace, occInspectedSpaceElementDTOList));
      }
      return new UploadDTO(inspectionId, occInspectedSpaceDTOList);
    });

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
