package com.cnf.module_inspection.dto;

import lombok.Data;

@Data
public class UserLoginDTO {

    public UserLoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String username;
    private String password;
}
