package com.cnf.service.api;

import static com.cnf.utils.RequestConstants.INSPECTION_CODE_ELEMENT_GUIDE_LIST_PATH;
import static com.cnf.utils.RequestConstants.INSPECTION_CODE_ELEMENT_LIST_PATH;
import static com.cnf.utils.RequestConstants.INSPECTION_CODE_SOURCE_LIST_PATH;
import static com.cnf.utils.RequestConstants.INSPECTION_INFO_PATH;
import static com.cnf.utils.RequestConstants.INSPECTION_INTENSITY_CLASS_LIST_PATH;
import static com.cnf.utils.RequestConstants.INSPECTION_OCC_CHECKLIST_SPACE_TYPE_ELEMENT_LIST_PATH;
import static com.cnf.utils.RequestConstants.INSPECTION_OCC_CHECKLIST_SPACE_TYPE_LIST_PATH;
import static com.cnf.utils.RequestConstants.INSPECTION_OCC_LOCATION_DESCRIPTION_LIST_PATH;
import static com.cnf.utils.RequestConstants.INSPECTION_OCC_SPACE_TYPE_LIST_PATH;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.room.Room;

import com.cnf.db.InspectionDatabase;
import com.cnf.domain.BlobBytes;
import com.cnf.domain.CodeElement;
import com.cnf.domain.CodeElementGuide;
import com.cnf.domain.CodeSource;
import com.cnf.domain.IntensityClass;
import com.cnf.domain.OccChecklistSpaceType;
import com.cnf.domain.OccChecklistSpaceTypeElement;
import com.cnf.domain.OccChecklistSpaceTypeElementHeavyDetails;
import com.cnf.domain.OccChecklistSpaceTypeHeavy;
import com.cnf.domain.OccInspectedSpace;
import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.domain.OccInspectedSpaceElementHeavy;
import com.cnf.domain.OccInspectedSpaceElementPhotoDoc;
import com.cnf.domain.OccInspectedSpaceHeavy;
import com.cnf.domain.OccLocationDescription;
import com.cnf.domain.OccSpaceType;
import com.cnf.domain.PhotoDoc;
import com.cnf.dto.InspectionTaskDTO;
import com.cnf.dto.OccInspectedSpaceDTO;
import com.cnf.dto.OccInspectedSpaceElementDTO;
import com.cnf.dto.PhotoDto;
import com.cnf.dto.UploadDTO;
import com.cnf.utils.RequestUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class InspectionActivityService {

    private static InspectionActivityService INSTANCE = null;

    private InspectionDatabase inspection_database;

    private InspectionActivityService(Context context) {
        this.inspection_database = Room.databaseBuilder(context, InspectionDatabase.class, "inspection_database").build();
    }

    public static InspectionActivityService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new InspectionActivityService(context);
        }
        return INSTANCE;
    }

//    public List<CodeSource> getCodeSourceList() {
//        List<CodeSource> codeSourceList = this.inspection_database.getCodeSourceDao().selectAllCodeSources();
//        if (codeSourceList != null && codeSourceList.size() != 0) {
//            System.out.println("Local Code Source : " + codeSourceList);
//            return codeSourceList;
//        }
//        return new ArrayList<>();
//    }

