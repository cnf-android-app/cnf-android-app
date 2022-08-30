package com.cnf.module_inspection.fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_IS_ONLINE;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Switch;
import androidx.annotation.Nullable;
import com.cnf.R;
import com.cnf.module_auth.activity.MainActivity;
import com.cnf.module_auth.activity.MuniActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.bottom_nav_layout,container, false);

    Button btnLogout = v.findViewById(R.id.btn_logout);
    btnLogout.setOnClickListener(v1 -> {
      Intent intent = new Intent(v1.getContext(), MainActivity.class);
      SharedPreferences sp = v1.getContext().getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, MODE_PRIVATE);
      SharedPreferences.Editor editor = sp.edit();
      editor.putString(SP_KEY_USER_LOGIN_TOKEN, null);
      editor.apply();
      startActivity(intent);
    });

    Switch swIsOnline = v.findViewById(R.id.sw_is_online);
    SharedPreferences sp = v.getContext().getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    boolean isOnline = sp.getBoolean(SP_KEY_IS_ONLINE, false);
    if (isOnline) {
      swIsOnline.setChecked(true);
    }else {
      swIsOnline.setChecked(false);
    }
    swIsOnline.setOnCheckedChangeListener((buttonView, isChecked) -> {
      Intent intent = new Intent(buttonView.getContext(), MuniActivity.class);
      SharedPreferences.Editor editor = sp.edit();
      if(isChecked) {
        editor.putBoolean(SP_KEY_IS_ONLINE, true);
        editor.apply();
      } else {
        editor.putBoolean(SP_KEY_IS_ONLINE, false);
        editor.apply();
      }
      startActivity(intent);
    });

    Button btnChangeSession = v.findViewById(R.id.btn_change_session);
    btnChangeSession.setOnClickListener(v1 -> {
      Intent intent = new Intent(v1.getContext(), MuniActivity.class);
      startActivity(intent);
    });
    return v;
  }

}
