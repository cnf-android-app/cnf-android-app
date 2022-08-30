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
import com.cnf.module_inspection.entity.infra.LoginMuniAuthPeriod;
import com.cnf.module_inspection.entity.infra.OccInspectionInfra;
import com.cnf.module_inspection.entity.tasks.OccInspectionTasks;
import com.cnf.module_inspection.service.exception.HttpBadRequestException;
import com.cnf.module_inspection.service.exception.HttpNoFoundException;
import com.cnf.module_inspection.service.exception.HttpServerErrorException;
import com.cnf.module_inspection.service.exception.HttpUnAuthorizedException;
import com.cnf.module_inspection.service.exception.HttpUnknownErrorException;
import com.cnf.module_inspection.service.exception.OccInspectionInfraEmptyException;
import com.cnf.module_inspection.service.remote.OccInspectionApiService;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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
      //TODO
      return;
    }
    TextView progressBarTv = activity.findViewById(R.id.initialization_progress_bar_sync_message);
    progressBarTv.setText("Synchronizing infrastructure from the server..");
  }

  @Override
  protected Boolean doInBackground(Void... voids) {
    InitializationActivity activity = activityWeakReference.get();
    if (activity == null) {
      //TODO
      return false;
    }
    SharedPreferences sp = activity.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, MODE_PRIVATE);
    String userLoginToken = sp.getString(SP_KEY_USER_LOGIN_TOKEN, null);
    if (userLoginToken == null) {
      //TODO
      return false;
    }
    OccInspectionInfra occInspectionInfra;
    List<LoginMuniAuthPeriod> loginMuniAuthPeriodList;
    List<OccInspectionTasks> occInspectionTasksList = new ArrayList<>();
    OccInspectionApiService occInspectionApiService = OccInspectionApiService.getInstance();

    OccInspectionSystemInitializationService occInspectionSystemInitializationService = OccInspectionSystemInitializationService.getInstance(activity);
    try {
      occInspectionInfra = occInspectionApiService.getOccInspectionInfra(userLoginToken);
      loginMuniAuthPeriodList = occInspectionApiService.getLoginMuniAuthPeriodList(userLoginToken);
      if (loginMuniAuthPeriodList == null || loginMuniAuthPeriodList.isEmpty()) {
        //TODO
        return false;
      }
      for (LoginMuniAuthPeriod loginMuniAuthPeriod : loginMuniAuthPeriodList) {
        OccInspectionTasks occInspectionTasks = occInspectionApiService.getOccInspectionDispatch(
            userLoginToken,
            String.valueOf(loginMuniAuthPeriod.getMuniAuthPeriodId()),
            String.valueOf(loginMuniAuthPeriod.getMuniCode()), null);
        occInspectionTasksList.add(occInspectionTasks);
      }

    } catch (OccInspectionInfraEmptyException
        | HttpNoFoundException
        | HttpBadRequestException
        | IOException
        | HttpServerErrorException
        | HttpUnknownErrorException
        | HttpUnAuthorizedException e) {
      //TODO
      e.printStackTrace();
      return false;
    }

    try {
      occInspectionSystemInitializationService.initializeSystem(occInspectionInfra, loginMuniAuthPeriodList, occInspectionTasksList);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  @Override
  protected void onPostExecute(Boolean isSuccess) {
    super.onPostExecute(isSuccess);
    InitializationActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
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
          .setMessage("Back to Login!")
          .setPositiveButton("Yes", (dialog, which) -> {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
          })
          .create()
          .show();
    }
  }
}
