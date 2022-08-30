package com.cnf.module_auth.async;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;
import static com.cnf.utils.AppConstants.TOAST_INVALID_LOGIN_AUTHORIZATION_MSG;
import static com.cnf.utils.AppConstants.TOAST_NETWORK_CONNECTION_ERROR_MSG;
import static com.cnf.utils.AppConstants.TOAST_UNKNOWN_NONE_RESPONSE_MSG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import com.cnf.R;
import com.cnf.module_auth.activity.InitializationActivity;
import com.cnf.module_auth.activity.UserActivity;
import com.cnf.module_inspection.service.exception.HttpBadRequestException;
import com.cnf.module_inspection.service.exception.HttpNoFoundException;
import com.cnf.module_inspection.service.exception.HttpNoneResponseForLoginUserTokenException;
import com.cnf.module_inspection.service.exception.HttpServerErrorException;
import com.cnf.module_inspection.service.exception.HttpUnAuthorizedException;
import com.cnf.module_inspection.service.exception.HttpUnknownErrorException;
import com.cnf.module_inspection.service.remote.UserLoginApiService;
import com.google.android.material.snackbar.Snackbar;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.time.LocalDateTime;


public class LoginByPasswordTask extends AsyncTask<Void, Void, String> {

  private final WeakReference<UserActivity> activityWeakReference;
  private final String username;
  private final String password;

  public LoginByPasswordTask(@NonNull Activity activity, String username, String password) {
    this.activityWeakReference = new WeakReference<>((UserActivity) activity);
    this.username = username;
    this.password = password;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    UserActivity activity = activityWeakReference.get();
    if (activity == null) {
      //TODO
      return;
    }
    LinearLayout loginProgress = activity.findViewById(R.id.ll_login_progress_container);
    loginProgress.setVisibility(View.VISIBLE);
  }

  @Override
  protected String doInBackground(Void... voids) {
    String loginUserToken = null;
    UserActivity activity = activityWeakReference.get();
    if (activity == null) {
      //TODO
      return null;
    }
    UserLoginApiService userLoginApiService = UserLoginApiService.getInstance();
    try {
       loginUserToken = userLoginApiService.userLogin(username, password);
    } catch (IOException e) {
      Log.e(TAG, String.format("Date: %s, " + e, LocalDateTime.now()));
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_NETWORK_CONNECTION_ERROR_MSG, Snackbar.LENGTH_LONG).show();
    } catch (HttpUnAuthorizedException e) {
      Log.e(TAG, String.format("Date: %s, " + e, LocalDateTime.now()));
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_INVALID_LOGIN_AUTHORIZATION_MSG, Snackbar.LENGTH_LONG).show();
    } catch (HttpNoneResponseForLoginUserTokenException e) {
      Log.e(TAG, String.format("Date: %s, " + e, LocalDateTime.now()));
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_UNKNOWN_NONE_RESPONSE_MSG, Snackbar.LENGTH_LONG).show();
    } catch (HttpBadRequestException e) {
      Log.e(TAG, String.format("Date: %s, " + e, LocalDateTime.now()));
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_UNKNOWN_NONE_RESPONSE_MSG, Snackbar.LENGTH_LONG).show();
    } catch (HttpServerErrorException e) {
      Log.e(TAG, String.format("Date: %s, " + e, LocalDateTime.now()));
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_UNKNOWN_NONE_RESPONSE_MSG, Snackbar.LENGTH_LONG).show();
    } catch (HttpUnknownErrorException e) {
      Log.e(TAG, String.format("Date: %s, " + e, LocalDateTime.now()));
      Snackbar.make(activity.getWindow().getDecorView(), TOAST_UNKNOWN_NONE_RESPONSE_MSG, Snackbar.LENGTH_LONG).show();
    } catch (HttpNoFoundException e) {
      e.printStackTrace();
    }

    return loginUserToken;
  }

  @Override
  protected void onPostExecute(String loginUserToken) {
    super.onPostExecute(loginUserToken);
    UserActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return;
    }
    if (loginUserToken == null) {
      return;
    }
    LinearLayout loginProgress = activity.findViewById(R.id.ll_login_progress_container);
    loginProgress.setVisibility(View.GONE);
    SharedPreferences sp = activity.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();
    editor.putString(SP_KEY_USER_LOGIN_TOKEN, loginUserToken);
    editor.apply();
    Intent intent = new Intent(activity, InitializationActivity.class);
    activity.startActivity(intent);
  }
}
