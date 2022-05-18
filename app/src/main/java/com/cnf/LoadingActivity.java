package com.cnf;

import static com.cnf.utils.AppConstants.LOGIN_TOKEN_SHARE_PREFERENCE_NAME;
import static com.cnf.utils.AppConstants.USER_MUNICIPALITY_LOGIN_TOKEN_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cnf.service.api.InspectionActivityService;

import java.time.LocalDateTime;

public class LoadingActivity extends AppCompatActivity {

    private InspectionActivityService inspectionActivityService;
    private String token;
    private boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        this.token = getSharedPreferences(LOGIN_TOKEN_SHARE_PREFERENCE_NAME, MODE_PRIVATE).getString(USER_MUNICIPALITY_LOGIN_TOKEN_KEY, null);
        this.inspectionActivityService = InspectionActivityService.getInstance(LoadingActivity.this);

        Thread t = new Thread() {
            @Override
            public void run() {
                inspectionActivityService.deleteALL();
                success = inspectionActivityService.getAll(token);
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    Log.e("TAG", String.format("Date: %s, " + e, LocalDateTime.now()));
                }
                Intent intent = null;
                if (success) {
                    intent = new Intent(LoadingActivity.this, InspectionActivity.class);
                } else {
                    intent = new Intent(LoadingActivity.this, MainActivity.class);
                }
                startActivity(intent);
            }
        };
        t.start();
    }
}