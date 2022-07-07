package com.cnf;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.ERROR_INFRASTRUCTURE_INITIALIZATION_MSG;
import static com.cnf.utils.AppConstants.ERROR_VALID_MUNICIPALITY_PERIOD_INSERT_MSG;
import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_IS_ONLINE;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;

import android.app.ProgressDialog;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.cnf.adapter.MunicipalityLoginAdapter;
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.infra.LoginMuniAuthPeriod;
import com.cnf.domain.infra_heavy.LoginMuniAuthPeriodHeavy;
import com.cnf.domain.tasks.OccInspectionTasks;
import com.cnf.service.exception.HttpBadRequestException;
import com.cnf.service.exception.HttpNoFoundException;
import com.cnf.service.exception.HttpServerErrorException;
import com.cnf.service.exception.HttpUnAuthorizedException;
import com.cnf.service.exception.HttpUnknownErrorException;
import com.cnf.service.exception.OccLoginMuniAuthPeriodNullPointerException;
import com.cnf.service.local.OccInspectionInfraService;
import com.cnf.service.local.OccInspectionDispatchService;
import com.cnf.service.local.OccLoginMuniAuthPeriodService;
import com.cnf.service.remote.OccInspectionApiService;

import com.google.android.material.snackbar.Snackbar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MuniActivity extends AppCompatActivity {

  private final Handler textHandler = new Handler();

  private OccInspectionApiService occInspectionApiService;
  private OccLoginMuniAuthPeriodService occLoginMuniAuthPeriodService;
  private OccInspectionInfraService occInspectionInfraService;
  private OccInspectionDispatchService occInspectionDispatchService;
  private InspectionDatabase inspectionDB;

  private SharedPreferences sp;
  private Boolean isOnline;
  private String userLoginToken;

  private ProgressDialog progressDialog;
  private RecyclerView loginMunicipalityListRv;

  private List<LoginMuniAuthPeriodHeavy> loginMuniAuthPeriodHeavyList;
  private MunicipalityLoginAdapter municipalityLoginAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_muni);

    this.occInspectionApiService = OccInspectionApiService.getInstance();
    this.occLoginMuniAuthPeriodService = OccLoginMuniAuthPeriodService.getInstance();
    this.occInspectionInfraService = OccInspectionInfraService.getInstance();
    this.occInspectionDispatchService = OccInspectionDispatchService.getInstance();

    this.inspectionDB = Room.databaseBuilder(this, InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();
    this.loginMuniAuthPeriodHeavyList = new ArrayList<>();

    this.sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    this.isOnline = this.sp.getBoolean(SP_KEY_IS_ONLINE, false);
    this.userLoginToken = sp.getString(SP_KEY_USER_LOGIN_TOKEN, null);
  }

  @Override
  protected void onStart() {
    super.onStart();
    androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.muni_login_toolbar);
    TextView toolBarTv = findViewById(R.id.muni_login_toolbar_title);

    toolBarTv.setText(String.format("Session - %s", this.isOnline ? "Online" : "Offline"));
    toolbar.setNavigationOnClickListener(v -> {
      Intent intent = new Intent(MuniActivity.this, MainActivity.class);
      startActivity(intent);
    });

    this.progressDialog = new ProgressDialog(MuniActivity.this);
    this.progressDialog.setMessage("Loading Session..");
    this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    this.progressDialog.setProgress(0);
    this.progressDialog.show();

    new Thread(new Loading()).start();
  }

  class MyResetListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
      Intent intent = new Intent(MuniActivity.this, MainActivity.class);
      startActivity(intent);
    }
  }

  class Loading implements Runnable {

    @Override
    public void run() {
      List<LoginMuniAuthPeriod> loginMuniAuthPeriodList = null;
      List<OccInspectionTasks> occInspectionTasksList = new ArrayList<>();
      View view = getWindow().getDecorView();
      boolean isSuccess = false;
      if (isOnline) {
        boolean isDownloaded = false;
        try {
          loginMuniAuthPeriodList = occInspectionApiService.getLoginMuniAuthPeriodList(userLoginToken);
          textHandler.post(() -> {
            progressDialog.setProgress(25);
            progressDialog.setMessage("Synchronizing valid auth period list from the server..");
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
              progressDialog.setProgress(50);
              progressDialog.setMessage("Synchronizing inspection list list from the server..");
            });
            isDownloaded = true;
          }
        } catch (HttpBadRequestException | HttpServerErrorException | HttpUnknownErrorException | HttpNoFoundException | HttpUnAuthorizedException | IOException e) {
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
            occLoginMuniAuthPeriodService.deleteAllLoginMuniAuthPeriodList(inspectionDB);
            occInspectionDispatchService.deleteOccInspectionTask(inspectionDB);

            occLoginMuniAuthPeriodService.insertLoginMuniAuthPeriodList(inspectionDB, loginMuniAuthPeriodList);
            for (OccInspectionTasks occInspectionTasks : occInspectionTasksList) {
              occInspectionDispatchService.insertOccInspectionTask(inspectionDB, occInspectionTasks);
            }
            textHandler.post(() -> {
              progressDialog.setProgress(75);
              progressDialog.setMessage("Inserting data to local device..");
            });
            isSuccess = true;
          } catch (OccLoginMuniAuthPeriodNullPointerException e) {
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

        if (isSuccess) {
          textHandler.post(() -> {
            progressDialog.setProgress(100);
            progressDialog.setMessage("Online loading finished!");
          });
        } else {
          textHandler.post(() -> {
            progressDialog.setProgress(100);
            progressDialog.setMessage("Online loading failed!");
          });
        }
      }

      textHandler.post(() -> {
        progressDialog.dismiss();
      });

      loginMuniAuthPeriodHeavyList = occLoginMuniAuthPeriodService.getLoginMuniAuthPeriodHeavyList(inspectionDB);

      textHandler.post(() -> {
        loginMunicipalityListRv = findViewById(R.id.rv_muniList);
        loginMunicipalityListRv.setLayoutManager(new LinearLayoutManager(MuniActivity.this));
        municipalityLoginAdapter = new MunicipalityLoginAdapter(MuniActivity.this, loginMuniAuthPeriodHeavyList);
        loginMunicipalityListRv.setAdapter(municipalityLoginAdapter);
      });

    }
  }
}

