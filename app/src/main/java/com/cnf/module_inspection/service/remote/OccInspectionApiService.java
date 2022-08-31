package com.cnf.module_inspection.service.remote;

import static com.cnf.utils.RequestConstants.AUTH_PERIOD_PATH;
import static com.cnf.utils.RequestConstants.OCC_INSPECTION_DISPATCH_ALL_ADDRESS;
import static com.cnf.utils.RequestConstants.OCC_INSPECTION_DISPATCH_SYNCHRONIZED_ADDRESS;
import static com.cnf.utils.RequestConstants.OCC_INSPECTION_DISPATCH_UN_SYNCHRONIZE_ADDRESS;
import static com.cnf.utils.RequestConstants.OCC_INSPECTION_INFRA_ADDRESS;
import static com.cnf.utils.RequestConstants.OCC_INSPECTION_UPLOAD;

import com.cnf.module_inspection.entity.infra.LoginMuniAuthPeriod;
import com.cnf.module_inspection.entity.infra.OccInspectionInfra;
import com.cnf.module_inspection.entity.tasks.OccInspectionTasks;
import com.cnf.module_inspection.dto.UploadDTO;
import com.cnf.module_inspection.service.exception.HttpBadRequestException;
import com.cnf.module_inspection.service.exception.HttpNoFoundException;
import com.cnf.module_inspection.service.exception.HttpServerErrorException;
import com.cnf.module_inspection.service.exception.HttpUnAuthorizedException;
import com.cnf.module_inspection.service.exception.HttpUnknownErrorException;
import com.cnf.utils.RequestUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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
      throws HttpNoFoundException, HttpBadRequestException, IOException, HttpServerErrorException, HttpUnknownErrorException, HttpUnAuthorizedException, JsonSyntaxException {
    String body = RequestUtils.sendGetRequest(loginToken, OCC_INSPECTION_INFRA_ADDRESS, null);
    OccInspectionInfra occInspectionInfra = new Gson().fromJson(body, OccInspectionInfra.class);
    return occInspectionInfra;
  }

  public OccInspectionTasks getOccInspectionDispatch(String loginToken, String authPeriodId, String municipalityCode, Boolean isSynchronized)
      throws HttpNoFoundException, HttpBadRequestException, IOException, HttpServerErrorException, HttpUnknownErrorException, HttpUnAuthorizedException, JsonSyntaxException {
    HashMap<String, String> paramsMap = new HashMap<>();
    paramsMap.put(MUNICIPALITY_CODE, municipalityCode);
    paramsMap.put(AUTH_PERIOD_ID, authPeriodId);
    String url;
    if (isSynchronized == null) {
      url = OCC_INSPECTION_DISPATCH_ALL_ADDRESS;
    } else if (isSynchronized) {
      url = OCC_INSPECTION_DISPATCH_SYNCHRONIZED_ADDRESS;
    } else {
      url = OCC_INSPECTION_DISPATCH_UN_SYNCHRONIZE_ADDRESS;
    }
    String body = RequestUtils.sendGetRequest(loginToken, url, paramsMap);
    OccInspectionTasks occInspectionTasks = new Gson().fromJson(body, OccInspectionTasks.class);
    return occInspectionTasks;
  }

  public List<LoginMuniAuthPeriod> getLoginMuniAuthPeriodList(String userLoginToken)
      throws HttpNoFoundException, HttpBadRequestException, IOException, HttpServerErrorException, HttpUnknownErrorException, HttpUnAuthorizedException {
    String body = RequestUtils.sendPostRequest(userLoginToken, null, AUTH_PERIOD_PATH, null);
    if (body == null || body.isEmpty()) {
      return new ArrayList<>();
    }
    return new Gson().fromJson(body, new TypeToken<ArrayList<LoginMuniAuthPeriod>>() {
    }.getType());
  }

  public void uploadToServer(UploadDTO uploadDTO, String userLoginToken, String authPeriodId, String municipalityCode)
      throws IOException, HttpUnAuthorizedException, HttpBadRequestException, HttpServerErrorException, HttpUnknownErrorException, HttpNoFoundException {
    String requestBody = new Gson().toJson(uploadDTO);
    HashMap<String, String> paramsMap = new HashMap<>();
    paramsMap.put(MUNICIPALITY_CODE, municipalityCode);
    paramsMap.put(AUTH_PERIOD_ID, authPeriodId);
    String response = RequestUtils.sendPostRequest(userLoginToken, requestBody, OCC_INSPECTION_UPLOAD, paramsMap);
  }
}
