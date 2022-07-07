//package com.cnf;
//
//import static android.content.ContentValues.TAG;
//import static com.cnf.utils.AppConstants.ERROR_INFRASTRUCTURE_INITIALIZATION_MSG;
//import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;
//import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
//import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.view.View;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.room.Room;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.cnf.db.InspectionDatabase;
//import com.cnf.domain.infra.OccInspectionInfra;
//import com.cnf.service.exception.HttpBadRequestException;
//import com.cnf.service.exception.HttpNoFoundException;
//import com.cnf.service.exception.HttpServerErrorException;
//import com.cnf.service.exception.HttpUnAuthorizedException;
//import com.cnf.service.exception.HttpUnknownErrorException;
//import com.cnf.service.exception.OccInspectionInfraEmptyException;
//import com.cnf.service.local.OccInspectionInfraService;
//import com.cnf.service.remote.OccInspectionApiService;
//
//import com.google.android.material.snackbar.Snackbar;
//import java.io.IOException;
//
//public class LoadingActivity extends AppCompatActivity {
//
//  private OccInspectionApiService occInspectionApiService;
//  private OccInspectionInfraService occInspectionInfraService;
//  private OccInspectionInfraApiService occInspectionInfraApiService;
//
//  private InspectionDatabase inspectionDB;
//  private ProgressBar progressBar;
//  private TextView progressBarTv;
//
//  private String userLoginToken;
//  private SharedPreferences sp;
//
//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_loading);
//
//    this.sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
//    this.userLoginToken = this.sp.getString(SP_KEY_USER_LOGIN_TOKEN, null);
//
//    this.inspectionDB = Room.databaseBuilder(this, InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();
//    this.occInspectionApiService = OccInspectionApiService.getInstance();
//    this.occInspectionInfraService = OccInspectionInfraService.getInstance();
//    this.occInspectionInfraApiService = OccInspectionInfraApiService.getInstance();
//
//  }
//
//  @Override
//  protected void onStart() {
//    super.onStart();
//
//    this.progressBar = findViewById(R.id.loading_progress_bar_sync);
//    this.progressBarTv = findViewById(R.id.loading_progress_bar_sync_message);
//    this.progressBar.setMax(100);
//    this.progressBar.setProgress(10);
//
//    Thread t = new Thread() {
//      @Override
//      public void run() {
//
//        //OccCopy occCopy = null;
//        OccInspectionInfra occInspectionInfra = null;
//        View view = getWindow().getDecorView();
//        boolean isDownloaded = false;
//
//        try {
//          //occCopy = occInspectionApiService.getOccCopyFromApi(userLoginToken);
//          occInspectionInfra = occInspectionInfraApiService.getOccInspectionInfra(userLoginToken);
//          progressBar.setProgress(25);
//          progressBarTv.setText("Synchronizing infrastructure from the server..");
//          isDownloaded = true;
//        } catch (HttpBadRequestException | HttpServerErrorException | HttpUnknownErrorException | HttpNoFoundException | HttpUnAuthorizedException | IOException | OccInspectionInfraEmptyException e) {
//          Log.e(TAG, String.format(e.toString()));
//          Snackbar.make(view, ERROR_INFRASTRUCTURE_INITIALIZATION_MSG, Snackbar.LENGTH_LONG).show();
//        }
//
//        try {
//          sleep(1000);
//        } catch (InterruptedException e) {
//          Log.e(TAG, String.format(e.toString()));
//        }
//
//        if (isDownloaded) {
//          try {
//            occInspectionInfraService.deleteOccInspectionInfraFromSQLite(inspectionDB);
//            progressBar.setProgress(50);
//            progressBarTv.setText("Deleting data to local device..");
//            occInspectionInfraService.insertOccInspectionInfraToSQLite(inspectionDB, occInspectionInfra);
//            progressBar.setProgress(70);
//            progressBarTv.setText("Inserting data to local device..");
//          } catch (Exception e) {
//            Log.e(TAG, String.format(e.toString()));
//            Snackbar.make(view, ERROR_INFRASTRUCTURE_INITIALIZATION_MSG, Snackbar.LENGTH_LONG).show();
//          }
//        }
//        progressBar.setProgress(100);
//        progressBarTv.setText("Reloading finished!");
//
//        try {
//          sleep(3000);
//        } catch (InterruptedException e) {
//          Log.e(TAG, String.format(e.toString()));
//        }
//        Intent intent = new Intent(LoadingActivity.this, InspectionActivity.class);
//        startActivity(intent);
//      }
//    };
//    t.start();
//  }
//}