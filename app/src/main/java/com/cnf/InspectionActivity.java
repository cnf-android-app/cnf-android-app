package com.cnf;

import static com.cnf.utils.AppConstants.INVALID_LOGIN_AUTHORIZATION_MSG;
import static com.cnf.utils.AppConstants.LOGIN_TOKEN_SHARE_PREFERENCE_NAME;
import static com.cnf.utils.AppConstants.USER_MUNICIPALITY_LOGIN_TOKEN_KEY;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.cnf.adapter.InspectionAdapter;
import com.cnf.dto.InspectionTaskDTO;
import com.cnf.service.api.InspectionActivityService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class InspectionActivity extends AppCompatActivity {

    private RecyclerView inspectionListRv;
    private Button synchronizeBtn;
    private Button loadGuideBtn;
    private InspectionActivityService inspectionActivityService;
    private List<InspectionTaskDTO> inspectionTaskDTOList;
    private String token;
    private boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);

        this.synchronizeBtn = findViewById(R.id.btn_synchronization);
        this.loadGuideBtn = findViewById(R.id.btn_loading_guide);
        this.token = getSharedPreferences(LOGIN_TOKEN_SHARE_PREFERENCE_NAME, MODE_PRIVATE).getString(USER_MUNICIPALITY_LOGIN_TOKEN_KEY, null);
        this.inspectionActivityService = InspectionActivityService.getInstance(InspectionActivity.this);

        this.loadGuideBtn.setOnClickListener(view -> {
            Intent intent = new Intent(InspectionActivity.this, LoadingActivity.class);
            startActivity(intent);
            finish();
        });

        this.synchronizeBtn.setOnClickListener(view -> {
            Thread t = new Thread() {
                @Override
                public void run() {
                    finish();
                    inspectionActivityService.deleteAllInspectionList();
                    startActivity(getIntent());
                }
            };
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        success = false;

        Thread t = new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                try {
                    inspectionTaskDTOList = inspectionActivityService.getInspectionList(token);
                    success = true;
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
            Toast.makeText(InspectionActivity.this, INVALID_LOGIN_AUTHORIZATION_MSG, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(InspectionActivity.this, MainActivity.class);
            SharedPreferences sp = getSharedPreferences(LOGIN_TOKEN_SHARE_PREFERENCE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(USER_MUNICIPALITY_LOGIN_TOKEN_KEY, null);
            editor.commit();
            startActivity(intent);
            return;
        }

        inspectionListRv = findViewById(R.id.rv_inspection_list);
        inspectionListRv.setLayoutManager(new LinearLayoutManager(InspectionActivity.this));
        InspectionAdapter inspectionAdapter = new InspectionAdapter(InspectionActivity.this, inspectionTaskDTOList);
        inspectionListRv.setAdapter(inspectionAdapter);
    }

}