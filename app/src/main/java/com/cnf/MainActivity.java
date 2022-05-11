package com.cnf;

import static com.cnf.utils.RequestConstants.USER_LOGIN_PATH;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.cnf.dto.Result;
import com.cnf.dto.UserLoginDTO;
import com.cnf.service.UserLoginActivityService;
import com.cnf.utils.RequestUtils;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private EditText loginUsername;
    private EditText loginPassword;
    private CheckBox loginIsSave;
    private Button loginButton;

    private boolean success = false;
    private UserLoginActivityService userLoginActivityService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.userLoginActivityService = new UserLoginActivityService(this);
        if (this.userLoginActivityService.isAuthLogin()) {
            Intent intent = new Intent(MainActivity.this, InspectionActivity.class);
            startActivity(intent);
            return;
        }
        this.loginUsername = findViewById(R.id.et_username);
        this.loginPassword = findViewById(R.id.et_password);
        this.loginIsSave = findViewById(R.id.cb_isSave);
        this.loginButton = findViewById(R.id.btn_login);
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUsername.getText().toString().trim();
                String pwd = loginPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                success = false;
                Thread t = new Thread() {
                    public void run() {
                        success = userLoginActivityService.userLogin(username, pwd);
                    }
                };
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!success) {
                    Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, MuniActivity.class);
                startActivity(intent);
            }
        });
    }
}