package com.cnf.module_auth.activity;

import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.TOAST_INVALID_USERNAME_OR_PASSWORD_MSG;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;

import android.os.AsyncTask;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.cnf.R;
import com.cnf.module_auth.async.LoginByPasswordTask;
import com.cnf.module_auth.async.LoginByTokenTask;
import com.google.android.material.snackbar.Snackbar;

public class UserActivity extends AppCompatActivity {

  private EditText loginUsernameEt;
  private EditText loginPasswordEt;
  private Button loginBtn;


  private String username;
  private String password;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user);

    this.loginUsernameEt = findViewById(R.id.et_username);
    this.loginPasswordEt = findViewById(R.id.et_password);
    this.loginBtn = findViewById(R.id.btn_login);

  }

  @Override
  protected void onStart() {
    super.onStart();

    SharedPreferences sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    String loginUserToken = sp.getString(SP_KEY_USER_LOGIN_TOKEN, null);

    if (loginUserToken != null && !loginUserToken.isEmpty()) {
      LoginByTokenTask task = new LoginByTokenTask(UserActivity.this, loginUserToken);
      task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    loginBtn.setOnClickListener(view -> {
      username = loginUsernameEt.getText().toString().trim();
      password = loginPasswordEt.getText().toString().trim();
      if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
        Snackbar.make(view, TOAST_INVALID_USERNAME_OR_PASSWORD_MSG, Snackbar.LENGTH_LONG).show();
        return;
      }
      LoginByPasswordTask task = new LoginByPasswordTask(UserActivity.this, username, password);
      task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    });

    loginUsernameEt.setOnFocusChangeListener((v, hasFocus) -> {
      if (!hasFocus) {
        hideKeyboard(v);
      }
    });

    loginPasswordEt.setOnFocusChangeListener((v, hasFocus) -> {
      if (!hasFocus) {
        hideKeyboard(v);
      }
    });
  }


  public void hideKeyboard(View view) {
    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }
}