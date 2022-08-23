package com.cnf.module_inspection.dto;

import lombok.Data;

@Data
public class MuniLoginForm {

    private String muni_municode;

    public MuniLoginForm(String muni_municode) {
        this.muni_municode = muni_municode;
    }
}
