package com.cnf.dto;

import lombok.Data;

@Data
public class MuniLoginForm {

  private String muni_municode;

  public MuniLoginForm() {
  }

  public MuniLoginForm(String muni_municode) {
    this.muni_municode = muni_municode;
  }
}
