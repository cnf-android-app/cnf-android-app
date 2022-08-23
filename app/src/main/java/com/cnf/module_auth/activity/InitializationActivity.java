package com.cnf.module_auth.activity;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.ERROR_INFRASTRUCTURE_INITIALIZATION_MSG;
import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_IS_INITIALIZED;
import static com.cnf.utils.AppConstants.SP_KEY_IS_ONLINE;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cnf.R;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra.LoginMuniAuthPeriod;
import com.cnf.module_inspection.entity.infra.OccInspectionInfra;
import com.cnf.module_inspection.entity.tasks.OccInspectionTasks;
import com.cnf.module_inspection.service.exception.HttpBadRequestException;
import com.cnf.module_inspection.service.exception.HttpNoFoundException;
import com.cnf.module_inspection.service.exception.HttpServerErrorException;
import com.cnf.module_inspection.service.exception.HttpUnAuthorizedException;
import com.cnf.module_inspection.service.exception.HttpUnknownErrorException;
import com.cnf.module_inspection.service.exception.OccInspectionCopyNullPointerException;
import com.cnf.module_inspection.service.exception.OccInspectionInfraEmptyException;
import com.cnf.module_inspection.service.exception.OccLoginMuniAuthPeriodNullPointerException;
import com.cnf.module_inspection.service.local.OccInspectionInfraService;
import com.cnf.module_inspection.service.local.OccInspectionDispatchRepository;
import com.cnf.module_auth.service.OccLoginMuniAuthPeriodService;
import com.cnf.module_inspection.service.local.OccInspectionTaskRepository;
import com.cnf.module_inspection.service.remote.OccInspectionApiService;

