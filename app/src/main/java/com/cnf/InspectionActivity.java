package com.cnf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cnf.adapter.InspectionAdapter;
import com.cnf.dto.InspectionTaskDTO;
import com.cnf.service.InspectionActivityService;

import java.util.List;

public class InspectionActivity extends AppCompatActivity {

    private RecyclerView inspection_list_rv;
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
        this.token = getSharedPreferences("muni_token", MODE_PRIVATE).getString("user_muni_login_token", null);

        this.inspectionActivityService = new InspectionActivityService(InspectionActivity.this);

        this.loadGuideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InspectionActivity.this, LoadingActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        this.synchronizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        finish();
                        inspectionActivityService.deleteAllInspectionList();
                        startActivity(getIntent());
                        return;
                    }
                };
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        success = false;

        Thread t = new Thread() {
            @Override
            public void run() {
                inspectionTaskDTOList = inspectionActivityService.getInspectionList(token);
                if (inspectionTaskDTOList == null) {
                    success = false;
                } else {
                    success = true;
                }
            }
        };

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!success) {
            Toast.makeText(InspectionActivity.this, "Invalid Login Auth", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(InspectionActivity.this, MainActivity.class);
            SharedPreferences sp0 = getSharedPreferences("muni_token", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp0.edit();
            editor.putString("user_muni_login_token", null);
            editor.commit();
            startActivity(intent);
            return;
        }

        inspection_list_rv = findViewById(R.id.rv_inspection_list);
        inspection_list_rv.setLayoutManager(new LinearLayoutManager(InspectionActivity.this));
        InspectionAdapter inspectionAdapter = new InspectionAdapter(InspectionActivity.this, inspectionTaskDTOList);
        inspection_list_rv.setAdapter(inspectionAdapter);
    }

}