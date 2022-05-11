package com.cnf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginMuniDTO {

    private Integer userid;
    private Integer muni_municode;
    private String muniname;
    private String userrole;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getMuni_municode() {
        return muni_municode;
    }

    public void setMuni_municode(Integer muni_municode) {
        this.muni_municode = muni_municode;
    }

    public String getMuniname() {
        return muniname;
    }

    public void setMuniname(String muniname) {
        this.muniname = muniname;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }
}
