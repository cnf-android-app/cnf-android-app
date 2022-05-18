package com.cnf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.cnf.adapter.MuniLoginAdapter;
import com.cnf.dto.LoginMuniDTO;
import com.cnf.service.api.MuniLoginActivityService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class MuniActivity extends AppCompatActivity {

    private RecyclerView loginMuniListRv;
    private List<LoginMuniDTO> loginMuniDTOList;
    private MuniLoginActivityService muniLoginActivityService;
    private boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muni);
        this.muniLoginActivityService = MuniLoginActivityService.getInstance(this);
        Thread t = new Thread() {
            public void run() {
                try {
                    loginMuniDTOList = muniLoginActivityService.getMunicipalityList();
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
            return;
        }
        this.loginMuniListRv = findViewById(R.id.rv_muniList);
        this.loginMuniListRv.setLayoutManager(new LinearLayoutManager(MuniActivity.this));
        MuniLoginAdapter muniLoginAdapter = new MuniLoginAdapter(MuniActivity.this, loginMuniDTOList);
        this.loginMuniListRv.setAdapter(muniLoginAdapter);
    }

}