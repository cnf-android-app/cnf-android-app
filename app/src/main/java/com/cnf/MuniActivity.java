package com.cnf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cnf.adapter.MuniLoginAdapter;
import com.cnf.dto.LoginMuniDTO;
import com.cnf.service.MuniLoginActivityService;

import java.util.List;

public class MuniActivity extends AppCompatActivity {

    private RecyclerView loginMuniList;
    private List<LoginMuniDTO> muniLoginList;
    private MuniLoginActivityService muniLoginActivityService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muni);
        this.muniLoginActivityService = new MuniLoginActivityService(this);
        Thread t = new Thread() {
            public void run() {
                muniLoginList = muniLoginActivityService.getMuniList();
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loginMuniList = findViewById(R.id.rv_muniList);
        loginMuniList.setLayoutManager(new LinearLayoutManager(MuniActivity.this));
        MuniLoginAdapter muniLoginAdapter = new MuniLoginAdapter(MuniActivity.this, muniLoginList, muniLoginActivityService);
        loginMuniList.setAdapter(muniLoginAdapter);
    }

}