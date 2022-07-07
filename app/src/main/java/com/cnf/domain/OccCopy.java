package com.cnf.domain;

import com.cnf.domain.infra.CodeElement;
import com.cnf.domain.infra.CodeElementGuide;
import com.cnf.domain.infra.CodeSetElement;
import com.cnf.domain.infra.CodeSource;
import com.cnf.domain.infra.IntensityClass;
import com.cnf.domain.infra.Municipality;
import com.cnf.domain.infra.OccChecklistSpaceType;
import com.cnf.domain.infra.OccChecklistSpaceTypeElement;
import com.cnf.domain.infra.OccLocationDescription;
import com.cnf.domain.infra.OccSpaceType;

import java.util.List;

public class OccCopy {

    private List<CodeSource> codeSourceList;
    private List<CodeElement> codeElementList;
    private List<CodeSetElement> codeSetElementList;
    private List<CodeElementGuide> codeElementGuideList;
    private List<OccChecklistSpaceType> occChecklistSpaceTypeList;
    private List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList;
    private List<OccSpaceType> occSpaceTypeList;
    private List<OccLocationDescription> occLocationDescriptionList;
    private List<IntensityClass> intensityClassList;
    private List<Municipality> municipalityList;

    public OccCopy(List<CodeSource> codeSourceList, List<CodeElement> codeElementList, List<CodeSetElement> codeSetElementList, List<CodeElementGuide> codeElementGuideList,
        List<OccChecklistSpaceType> occChecklistSpaceTypeList, List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList, List<OccSpaceType> occSpaceTypeList,
        List<OccLocationDescription> occLocationDescriptionList, List<IntensityClass> intensityClassList, List<Municipality> municipalityList) {
        this.codeSourceList = codeSourceList;
        this.codeElementList = codeElementList;
        this.codeSetElementList = codeSetElementList;
        this.codeElementGuideList = codeElementGuideList;
        this.occChecklistSpaceTypeList = occChecklistSpaceTypeList;
        this.occChecklistSpaceTypeElementList = occChecklistSpaceTypeElementList;
        this.occSpaceTypeList = occSpaceTypeList;
        this.occLocationDescriptionList = occLocationDescriptionList;
        this.intensityClassList = intensityClassList;
        this.municipalityList = municipalityList;
    }

    public List<CodeSource> getCodeSourceList() {
        return codeSourceList;
    }

    public void setCodeSourceList(List<CodeSource> codeSourceList) {
        this.codeSourceList = codeSourceList;
    }

    public List<CodeElement> getCodeElementList() {
        return codeElementList;
    }

    public void setCodeElementList(List<CodeElement> codeElementList) {
        this.codeElementList = codeElementList;
    }

    public List<CodeSetElement> getCodeSetElementList() {
        return codeSetElementList;
    }

    public void setCodeSetElementList(List<CodeSetElement> codeSetElementList) {
        this.codeSetElementList = codeSetElementList;
    }

    public List<CodeElementGuide> getCodeElementGuideList() {
        return codeElementGuideList;
    }

    public void setCodeElementGuideList(List<CodeElementGuide> codeElementGuideList) {
        this.codeElementGuideList = codeElementGuideList;
    }

    public List<OccChecklistSpaceType> getOccChecklistSpaceTypeList() {
        return occChecklistSpaceTypeList;
    }

    public void setOccChecklistSpaceTypeList(List<OccChecklistSpaceType> occChecklistSpaceTypeList) {
        this.occChecklistSpaceTypeList = occChecklistSpaceTypeList;
    }

    public List<OccChecklistSpaceTypeElement> getOccChecklistSpaceTypeElementList() {
        return occChecklistSpaceTypeElementList;
    }

    public void setOccChecklistSpaceTypeElementList(List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList) {
        this.occChecklistSpaceTypeElementList = occChecklistSpaceTypeElementList;
    }

    public List<OccSpaceType> getOccSpaceTypeList() {
        return occSpaceTypeList;
    }

    public void setOccSpaceTypeList(List<OccSpaceType> occSpaceTypeList) {
        this.occSpaceTypeList = occSpaceTypeList;
    }

    public List<OccLocationDescription> getOccLocationDescriptionList() {
        return occLocationDescriptionList;
    }

    public void setOccLocationDescriptionList(List<OccLocationDescription> occLocationDescriptionList) {
        this.occLocationDescriptionList = occLocationDescriptionList;
    }

    public List<IntensityClass> getIntensityClassList() {
        return intensityClassList;
    }

    public void setIntensityClassList(List<IntensityClass> intensityClassList) {
        this.intensityClassList = intensityClassList;
    }

    public List<Municipality> getMunicipalityList() {
        return municipalityList;
    }

    public void setMunicipalityList(List<Municipality> municipalityList) {
        this.municipalityList = municipalityList;
    }
}
