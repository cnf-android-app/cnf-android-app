package com.cnf.module_auth.async;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.ERROR_INFRASTRUCTURE_INITIALIZATION_MSG;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_IS_ONLINE;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cnf.R;
import com.cnf.module_auth.activity.MainActivity;
import com.cnf.module_auth.activity.MuniActivity;
import com.cnf.module_auth.activity.UserActivity;
import com.cnf.module_auth.adapter.MunicipalityLoginAdapter;
import com.cnf.module_auth.service.OccLoginMuniAuthPeriodService;
import com.cnf.module_auth.service.OccMunicipalitySessionInitializationService;
import com.cnf.module_inspection.entity.infra.LoginMuniAuthPeriod;
import com.cnf.module_inspection.entity.infra_heavy.LoginMuniAuthPeriodHeavy;
import com.cnf.module_inspection.entity.tasks.OccInspectionTasks;
import com.cnf.module_inspection.service.exception.HttpBadRequestException;
import com.cnf.module_inspection.service.exception.HttpNoFoundException;
import com.cnf.module_inspection.service.exception.HttpServerErrorException;
import com.cnf.module_inspection.service.exception.HttpUnAuthorizedException;
import com.cnf.module_inspection.service.exception.HttpUnknownErrorException;
import com.cnf.module_inspection.service.remote.OccInspectionApiService;
import com.google.android.material.snackbar.Snackbar;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class InitializeOccMunicipalitySessionTask extends AsyncTask<Void, Void, List<LoginMuniAuthPeriodHeavy>> {

  private final WeakReference<MuniActivity> activityWeakReference;


  public InitializeOccMunicipalitySessionTask(@NonNull Activity activity) {
    this.activityWeakReference = new WeakReference<>((MuniActivity) activity);
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    MuniActivity activity = activityWeakReference.get();
    if (activity == null) {
      //TODO
      return;
    }
    LinearLayout loginProgress = activity.findViewById(R.id.ll_municipality_init_progress_container);
    loginProgress.setVisibility(View.VISIBLE);
  }

  @Override
  protected List<LoginMuniAuthPeriodHeavy> doInBackground(Void... voids) {
    List<LoginMuniAuthPeriodHeavy> list = new ArrayList<>();
    MuniActivity activity = activityWeakReference.get();
    if (activity == null) {
      //TODO
      return list;
    }
    SharedPreferences sp = activity.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    String userLoginToken = sp.getString(SP_KEY_USER_LOGIN_TOKEN, null);
    boolean isOnline = sp.getBoolean(SP_KEY_IS_ONLINE, false);

    OccInspectionApiService occInspectionApiService = OccInspectionApiService.getInstance();

    List<LoginMuniAuthPeriod> loginMuniAuthPeriodList;
    List<OccInspectionTasks> occInspectionTasksList = new ArrayList<>();

    if (isOnline) {
      try {
        loginMuniAuthPeriodList = occInspectionApiService.getLoginMuniAuthPeriodList(userLoginToken);
      } catch (HttpBadRequestException | HttpServerErrorException | HttpUnknownErrorException | HttpNoFoundException | HttpUnAuthorizedException | IOException e) {
        Log.e(TAG, e.toString());
        Snackbar.make(activity.getWindow().getDecorView(), ERROR_INFRASTRUCTURE_INITIALIZATION_MSG, Snackbar.LENGTH_LONG).show();
        return list;
      }
      if (loginMuniAuthPeriodList == null) {
        return list;
      }
      try {
        for (LoginMuniAuthPeriod loginMuniAuthPeriod : loginMuniAuthPeriodList) {
          OccInspectionTasks occInspectionTasks = occInspectionApiService.getOccInspectionDispatch(
              userLoginToken,
              String.valueOf(loginMuniAuthPeriod.getMuniAuthPeriodId()),
              String.valueOf(loginMuniAuthPeriod.getMuniCode()), null);
          occInspectionTasksList.add(occInspectionTasks);
        }
      } catch (HttpBadRequestException | HttpServerErrorException | HttpUnknownErrorException | HttpNoFoundException | HttpUnAuthorizedException | IOException e) {
        Log.e(TAG, e.toString());
        Snackbar.make(activity.getWindow().getDecorView(), ERROR_INFRASTRUCTURE_INITIALIZATION_MSG, Snackbar.LENGTH_LONG).show();
        return list;
      }
      OccMunicipalitySessionInitializationService.getInstance(activity).initializeMunicipalitySession(loginMuniAuthPeriodList, occInspectionTasksList);
    }
    list = OccLoginMuniAuthPeriodService.getInstance(activity).getLoginMuniAuthPeriodHeavyList();
    return list;
  }

  @Override
  protected void onPostExecute(List<LoginMuniAuthPeriodHeavy> list) {
    super.onPostExecute(list);

    MuniActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return;
    }

    LinearLayout loginProgress = activity.findViewById(R.id.ll_municipality_init_progress_container);
    loginProgress.setVisibility(View.GONE);

    if (list == null) {
      new AlertDialog
          .Builder(activity)
          .setTitle("Alert")
          .setMessage("Server issue! Back to Login Page")
          .setPositiveButton("Yes", (dialog, which) -> {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
          })
          .create()
          .show();
    }

    RecyclerView loginMunicipalityListRv = activity.findViewById(R.id.rv_muniList);
    loginMunicipalityListRv.setLayoutManager(new LinearLayoutManager(activity));
    MunicipalityLoginAdapter adapter = new MunicipalityLoginAdapter(activity, list);
    loginMunicipalityListRv.setAdapter(adapter);

  }
}
