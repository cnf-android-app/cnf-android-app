package com.cnf.module_inspection.service.remote;

import static android.content.ContentValues.TAG;

import static com.cnf.utils.RequestConstants.AUTH_PERIOD_PATH;
import static com.cnf.utils.RequestConstants.OCC_INSPECTION_DISPATCH_ALL_ADDRESS;
import static com.cnf.utils.RequestConstants.OCC_INSPECTION_DISPATCH_SYNCHRONIZED_ADDRESS;
import static com.cnf.utils.RequestConstants.OCC_INSPECTION_DISPATCH_UN_SYNCHRONIZE_ADDRESS;
import static com.cnf.utils.RequestConstants.OCC_INSPECTION_INFRA_ADDRESS;
import static com.cnf.utils.RequestConstants.OCC_INSPECTION_UPLOAD;

import android.util.Log;

import com.cnf.module_inspection.entity.infra.LoginMuniAuthPeriod;
import com.cnf.module_inspection.entity.infra.OccInspectionInfra;
import com.cnf.module_inspection.entity.tasks.OccInspectionTasks;
import com.cnf.module_inspection.dto.UploadDTO;
import com.cnf.module_inspection.service.exception.HttpBadRequestException;
import com.cnf.module_inspection.service.exception.HttpNoFoundException;
import com.cnf.module_inspection.service.exception.HttpServerErrorException;
import com.cnf.module_inspection.service.exception.HttpUnAuthorizedException;
import com.cnf.module_inspection.service.exception.HttpUnknownErrorException;
import com.cnf.module_inspection.service.exception.OccInspectionInfraEmptyException;
import com.cnf.utils.RequestUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OccInspectionApiService {

  private static OccInspectionApiService INSTANCE = null;
  private static final String MUNICIPALITY_CODE = "municipality_code";
  private static final String AUTH_PERIOD_ID = "auth_period";

  private OccInspectionApiService() {
  }

  public static OccInspectionApiService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new OccInspectionApiService();
    }
    return INSTANCE;
  }

  public OccInspectionInfra getOccInspectionInfra(String loginToken)
      throws OccInspectionInfraEmptyException, HttpNoFoundException, HttpBadRequestException, IOException, HttpServerErrorException, HttpUnknownErrorException, HttpUnAuthorizedException {
    String body = RequestUtils.sendGetRequest(loginToken, OCC_INSPECTION_INFRA_ADDRESS, null);
    if (body == null || body.isEmpty()) {
      Log.d(TAG, String.format("Server OccInspectionInfra: %s", "EMPTY"));
      throw new OccInspectionInfraEmptyException("OccInspectionInfraEmptyException Error");
    }
    OccInspectionInfra occInspectionInfra = new Gson().fromJson(body, OccInspectionInfra.class);
    Log.d(TAG, String.format("Server OccInspectionInfra: %s", occInspectionInfra));
    return occInspectionInfra;
  }

  public OccInspectionTasks getOccInspectionDispatch(String loginToken, String authPeriodId, String municipalityCode, Boolean isSynchronized)
      throws HttpNoFoundException, HttpBadRequestException, IOException, HttpServerErrorException, HttpUnknownErrorException, HttpUnAuthorizedException {
    HashMap<String, String> paramsMap = new HashMap<>();
    paramsMap.put(MUNICIPALITY_CODE, municipalityCode);
    paramsMap.put(AUTH_PERIOD_ID, authPeriodId);
    String body;
    if (isSynchronized == null) {
      body = RequestUtils.sendGetRequest(loginToken, OCC_INSPECTION_DISPATCH_ALL_ADDRESS, paramsMap);
    } else if (isSynchronized) {
      body = RequestUtils.sendGetRequest(loginToken, OCC_INSPECTION_DISPATCH_SYNCHRONIZED_ADDRESS, paramsMap);
    } else {
      body = RequestUtils.sendGetRequest(loginToken, OCC_INSPECTION_DISPATCH_UN_SYNCHRONIZE_ADDRESS, paramsMap);
    }
    if (body == null || body.isEmpty()) {
      Log.d(TAG, String.format("Server Inspection Dispatch: %s", "EMPTY"));
      return new OccInspectionTasks();
    }
    OccInspectionTasks occInspectionTasks = new Gson().fromJson(body, OccInspectionTasks.class);
    Log.d(TAG, String.format("Server Inspection Tasks: %s", occInspectionTasks));
    return occInspectionTasks;
  }

  public List<LoginMuniAuthPeriod> getLoginMuniAuthPeriodList(String userLoginToken)
      throws HttpNoFoundException, HttpBadRequestException, IOException, HttpServerErrorException, HttpUnknownErrorException, HttpUnAuthorizedException {
    String body = RequestUtils.sendPostRequest(userLoginToken, null, AUTH_PERIOD_PATH, null);
    if (body == null || body.isEmpty()) {
      Log.d(TAG, String.format("Server LoginMuniAuthPeriod List: %s", "EMPTY"));
      return new ArrayList<>();
    }
    List<LoginMuniAuthPeriod> loginMuniAuthPeriodList = new Gson().fromJson(body, new TypeToken<ArrayList<LoginMuniAuthPeriod>>() {
    }.getType());
    Log.d(TAG, String.format("Server LoginMuniAuthPeriod List: %s", loginMuniAuthPeriodList));
    return loginMuniAuthPeriodList;
  }

  public void uploadToServer(UploadDTO uploadDTO, String userLoginToken,String authPeriodId, String municipalityCode)
      throws IOException, HttpUnAuthorizedException, HttpBadRequestException, HttpServerErrorException, HttpUnknownErrorException, HttpNoFoundException {
    String requestBody = new Gson().toJson(uploadDTO);
    HashMap<String, String> paramsMap = new HashMap<>();
    paramsMap.put(MUNICIPALITY_CODE, municipalityCode);
    paramsMap.put(AUTH_PERIOD_ID, authPeriodId);
    String response = RequestUtils.sendPostRequest(userLoginToken, requestBody, OCC_INSPECTION_UPLOAD, paramsMap);
  }
}
