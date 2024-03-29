package com.cnf.module_inspection.async;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_AUTH_PERIOD_ID;
import static com.cnf.utils.AppConstants.SP_KEY_MUNICIPALITY_CODE;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;
import static com.cnf.utils.AppConstants.TOAST_INVALID_CLIENT_MSG;
import static com.cnf.utils.AppConstants.TOAST_INVALID_LOGIN_AUTHORIZATION_MSG;
import static com.cnf.utils.AppConstants.TOAST_INVALID_SERVER_MSG;
import static com.cnf.utils.AppConstants.TOAST_UNKNOWN_MSG;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import com.cnf.module_auth.activity.HomeActivity;
import com.cnf.module_auth.activity.MuniActivity;
import com.cnf.module_inspection.activity.InspectionContainerActivity;
import com.cnf.module_inspection.entity.tasks.OccInspectionTasks;
import com.cnf.module_inspection.service.exception.HttpBadRequestException;
import com.cnf.module_inspection.service.exception.HttpNoFoundException;
import com.cnf.module_inspection.service.exception.HttpServerErrorException;
import com.cnf.module_inspection.service.exception.HttpUnAuthorizedException;
import com.cnf.module_inspection.service.exception.HttpUnknownErrorException;
import com.cnf.module_inspection.service.remote.OccInspectionApiService;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.lang.ref.WeakReference;


public class FetchOccInspectionDispatchTask extends AsyncTask<Void, Void, OccInspectionTasks> {

  private final WeakReference<Fragment> fragmentWeakReference;
  private final WeakReference<HomeActivity> activityWeakReference;

  public FetchOccInspectionDispatchTask(@NonNull Fragment fragment) {
    this.fragmentWeakReference = new WeakReference<>(fragment);
    this.activityWeakReference = new WeakReference<>((HomeActivity) fragment.getActivity());
  }

  @Override
  protected OccInspectionTasks doInBackground(Void... voids) {
    OccInspectionTasks occInspectionTasks = null;
    HomeActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return null;
    }
    SharedPreferences sp = activity.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    int muniCode = sp.getInt(SP_KEY_MUNICIPALITY_CODE, -1);
    int authPeriodId = sp.getInt(SP_KEY_AUTH_PERIOD_ID, -1);
    String loginUserToken = sp.getString(SP_KEY_USER_LOGIN_TOKEN, null);

    OccInspectionApiService occInspectionApiService = OccInspectionApiService.getInstance();

    try {
      occInspectionTasks = occInspectionApiService.getOccInspectionDispatch(loginUserToken, String.valueOf(authPeriodId), String.valueOf(muniCode), null);
      return occInspectionTasks;
    } catch (HttpNoFoundException | HttpBadRequestException | IOException e) {
      Log.e(TAG, e.toString());
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_INVALID_CLIENT_MSG, Snackbar.LENGTH_LONG).show();
      return null;
    } catch (HttpServerErrorException e) {
      Log.e(TAG, e.toString());
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_INVALID_SERVER_MSG, Snackbar.LENGTH_LONG).show();
      return null;
    } catch (HttpUnknownErrorException e) {
      Log.e(TAG, e.toString());
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_UNKNOWN_MSG, Snackbar.LENGTH_LONG).show();
      return null;
    } catch (HttpUnAuthorizedException e) {
      Log.i(TAG, e.toString());
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_INVALID_LOGIN_AUTHORIZATION_MSG, Snackbar.LENGTH_LONG).show();
      return null;
    } catch (JsonSyntaxException e) {
      Log.i(TAG, e.toString());
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_INVALID_SERVER_MSG, Snackbar.LENGTH_LONG).show();
      return null;
    }
  }

  @Override
  protected void onPostExecute(OccInspectionTasks occInspectionTasks) {
    super.onPostExecute(occInspectionTasks);
    HomeActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return;
    }
    Fragment fragment = fragmentWeakReference.get();
    if (fragment == null) {
      return;
    }
    activity.startActivity(new Intent(activity, MuniActivity.class));
  }
}

