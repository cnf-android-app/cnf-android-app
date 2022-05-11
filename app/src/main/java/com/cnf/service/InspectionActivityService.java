package com.cnf.service;

import static com.cnf.utils.RequestConstants.INSPECTION_CODE_ELEMENT_LIST_PATH;
import static com.cnf.utils.RequestConstants.INSPECTION_CODE_SOURCE_LIST_PATH;
import static com.cnf.utils.RequestConstants.INSPECTION_INFO_PATH;
import static com.cnf.utils.RequestConstants.INSPECTION_OCC_CHECKLIST_SPACE_TYPE_ELEMENT_LIST_PATH;
import static com.cnf.utils.RequestConstants.INSPECTION_OCC_CHECKLIST_SPACE_TYPE_LIST_PATH;
import static com.cnf.utils.RequestConstants.INSPECTION_OCC_LOCATION_DESCRIPTION_LIST_PATH;
import static com.cnf.utils.RequestConstants.INSPECTION_OCC_SPACE_TYPE_LIST_PATH;

import android.content.Context;

import androidx.room.Room;

import com.cnf.db.InspectionDatabase;
import com.cnf.domain.BlobBytes;
import com.cnf.domain.CodeElement;
import com.cnf.domain.CodeSource;
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
import com.cnf.utils.RequestUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class InspectionActivityService {

    private Context context;
    private InspectionDatabase inspection_database;


    public InspectionActivityService(Context context) {
        this.context = context;
        this.inspection_database = Room.databaseBuilder(context, InspectionDatabase.class, "inspection_database").build();
    }

    public List<InspectionTaskDTO> getInspectionList(String token) {
        List<InspectionTaskDTO> inspectionTaskDTOList = this.inspection_database.getInspectionTaskDao().selectAllInspectionTasks();
        if (inspectionTaskDTOList != null && inspectionTaskDTOList.size() != 0) {
            System.out.println("Local Inspection Task List:" + inspectionTaskDTOList);
            return inspectionTaskDTOList;
        }
        String body = RequestUtils.sendGetRequest(token, INSPECTION_INFO_PATH);
        if (body == null || body.isEmpty()) {
            return null;
        }
        inspectionTaskDTOList = new Gson().fromJson(body, new TypeToken<ArrayList<InspectionTaskDTO>>() {
        }.getType());
        this.inspection_database.getInspectionTaskDao().insertInspectionTasks(inspectionTaskDTOList);
        System.out.println("Server Inspection Task List:" + inspectionTaskDTOList);
        return inspectionTaskDTOList;
    }

    public List<CodeSource> getCodeSourceList() {
        List<CodeSource> codeSourceList = this.inspection_database.getCodeSourceDao().selectAllCodeSources();
        if (codeSourceList != null && codeSourceList.size() != 0) {
            System.out.println("Local Code Source : " + codeSourceList);
            return codeSourceList;
        }
        return null;
    }

    public List<OccChecklistSpaceType> getOccChecklistSpaceTypeList() {
        List<OccChecklistSpaceType> occChecklistSpaceTypeList = this.inspection_database.getOccChecklistSpaceTypeDao().selectAllOccChecklistSpaceTypeList();
        if (occChecklistSpaceTypeList != null && occChecklistSpaceTypeList.size() != 0) {
            System.out.println("Local occChecklistSpaceTypeList : " + occChecklistSpaceTypeList);
            return occChecklistSpaceTypeList;
        }
        return null;
    }

    public List<OccChecklistSpaceTypeHeavy> getOccChecklistSpaceTypeHeavyList() {
        List<OccChecklistSpaceTypeHeavy> occChecklistSpaceTypeHeavyList = this.inspection_database.getOccChecklistSpaceTypeDao().selectAllOccChecklistSpaceTypeHeavyList();
        if (occChecklistSpaceTypeHeavyList != null && occChecklistSpaceTypeHeavyList.size() != 0) {
            System.out.println("Local occChecklistSpaceTypeList : " + occChecklistSpaceTypeHeavyList);
            return occChecklistSpaceTypeHeavyList;
        }
        return null;
    }

    public List<OccChecklistSpaceTypeElementHeavyDetails> getOccChecklistSpaceTypeElementHeavyDetailsList(Integer CSTId) {
        List<OccChecklistSpaceTypeElementHeavyDetails> occChecklistSpaceTypeElementHeavyDetailsList = this.inspection_database.getOccChecklistSpaceTypeElementDao().selectAllOccChecklistSpaceTypeElementListDetailsByCSTId(CSTId);
        if (occChecklistSpaceTypeElementHeavyDetailsList != null && occChecklistSpaceTypeElementHeavyDetailsList.size() != 0) {
            System.out.println("Local occChecklistSpaceTypeList : " + occChecklistSpaceTypeElementHeavyDetailsList);
            return occChecklistSpaceTypeElementHeavyDetailsList;
        }
        return null;
    }

    public List<OccLocationDescription> getOccLocationDescriptionList() {
        List<OccLocationDescription> occLocationDescriptionList = this.inspection_database.getOccLocationDescriptionDao().selectAllOccLocationDescriptionList();
        if (occLocationDescriptionList != null && occLocationDescriptionList.size() != 0) {
            System.out.println("Local occLocationDescriptionList" + occLocationDescriptionList);
            return occLocationDescriptionList;
        }
        return null;
    }

    public List<OccInspectedSpace> getOccInspectedSpaceList() {
        List<OccInspectedSpace> occInspectedSpaceList = this.inspection_database.getOccInspectedSpaceDao().selectAllOccInspectedSpaceList();
        if (occInspectedSpaceList != null && occInspectedSpaceList.size() != 0) {
            System.out.println("Local occInspectedSpaces" + occInspectedSpaceList);
            return occInspectedSpaceList;
        }
        System.out.println(occInspectedSpaceList);
        return new ArrayList<>();
    }


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

    public List<CodeSource> getCodeSourceList(String token) {
        List<CodeSource> codeSourceList = this.inspection_database.getCodeSourceDao().selectAllCodeSources();
        if (codeSourceList != null && codeSourceList.size() != 0) {
            System.out.println("Local Code Source : " + codeSourceList);
            return codeSourceList;
        }
        String body = RequestUtils.sendGetRequest(token, INSPECTION_CODE_SOURCE_LIST_PATH);
        System.out.println(body);
        if (body == null || body.isEmpty()) {
            return null;
        }
        codeSourceList = new Gson().fromJson(body, new TypeToken<ArrayList<CodeSource>>() {
        }.getType());
        this.inspection_database.getCodeSourceDao().insertCodeSources(codeSourceList);
        System.out.println("Server Code Source : " + codeSourceList);
        return codeSourceList;
    }

    public List<CodeElement> getCodeElementList(String token) {
        List<CodeElement> codeElementList = this.inspection_database.getCodeElementDao().selectCodeElements();
        if (codeElementList != null && codeElementList.size() != 0) {
            System.out.println("Local Code Element : " + codeElementList);
            return codeElementList;
        }
        String body = RequestUtils.sendGetRequest(token, INSPECTION_CODE_ELEMENT_LIST_PATH);
        if (body == null || body.isEmpty()) {
            return null;
        }
        codeElementList = new Gson().fromJson(body, new TypeToken<ArrayList<CodeElement>>() {
        }.getType());
        this.inspection_database.getCodeElementDao().insertCodeElements(codeElementList);
        System.out.println("Server Code Element : " + codeElementList);
        return codeElementList;
    }

    public List<OccChecklistSpaceType> getOccChecklistSpaceTypeList(String token) {
        List<OccChecklistSpaceType> occChecklistSpaceTypeList = this.inspection_database.getOccChecklistSpaceTypeDao().selectAllOccChecklistSpaceTypeList();
        if (occChecklistSpaceTypeList != null && occChecklistSpaceTypeList.size() != 0) {
            System.out.println("Local occChecklistSpaceTypeList : " + occChecklistSpaceTypeList);
            return occChecklistSpaceTypeList;
        }
        String body = RequestUtils.sendGetRequest(token, INSPECTION_OCC_CHECKLIST_SPACE_TYPE_LIST_PATH);
        if (body == null || body.isEmpty()) {
            return null;
        }
        occChecklistSpaceTypeList = new Gson().fromJson(body, new TypeToken<ArrayList<OccChecklistSpaceType>>() {
        }.getType());
        this.inspection_database.getOccChecklistSpaceTypeDao().insertOccChecklistSpaceTypeList(occChecklistSpaceTypeList);
        System.out.println("Server Code Element : " + occChecklistSpaceTypeList);
        return occChecklistSpaceTypeList;
    }

    public List<OccChecklistSpaceTypeElement> getOccChecklistSpaceTypeElementList(String token) {
        List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList = this.inspection_database.getOccChecklistSpaceTypeElementDao().selectAllOccChecklistSpaceTypeElementList();
        if (occChecklistSpaceTypeElementList != null && occChecklistSpaceTypeElementList.size() != 0) {
            System.out.println("Local occChecklistSpaceTypeList : " + occChecklistSpaceTypeElementList);
            return occChecklistSpaceTypeElementList;
        }
        String body = RequestUtils.sendGetRequest(token, INSPECTION_OCC_CHECKLIST_SPACE_TYPE_ELEMENT_LIST_PATH);
        if (body == null || body.isEmpty()) {
            return null;
        }
        occChecklistSpaceTypeElementList = new Gson().fromJson(body, new TypeToken<ArrayList<OccChecklistSpaceTypeElement>>() {
        }.getType());
        this.inspection_database.getOccChecklistSpaceTypeElementDao().insertOccChecklistSpaceTypeElementList(occChecklistSpaceTypeElementList);
        System.out.println("Server Code Element  : " + occChecklistSpaceTypeElementList);
        return occChecklistSpaceTypeElementList;
    }

    public List<OccSpaceType> getOccSpaceTypeList(String token) {
        List<OccSpaceType> occSpaceTypeList = this.inspection_database.getOccSpaceTypeDao().selectAllOccSpaceTypeList();
        if (occSpaceTypeList != null && occSpaceTypeList.size() != 0) {
            System.out.println("Local occChecklistSpaceTypeList : " + occSpaceTypeList);
            return occSpaceTypeList;
        }
        String body = RequestUtils.sendGetRequest(token, INSPECTION_OCC_SPACE_TYPE_LIST_PATH);
        if (body == null || body.isEmpty()) {
            return null;
        }
        occSpaceTypeList = new Gson().fromJson(body, new TypeToken<ArrayList<OccSpaceType>>() {
        }.getType());
        this.inspection_database.getOccSpaceTypeDao().insertOccSpaceTypeList(occSpaceTypeList);
        System.out.println("Server occChecklistSpaceTypeList : " + occSpaceTypeList);
        return occSpaceTypeList;
    }

    public List<OccLocationDescription> getOccLocationDescriptionList(String token) {
        List<OccLocationDescription> occLocationDescriptionList = this.inspection_database.getOccLocationDescriptionDao().selectAllOccLocationDescriptionList();
        if (occLocationDescriptionList != null && occLocationDescriptionList.size() != 0) {
            System.out.println("Local occLocationDescriptionList" + occLocationDescriptionList);
            return occLocationDescriptionList;
        }
        String body = RequestUtils.sendGetRequest(token, INSPECTION_OCC_LOCATION_DESCRIPTION_LIST_PATH);
        if (body == null || body.isEmpty()) {
            return null;
        }
        occLocationDescriptionList = new Gson().fromJson(body, new TypeToken<ArrayList<OccLocationDescription>>() {
        }.getType());
        this.inspection_database.getOccLocationDescriptionDao().insertOccLocationDescriptionList(occLocationDescriptionList);
        System.out.println("Server occLocationDescriptionList : " + occLocationDescriptionList);
        return occLocationDescriptionList;
    }

    public OccChecklistSpaceTypeHeavy getOccChecklistSpaceTypeHeavy(int CSTId) {
        OccChecklistSpaceTypeHeavy occChecklistSpaceTypeHeavy = this.inspection_database.getOccChecklistSpaceTypeDao().selectOccChecklistSpaceTypeHeavy(CSTId);
        return occChecklistSpaceTypeHeavy;
    }


    public List<OccInspectedSpaceElement>  createDefaultAndAddOccInspectedSpaceElementList(int inspectedSpaceId) {
        List<OccInspectedSpaceElement> old_occInspectedSpaceElementList = this.inspection_database.getOccInspectedSpaceElementDao().selectAllOccInspectedSpaceElementList(inspectedSpaceId);
        if (old_occInspectedSpaceElementList == null || old_occInspectedSpaceElementList.size() == 0) {
            List<OccInspectedSpaceElement> occInspectedSpaceElementList = new ArrayList<>();
            OccInspectedSpace occInspectedSpace = this.inspection_database.getOccInspectedSpaceDao().selectOneOccInspectedSpaceById(inspectedSpaceId);
            List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList = this.inspection_database.getOccChecklistSpaceTypeElementDao().selectAllOccChecklistSpaceTypeElementListByCSTId(occInspectedSpace.getOccchecklistspacetype_chklstspctypid());
            for (OccChecklistSpaceTypeElement occChecklistSpaceTypeElement : occChecklistSpaceTypeElementList) {
                OccInspectedSpaceElement element = new OccInspectedSpaceElement(null, occInspectedSpace.getOcclocationdescription_descid(), null, null, null, null, inspectedSpaceId, null, occChecklistSpaceTypeElement.getSpaceelementid(), null, true);
                occInspectedSpaceElementList.add(element);
            }
            this.inspection_database.getOccInspectedSpaceElementDao().insertOccInspectedSpaceElementList(occInspectedSpaceElementList);
        }
        return old_occInspectedSpaceElementList;
    }

    public List<BlobBytes> getBlobBytesList() {
        return this.inspection_database.getBlobBytesDao().selectBlobBytes();
    }

    public List<OccInspectedSpaceElementHeavy> getOccInspectedSpaceElementHeavyList(int inspectedSpaceId) {
        return this.inspection_database.getOccInspectedSpaceElementDao().selectAllOccInspectedSpaceElementHeavyList(inspectedSpaceId);
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

    public void deleteAllInspectionList() {
        this.inspection_database.getInspectionTaskDao().deleteAllInspectionTasks();
    }

    public void deleteAllCodeSourceList() {
        this.inspection_database.getCodeSourceDao().deleteAllCodeSources();
    }

    public void deleteAllCodeElementList() {
        this.inspection_database.getCodeElementDao().deleteAllCodeElements();
    }

    public void deleteAllOccChecklistSpaceTypeList() {
        this.inspection_database.getOccChecklistSpaceTypeDao().deleteAllOccChecklistSpaceTypeList();
    }

    public void deleteAllOccChecklistSpaceTypeElementList() {
        this.inspection_database.getOccChecklistSpaceTypeElementDao().deleteAllOccChecklistSpaceTypeElementList();
    }

    public void deleteAllOccSpaceTypeList() {
        this.inspection_database.getOccSpaceTypeDao().deleteAllOccSpaceTypeList();
    }

    public void deleteAllOccLocationDescriptionList() {
        this.inspection_database.getOccLocationDescriptionDao().deleteAllOccLocationDescriptionList();
    }
}
