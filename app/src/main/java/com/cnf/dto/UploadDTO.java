package com.cnf.dto;

import java.util.List;

import lombok.Data;

@Data
public class UploadDTO {

    private Integer inspectionId;
    private List<OccInspectedSpaceDTO> occInspectedSpaceDTOList;

    public UploadDTO(Integer inspectionId, List<OccInspectedSpaceDTO> occInspectedSpaceDTOList) {
        this.inspectionId = inspectionId;
        this.occInspectedSpaceDTOList = occInspectedSpaceDTOList;
    }

    public Integer getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Integer inspectionId) {
        this.inspectionId = inspectionId;
    }

    public List<OccInspectedSpaceDTO> getOccInspectedSpaceDTOList() {
        return occInspectedSpaceDTOList;
    }

    public void setOccInspectedSpaceDTOList(List<OccInspectedSpaceDTO> occInspectedSpaceDTOList) {
        this.occInspectedSpaceDTOList = occInspectedSpaceDTOList;
    }

    @Override
    public String toString() {
        return "UploadDTO{" +
            "inspectionId=" + inspectionId +
            ", occInspectedSpaceDTOList=" + occInspectedSpaceDTOList +
            '}';
    }
}
