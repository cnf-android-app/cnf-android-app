package com.cnf.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.cnf.service.MuniLoginActivityService;

import java.util.List;
import java.util.Locale;

public class MuniLoginAdapter extends RecyclerView.Adapter<MuniLoginAdapter.LinearViewHolder> {

    private Context context;
    private List<LoginMuniDTO> muniLoginList;
    private MuniLoginActivityService muniLoginActivityService;
    boolean success = false;

    public MuniLoginAdapter(Context context, List<LoginMuniDTO> muniLoginList, MuniLoginActivityService muniLoginActivityService) {
        this.context = context;
        this.muniLoginList = muniLoginList;
        this.muniLoginActivityService = muniLoginActivityService;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_muni_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        Integer user_id = muniLoginList.get(position).getUserid();
        Integer muni_municode = muniLoginList.get(position).getMuni_municode();
        String muniName = muniLoginList.get(position).getMuniname().toUpperCase(Locale.ROOT);
        String userrole = muniLoginList.get(position).getUserrole().toUpperCase(Locale.ROOT);
        View itemView = holder.itemView;
        TextView muniNameTV = (TextView) itemView.findViewById(R.id.tv_muni_name);
        muniNameTV.setText(muniName);
        TextView userRoleTV = (TextView) itemView.findViewById(R.id.tv_muni_role);
        userRoleTV.setText(userrole);
        Button button = itemView.findViewById(R.id.btn_muni_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                success = false;
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        SharedPreferences sp = context.getSharedPreferences("user_session", context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("user_id", user_id);
                        editor.putInt("muni_id", muni_municode);
                        editor.commit();
                        success = muniLoginActivityService.muniLogin(muni_municode);
                        Intent intent = new Intent(context, InspectionActivity.class);
                        context.startActivity(intent);
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
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return muniLoginList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_muni_name);
        }
    }
}
