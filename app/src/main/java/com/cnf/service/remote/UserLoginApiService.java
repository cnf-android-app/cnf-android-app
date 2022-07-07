package com.cnf.service.remote;

import static com.cnf.utils.RequestConstants.USER_LOGIN_PATH;

import com.cnf.dto.TokenDTO;
import com.cnf.dto.UserLoginDTO;
import com.cnf.service.exception.HttpBadRequestException;
import com.cnf.service.exception.HttpNoFoundException;
import com.cnf.service.exception.HttpNoneResponseForLoginUserTokenException;
import com.cnf.service.exception.HttpServerErrorException;
import com.cnf.service.exception.HttpUnAuthorizedException;
import com.cnf.service.exception.HttpUnknownErrorException;
import com.cnf.utils.RequestUtils;
import com.google.gson.Gson;

import java.io.IOException;

public class UserLoginApiService {


    private static UserLoginApiService INSTANCE = null;

    private UserLoginApiService() {
    }

    public static UserLoginApiService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserLoginApiService();
        }
        return INSTANCE;
    }

    public String userLogin(String username, String password) throws
        IOException,
        HttpUnAuthorizedException,
        HttpNoneResponseForLoginUserTokenException, HttpBadRequestException, HttpServerErrorException, HttpUnknownErrorException, HttpNoFoundException {

        UserLoginDTO userLoginDTO = new UserLoginDTO(username, password);
        String requestBody = new Gson().toJson(userLoginDTO);
        String response = RequestUtils.sendPostRequest(null, requestBody, USER_LOGIN_PATH, null);
        if (response == null) {
            throw new HttpNoneResponseForLoginUserTokenException("Http: none response for login user token exception");
        }
        TokenDTO tokenDTO = new Gson().fromJson(response, TokenDTO.class);
        return tokenDTO.getToken();
    }

    // TODO write a method to check if current user muni token is valid or not
    public boolean isLoginUserTokenValid(String loginUserToken) {
        return true;
    }

}
