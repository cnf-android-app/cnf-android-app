package com.cnf.service.api;

import static com.cnf.utils.AppConstants.LOGIN_TOKEN_SHARE_PREFERENCE_NAME;
import static com.cnf.utils.AppConstants.USER_LOGIN_TOKEN_KEY;
import static com.cnf.utils.AppConstants.USER_MUNICIPALITY_LOGIN_TOKEN_KEY;
import static com.cnf.utils.RequestConstants.USER_LOGIN_PATH;

import android.content.Context;
import android.content.SharedPreferences;

import com.cnf.dto.Result;
import com.cnf.dto.UserLoginDTO;
import com.cnf.utils.RequestUtils;
import com.google.gson.Gson;

import java.io.IOException;

public class UserLoginActivityService {

    private Context context;
    private static UserLoginActivityService INSTANCE = null;

    private UserLoginActivityService(Context context) {
        this.context = context;
    }

    public static UserLoginActivityService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserLoginActivityService(context);
        }
        return INSTANCE;
    }

    public boolean userLogin(String username, String pwd) throws IOException {
        UserLoginDTO userLoginDTO = new UserLoginDTO(username, pwd);
        String requestBody = new Gson().toJson(userLoginDTO);
        String response = RequestUtils.sendPostRequest(null, requestBody, USER_LOGIN_PATH);
        if (response == null) {
            return false;
        }
        Result result = new Gson().fromJson(response.toString(), Result.class);
        String token = (String) result.getData();
        SharedPreferences sp = this.context.getSharedPreferences(LOGIN_TOKEN_SHARE_PREFERENCE_NAME, this.context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_LOGIN_TOKEN_KEY, token);
        editor.commit();
        return true;
    }

    public boolean isAuthLogin() {
        String token = context.getSharedPreferences(LOGIN_TOKEN_SHARE_PREFERENCE_NAME, context.MODE_PRIVATE).getString(USER_MUNICIPALITY_LOGIN_TOKEN_KEY, null);
        if (token == null || token.length() == 0) {
            return false;
        }
        return true;
    }
}