//    public List<OccChecklistSpaceType> getOccChecklistSpaceTypeList() {
//        List<OccChecklistSpaceType> occChecklistSpaceTypeList = this.inspection_database.getOccChecklistSpaceTypeDao().selectAllOccChecklistSpaceTypeList();
//        if (occChecklistSpaceTypeList != null && occChecklistSpaceTypeList.size() != 0) {
//            System.out.println("Local occChecklistSpaceTypeList : " + occChecklistSpaceTypeList);
//            return occChecklistSpaceTypeList;
//        }
//        return new ArrayList<>();
//    }

    public List<OccChecklistSpaceTypeHeavy> getOccChecklistSpaceTypeHeavyList() {
        List<OccChecklistSpaceTypeHeavy> occChecklistSpaceTypeHeavyList = this.inspection_database.getOccChecklistSpaceTypeDao().selectAllOccChecklistSpaceTypeHeavyList();
        if (occChecklistSpaceTypeHeavyList != null && occChecklistSpaceTypeHeavyList.size() != 0) {
            System.out.println("Local occChecklistSpaceTypeList : " + occChecklistSpaceTypeHeavyList);
            return occChecklistSpaceTypeHeavyList;
        }
        return new ArrayList<>();
    }

    public List<OccChecklistSpaceTypeElementHeavyDetails> getOccChecklistSpaceTypeElementHeavyDetailsList(Integer CSTId) {
        List<OccChecklistSpaceTypeElementHeavyDetails> occChecklistSpaceTypeElementHeavyDetailsList = this.inspection_database.getOccChecklistSpaceTypeElementDao().selectAllOccChecklistSpaceTypeElementListDetailsByCSTId(CSTId);
        if (occChecklistSpaceTypeElementHeavyDetailsList != null && occChecklistSpaceTypeElementHeavyDetailsList.size() != 0) {
            System.out.println("Local occChecklistSpaceTypeList : " + occChecklistSpaceTypeElementHeavyDetailsList);
            return occChecklistSpaceTypeElementHeavyDetailsList;
        }
        return new ArrayList<>();
    }

//    public List<OccLocationDescription> getOccLocationDescriptionList() {
//        List<OccLocationDescription> occLocationDescriptionList = this.inspection_database.getOccLocationDescriptionDao().selectAllOccLocationDescriptionList();
//        if (occLocationDescriptionList != null && occLocationDescriptionList.size() != 0) {
//            System.out.println("Local occLocationDescriptionList" + occLocationDescriptionList);
//            return occLocationDescriptionList;
//        }
//        return new ArrayList<>();
//    }

