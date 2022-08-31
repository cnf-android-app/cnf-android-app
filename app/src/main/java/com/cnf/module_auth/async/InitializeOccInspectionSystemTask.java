package com.cnf.module_auth.async;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_IS_INITIALIZED;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.cnf.R;
import com.cnf.module_auth.activity.InitializationActivity;
import com.cnf.module_auth.activity.MainActivity;
import com.cnf.module_auth.activity.MuniActivity;
import com.cnf.module_auth.service.OccInspectionSystemInitializationService;
import com.cnf.module_inspection.entity.infra.OccInspectionInfra;
import com.cnf.module_inspection.service.exception.HttpBadRequestException;
import com.cnf.module_inspection.service.exception.HttpNoFoundException;
import com.cnf.module_inspection.service.exception.HttpServerErrorException;
import com.cnf.module_inspection.service.exception.HttpUnAuthorizedException;
import com.cnf.module_inspection.service.exception.HttpUnknownErrorException;
import com.cnf.module_inspection.service.remote.OccInspectionApiService;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class InitializeOccInspectionSystemTask extends AsyncTask<Void, Void, Boolean> {

  private final WeakReference<InitializationActivity> activityWeakReference;

  public InitializeOccInspectionSystemTask(@NonNull Activity activity) {
    this.activityWeakReference = new WeakReference<>((InitializationActivity) activity);
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    InitializationActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "InitializationActivity null");
      return;
    }
    TextView progressBarTv = activity.findViewById(R.id.initialization_progress_bar_sync_message);
    progressBarTv.setText("Synchronizing infrastructure from the server..");
  }

  @Override
  protected Boolean doInBackground(Void... voids) {
    InitializationActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "InitializationActivity null");
      return false;
    }
    SharedPreferences sp = activity.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, MODE_PRIVATE);
    String userLoginToken = sp.getString(SP_KEY_USER_LOGIN_TOKEN, null);
    if (userLoginToken == null) {
      return false;
    }

    OccInspectionInfra occInspectionInfra;
    OccInspectionApiService occInspectionApiService = OccInspectionApiService.getInstance();
    OccInspectionSystemInitializationService occInspectionSystemInitializationService = OccInspectionSystemInitializationService.getInstance(activity);

    try {
      occInspectionInfra = occInspectionApiService.getOccInspectionInfra(userLoginToken);
      if (occInspectionInfra == null) {
        return false;
      }
    } catch (HttpNoFoundException | HttpBadRequestException | IOException | HttpServerErrorException | HttpUnknownErrorException | HttpUnAuthorizedException | JsonSyntaxException e) {
      Log.e(TAG, e.toString());
      return false;
    }
    try {
      occInspectionSystemInitializationService.initializeSystem(occInspectionInfra);
    } catch (Exception e) {
      Log.e(TAG, e.toString());
      return false;
    }
    return true;
  }

  @Override
  protected void onPostExecute(Boolean isSuccess) {
    super.onPostExecute(isSuccess);
    InitializationActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "InitializationActivity null");
      return;
    }
    SharedPreferences.Editor editor = activity.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, MODE_PRIVATE).edit();
    if (isSuccess) {
      editor.putBoolean(SP_KEY_IS_INITIALIZED, true);
      editor.apply();
      new AlertDialog
          .Builder(activity)
          .setTitle("Initialization Finish!")
          .setMessage("Step to municipality session!")
          .setPositiveButton("Yes", (dialog, which) -> {
            Intent intent = new Intent(activity, MuniActivity.class);
            activity.startActivity(intent);
          })
          .create()
          .show();
    } else {
      editor.putBoolean(SP_KEY_IS_INITIALIZED, false);
      editor.putString(SP_KEY_USER_LOGIN_TOKEN, null);
      editor.apply();
      new AlertDialog
          .Builder(activity)
          .setTitle("Initialization Failed!")
          .setMessage("Try to login again, or contact CODENFORCE!")
          .setPositiveButton("Yes", (dialog, which) -> {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
          })
          .create()
          .show();
    }
  }
}
