package com.cnf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.cnf.domain.CodeElement;
import com.cnf.domain.CodeSource;
import com.cnf.domain.OccChecklistSpaceType;
import com.cnf.domain.OccChecklistSpaceTypeElement;
import com.cnf.domain.OccLocationDescription;
import com.cnf.domain.OccSpaceType;
import com.cnf.dto.InspectionTaskDTO;
import com.cnf.service.InspectionActivityService;

import java.util.List;

public class LoadingActivity extends AppCompatActivity {

    private InspectionActivityService inspectionActivityService;
    private List<CodeSource> codeSourceList;
    private List<CodeElement> codeElementList;
    private List<OccChecklistSpaceType> occChecklistSpaceTypeList;
    private List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList;
    private List<OccSpaceType> occSpaceTypeList;
    private List<OccLocationDescription> occLocationDescriptionList;
    private String token;
    private boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        this.token = getSharedPreferences("muni_token", MODE_PRIVATE).getString("user_muni_login_token", null);
        this.inspectionActivityService = new InspectionActivityService(LoadingActivity.this);

        Thread t = new Thread() {
            @Override
            public void run() {

                inspectionActivityService.deleteAllCodeSourceList();
                inspectionActivityService.deleteAllCodeElementList();
                inspectionActivityService.deleteAllOccChecklistSpaceTypeList();
                inspectionActivityService.deleteAllOccChecklistSpaceTypeElementList();
                inspectionActivityService.deleteAllOccChecklistSpaceTypeElementList();
                inspectionActivityService.deleteAllOccLocationDescriptionList();

                codeSourceList = inspectionActivityService.getCodeSourceList(token);
                codeElementList = inspectionActivityService.getCodeElementList(token);
                occChecklistSpaceTypeList = inspectionActivityService.getOccChecklistSpaceTypeList(token);
                occChecklistSpaceTypeElementList = inspectionActivityService.getOccChecklistSpaceTypeElementList(token);
                occSpaceTypeList = inspectionActivityService.getOccSpaceTypeList(token);
                occLocationDescriptionList = inspectionActivityService.getOccLocationDescriptionList(token);

                if (codeSourceList == null
                        || codeElementList == null
                        || occChecklistSpaceTypeList == null
                        || occChecklistSpaceTypeElementList == null
                        || occSpaceTypeList == null
                        || occLocationDescriptionList == null) {
                    success = false;
                } else {
                    success = true;
                }

                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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