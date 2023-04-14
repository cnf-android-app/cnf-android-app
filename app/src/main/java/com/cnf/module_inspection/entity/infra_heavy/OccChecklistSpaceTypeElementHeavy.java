package com.cnf.module_inspection.entity.infra_heavy;

import androidx.room.Embedded;

import com.cnf.module_inspection.entity.infra.CodeElement;
import com.cnf.module_inspection.entity.infra.CodeSetElement;
import com.cnf.module_inspection.entity.infra.OccChecklistSpaceType;
import com.cnf.module_inspection.entity.infra.OccChecklistSpaceTypeElement;

import lombok.Data;

@Data
public class OccChecklistSpaceTypeElementHeavy {

    @Embedded
    private OccChecklistSpaceTypeElement occChecklistSpaceTypeElement;
    @Embedded
    private CodeElement codeElement;
    @Embedded
    private CodeSetElement codeSetElement;
    @Embedded
    private OccChecklistSpaceType occChecklistSpaceType;

    public OccChecklistSpaceTypeElement getOccChecklistSpaceTypeElement() {
        return occChecklistSpaceTypeElement;
    }

    public void setOccChecklistSpaceTypeElement(OccChecklistSpaceTypeElement occChecklistSpaceTypeElement) {
        this.occChecklistSpaceTypeElement = occChecklistSpaceTypeElement;
    }

    public CodeElement getCodeElement() {
        return codeElement;
    }

    public void setCodeElement(CodeElement codeElement) {
        this.codeElement = codeElement;
    }

    public CodeSetElement getCodeSetElement() {
        return codeSetElement;
    }

    public void setCodeSetElement(CodeSetElement codeSetElement) {
        this.codeSetElement = codeSetElement;
    }

    public OccChecklistSpaceType getOccChecklistSpaceType() {
        return occChecklistSpaceType;
    }

    public void setOccChecklistSpaceType(OccChecklistSpaceType occChecklistSpaceType) {
        this.occChecklistSpaceType = occChecklistSpaceType;
    }
}
