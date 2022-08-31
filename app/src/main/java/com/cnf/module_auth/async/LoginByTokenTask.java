package com.cnf.module_auth.async;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;
import static com.cnf.utils.AppConstants.TOAST_INVALID_CLIENT_MSG;
import static com.cnf.utils.AppConstants.TOAST_INVALID_LOGIN_AUTHORIZATION_MSG;
import static com.cnf.utils.AppConstants.TOAST_INVALID_SERVER_MSG;
import static com.cnf.utils.AppConstants.TOAST_UNKNOWN_MSG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import com.cnf.module_auth.activity.InitializationActivity;
import com.cnf.module_auth.activity.UserActivity;
import com.cnf.module_inspection.service.exception.HttpBadRequestException;
import com.cnf.module_inspection.service.exception.HttpNoFoundException;
import com.cnf.module_inspection.service.exception.HttpServerErrorException;
import com.cnf.module_inspection.service.exception.HttpUnAuthorizedException;
import com.cnf.module_inspection.service.exception.HttpUnknownErrorException;
import com.cnf.module_inspection.service.remote.UserLoginApiService;
import com.google.android.material.snackbar.Snackbar;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class LoginByTokenTask extends AsyncTask<Void, Void, Boolean> {

  private final WeakReference<UserActivity> activityWeakReference;
  private final String loginUserToken;

  public LoginByTokenTask(@NonNull Activity activity, String loginUserToken) {
    this.activityWeakReference = new WeakReference<>((UserActivity) activity);
    this.loginUserToken = loginUserToken;
  }

  @Override
  protected Boolean doInBackground(Void... voids) {
    UserActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "UserActivity null");
      return null;
    }
    UserLoginApiService userLoginApiService = UserLoginApiService.getInstance();
    try {
      return userLoginApiService.isLoginUserTokenValid(loginUserToken);
    } catch (HttpNoFoundException | HttpBadRequestException | IOException e) {
      Log.e(TAG, e.toString());
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_INVALID_CLIENT_MSG, Snackbar.LENGTH_LONG).show();
    } catch (HttpServerErrorException e) {
      Log.e(TAG, e.toString());
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_INVALID_SERVER_MSG, Snackbar.LENGTH_LONG).show();
    } catch (HttpUnknownErrorException e) {
      Log.e(TAG, e.toString());
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_UNKNOWN_MSG, Snackbar.LENGTH_LONG).show();
    } catch (HttpUnAuthorizedException e) {
      Log.i(TAG, e.toString());
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_INVALID_LOGIN_AUTHORIZATION_MSG, Snackbar.LENGTH_LONG).show();
    }
    return null;
  }

  @Override
  protected void onPostExecute(Boolean isValidLoginUserToken) {
    super.onPostExecute(isValidLoginUserToken);
    UserActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "UserActivity null");
      return;
    }
    if (isValidLoginUserToken == null) {
      return;
    }
    if (isValidLoginUserToken) {
      Intent intent = new Intent(activity, InitializationActivity.class);
      activity.startActivity(intent);
    } else {
      SharedPreferences sp = activity.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sp.edit();
      editor.putString(SP_KEY_USER_LOGIN_TOKEN, null);
      editor.apply();
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_INVALID_LOGIN_AUTHORIZATION_MSG, Snackbar.LENGTH_LONG).show();
    }
  }
}
