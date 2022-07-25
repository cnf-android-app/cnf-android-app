package com.cnf;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_IS_ONLINE;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;

import android.database.CursorWindow;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {

  private Button onlineBtn;
  private Button offlineBtn;

  private SharedPreferences sp;
  private Boolean isOnline;
  private String loginUserToken;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    this.sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    this.isOnline = this.sp.getBoolean(SP_KEY_IS_ONLINE, false);
    this.loginUserToken = this.sp.getString(SP_KEY_USER_LOGIN_TOKEN, null);
    Log.d(TAG, String.format("Date: %s, %s, %s", LocalDateTime.now(), MuniActivity.class.getName(), "is online: " + this.isOnline));
    Log.d(TAG, String.format("Date: %s, %s, %s", LocalDateTime.now(), MuniActivity.class.getName(), "login user token: " + this.loginUserToken));

    try {
      Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
      field.setAccessible(true);
      field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    this.onlineBtn = findViewById(R.id.btn_online);
    this.offlineBtn = findViewById(R.id.btn_offline);

    this.onlineBtn.setOnClickListener(v -> {
      SharedPreferences sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sp.edit();
      editor.putBoolean(SP_KEY_IS_ONLINE, true);
      editor.apply();
      Intent intent = new Intent(MainActivity.this, UserActivity.class);
      startActivity(intent);
    });

    this.offlineBtn.setOnClickListener(v -> {
      SharedPreferences sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sp.edit();
      editor.putBoolean(SP_KEY_IS_ONLINE, false);
      editor.apply();
      Intent intent = new Intent(MainActivity.this, InitializationActivity.class);
      startActivity(intent);
    });
  }
}