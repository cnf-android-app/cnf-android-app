package com.cnf.module_inspection.service.remote;

import static com.cnf.utils.RequestConstants.IS_VALID_USER_TOKEN_PATH;
import static com.cnf.utils.RequestConstants.USER_LOGIN_PATH;

import com.cnf.module_inspection.dto.TokenDTO;
import com.cnf.module_inspection.dto.UserLoginDTO;
import com.cnf.module_inspection.service.exception.HttpBadRequestException;
import com.cnf.module_inspection.service.exception.HttpNoFoundException;
import com.cnf.module_inspection.service.exception.HttpNoneResponseForLoginUserTokenException;
import com.cnf.module_inspection.service.exception.HttpServerErrorException;
import com.cnf.module_inspection.service.exception.HttpUnAuthorizedException;
import com.cnf.module_inspection.service.exception.HttpUnknownErrorException;
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

  public String userLogin(String username, String password)
      throws HttpNoFoundException, HttpBadRequestException, IOException, HttpServerErrorException, HttpUnknownErrorException, HttpUnAuthorizedException {
    UserLoginDTO userLoginDTO = new UserLoginDTO(username, password);
    String requestBody = new Gson().toJson(userLoginDTO);
    String response = RequestUtils.sendPostRequest(null, requestBody, USER_LOGIN_PATH, null);
    TokenDTO tokenDTO = new Gson().fromJson(response, TokenDTO.class);
    return tokenDTO.getToken();
  }

  public boolean isLoginUserTokenValid(String loginUserToken)
      throws HttpNoFoundException, HttpBadRequestException, IOException, HttpServerErrorException, HttpUnknownErrorException, HttpUnAuthorizedException {
    String requestBody = RequestUtils.sendPostRequest(loginUserToken, null, IS_VALID_USER_TOKEN_PATH, null);
    int userId = Integer.parseInt(requestBody);
    return userId != -1;
  }
}