import com.google.android.material.snackbar.Snackbar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InitializationActivity extends AppCompatActivity {

  private final Handler textHandler = new Handler();

  private OccInspectionApiService occInspectionApiService;
  private OccInspectionInfraService occInspectionInfraService;
  private OccLoginMuniAuthPeriodService occLoginMuniAuthPeriodService;
  private OccInspectionDispatchRepository occInspectionDispatchRepository;
  private OccInspectionTaskRepository occInspectionTaskRepository;

  private InspectionDatabase inspectionDB;

  private SharedPreferences sp;
  private String userLoginToken;
  private Boolean isInitialized;
  private Boolean isOnline;

  private ProgressBar progressBar;
  private TextView progressBarTv;
  private AlertDialog.Builder builder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_initialization);

    this.occInspectionApiService = OccInspectionApiService.getInstance();
    this.occInspectionInfraService = OccInspectionInfraService.getInstance(this);
    this.occLoginMuniAuthPeriodService = OccLoginMuniAuthPeriodService.getInstance(this);
    this.occInspectionDispatchRepository = OccInspectionDispatchRepository.getInstance(this);
    this.occInspectionTaskRepository = OccInspectionTaskRepository.getInstance(this);

    this.inspectionDB = Room.databaseBuilder(this, InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();

    this.sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    this.userLoginToken = this.sp.getString(SP_KEY_USER_LOGIN_TOKEN, null);
    this.isInitialized = this.sp.getBoolean(SP_KEY_IS_INITIALIZED, false);
    this.isOnline = this.sp.getBoolean(SP_KEY_IS_ONLINE, false);
  }

  @Override
  protected void onStart() {
    super.onStart();

    if (this.isInitialized) {
      Intent intent = new Intent(InitializationActivity.this, MuniActivity.class);
      startActivity(intent);
    }

    if (!this.isOnline) {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("Alert");
      builder.setMessage("Initialization is not working in offline Mode. Please switch to online mode");
      builder.setPositiveButton("Yes", (dialog, which) -> {
        Intent intent = new Intent(InitializationActivity.this, MainActivity.class);
        startActivity(intent);
      });
      builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
      AlertDialog alertDialog = builder.create();
      alertDialog.show();
    }

    this.builder = new AlertDialog.Builder(InitializationActivity.this);
    this.progressBar = findViewById(R.id.initialization_progress_bar_sync);
    this.progressBarTv = findViewById(R.id.initialization_progress_bar_sync_message);
    this.progressBar.setMax(100);
    this.progressBar.setProgress(0);

    new Thread(new Initialization()).start();
  }

  class Initialization implements Runnable {

    @Override
    public void run() {
      OccInspectionInfra occInspectionInfra = null;
      List<LoginMuniAuthPeriod> loginMuniAuthPeriodList = null;
      List<OccInspectionTasks> occInspectionTasksList = new ArrayList<>();
      View view = getWindow().getDecorView();
      boolean isDownloaded = false;
      boolean isSuccess = false;
      try {
        occInspectionInfra = occInspectionApiService.getOccInspectionInfra(userLoginToken);
        textHandler.post(() -> {
          progressBar.setProgress(20);
          progressBarTv.setText("Synchronizing infrastructure from the server..");
        });

        loginMuniAuthPeriodList = occInspectionApiService.getLoginMuniAuthPeriodList(userLoginToken);
        textHandler.post(() -> {
          progressBar.setProgress(40);
          progressBarTv.setText("Synchronizing valid auth period list from the server..");
        });

        if (loginMuniAuthPeriodList != null) {
          for (LoginMuniAuthPeriod loginMuniAuthPeriod : loginMuniAuthPeriodList) {
            OccInspectionTasks occInspectionTasks = occInspectionApiService.getOccInspectionDispatch(
                userLoginToken,
                String.valueOf(loginMuniAuthPeriod.getMuniAuthPeriodId()),
                String.valueOf(loginMuniAuthPeriod.getMuniCode()), null);
            occInspectionTasksList.add(occInspectionTasks);
          }
          textHandler.post(() -> {
            progressBar.setProgress(60);
            progressBarTv.setText("Synchronizing inspection list list from the server..");
          });
          isDownloaded = true;
        }
      } catch (HttpBadRequestException | HttpServerErrorException | HttpUnknownErrorException | HttpNoFoundException | HttpUnAuthorizedException | IOException | OccInspectionInfraEmptyException e) {
        Log.e(TAG, String.format(e.toString()));
        Snackbar.make(view, ERROR_INFRASTRUCTURE_INITIALIZATION_MSG, Snackbar.LENGTH_LONG).show();
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Log.e(TAG, String.format(e.toString()));
      }
      if (isDownloaded) {
        try {
          occInspectionInfraService.deleteOccInspectionInfraFromSQLite();
          occLoginMuniAuthPeriodService.deleteAllLoginMuniAuthPeriodList();
          occInspectionTaskRepository.deleteOccInspectionTask();
          occInspectionInfraService.insertOccInspectionInfraToSQLite(occInspectionInfra);
          occLoginMuniAuthPeriodService.insertLoginMuniAuthPeriodList(loginMuniAuthPeriodList);
          for (OccInspectionTasks occInspectionTasks : occInspectionTasksList) {
            occInspectionTaskRepository.insertOccInspectionTask( occInspectionTasks);
          }
          textHandler.post(() -> {
            progressBar.setProgress(80);
            progressBarTv.setText("Inserting data to local device..");
          });
          isSuccess = true;
        } catch (  OccInspectionCopyNullPointerException e) {
          occInspectionInfraService.deleteOccInspectionInfraFromSQLite();
          occLoginMuniAuthPeriodService.deleteAllLoginMuniAuthPeriodList();
          occInspectionTaskRepository.deleteOccInspectionTask();
          Log.e(TAG, String.format(e.toString()));
          Snackbar.make(view, ERROR_INFRASTRUCTURE_INITIALIZATION_MSG, Snackbar.LENGTH_LONG).show();
        }
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Log.e(TAG, String.format(e.toString()));
      }

      textHandler.post(() -> {
        progressBar.setProgress(100);
        progressBarTv.setText("Initialization finished!");
      });

      if (isSuccess) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(SP_KEY_IS_INITIALIZED, true);
        editor.apply();
        textHandler.post(() -> {
          builder.setTitle("Initialization Finish!");
          builder.setMessage("Step to municipality session!");
          builder.setPositiveButton("Yes", (dialog, which) -> {
            Intent intent = new Intent(InitializationActivity.this, MuniActivity.class);
            startActivity(intent);
          });
          AlertDialog alertDialog = builder.create();
          alertDialog.show();
        });
      } else {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SP_KEY_USER_LOGIN_TOKEN, null);
        editor.apply();
        textHandler.post(() -> {
          builder.setTitle("Initialization Failed!");
          builder.setMessage("Back to Login!");
          builder.setPositiveButton("Yes", (dialog, which) -> {
            Intent intent = new Intent(InitializationActivity.this, MainActivity.class);
            startActivity(intent);
          });
          AlertDialog alertDialog = builder.create();
          alertDialog.show();
        });
      }
    }
  }
}