package com.cnf.adapter;

import static com.cnf.utils.AppConstants.MUNICIPALITY_CODE;
import static com.cnf.utils.AppConstants.USER_ID_KEY;
import static com.cnf.utils.AppConstants.USER_SESSION_SHARE_PREFERENCE_NAME;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.InspectionActivity;
import com.cnf.R;
import com.cnf.dto.LoginMuniDTO;
import com.cnf.service.api.MuniLoginActivityService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public class MuniLoginAdapter extends RecyclerView.Adapter<MuniLoginAdapter.LinearViewHolder> {

    private Context context;
    private List<LoginMuniDTO> loginMuniDTOList;
    private MuniLoginActivityService muniLoginActivityService;
    boolean success;

    public MuniLoginAdapter(Context context, List<LoginMuniDTO> loginMuniDTOList) {
        this.success = false;
        this.context = context;
        this.loginMuniDTOList = loginMuniDTOList;
        this.muniLoginActivityService = MuniLoginActivityService.getInstance(this.context);
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_muni_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        Integer userId = loginMuniDTOList.get(position).getUserid();
        Integer muniCode = loginMuniDTOList.get(position).getMuni_municode();
        String muniName = loginMuniDTOList.get(position).getMuniname().toUpperCase(Locale.ROOT);
        String userRole = loginMuniDTOList.get(position).getUserrole().toUpperCase(Locale.ROOT);
        holder.municipalityNameTv.setText(muniName);
        holder.userRoleTv.setText(userRole);
        holder.municipalityLoginBtn.setOnClickListener(view -> {
            success = false;
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        success = muniLoginActivityService.municipalityLogin(muniCode);
                    } catch (IOException e) {
                        Log.e("TAG", String.format("Date: %s, " + e, LocalDateTime.now()));
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
                Toast.makeText(context, "Municipality Session is Expired", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sp = context.getSharedPreferences(USER_SESSION_SHARE_PREFERENCE_NAME, context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(USER_ID_KEY, userId);
            editor.putInt(MUNICIPALITY_CODE, muniCode);
            editor.commit();

            Intent intent = new Intent(context, InspectionActivity.class);
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return loginMuniDTOList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {

        TextView municipalityNameTv, userRoleTv;
        Button municipalityLoginBtn;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            municipalityNameTv = itemView.findViewById(R.id.tv_muni_name);
            userRoleTv = itemView.findViewById(R.id.tv_muni_role);
            municipalityLoginBtn = itemView.findViewById(R.id.btn_muni_login);
        }
    }
}
