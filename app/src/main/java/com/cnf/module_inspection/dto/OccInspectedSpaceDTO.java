package com.cnf.module_inspection.dto;

import com.cnf.module_inspection.entity.OccInspectedSpace;

import java.util.List;

public class OccInspectedSpaceDTO {

    private OccInspectedSpace occInspectedSpace;

    private List<OccInspectedSpaceElementDTO> occInspectedSpaceElementDTOList;

    public OccInspectedSpaceDTO(OccInspectedSpace occInspectedSpace, List<OccInspectedSpaceElementDTO> occInspectedSpaceElementDTOList) {
        this.occInspectedSpace = occInspectedSpace;
        this.occInspectedSpaceElementDTOList = occInspectedSpaceElementDTOList;
    }

    public OccInspectedSpace getOccInspectedSpace() {
        return occInspectedSpace;
    }

    public void setOccInspectedSpace(OccInspectedSpace occInspectedSpace) {
        this.occInspectedSpace = occInspectedSpace;
    }

    public List<OccInspectedSpaceElementDTO> getOccInspectedSpaceElementDTOList() {
        return occInspectedSpaceElementDTOList;
    }

    public void setOccInspectedSpaceElementDTOList(List<OccInspectedSpaceElementDTO> occInspectedSpaceElementDTOList) {
        this.occInspectedSpaceElementDTOList = occInspectedSpaceElementDTOList;
    }

    @Override
    public String toString() {
        return "OccInspectedSpaceDTO{" +
            "occInspectedSpace=" + occInspectedSpace +
            ", occInspectedSpaceElementDTOList=" + occInspectedSpaceElementDTOList +
            '}';
    }
}
