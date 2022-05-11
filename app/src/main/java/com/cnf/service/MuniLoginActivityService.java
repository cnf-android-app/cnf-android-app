package com.cnf.service;

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

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

public class MuniLoginActivityService {

    private Context context;

    public MuniLoginActivityService(Context context) {
        this.context = context;
    }

    public List<LoginMuniDTO> getMuniList() {
        SharedPreferences sp = context.getSharedPreferences("login_token", context.MODE_PRIVATE);
        String user_login_token = sp.getString("user_login_token", null);
        String response = RequestUtils.sendGetRequest(user_login_token, MUNI_LOGIN_INFO_PATH);
        Type listType = new TypeToken<ArrayList<LoginMuniDTO>>() {
        }.getType();
        List<LoginMuniDTO> muniLoginList = new Gson().fromJson(response, listType);
        return muniLoginList;
    }



    public boolean muniLogin(int muni_municode) {
        SharedPreferences sp = context.getSharedPreferences("login_token", context.MODE_PRIVATE);
        String user_login_token = sp.getString("user_login_token", null);
        MuniLoginForm muniLoginForm = new MuniLoginForm(String.valueOf(muni_municode));
        String requestBody = new Gson().toJson(muniLoginForm);
        String reponse = RequestUtils.sendPostRequest(user_login_token, requestBody, MUNI_LOGIN_PATH);

        if (reponse == null) {
            return false;
        }

        Result result = new Gson().fromJson(reponse, Result.class);
        String token = (String) result.getData();

        SharedPreferences sp0 = context.getSharedPreferences("muni_token", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp0.edit();
        editor.putString("user_muni_login_token", token);
        editor.commit();
        return true;
    }

}
