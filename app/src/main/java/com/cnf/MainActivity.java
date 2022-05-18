package com.cnf;

import static com.cnf.utils.AppConstants.INVALID_USERNAME_OR_PASSWORD_MSG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.cnf.service.api.UserLoginActivityService;

import java.io.IOException;
import java.time.LocalDateTime;

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
        this.userLoginActivityService = UserLoginActivityService.getInstance(this);
        if (this.userLoginActivityService.isAuthLogin()) {
            Intent intent = new Intent(MainActivity.this, InspectionActivity.class);
            startActivity(intent);
            return;
        }
        this.loginUsername = findViewById(R.id.et_username);
        this.loginPassword = findViewById(R.id.et_password);
        this.loginIsSave = findViewById(R.id.cb_isSave);
        this.loginButton = findViewById(R.id.btn_login);
        this.loginButton.setOnClickListener(view -> {
            String username = loginUsername.getText().toString().trim();
            String pwd = loginPassword.getText().toString().trim();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
                Toast.makeText(MainActivity.this, INVALID_USERNAME_OR_PASSWORD_MSG, Toast.LENGTH_SHORT).show();
                return;
            }
            success = false;
            Thread t = new Thread() {
                public void run() {
                    try {
                        success = userLoginActivityService.userLogin(username, pwd);
                    } catch (IOException e) {
                        Log.e("TAG", String.format("Date: %s, " + e, LocalDateTime.now()));
                    }
                }
            };
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                Log.e("TAG", String.format("Date: %s, " + e, LocalDateTime.now()));
            }
            if (!success) {
                Toast.makeText(MainActivity.this, INVALID_USERNAME_OR_PASSWORD_MSG, Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(MainActivity.this, MuniActivity.class);
            startActivity(intent);
        });
    }
}