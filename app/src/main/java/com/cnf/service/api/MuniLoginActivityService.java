package com.cnf.service.api;

import static com.cnf.utils.AppConstants.LOGIN_TOKEN_SHARE_PREFERENCE_NAME;
import static com.cnf.utils.AppConstants.USER_LOGIN_TOKEN_KEY;
import static com.cnf.utils.AppConstants.USER_MUNICIPALITY_LOGIN_TOKEN_KEY;
import static com.cnf.utils.RequestConstants.MUNI_LOGIN_INFO_PATH;
import static com.cnf.utils.RequestConstants.MUNI_LOGIN_PATH;

import android.content.Context;
import android.content.SharedPreferences;

import com.cnf.dto.LoginMuniDTO;
import com.cnf.dto.MuniLoginForm;
import com.cnf.dto.Result;
import com.cnf.utils.RequestUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

public class MuniLoginActivityService {

    private Context context;
    private static MuniLoginActivityService INSTANCE = null;

    private MuniLoginActivityService(Context context) {
        this.context = context;
    }

    public static MuniLoginActivityService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MuniLoginActivityService(context);
        }
        return INSTANCE;
    }

    public List<LoginMuniDTO> getMunicipalityList() throws IOException {
        SharedPreferences sp = context.getSharedPreferences(LOGIN_TOKEN_SHARE_PREFERENCE_NAME, context.MODE_PRIVATE);
        String user_login_token = sp.getString(USER_LOGIN_TOKEN_KEY, null);
        String response = RequestUtils.sendGetRequest(user_login_token, MUNI_LOGIN_INFO_PATH);
        Type listType = new TypeToken<ArrayList<LoginMuniDTO>>() {
        }.getType();
        List<LoginMuniDTO> loginMuniList = new Gson().fromJson(response, listType);
        if (loginMuniList == null) {
            loginMuniList = new ArrayList<>();
        }
        return loginMuniList;
    }

    public boolean municipalityLogin(int municipalityCode) throws IOException {
        SharedPreferences sp = context.getSharedPreferences(LOGIN_TOKEN_SHARE_PREFERENCE_NAME, context.MODE_PRIVATE);
        String user_login_token = sp.getString(USER_LOGIN_TOKEN_KEY, null);
        MuniLoginForm muniLoginForm = new MuniLoginForm(String.valueOf(municipalityCode));
        String requestBody = new Gson().toJson(muniLoginForm);
        String response = RequestUtils.sendPostRequest(user_login_token, requestBody, MUNI_LOGIN_PATH);
        if (response == null) {
            return false;
        }
        Result result = new Gson().fromJson(response, Result.class);
        String token = (String) result.getData();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_MUNICIPALITY_LOGIN_TOKEN_KEY, token);
        editor.commit();
        return true;
    }
}
