package com.cnf.module_auth.activity;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_IS_ONLINE;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.cnf.R;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    SharedPreferences sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    boolean isOnline = sp.getBoolean(SP_KEY_IS_ONLINE, false);
    String loginUserToken = sp.getString(SP_KEY_USER_LOGIN_TOKEN, null);
    Log.d(TAG, String.format("Date: %s, %s, %s", LocalDateTime.now(), MuniActivity.class.getName(), "is online: " + isOnline));
    Log.d(TAG, String.format("Date: %s, %s, %s", LocalDateTime.now(), MuniActivity.class.getName(), "login user token: " + loginUserToken));
  }

  @Override
  protected void onStart() {
    super.onStart();
    Button onlineBtn = findViewById(R.id.btn_online);
    Button offlineBtn = findViewById(R.id.btn_offline);

    onlineBtn.setOnClickListener(v -> {
      SharedPreferences sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sp.edit();
      editor.putBoolean(SP_KEY_IS_ONLINE, true);
      editor.apply();
      Intent intent = new Intent(MainActivity.this, UserActivity.class);
      startActivity(intent);
    });

    offlineBtn.setOnClickListener(v -> {
      SharedPreferences sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sp.edit();
      editor.putBoolean(SP_KEY_IS_ONLINE, false);
      editor.apply();
      Intent intent = new Intent(MainActivity.this, InitializationActivity.class);
      startActivity(intent);
    });
  }
}