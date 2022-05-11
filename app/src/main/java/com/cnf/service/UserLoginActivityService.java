package com.cnf.service;

import static com.cnf.utils.RequestConstants.USER_LOGIN_PATH;

import android.content.Context;
import android.content.SharedPreferences;

import com.cnf.dto.Result;
import com.cnf.dto.UserLoginDTO;
import com.cnf.utils.RequestUtils;
import com.google.gson.Gson;

public class UserLoginActivityService {

    private Context context;

    public UserLoginActivityService(Context context) {
        this.context = context;
    }

    public boolean userLogin(String username, String pwd) {
        UserLoginDTO userLoginDTO = new UserLoginDTO(username, pwd);
        String requestBody = new Gson().toJson(userLoginDTO);
        String response = RequestUtils.sendPostRequest(null, requestBody, USER_LOGIN_PATH);
        if (response == null) {
            return false;
        }
        Result result = new Gson().fromJson(response.toString(), Result.class);
        String token = (String) result.getData();
        SharedPreferences sp = this.context.getSharedPreferences("login_token", this.context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user_login_token", token);
        editor.commit();
        return true;
    }

    public boolean isAuthLogin() {
        String token = context.getSharedPreferences("muni_token", context.MODE_PRIVATE).getString("user_muni_login_token", null);
        if (token == null || token.length() == 0) {
            return false;
        }
        return true;
    }
}