//    public List<OccInspectedSpace> getOccInspectedSpaceList() {
//        List<OccInspectedSpace> occInspectedSpaceList = this.inspection_database.getOccInspectedSpaceDao().selectAllOccInspectedSpaceList();
//        if (occInspectedSpaceList != null && occInspectedSpaceList.size() != 0) {
//            System.out.println("Local occInspectedSpaces" + occInspectedSpaceList);
//            return occInspectedSpaceList;
//        }
//        System.out.println(occInspectedSpaceList);
//        return new ArrayList<>();
//    }


    public List<OccInspectedSpaceHeavy> getOccInspectedSpaceHeavyList() {
        List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList = this.inspection_database.getOccInspectedSpaceDao().selectAllOccInspectedSpaceHeavyList();
        if (occInspectedSpaceHeavyList != null && occInspectedSpaceHeavyList.size() != 0) {
            System.out.println("Local occInspectedSpaces" + occInspectedSpaceHeavyList);
            return occInspectedSpaceHeavyList;
        }
        System.out.println(occInspectedSpaceHeavyList);
        return new ArrayList<>();
    }

    public List<OccInspectedSpaceHeavy> getOccInspectedSpaceHeavyListByInspectionId(int inspectionId) {
        List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList = this.inspection_database.getOccInspectedSpaceDao().selectAllOccInspectedSpaceHeavyListByInspectionId(inspectionId);
        if (occInspectedSpaceHeavyList != null && occInspectedSpaceHeavyList.size() != 0) {
            System.out.println("Local occInspectedSpaces" + occInspectedSpaceHeavyList);
            return occInspectedSpaceHeavyList;
        }
        System.out.println(occInspectedSpaceHeavyList);
        return new ArrayList<>();
    }


    public OccChecklistSpaceTypeHeavy getOccChecklistSpaceTypeHeavy(int CSTId) {
        OccChecklistSpaceTypeHeavy occChecklistSpaceTypeHeavy = this.inspection_database.getOccChecklistSpaceTypeDao().selectOccChecklistSpaceTypeHeavy(CSTId);
        return occChecklistSpaceTypeHeavy;
    }


    public long addBlobBytes(BlobBytes blobBytes) {
        return this.inspection_database.getBlobBytesDao().insertBlobBytes(blobBytes);
    }

    public long addPhotoDoc(PhotoDoc photoDoc) {
        return this.inspection_database.getPhotoDocDao().insertPhotoDoc(photoDoc);
    }

    public long addOccInspectedSpaceElementPhotoDoc(OccInspectedSpaceElementPhotoDoc occInspectedSpaceElementPhotoDoc) {
        return this.inspection_database.getOccInspectedSpaceElementPhotoDocDao().insertOccInspectedSpaceElementPhotoDoc(occInspectedSpaceElementPhotoDoc);
    }


    public boolean addInspectedSpace(OccInspectedSpace occInspectedSpace) {
        this.inspection_database.getOccInspectedSpaceDao().insertInspectedSpace(occInspectedSpace);
        return true;
    }

    /**
     * To get inspection list from sqlite. If there is no data in sqlite, fetch data from remote api
     *
     * @param token login authorization token
     * @return
     * @throws IOException
     */
    public List<InspectionTaskDTO> getInspectionList(String token) throws IOException {
        List<InspectionTaskDTO> inspectionTaskDTOList = this.inspection_database.getInspectionTaskDao().selectAllInspectionTasks();
        if (token == null) {
            return inspectionTaskDTOList == null ? new ArrayList<>() : inspectionTaskDTOList;
        }
        if (inspectionTaskDTOList != null && inspectionTaskDTOList.size() != 0) {
            Log.d("TAG", String.format("Local Inspection Task List: %s", inspectionTaskDTOList));
            return inspectionTaskDTOList;
        }
        String body = RequestUtils.sendGetRequest(token, INSPECTION_INFO_PATH);
        if (body == null || body.isEmpty()) {
            return new ArrayList<>();
        }
        inspectionTaskDTOList = new Gson().fromJson(body, new TypeToken<ArrayList<InspectionTaskDTO>>() {
        }.getType());
        this.inspection_database.getInspectionTaskDao().insertInspectionTasks(inspectionTaskDTOList);
        Log.d("TAG", String.format("Server Inspection Task List: %s", inspectionTaskDTOList));
        return inspectionTaskDTOList;
    }


    /**
     * To get code source list from sqlite. If there is no data in sqlite, fetch data from remote api
     *
     * @param token
     * @return
     * @throws IOException
     */
    public List<CodeSource> getCodeSourceList(String token) throws IOException {
        List<CodeSource> codeSourceList = this.inspection_database.getCodeSourceDao().selectAllCodeSources();
        if (token == null) {
            return codeSourceList == null ? new ArrayList<>() : codeSourceList;
        }
        if (codeSourceList != null && codeSourceList.size() != 0) {
            Log.d("TAG", String.format("Local Code Source List: %s", codeSourceList));
            return codeSourceList;
        }
        String body = RequestUtils.sendGetRequest(token, INSPECTION_CODE_SOURCE_LIST_PATH);
        if (body == null || body.isEmpty()) {
            return new ArrayList<>();
        }
        codeSourceList = new Gson().fromJson(body, new TypeToken<ArrayList<CodeSource>>() {
        }.getType());
        this.inspection_database.getCodeSourceDao().insertCodeSources(codeSourceList);
        Log.d("TAG", String.format("Server Code Source List: %s", codeSourceList));
        return codeSourceList;
    }


    /**
     * To get code element list from sqlite. If there is no data in sqlite, fetch data from remote api
     *
     * @param token
     * @return
     * @throws IOException
     */
    public List<CodeElement> getCodeElementList(String token) throws IOException {
        List<CodeElement> codeElementList = this.inspection_database.getCodeElementDao().selectCodeElements();
        if (token == null) {
            return codeElementList == null ? new ArrayList<>() : codeElementList;
        }
        if (codeElementList != null && codeElementList.size() != 0) {
            Log.d("TAG", String.format("Local Code Element List: %s", codeElementList));
            return codeElementList;
        }
        String body = RequestUtils.sendGetRequest(token, INSPECTION_CODE_ELEMENT_LIST_PATH);
        if (body == null || body.isEmpty()) {
            return new ArrayList<>();
        }
        codeElementList = new Gson().fromJson(body, new TypeToken<ArrayList<CodeElement>>() {
        }.getType());
        this.inspection_database.getCodeElementDao().insertCodeElements(codeElementList);
        Log.d("TAG", String.format("Server Code Element List: %s", codeElementList));
        return codeElementList;
    }


    /**
     * To get code element guide list from sqlite. If there is no data in sqlite, fetch data from remote api
     *
     * @param token
     * @return
     * @throws IOException
     */
    public List<CodeElementGuide> getCodeElementGuideList(String token) throws IOException {
        List<CodeElementGuide> codeElementGuideList = this.inspection_database.getCodeElementGuideDao().selectCodeElementGuides();
        if (token == null) {
            return codeElementGuideList == null ? new ArrayList<>() : codeElementGuideList;
        }
        if (codeElementGuideList != null && codeElementGuideList.size() != 0) {
            Log.d("TAG", String.format("Local Code Element Guide List: %s", codeElementGuideList));
            return codeElementGuideList;
        }
        String body = RequestUtils.sendGetRequest(token, INSPECTION_CODE_ELEMENT_GUIDE_LIST_PATH);
        if (body == null || body.isEmpty()) {
            return new ArrayList<>();
        }
        codeElementGuideList = new Gson().fromJson(body, new TypeToken<ArrayList<CodeElementGuide>>() {
        }.getType());
        this.inspection_database.getCodeElementGuideDao().insertCodeElementGuides(codeElementGuideList);
        Log.d("TAG", String.format("Server Code Element Guide List: %s", codeElementGuideList));
        return codeElementGuideList;
    }

    /**
     * To get Occ Checklist Space type list from sqlite. If there is no data in sqlite, fetch data from remote api
     *
     * @param token
     * @return
     * @throws IOException
     */
    public List<OccChecklistSpaceType> getOccChecklistSpaceTypeList(String token) throws IOException {
        List<OccChecklistSpaceType> occChecklistSpaceTypeList = this.inspection_database.getOccChecklistSpaceTypeDao().selectAllOccChecklistSpaceTypeList();
        if (token == null) {
            return occChecklistSpaceTypeList == null ? new ArrayList<>() : occChecklistSpaceTypeList;
        }
        if (occChecklistSpaceTypeList != null && occChecklistSpaceTypeList.size() != 0) {
            Log.d("TAG", String.format("Local Occ Checklist Space Type List: %s", occChecklistSpaceTypeList));
            return occChecklistSpaceTypeList;
        }
        String body = RequestUtils.sendGetRequest(token, INSPECTION_OCC_CHECKLIST_SPACE_TYPE_LIST_PATH);
        if (body == null || body.isEmpty()) {
            return new ArrayList<>();
        }
        occChecklistSpaceTypeList = new Gson().fromJson(body, new TypeToken<ArrayList<OccChecklistSpaceType>>() {
        }.getType());
        this.inspection_database.getOccChecklistSpaceTypeDao().insertOccChecklistSpaceTypeList(occChecklistSpaceTypeList);
        Log.d("TAG", String.format("Server Occ Checklist Space Type List: %s", occChecklistSpaceTypeList));
        return occChecklistSpaceTypeList;
    }

    /**
     * To get Occ Checklist Space Type Element list from sqlite. If there is no data in sqlite, fetch data from remote api
     *
     * @param token
     * @return
     * @throws IOException
     */
    public List<OccChecklistSpaceTypeElement> getOccChecklistSpaceTypeElementList(String token) throws IOException {
        List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList = this.inspection_database.getOccChecklistSpaceTypeElementDao().selectAllOccChecklistSpaceTypeElementList();
        if (token == null) {
            return occChecklistSpaceTypeElementList == null ? new ArrayList<>() : occChecklistSpaceTypeElementList;
        }
        if (occChecklistSpaceTypeElementList != null && occChecklistSpaceTypeElementList.size() != 0) {
            Log.d("TAG", String.format("Local Occ Checklist Space Type Element List: %s", occChecklistSpaceTypeElementList));
            return occChecklistSpaceTypeElementList;
        }
        String body = RequestUtils.sendGetRequest(token, INSPECTION_OCC_CHECKLIST_SPACE_TYPE_ELEMENT_LIST_PATH);
        if (body == null || body.isEmpty()) {
            return new ArrayList<>();
        }
        occChecklistSpaceTypeElementList = new Gson().fromJson(body, new TypeToken<ArrayList<OccChecklistSpaceTypeElement>>() {
        }.getType());
        this.inspection_database.getOccChecklistSpaceTypeElementDao().insertOccChecklistSpaceTypeElementList(occChecklistSpaceTypeElementList);
        Log.d("TAG", String.format("Server Occ Checklist Space Type Element List: %s", occChecklistSpaceTypeElementList));
        return occChecklistSpaceTypeElementList;
    }

    /**
     * To get Occ Space Type list from sqlite. If there is no data in sqlite, fetch data from remote api
     *
     * @param token
     * @return
     * @throws IOException
     */
    public List<OccSpaceType> getOccSpaceTypeList(String token) throws IOException {
        List<OccSpaceType> occSpaceTypeList = this.inspection_database.getOccSpaceTypeDao().selectAllOccSpaceTypeList();
        if (token == null) {
            return occSpaceTypeList == null ? new ArrayList<>() : occSpaceTypeList;
        }
        if (occSpaceTypeList != null && occSpaceTypeList.size() != 0) {
            Log.d("TAG", String.format("Local Occ Space Type List: %s", occSpaceTypeList));
            return occSpaceTypeList;
        }
        String body = RequestUtils.sendGetRequest(token, INSPECTION_OCC_SPACE_TYPE_LIST_PATH);
        if (body == null || body.isEmpty()) {
            return new ArrayList<>();
        }
        occSpaceTypeList = new Gson().fromJson(body, new TypeToken<ArrayList<OccSpaceType>>() {
        }.getType());
        this.inspection_database.getOccSpaceTypeDao().insertOccSpaceTypeList(occSpaceTypeList);
        Log.d("TAG", String.format("Server Occ Space Type List: %s", occSpaceTypeList));
        return occSpaceTypeList;
    }

    /**
     * To get Occ Location Description list from sqlite. If there is no data in sqlite, fetch data from remote api
     *
     * @param token
     * @return
     * @throws IOException
     */
    public List<OccLocationDescription> getOccLocationDescriptionList(String token) throws IOException {
        List<OccLocationDescription> occLocationDescriptionList = this.inspection_database.getOccLocationDescriptionDao().selectAllOccLocationDescriptionList();
        if (token == null) {
            return occLocationDescriptionList == null ? new ArrayList<>() : occLocationDescriptionList;
        }

        if (occLocationDescriptionList != null && occLocationDescriptionList.size() != 0) {
            Log.d("TAG", String.format("Local Occ Location Description List: %s", occLocationDescriptionList));
            return occLocationDescriptionList;
        }
        String body = RequestUtils.sendGetRequest(token, INSPECTION_OCC_LOCATION_DESCRIPTION_LIST_PATH);
        if (body == null || body.isEmpty()) {
            return new ArrayList<>();
        }
        occLocationDescriptionList = new Gson().fromJson(body, new TypeToken<ArrayList<OccLocationDescription>>() {
        }.getType());
        this.inspection_database.getOccLocationDescriptionDao().insertOccLocationDescriptionList(occLocationDescriptionList);
        Log.d("TAG", String.format("Server Occ Location Description List: %s", occLocationDescriptionList));
        return occLocationDescriptionList;
    }

    /**
     * To getIntensityClass list from sqlite. If there is no data in sqlite, fetch data from remote api
     *
     * @param token
     * @return
     * @throws IOException
     */
    public List<IntensityClass> getIntensityClassList(String token) throws IOException {
        List<IntensityClass> intensityClassList = this.inspection_database.getIntensityClassDao().selectAllIntensityClassList();
        if (token == null) {
            return intensityClassList == null ? new ArrayList<>() : intensityClassList;
        }

        if (intensityClassList != null && intensityClassList.size() != 0) {
            Log.d("TAG", String.format("Local Intensity Class List: %s", intensityClassList));
            return intensityClassList;
        }
        String body = RequestUtils.sendGetRequest(token, INSPECTION_INTENSITY_CLASS_LIST_PATH);
        if (body == null || body.isEmpty()) {
            return new ArrayList<>();
        }
        intensityClassList = new Gson().fromJson(body, new TypeToken<ArrayList<IntensityClass>>() {
        }.getType());
        this.inspection_database.getIntensityClassDao().insertIntensityClassList(intensityClassList);
        Log.d("TAG", String.format("Server Intensity Class List: %s", intensityClassList));
        return intensityClassList;
    }

    /**
     * To delete inspection list from sqlite.
     */
    public void deleteAllInspectionList() {
        this.inspection_database.getInspectionTaskDao().deleteAllInspectionTasks();
    }


    private void deleteAllCodeSourceList() {
        this.inspection_database.getCodeSourceDao().deleteAllCodeSources();
    }

    private void deleteAllCodeElementList() {
        this.inspection_database.getCodeElementDao().deleteAllCodeElements();
    }

    private void deleteAllCodeElementGuideList() {
        this.inspection_database.getCodeElementGuideDao().deleteAllCodeElementGuides();
    }

    private void deleteAllOccChecklistSpaceTypeList() {
        this.inspection_database.getOccChecklistSpaceTypeDao().deleteAllOccChecklistSpaceTypeList();
    }

    private void deleteAllOccChecklistSpaceTypeElementList() {
        this.inspection_database.getOccChecklistSpaceTypeElementDao().deleteAllOccChecklistSpaceTypeElementList();
    }

    private void deleteAllOccSpaceTypeList() {
        this.inspection_database.getOccSpaceTypeDao().deleteAllOccSpaceTypeList();
    }

    private void deleteAllOccLocationDescriptionList() {
        this.inspection_database.getOccLocationDescriptionDao().deleteAllOccLocationDescriptionList();
    }

    private void deleteAllIntensityClassList() {
        this.inspection_database.getIntensityClassDao().deleteAllIntensityClassList();
    }

    public void deleteALL() {
        this.deleteAllInspectionList();
        this.deleteAllCodeSourceList();
        this.deleteAllCodeElementList();
        this.deleteAllCodeElementGuideList();
        this.deleteAllOccChecklistSpaceTypeList();
        this.deleteAllOccChecklistSpaceTypeElementList();
        this.deleteAllOccSpaceTypeList();
        this.deleteAllOccLocationDescriptionList();
        this.deleteAllIntensityClassList();
    }

    public boolean getAll(String token) {
        try {
            this.getInspectionList(token);
            this.getCodeSourceList(token);
            this.getCodeElementList(token);
            this.getCodeElementGuideList(token);
            this.getOccChecklistSpaceTypeList(token);
            this.getOccChecklistSpaceTypeElementList(token);
            this.getOccSpaceTypeList(token);
            this.getOccLocationDescriptionList(token);
            this.getIntensityClassList(token);
            return true;
        } catch (IOException e) {
            Log.e("TAG", String.format("Date: %s, " + e, LocalDateTime.now()));
        }
        return false;
    }


    public UploadDTO getUploadDTO(Integer inspectionId) {

        List<OccInspectedSpace> occInspectedSpaceList = this.inspection_database.getOccInspectedSpaceDao().selectAllOccInspectedSpaceList(inspectionId);
        List<OccInspectedSpaceDTO> occInspectedSpaceDTOList = new ArrayList<>();
        for (OccInspectedSpace occInspectedSpace : occInspectedSpaceList) {
            List<OccInspectedSpaceElement> occInspectedSpaceElementList = this.inspection_database.getOccInspectedSpaceElementDao().selectAllOccInspectedSpaceElementList(occInspectedSpace.getInspectedspaceid());
            List<OccInspectedSpaceElementDTO> occInspectedSpaceElementDTOList = new ArrayList<>();
            for (int i = 0; i < occInspectedSpaceElementList.size(); i++) {
                OccInspectedSpaceElement occInspectedSpaceElement = occInspectedSpaceElementList.get(i);
                List<BlobBytes> blobBytesList = this.inspection_database.getBlobBytesDao().selectBlobBytesByInspectedElementId(occInspectedSpaceElement.getInspectedspaceelementid());
                List<PhotoDto> photoDtoList = new ArrayList<>();
                for (BlobBytes blobBytes : blobBytesList) {
                    PhotoDoc photoDoc = this.inspection_database.getPhotoDocDao().selectOnePhotoDoc(blobBytes.getBytesid());
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
