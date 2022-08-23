package com.cnf.module_auth.adapter;

import static com.cnf.utils.AppConstants.SP_KEY_AUTH_PERIOD_ID;
import static com.cnf.utils.AppConstants.SP_KEY_MUNICIPALITY_CODE;
import static com.cnf.utils.AppConstants.SP_KEY_USER_ID;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.module_auth.activity.HomeActivity;
import com.cnf.R;
import com.cnf.module_auth.adapter.MunicipalityLoginAdapter.MuniAuthPeriodHolder;
import com.cnf.module_inspection.entity.infra.LoginMuniAuthPeriod;

import com.cnf.module_inspection.entity.infra.Municipality;
import com.cnf.module_inspection.entity.infra_heavy.LoginMuniAuthPeriodHeavy;
import java.util.List;

public class MunicipalityLoginAdapter extends RecyclerView.Adapter<MuniAuthPeriodHolder> {

  private final Context context;
  private final List<LoginMuniAuthPeriodHeavy> loginMuniAuthPeriodHeavyList;

  public MunicipalityLoginAdapter(Context context, List<LoginMuniAuthPeriodHeavy> loginMuniAuthPeriodHeavyList) {
    this.context = context;
    this.loginMuniAuthPeriodHeavyList = loginMuniAuthPeriodHeavyList;
  }

  @NonNull
  @Override
  public MuniAuthPeriodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new MuniAuthPeriodHolder(LayoutInflater.from(context).inflate(R.layout.layout_muni_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull MuniAuthPeriodHolder holder, int position) {
    LoginMuniAuthPeriod loginMuniAuthPeriod = loginMuniAuthPeriodHeavyList.get(position).getLoginMuniAuthPeriod();
    Municipality municipality = loginMuniAuthPeriodHeavyList.get(position).getMunicipality();

    Integer userId = loginMuniAuthPeriod.getUserId();
    Integer muniCode = loginMuniAuthPeriod.getMuniCode();
    Integer muniAuthPeriodId = loginMuniAuthPeriod.getMuniAuthPeriodId();
    String authorizedRole = loginMuniAuthPeriod.getAuthorizedRole();
    String muniName = municipality.getMuniName();

    holder.authPeriodIdTv.setText(muniAuthPeriodId + "");
    holder.authPeriodMuniNameTv.setText(muniName + "");
    holder.authPeriodMuniRoleTv.setText(authorizedRole);

    holder.authPeriodLoadBtn.setOnClickListener(view -> {
      SharedPreferences sp = context.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sp.edit();
      editor.putInt(SP_KEY_USER_ID, userId);
      editor.putInt(SP_KEY_MUNICIPALITY_CODE, muniCode);
      editor.putInt(SP_KEY_AUTH_PERIOD_ID, muniAuthPeriodId);
      editor.apply();
      Intent intent = new Intent(context, HomeActivity.class);
      context.startActivity(intent);
    });
  }

  @Override
  public int getItemCount() {
    return loginMuniAuthPeriodHeavyList.size();
  }

  static class MuniAuthPeriodHolder extends RecyclerView.ViewHolder {

    TextView authPeriodIdTv, authPeriodMuniNameTv, authPeriodMuniRoleTv;
    Button authPeriodLoadBtn;

    public MuniAuthPeriodHolder(@NonNull View itemView) {
      super(itemView);
      authPeriodIdTv = itemView.findViewById(R.id.tv_auth_period_id);
      authPeriodMuniRoleTv = itemView.findViewById(R.id.tv_auth_period_muni_role);
      authPeriodMuniNameTv = itemView.findViewById(R.id.tv_auth_period_muni_name);
      authPeriodLoadBtn = itemView.findViewById(R.id.btn_auth_period_load);
    }
  }
}
