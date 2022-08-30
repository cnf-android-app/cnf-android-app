package com.cnf.module_auth.activity;


import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_IS_ONLINE;

import android.os.AsyncTask;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.cnf.R;
import com.cnf.module_auth.async.InitializeOccMunicipalitySessionTask;

public class MuniActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_muni);
  }

  @Override
  protected void onStart() {
    super.onStart();
    SharedPreferences sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    boolean isOnline = sp.getBoolean(SP_KEY_IS_ONLINE, false);

    androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.muni_login_toolbar);
    TextView toolBarTv = findViewById(R.id.muni_login_toolbar_title);

    toolBarTv.setText(String.format("Session - %s", isOnline ? "Online" : "Offline"));
    toolbar.setNavigationOnClickListener(v -> {
      Intent intent = new Intent(MuniActivity.this, MainActivity.class);
      startActivity(intent);
    });

    InitializeOccMunicipalitySessionTask task = new InitializeOccMunicipalitySessionTask(MuniActivity.this);
    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

}

