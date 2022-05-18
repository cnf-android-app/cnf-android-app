package com.cnf.service.occ;

import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;

import android.content.Context;

import androidx.room.Room;

import com.cnf.dao.PhotoDocDao;
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.BlobBytes;
import com.cnf.domain.CodeElement;
import com.cnf.domain.CodeElementGuide;
import com.cnf.domain.CodeSource;
import com.cnf.domain.IntensityClass;
import com.cnf.domain.OccChecklistSpaceTypeElement;
import com.cnf.domain.OccInspectableStatus;
import com.cnf.domain.OccInspectedSpace;
import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.domain.OccInspectedSpaceElementHeavy;
import com.cnf.domain.OccInspectionStatusEnum;
import com.cnf.domain.PhotoDoc;
import com.cnf.dto.OccInspectedSpaceDTO;
import com.cnf.dto.OccInspectedSpaceElementDTO;
import com.cnf.dto.PhotoDto;
import com.cnf.dto.UploadDTO;
import com.cnf.service.api.InspectionActivityService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OccInspectionSpaceElementService {

    private final static String FMT_CH = "ch.";
    private final static String FMT_SPACE = " ";
    private final static String FMT_PAREN_L = "(";
    private final static String FMT_PAREN_R = ")";
    private final static String FMT_DASH = "-";
    private final static String FMT_COLON = ":";
    private final static String FMT_SECTION_ENTITY = "§";

    private static OccInspectionSpaceElementService INSTANCE = null;
    private InspectionDatabase inspection_database;

    private OccInspectionSpaceElementService(Context context) {
        this.inspection_database = Room.databaseBuilder(context, InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();
    }

    public static OccInspectionSpaceElementService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new OccInspectionSpaceElementService(context);
        }
        return INSTANCE;
    }




    public List<IntensityClass> selectAllIntensityClassListBySchemaLabel(String schemaLabel) {
        if (schemaLabel == null || schemaLabel.length() == 0) {
            return new ArrayList<>();
        }
        return this.inspection_database.getIntensityClassDao().selectAllIntensityClassListBySchemaLabel(schemaLabel);
    }

    public OccInspectedSpaceElement inspectionAction_configureElementForNotInspected(OccInspectedSpaceElement oise, int userId)  {

        oise.setCompliancegrantedby_userid(null);
        oise.setCompliancegrantedts(null);
        oise.setLastinspectedts(null);
        oise.setLastinspectedby_userid(null);

        return oise;
    }

    public OccInspectedSpaceElement inspectionAction_configureElementForCompliance(OccInspectedSpaceElement oise, int userId) {
        oise.setCompliancegrantedby_userid(userId);
        oise.setCompliancegrantedts(LocalDateTime.now().toString());
        oise.setLastinspectedts(LocalDateTime.now().toString());
        oise.setLastinspectedby_userid(userId);
        return oise;
    }


    public OccInspectedSpaceElement inspectionAction_configureElementForInspectionNoCompliance(OccInspectedSpaceElement oise, int userId, boolean useDefFindOnFail) {


        oise.setCompliancegrantedby_userid(null);
        oise.setCompliancegrantedts(null);
        oise.setLastinspectedts(LocalDateTime.now().toString());
        oise.setLastinspectedby_userid(userId);

//        if(useDefFindOnFail){
//            StringBuilder sb = new StringBuilder();
//            if(oise.getInspectionNotes() != null){
//                sb.append(oise.getInspectionNotes());
//                sb.append(SPACE);
//            }
//            if(oise.getDefaultViolationDescription() != null){
//                sb.append(oise.getDefaultViolationDescription());
//            }
//            oise.setInspectionNotes(sb.toString());
//        }

        return oise;
    }

    public void updateOccInspectedSpaceElement(OccInspectedSpaceElement oise) {
        this.inspection_database.getOccInspectedSpaceElementDao().updateOccInspectedSpaceElementList(oise);
    }

    public List<BlobBytes> getInspectedPhotoBlobBytesList(int inspectedSpaceElementId) {
        return this.inspection_database.getBlobBytesDao().selectBlobBytesByInspectedElementId(inspectedSpaceElementId);
    }


    public List<OccInspectedSpaceElement> createDefaultOccInspectedSpaceElementList(int inspectedSpaceId) {
        List<OccInspectedSpaceElement> occInspectedSpaceElementList = this.inspection_database.getOccInspectedSpaceElementDao().selectAllOccInspectedSpaceElementList(inspectedSpaceId);
        if (occInspectedSpaceElementList == null || occInspectedSpaceElementList.size() == 0) {
            occInspectedSpaceElementList = new ArrayList<>();
            OccInspectedSpace occInspectedSpace = this.inspection_database.getOccInspectedSpaceDao().selectOneOccInspectedSpaceById(inspectedSpaceId);
            List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList = this.inspection_database.getOccChecklistSpaceTypeElementDao().selectAllOccChecklistSpaceTypeElementListByCSTId(occInspectedSpace.getOccchecklistspacetype_chklstspctypid());
            for (OccChecklistSpaceTypeElement occChecklistSpaceTypeElement : occChecklistSpaceTypeElementList) {
                OccInspectedSpaceElement element = new OccInspectedSpaceElement(null, occInspectedSpace.getOcclocationdescription_descid(), null, null, null, null, inspectedSpaceId, null, occChecklistSpaceTypeElement.getSpaceelementid(), null, true);
                occInspectedSpaceElementList.add(element);
            }
            this.inspection_database.getOccInspectedSpaceElementDao().insertOccInspectedSpaceElementList(occInspectedSpaceElementList);
        }
        return occInspectedSpaceElementList;
    }

    public Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> getOccInspectedSpaceElementHeavyMap(int inspectedSpaceId) {
        return this.inspection_database.getOccInspectedSpaceElementDao().selectAllOccInspectedSpaceElementHeavyMap(inspectedSpaceId);
    }


    public List<OccInspectedSpaceElementHeavy> getOccInspectedSpaceElementHeavyList(int inspectedSpaceId) {
        return this.inspection_database.getOccInspectedSpaceElementDao().selectAllOccInspectedSpaceElementHeavyList(inspectedSpaceId);
    }

    public List<OccInspectedSpaceElementHeavy> getOccInspectedSpaceElementHeavyList(int inspectedSpaceElementGuideId, int inspectedSpaceId) {
        return this.inspection_database.getOccInspectedSpaceElementDao().selectAllOccInspectedSpaceElementHeavyList(inspectedSpaceElementGuideId, inspectedSpaceId);
    }


    public boolean isAllInspectedSpaceElementComplete(List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList) {
        occInspectedSpaceElementHeavyList = configureOccInspectedSpaceElementListStatus(occInspectedSpaceElementHeavyList);
        for (OccInspectedSpaceElementHeavy o : occInspectedSpaceElementHeavyList) {
            if (!isInspectedSpaceElementPass(o)) {
                return false;
            }
        }
        return true;
    }

    public boolean isInspectedSpaceElementPass(OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
        OccInspectedSpaceElement e = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
        if (OccInspectionStatusEnum.PASS.equals(e.getStatus().getStatusEnum())) {
            return true;
        }
        return false;
    }

    public boolean isInspectedSpaceElementNotInspected(OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
        OccInspectedSpaceElement e = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
        if (OccInspectionStatusEnum.NOTINSPECTED.equals(e.getStatus().getStatusEnum())) {
            return true;
        }
        return false;
    }

    public boolean isInspectedSpaceElementViolation(OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
        OccInspectedSpaceElement e = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
        if (OccInspectionStatusEnum.VIOLATION.equals(e.getStatus().getStatusEnum())) {
            return true;
        }
        return false;
    }

    public Map<OccInspectionStatusEnum, List<OccInspectedSpaceElementHeavy>> getElementStatusMap(List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList) {
        Map<OccInspectionStatusEnum, List<OccInspectedSpaceElementHeavy>> elementStatusMap = new HashMap<>();
        elementStatusMap.put(OccInspectionStatusEnum.PASS, new ArrayList<>());
        elementStatusMap.put(OccInspectionStatusEnum.VIOLATION, new ArrayList<>());
        elementStatusMap.put(OccInspectionStatusEnum.NOTINSPECTED, new ArrayList<>());
        for (OccInspectedSpaceElementHeavy occInspectedSpaceHeavy : occInspectedSpaceElementHeavyList) {
            OccInspectedSpaceElement e = occInspectedSpaceHeavy.getOccInspectedSpaceElement();
            configureOccInspectedSpaceElementStatus(e);
            switch (e.getStatus().getStatusEnum()) {
                case VIOLATION:
                    elementStatusMap.get(OccInspectionStatusEnum.VIOLATION).add(occInspectedSpaceHeavy);
                    break;
                case NOTINSPECTED:
                    elementStatusMap.get(OccInspectionStatusEnum.NOTINSPECTED).add(occInspectedSpaceHeavy);
                    break;
                case PASS:
                    elementStatusMap.get(OccInspectionStatusEnum.PASS).add(occInspectedSpaceHeavy);
                    break;
            }
        }
        return elementStatusMap;
    }

    public List<OccInspectedSpaceElementHeavy> configureOccInspectedSpaceElementListStatus(List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList) {
        for (OccInspectedSpaceElementHeavy occInspectedSpaceHeavy : occInspectedSpaceElementHeavyList) {
            OccInspectedSpaceElement e = occInspectedSpaceHeavy.getOccInspectedSpaceElement();
            configureOccInspectedSpaceElementStatus(e);
        }
        return occInspectedSpaceElementHeavyList;
    }

    public OccInspectedSpaceElement configureOccInspectedSpaceElementStatus(OccInspectedSpaceElement occInspectedSpaceElement) {
        if (occInspectedSpaceElement == null) {
            return null;
        }
        if (occInspectedSpaceElement.getLastinspectedby_userid() != null && occInspectedSpaceElement.getCompliancegrantedts() == null) {
            occInspectedSpaceElement.setStatus(new OccInspectableStatus(OccInspectionStatusEnum.VIOLATION));
            return occInspectedSpaceElement;
        }
        if (occInspectedSpaceElement.getLastinspectedby_userid() != null && occInspectedSpaceElement.getCompliancegrantedts() != null) {
            occInspectedSpaceElement.setStatus(new OccInspectableStatus(OccInspectionStatusEnum.PASS));
            return occInspectedSpaceElement;
        }
        occInspectedSpaceElement.setStatus(new OccInspectableStatus(OccInspectionStatusEnum.NOTINSPECTED));
        return occInspectedSpaceElement;
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

    public String buildOrdinanceHeaderString(OccInspectedSpaceElementHeavy e) {
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

            // If we have a standard IPMC listing, the chapter and section
            // numbers are actually in the sub-section number, so just use that number
            // such as 302.1.1: Chapter 3, section 2, subsec 1, subsubsec 1
            // check if section number is in sub-section number
            if (checkForPureIRCRecursiveListing(ce)) {
                sb.append(FMT_SECTION_ENTITY);
                sb.append(FMT_SPACE);
                if (ce.getOrdsubsubsecnum() != null
                        && !ce.getOrdsubsubsecnum().equals("''")) {
                    // eg 302.1.1
                    sb.append(ce.getOrdsubsubsecnum());
                } else { // we don't have a recursive sub-sub sec number, so use sub-sec number
                    sb.append(ce.getOrdsubsecnum());
                    sb.append(FMT_COLON);
                    sb.append(FMT_SPACE);
                }
                // now insert the titles
                // NOTE we are not using chapter titles in the standard IRC
                // they are implied by the section and subsection titles

                if (ce.getOrdsectitle() != null) {
                    sb.append(ce.getOrdsectitle());
                    sb.append(FMT_SPACE);
                    sb.append(FMT_DASH);
                    sb.append(FMT_SPACE);
                }
                if (ce.getOrdsubsectitle() != null) {
                    sb.append(ce.getOrdsubsectitle());
                }
            } else { // We do not have a pure IRC, so piece the header together straight across
                // we have a non-zero chapter number
                if (ce.getOrdchapterno() != 0) {
                    sb.append(FMT_CH);
                    sb.append(ce.getOrdchapterno());
                    if (ce.getOrdchaptertitle() != null && !ce.getOrdchaptertitle().equals("''")) {
                        sb.append(FMT_COLON);
                        sb.append(ce.getOrdchaptertitle());
                    }
                }
                // setup our section
                // if we have a section number, put it in
                if (ce.getOrdsecnum() != null && !ce.getOrdsecnum().equals("''")) {
                    sb.append(FMT_SPACE);
                    sb.append(FMT_SECTION_ENTITY);
                    sb.append(FMT_SPACE);
                    sb.append(ce.getOrdsecnum());
                    // if we have a sub sec number, it follows in parents (a)
                    if (ce.getOrdsubsecnum() != null && !ce.getOrdsubsecnum().equals("''")) {
                        sb.append(FMT_PAREN_L);
                        sb.append(ce.getOrdsubsecnum());
                        sb.append(FMT_PAREN_R);
                        // if we have a sub sub sec number, it follows in parents (1)
                        if (ce.getOrdsubsubsecnum() != null && !ce.getOrdsubsubsecnum().equals("''")) {
                            sb.append(FMT_PAREN_L);
                            sb.append(ce.getOrdsubsecnum());
                            sb.append(FMT_PAREN_R);
                        }
                    }
                } // done with section numbers, now for section titles

                if (ce.getOrdsectitle() != null && !ce.getOrdsectitle().equals("''")) {
                    sb.append(FMT_SPACE);
                    sb.append(ce.getOrdsectitle());
                }
                if (ce.getOrdsubsectitle() != null && !ce.getOrdsubsectitle().equals("''")) {
                    sb.append(FMT_SPACE);
                    sb.append(FMT_DASH);
                    sb.append(FMT_SPACE);
                    sb.append(ce.getOrdsubsectitle());
                }
            } // end outer if check on standard IRC
        } // element not null

        return sb.toString();
    }

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
                if (ce.getOrdsubsubsecnum() != null
                        && !ce.getOrdsubsubsecnum().equals("''")) {
                    // eg 302.1.1
                    sb.append(ce.getOrdsubsubsecnum());
                } else { // we don't have a recursive sub-sub sec number, so use sub-sec number
                    sb.append(ce.getOrdsubsecnum());
                    sb.append(FMT_COLON);
                    sb.append(FMT_SPACE);
                }
                // now insert the titles
                // NOTE we are not using chapter titles in the standard IRC
                // they are implied by the section and subsection titles

                if (ce.getOrdsectitle() != null) {
                    sb.append(ce.getOrdsectitle());
                    sb.append(FMT_SPACE);
                    sb.append(FMT_DASH);
                    sb.append(FMT_SPACE);
                }
                if (ce.getOrdsubsectitle() != null) {
                    sb.append(ce.getOrdsubsectitle());
                }
            } else { // We do not have a pure IRC, so piece the header together straight across
                // we have a non-zero chapter number
                if (ce.getOrdchapterno() != 0) {
                    sb.append(FMT_CH);
                    sb.append(ce.getOrdchapterno());
                    if (ce.getOrdchaptertitle() != null && !ce.getOrdchaptertitle().equals("''")) {
                        sb.append(FMT_COLON);
                        sb.append(ce.getOrdchaptertitle());
                    }
                }
                // setup our section
                // if we have a section number, put it in
                if (ce.getOrdsecnum() != null && !ce.getOrdsecnum().equals("''")) {
                    sb.append(FMT_SPACE);
                    sb.append(FMT_SECTION_ENTITY);
                    sb.append(FMT_SPACE);
                    sb.append(ce.getOrdsecnum());
                    // if we have a sub sec number, it follows in parents (a)
                    if (ce.getOrdsubsecnum() != null && !ce.getOrdsubsecnum().equals("''")) {
                        sb.append(FMT_PAREN_L);
                        sb.append(ce.getOrdsubsecnum());
                        sb.append(FMT_PAREN_R);
                        // if we have a sub sub sec number, it follows in parents (1)
                        if (ce.getOrdsubsubsecnum() != null && !ce.getOrdsubsubsecnum().equals("''")) {
                            sb.append(FMT_PAREN_L);
                            sb.append(ce.getOrdsubsecnum());
                            sb.append(FMT_PAREN_R);
                        }
                    }
                } // done with section numbers, now for section titles

                if (ce.getOrdsectitle() != null && !ce.getOrdsectitle().equals("''")) {
                    sb.append(FMT_SPACE);
                    sb.append(ce.getOrdsectitle());
                }
                if (ce.getOrdsubsectitle() != null && !ce.getOrdsubsectitle().equals("''")) {
                    sb.append(FMT_SPACE);
                    sb.append(FMT_DASH);
                    sb.append(FMT_SPACE);
                    sb.append(ce.getOrdsubsectitle());
                }
            } // end outer if check on standard IRC
        }
        return sb.toString();
    }

    private boolean checkForPureIRCRecursiveListing(CodeElement ce) {

        boolean isPureIRCFormat = true;

        // nulls or zero for ch, sec, and subsec violates purity
        if (ce.getOrdchapterno() == 0
                || ce.getOrdsecnum() == null
                || ce.getOrdsubsecnum() == null) {
            return false;
        }

        // no ch, sec, or sub-sec titles violates purity
        if (ce.getOrdchaptertitle() == null
                || ce.getOrdsectitle() == null
                || ce.getOrdsubsectitle() == null) {
            return false;
        }

        // chapter No. should be in our section #
        if (!ce.getOrdsecnum().contains(String.valueOf(ce.getOrdchapterno()))) {
            return false;
        }

        // sec number should be in our sub-section #
        if (!ce.getOrdsubsecnum().contains(ce.getOrdsecnum())) {
            return false;
        }

        // if there is a sub-sub number, section should be in there, too
        if (ce.getOrdsubsubsecnum() != null && !ce.getOrdsubsubsecnum().equals("''")) {
            if (!ce.getOrdsubsubsecnum().contains(ce.getOrdsubsecnum())) {
                return false;
            }
        }

        return isPureIRCFormat;
    }
}
