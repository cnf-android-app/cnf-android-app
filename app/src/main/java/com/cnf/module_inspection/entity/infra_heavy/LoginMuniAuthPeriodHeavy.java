package com.cnf.module_inspection.entity.infra_heavy;

import androidx.room.Embedded;
import androidx.room.Entity;
import com.cnf.module_inspection.entity.infra.LoginMuniAuthPeriod;
import com.cnf.module_inspection.entity.infra.Municipality;

@Entity
public class LoginMuniAuthPeriodHeavy {

  @Embedded
  LoginMuniAuthPeriod loginMuniAuthPeriod;

  @Embedded
  Municipality municipality;

  public LoginMuniAuthPeriod getLoginMuniAuthPeriod() {
    return loginMuniAuthPeriod;
  }

  public void setLoginMuniAuthPeriod(LoginMuniAuthPeriod loginMuniAuthPeriod) {
    this.loginMuniAuthPeriod = loginMuniAuthPeriod;
  }

  public Municipality getMunicipality() {
    return municipality;
  }

  public void setMunicipality(Municipality municipality) {
    this.municipality = municipality;
  }

  @Override
  public String toString() {
    return "LoginMuniAuthPeriodHeavy{" +
        "loginMuniAuthPeriod=" + loginMuniAuthPeriod +
        ", municipality=" + municipality +
        '}';
  }
}
