package com.cnf;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.TOAST_NETWORK_CONNECTION_ERROR_MSG;
import static com.cnf.utils.AppConstants.TOAST_INVALID_LOGIN_AUTHORIZATION_MSG;
import static com.cnf.utils.AppConstants.TOAST_INVALID_USERNAME_OR_PASSWORD_MSG;
import static com.cnf.utils.AppConstants.TOAST_UNKNOWN_NONE_RESPONSE_MSG;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;

import android.app.ProgressDialog;
import android.os.Handler;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.cnf.service.exception.HttpBadRequestException;
import com.cnf.service.exception.HttpNoFoundException;
import com.cnf.service.exception.HttpNoneResponseForLoginUserTokenException;
import com.cnf.service.exception.HttpServerErrorException;
import com.cnf.service.exception.HttpUnAuthorizedException;
import com.cnf.service.exception.HttpUnknownErrorException;
import com.cnf.service.remote.UserLoginApiService;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.time.LocalDateTime;

public class UserActivity extends AppCompatActivity {

  private final Handler textHandler = new Handler();

  private EditText loginUsernameEt;
  private EditText loginPasswordEt;
  private Button loginBtn;

  private UserLoginApiService userLoginApiService;
  private String loginUserToken;
  private SharedPreferences sp;

  ProgressDialog progressDialog;

  private String username;
  private String password;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user);

    this.loginUsernameEt = findViewById(R.id.et_username);
    this.loginPasswordEt = findViewById(R.id.et_password);
    this.loginBtn = findViewById(R.id.btn_login);
    this.userLoginApiService = UserLoginApiService.getInstance();
    this.sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    this.loginUserToken = sp.getString(SP_KEY_USER_LOGIN_TOKEN, null);
    this.progressDialog = new ProgressDialog(UserActivity.this);
  }

  @Override
  protected void onStart() {
    super.onStart();

    if (loginUserToken != null && !loginUserToken.isEmpty()) {
      new Thread(new UserTokenLogin()).start();
    }
    loginBtn.setOnClickListener(view -> {
      username = loginUsernameEt.getText().toString().trim();
      password = loginPasswordEt.getText().toString().trim();
      if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
        Snackbar.make(view, TOAST_INVALID_USERNAME_OR_PASSWORD_MSG, Snackbar.LENGTH_LONG).show();
        return;
      }
      progressDialog.setMessage("User Login...");
      progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
      progressDialog.setIndeterminate(true);
      progressDialog.show();
      new Thread(new Login()).start();
    });
  }

  class UserTokenLogin implements Runnable {
    View view = UserActivity.this.getWindow().getDecorView();
    @Override
    public void run() {
      boolean isValidLoginUserToken = userLoginApiService.isLoginUserTokenValid(loginUserToken);
      if (isValidLoginUserToken) {
        Intent intent = new Intent(UserActivity.this, InitializationActivity.class);
        startActivity(intent);
      } else {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SP_KEY_USER_LOGIN_TOKEN, null);
        editor.apply();
        Snackbar.make(view, "Invalid User Token. Please login again..", Snackbar.LENGTH_LONG).show();
      }
    }
  }

  class Login implements Runnable {
    View view = UserActivity.this.getWindow().getDecorView();
    @Override
    public void run() {
      try {
        loginUserToken = userLoginApiService.userLogin(username, password);
        SharedPreferences sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SP_KEY_USER_LOGIN_TOKEN, loginUserToken);
        editor.apply();
        Thread.sleep(1000);
        Intent intent = new Intent(UserActivity.this, InitializationActivity.class);
        startActivity(intent);
      } catch (IOException e) {
        Log.e(TAG, String.format("Date: %s, " + e, LocalDateTime.now()));
        Snackbar.make(view, TOAST_NETWORK_CONNECTION_ERROR_MSG, Snackbar.LENGTH_LONG).show();
      } catch (HttpUnAuthorizedException e) {
        Log.e(TAG, String.format("Date: %s, " + e, LocalDateTime.now()));
        Snackbar.make(view, TOAST_INVALID_LOGIN_AUTHORIZATION_MSG, Snackbar.LENGTH_LONG).show();
      } catch (HttpNoneResponseForLoginUserTokenException e) {
        Log.e(TAG, String.format("Date: %s, " + e, LocalDateTime.now()));
        Snackbar.make(view, TOAST_UNKNOWN_NONE_RESPONSE_MSG, Snackbar.LENGTH_LONG).show();
      } catch (HttpBadRequestException e) {
        Log.e(TAG, String.format("Date: %s, " + e, LocalDateTime.now()));
        Snackbar.make(view, TOAST_UNKNOWN_NONE_RESPONSE_MSG, Snackbar.LENGTH_LONG).show();
      } catch (HttpServerErrorException e) {
        Log.e(TAG, String.format("Date: %s, " + e, LocalDateTime.now()));
        Snackbar.make(view, TOAST_UNKNOWN_NONE_RESPONSE_MSG, Snackbar.LENGTH_LONG).show();
      } catch (HttpUnknownErrorException e) {
        Log.e(TAG, String.format("Date: %s, " + e, LocalDateTime.now()));
        Snackbar.make(view, TOAST_UNKNOWN_NONE_RESPONSE_MSG, Snackbar.LENGTH_LONG).show();
      } catch (HttpNoFoundException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        textHandler.post(() -> progressDialog.dismiss());
      }
    }
  }
}