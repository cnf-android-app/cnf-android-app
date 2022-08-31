package com.cnf.module_auth.activity;

import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_IS_INITIALIZED;
import static com.cnf.utils.AppConstants.SP_KEY_IS_ONLINE;

import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.cnf.R;
import com.cnf.module_auth.async.InitializeOccInspectionSystemTask;

public class InitializationActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_initialization);
  }

  @Override
  protected void onStart() {
    super.onStart();
    SharedPreferences sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    boolean isInitialized = sp.getBoolean(SP_KEY_IS_INITIALIZED, false);
    boolean isOnline = sp.getBoolean(SP_KEY_IS_ONLINE, false);
    if (isInitialized) {
      Intent intent = new Intent(InitializationActivity.this, MuniActivity.class);
      startActivity(intent);
      return;
    }
    if (!isOnline) {
      new AlertDialog
          .Builder(this)
          .setTitle("Alert")
          .setMessage("Initialization is not working in offline Mode. Please switch to online mode")
          .setPositiveButton("Yes", (dialog, which) -> startActivity(new Intent(InitializationActivity.this, MainActivity.class)))
          .create()
          .show();
    }
    InitializeOccInspectionSystemTask task = new InitializeOccInspectionSystemTask(InitializationActivity.this);
    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }
}