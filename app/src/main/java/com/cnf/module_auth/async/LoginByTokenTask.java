package com.cnf.module_auth.async;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import com.cnf.module_auth.activity.InitializationActivity;
import com.cnf.module_auth.activity.UserActivity;
import com.cnf.module_inspection.service.remote.UserLoginApiService;
import com.google.android.material.snackbar.Snackbar;
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
      //TODO
      return null;
    }
    UserLoginApiService userLoginApiService = UserLoginApiService.getInstance();
    return userLoginApiService.isLoginUserTokenValid(loginUserToken);
  }

  @Override
  protected void onPostExecute(Boolean isValidLoginUserToken) {
    super.onPostExecute(isValidLoginUserToken);
    UserActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
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
      Snackbar.make(activity.getWindow().getDecorView(), "Invalid User Token. Please login again..", Snackbar.LENGTH_LONG).show();
    }
  }
}
