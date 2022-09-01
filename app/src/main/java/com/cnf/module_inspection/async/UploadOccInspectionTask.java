package com.cnf.module_inspection.async;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_ID_KEY;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_AUTH_PERIOD_ID;
import static com.cnf.utils.AppConstants.SP_KEY_MUNICIPALITY_CODE;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;
import static com.cnf.utils.AppConstants.TOAST_INVALID_CLIENT_MSG;
import static com.cnf.utils.AppConstants.TOAST_INVALID_LOGIN_AUTHORIZATION_MSG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.cnf.module_auth.activity.HomeActivity;
import com.cnf.module_inspection.dto.UploadDTO;
import com.cnf.module_inspection.entity.tasks.OccInspectionDispatch;
import com.cnf.module_inspection.service.exception.HttpBadRequestException;
import com.cnf.module_inspection.service.exception.HttpNoFoundException;
import com.cnf.module_inspection.service.exception.HttpServerErrorException;
import com.cnf.module_inspection.service.exception.HttpUnAuthorizedException;
import com.cnf.module_inspection.service.exception.HttpUnknownErrorException;
import com.cnf.module_inspection.service.local.OccInspectionDispatchRepository;
import com.cnf.module_inspection.service.local.OccInspectionInfraService;
import com.cnf.module_inspection.service.remote.OccInspectionApiService;
import com.google.android.material.snackbar.Snackbar;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.time.OffsetDateTime;

public class UploadOccInspectionTask extends AsyncTask<Void, Void, Void> {

  private final WeakReference<Fragment> fragmentWeakReference;
  private final WeakReference<HomeActivity> activityWeakReference;
  private final OccInspectionDispatch occInspectionDispatch;

  public UploadOccInspectionTask(OccInspectionDispatch occInspectionDispatch, @NonNull Fragment fragment) {
    this.fragmentWeakReference = new WeakReference<>(fragment);
    this.activityWeakReference = new WeakReference<>((HomeActivity) fragment.getActivity());
    this.occInspectionDispatch = occInspectionDispatch;
  }

  @Override
  protected Void doInBackground(Void... voids) {
    HomeActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return null;
    }
    OccInspectionInfraService occInspectionInfraService = OccInspectionInfraService.getInstance(activity);
    OccInspectionApiService occInspectionApiService = OccInspectionApiService.getInstance();
    OccInspectionDispatchRepository occInspectionDispatchRepository = OccInspectionDispatchRepository.getInstance(activity);
    SharedPreferences sp = activity.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    int muniCode = sp.getInt(SP_KEY_MUNICIPALITY_CODE, -1);
    int authPeriodId = sp.getInt(SP_KEY_AUTH_PERIOD_ID, -1);
    String loginUserToken = sp.getString(SP_KEY_USER_LOGIN_TOKEN, null);

    Integer inspectionId = occInspectionDispatch.getInspectionId();
    if (inspectionId == null) {
      Log.e(TAG, "inspectionId null");
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_INVALID_CLIENT_MSG, Snackbar.LENGTH_LONG).show();
      return null;
    }
    UploadDTO uploadDTO = occInspectionInfraService.getUploadDTO(inspectionId);
    try {
      occInspectionApiService.uploadToServer(uploadDTO, loginUserToken, String.valueOf(authPeriodId), String.valueOf(muniCode));
    } catch (HttpUnAuthorizedException | HttpBadRequestException | HttpServerErrorException | HttpUnknownErrorException | HttpNoFoundException | IOException e) {
      e.printStackTrace();
    }

    occInspectionDispatch.setSynchronizationTS(OffsetDateTime.now().toString());
    occInspectionDispatchRepository.updateOccInspectionDispatch(occInspectionDispatch);

    return null;
  }

  @Override
  protected void onPostExecute(Void unused) {
    super.onPostExecute(unused);
    HomeActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return;
    }
    activity.startActivity(new Intent(activity, HomeActivity.class));
  }
}

