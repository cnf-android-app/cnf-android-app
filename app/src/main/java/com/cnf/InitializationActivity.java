package com.cnf;

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

import com.cnf.db.InspectionDatabase;
import com.cnf.domain.infra.LoginMuniAuthPeriod;
import com.cnf.domain.infra.OccInspectionInfra;
import com.cnf.domain.tasks.OccInspectionTasks;
import com.cnf.service.exception.HttpBadRequestException;
import com.cnf.service.exception.HttpNoFoundException;
import com.cnf.service.exception.HttpServerErrorException;
import com.cnf.service.exception.HttpUnAuthorizedException;
import com.cnf.service.exception.HttpUnknownErrorException;
import com.cnf.service.exception.OccInspectionCopyNullPointerException;
import com.cnf.service.exception.OccInspectionInfraEmptyException;
import com.cnf.service.exception.OccLoginMuniAuthPeriodNullPointerException;
import com.cnf.service.local.OccInspectionInfraService;
import com.cnf.service.local.OccInspectionDispatchService;
import com.cnf.service.local.OccLoginMuniAuthPeriodService;
import com.cnf.service.remote.OccInspectionApiService;

import com.google.android.material.snackbar.Snackbar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InitializationActivity extends AppCompatActivity {

  private final Handler textHandler = new Handler();

  private OccInspectionApiService occInspectionApiService;
  private OccInspectionInfraService occInspectionInfraService;
  private OccLoginMuniAuthPeriodService occLoginMuniAuthPeriodService;
  private OccInspectionDispatchService occInspectionDispatchService;

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
    this.occInspectionInfraService = OccInspectionInfraService.getInstance();
    this.occLoginMuniAuthPeriodService = OccLoginMuniAuthPeriodService.getInstance();
    this.occInspectionDispatchService = OccInspectionDispatchService.getInstance();

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
      return;
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
          occInspectionInfraService.deleteOccInspectionInfraFromSQLite(inspectionDB);
          occLoginMuniAuthPeriodService.deleteAllLoginMuniAuthPeriodList(inspectionDB);
          occInspectionDispatchService.deleteOccInspectionTask(inspectionDB);
          occInspectionInfraService.insertOccInspectionInfraToSQLite(inspectionDB, occInspectionInfra);
          occLoginMuniAuthPeriodService.insertLoginMuniAuthPeriodList(inspectionDB, loginMuniAuthPeriodList);
          for (OccInspectionTasks occInspectionTasks : occInspectionTasksList) {
            occInspectionDispatchService.insertOccInspectionTask(inspectionDB, occInspectionTasks);
          }
          textHandler.post(() -> {
            progressBar.setProgress(80);
            progressBarTv.setText("Inserting data to local device..");
          });
          isSuccess = true;
        } catch (OccLoginMuniAuthPeriodNullPointerException | OccInspectionCopyNullPointerException e) {
          occInspectionInfraService.deleteOccInspectionInfraFromSQLite(inspectionDB);
          occLoginMuniAuthPeriodService.deleteAllLoginMuniAuthPeriodList(inspectionDB);
          occInspectionDispatchService.deleteOccInspectionTask(inspectionDB);
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
        textHandler.post(() -> {
          builder.setTitle("Initialization Failed!");
          builder.setMessage("Switch to Online mode!");
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