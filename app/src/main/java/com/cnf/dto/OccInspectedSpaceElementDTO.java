package com.cnf.dto;

import com.cnf.domain.OccInspectedSpaceElement;

import java.util.List;

import lombok.Data;

@Data
public class OccInspectedSpaceElementDTO {

    private OccInspectedSpaceElement occInspectedSpaceElement;

    private List<PhotoDto> photoDtoList;

    public OccInspectedSpaceElementDTO(OccInspectedSpaceElement occInspectedSpaceElement, List<PhotoDto> photoDtoList) {
        this.occInspectedSpaceElement = occInspectedSpaceElement;
        this.photoDtoList = photoDtoList;
    }

    public OccInspectedSpaceElement getOccInspectedSpaceElement() {
        return occInspectedSpaceElement;
    }

    public void setOccInspectedSpaceElement(OccInspectedSpaceElement occInspectedSpaceElement) {
        this.occInspectedSpaceElement = occInspectedSpaceElement;
    }

    public List<PhotoDto> getPhotoDtoList() {
        return photoDtoList;
    }

    public void setPhotoDtoList(List<PhotoDto> photoDtoList) {
        this.photoDtoList = photoDtoList;
    }

    @Override
    public String toString() {
        return "OccInspectedSpaceElementDTO{" +
            "occInspectedSpaceElement=" + occInspectedSpaceElement +
            ", photoDtoList=" + photoDtoList +
            '}';
    }
}
