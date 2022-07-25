package com.cnf;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.ERROR_INSPECTION_TASK_FETCH_MSG;
import static com.cnf.utils.AppConstants.SP_KEY_AUTH_PERIOD_ID;
import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;
import static com.cnf.utils.AppConstants.SP_KEY_IS_ONLINE;
import static com.cnf.utils.AppConstants.SP_KEY_MUNICIPALITY_CODE;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;
import static com.cnf.utils.AppConstants.SUCCESS_INSPECTION_TASK_FETCH_MSG;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.cnf.adapter.InspectionAdapter;
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.infra_heavy.OccInspectionDispatchHeavy;
import com.cnf.domain.tasks.OccInspection;
import com.cnf.domain.tasks.OccInspectionTasks;
import com.cnf.service.exception.HttpBadRequestException;
import com.cnf.service.exception.HttpNoFoundException;
import com.cnf.service.exception.HttpServerErrorException;
import com.cnf.service.exception.HttpUnAuthorizedException;
import com.cnf.service.exception.HttpUnknownErrorException;
import com.cnf.service.local.OccInspectedSpaceService;
import com.cnf.service.local.OccInspectionDispatchService;
import com.cnf.service.local.OccInspectionService;
import com.cnf.service.remote.OccInspectionApiService;
import com.cnf.service.local.OccInspectionInfraService;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class InspectionActivity extends AppCompatActivity {

  private final Handler textHandler = new Handler();

  private OccInspectionApiService occInspectionApiService;
  private OccInspectionInfraService occInspectionInfraService;
  private OccInspectionDispatchService occInspectionDispatchService;
  private OccInspectedSpaceService occInspectedSpaceService;
  private OccInspectionService occInspectionService;

  private InspectionDatabase inspectionDB;

  private List<OccInspectionDispatchHeavy> synchronizedInspectionDispatchHeavyList;
  private List<OccInspectionDispatchHeavy> unSynchronizeInspectionDispatchHeavyList;
  private InspectionAdapter occInspectionDispatchSynchronizedAdapter;
  private InspectionAdapter occInspectionDispatchUnSynchronizeAdapter;

  private List<OccInspectionDispatchHeavy> finishedInspectionDispatchHeavyList;
  private List<OccInspectionDispatchHeavy> unFinishInspectionDispatchHeavyList;
  private InspectionAdapter finishedOccInspectionDispatchAdapter;
  private InspectionAdapter unFinishOccInspectionDispatchAdapter;

  private SharedPreferences sp;
  private Integer muniCode;
  private Integer authPeriodId;
  private String loginUserToken;
  private Boolean isOnline;

  private FloatingActionButton btnFetchDispatch;
  private RecyclerView inspectionListRv;
  private AlertDialog.Builder dialog;
  private RadioButton rBtnUnFinish;
  private RadioButton rBtnFinished;
  private RadioButton rBtnSynchronized;
  private EditText etSearch;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_inspection);

    this.occInspectionApiService = OccInspectionApiService.getInstance();
    this.occInspectionInfraService = OccInspectionInfraService.getInstance();
    this.occInspectionDispatchService = OccInspectionDispatchService.getInstance();
    this.occInspectedSpaceService = OccInspectedSpaceService.getInstance();
    this.occInspectionService = OccInspectionService.getInstance();

    this.inspectionDB = Room.databaseBuilder(this, InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();

    this.sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    this.muniCode = sp.getInt(SP_KEY_MUNICIPALITY_CODE, -1);
    this.authPeriodId = sp.getInt(SP_KEY_AUTH_PERIOD_ID, -1);
    this.loginUserToken = this.sp.getString(SP_KEY_USER_LOGIN_TOKEN, null);
    this.isOnline = this.sp.getBoolean(SP_KEY_IS_ONLINE, false);

    this.rBtnUnFinish = findViewById(R.id.rb_inspection_un_finish);
    this.rBtnFinished = findViewById(R.id.rb_inspection_finished);
    this.rBtnSynchronized = findViewById(R.id.rb_inspection_synchronized);
    this.etSearch = findViewById(R.id.et_inspection_search);
  }

  @Override
  protected void onStart() {
    super.onStart();

    androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.inspection_toolbar);
    TextView inspectionToolBarTitleTv = findViewById(R.id.tv_occ_inspection_container_nav_title);
    inspectionToolBarTitleTv.setText(String.format("INSPECTION - %S", isOnline ? "ONLINE" : "OFFLINE"));

    btnFetchDispatch = findViewById(R.id.btn_inspection_fetch_dispatch);
    dialog = new AlertDialog.Builder(this);
    inspectionListRv = findViewById(R.id.rv_inspection_list);
    inspectionListRv.setLayoutManager(new LinearLayoutManager(InspectionActivity.this));

    setSupportActionBar(toolbar);

    toolbar.setNavigationOnClickListener(v -> {
      Intent intent = new Intent(InspectionActivity.this, MuniActivity.class);
      startActivity(intent);
      return;
    });

    toolbar.setOnMenuItemClickListener(item -> {
      if (item.getItemId() == R.id.occ_inspection_config) {
        BottomSheetDialog bottomSheet = new BottomSheetDialog();
        bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
        return true;
      }
      return true;
    });

    etSearch.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (rBtnUnFinish.isChecked()) {
          unFinishOccInspectionDispatchAdapter.getFilter().filter(s.toString());
        }else if (rBtnFinished.isChecked()) {
          finishedOccInspectionDispatchAdapter.getFilter().filter(s.toString());
        }else if (rBtnSynchronized.isChecked()) {
          occInspectionDispatchSynchronizedAdapter.getFilter().filter(s.toString());
        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

    btnFetchDispatch.setOnClickListener(view -> {
      dialog.setTitle("Confirm");
      dialog.setMessage("Are you sure to re-load inspection dispatches?");
      dialog.setPositiveButton("Yes", (dialog, which) -> {
        new Thread(new FetchOccInspectionDispatch()).start();
      });
      dialog.setNegativeButton("Dismiss", (dialog, which) -> dialog.dismiss());
      AlertDialog alertDialog = dialog.create();
      alertDialog.show();
    });

    new Thread(new LoadingDispatchHeavy()).start();
  }

  // TODO CASE TO IGNORE VS CASE TO REPLACE
  class FetchOccInspectionDispatch implements Runnable {

    @Override
    public void run() {
      OccInspectionTasks occInspectionTasks = null;
      try {
        occInspectionTasks = occInspectionApiService.getOccInspectionDispatch(loginUserToken, String.valueOf(authPeriodId), String.valueOf(muniCode), null);
        occInspectionDispatchService.insertOccInspectionTask(inspectionDB, occInspectionTasks);
        Snackbar.make(getWindow().getDecorView(), SUCCESS_INSPECTION_TASK_FETCH_MSG, Snackbar.LENGTH_LONG).show();
        startActivity(new Intent(InspectionActivity.this, InspectionActivity.class));
      } catch (HttpBadRequestException
          | HttpServerErrorException
          | HttpUnknownErrorException
          | HttpNoFoundException
          | HttpUnAuthorizedException
          | IOException e) {
        Log.e(TAG, String.format(e.toString()));
        Snackbar.make(getWindow().getDecorView(), ERROR_INSPECTION_TASK_FETCH_MSG, Snackbar.LENGTH_LONG).show();
      }
    }
  }

  public void onIsFinishedRadioButtonClicked(View view) {
    boolean checked = ((RadioButton) view).isChecked();
    switch (view.getId()) {
      case R.id.rb_inspection_un_finish:
        if (checked) {
          inspectionListRv.setAdapter(unFinishOccInspectionDispatchAdapter);
          rBtnUnFinish.setTextColor(getColor(R.color.on_switch_font));
          rBtnUnFinish.setBackground(getDrawable(R.drawable.toggle_widget_background));
          rBtnFinished.setTextColor(getColor(R.color.off_switch_font));
          rBtnFinished.setBackground(null);
          rBtnSynchronized.setTextColor(getColor(R.color.off_switch_font));
          rBtnSynchronized.setBackground(null);
        }
        break;
      case R.id.rb_inspection_finished:
        if (checked) {
          inspectionListRv.setAdapter(finishedOccInspectionDispatchAdapter);
          rBtnFinished.setTextColor(getColor(R.color.on_switch_font));
          rBtnFinished.setBackground(getDrawable(R.drawable.toggle_widget_background));
          rBtnUnFinish.setTextColor(getColor(R.color.off_switch_font));
          rBtnUnFinish.setBackground(null);
          rBtnSynchronized.setTextColor(getColor(R.color.off_switch_font));
          rBtnSynchronized.setBackground(null);
        }
        break;
      case R.id.rb_inspection_synchronized :
        if (checked) {
          inspectionListRv.setAdapter(occInspectionDispatchSynchronizedAdapter);
          rBtnSynchronized.setTextColor(getColor(R.color.on_switch_font));
          rBtnSynchronized.setBackground(getDrawable(R.drawable.toggle_widget_background));
          rBtnUnFinish.setTextColor(getColor(R.color.off_switch_font));
          rBtnUnFinish.setBackground(null);
          rBtnFinished.setTextColor(getColor(R.color.off_switch_font));
          rBtnFinished.setBackground(null);
        }
    }
  }

  class LoadingDispatchHeavy implements Runnable {
    @Override
    public void run() {
      synchronizedInspectionDispatchHeavyList = occInspectionDispatchService.getSynchronizedInspectionDispatchHeavy(inspectionDB, muniCode);
      unSynchronizeInspectionDispatchHeavyList = occInspectionDispatchService.getUnSynchronizeInspectionDispatchHeavy(inspectionDB, muniCode);
      occInspectionDispatchSynchronizedAdapter = new InspectionAdapter(InspectionActivity.this, synchronizedInspectionDispatchHeavyList, true);
      Map<String, List<OccInspectionDispatchHeavy>> occInspectionDispatchHeavyListMap = occInspectionDispatchService.getOccInspectionDispatchHeavyListMap(inspectionDB,
          unSynchronizeInspectionDispatchHeavyList);
      finishedInspectionDispatchHeavyList = occInspectionDispatchHeavyListMap.get("FINISHED");
      unFinishInspectionDispatchHeavyList = occInspectionDispatchHeavyListMap.get("UNFINISH");
      finishedOccInspectionDispatchAdapter = new InspectionAdapter(InspectionActivity.this, finishedInspectionDispatchHeavyList, false);
      unFinishOccInspectionDispatchAdapter = new InspectionAdapter(InspectionActivity.this, unFinishInspectionDispatchHeavyList, false);
      finishedOccInspectionDispatchAdapter.setFinished(true);
      unFinishOccInspectionDispatchAdapter.setFinished(false);
      textHandler.post(() -> inspectionListRv.setAdapter(unFinishOccInspectionDispatchAdapter));
    }
  }

  @Override
  public boolean onCreateOptionsMenu(@NonNull Menu menu) {
    getMenuInflater().inflate(R.menu.inspection_menu, menu);
    return true;
  }



  private void buildDeviceMode(Switch sw) {
    if (!sw.isChecked()) {
      btnFetchDispatch.setVisibility(View.GONE);
      SharedPreferences.Editor editor = sp.edit();
      editor.putBoolean(SP_KEY_IS_ONLINE, false);
      editor.apply();
      sw.setText("OFFLINE");
    } else {
      btnFetchDispatch.setVisibility(View.VISIBLE);
      SharedPreferences.Editor editor = sp.edit();
      editor.putBoolean(SP_KEY_IS_ONLINE, true);
      editor.apply();
      sw.setText("ONLINE");
    }
  }

}