package com.cnf.module_inspection.fragment;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_AUTH_PERIOD_ID;
import static com.cnf.utils.AppConstants.SP_KEY_IS_ONLINE;
import static com.cnf.utils.AppConstants.SP_KEY_MUNICIPALITY_CODE;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cnf.module_auth.activity.HomeActivity;
import com.cnf.R;
import com.cnf.module_inspection.adapter.InspectionAdapter;
import com.cnf.module_inspection.entity.infra_heavy.OccInspectionDispatchHeavy;
import com.cnf.module_inspection.entity.tasks.OccInspectionTasks;
import com.cnf.module_inspection.service.exception.HttpBadRequestException;
import com.cnf.module_inspection.service.exception.HttpNoFoundException;
import com.cnf.module_inspection.service.exception.HttpServerErrorException;
import com.cnf.module_inspection.service.exception.HttpUnAuthorizedException;
import com.cnf.module_inspection.service.exception.HttpUnknownErrorException;
import com.cnf.module_inspection.service.local.OccInspectionDispatchRepository;
import com.cnf.module_inspection.service.local.OccInspectionTaskRepository;
import com.cnf.module_inspection.service.remote.OccInspectionApiService;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class InspectionDispatchFragment extends Fragment {

  public enum Category {FINISHED, UN_FINISH, SYNCHRONIZED}

  private OccInspectionApiService occInspectionApiService;
  private OccInspectionDispatchRepository occInspectionDispatchRepository;
  private OccInspectionTaskRepository occInspectionTaskRepository;

  private SharedPreferences sp;
  private Integer muniCode;
  private Integer authPeriodId;
  private String loginUserToken;
  private Boolean isOnline;

  private FloatingActionButton btnFetchDispatch;
  private RecyclerView rvInspectionList;
  private AlertDialog.Builder dialog;
  private RadioGroup radioGroup;
  private RadioButton rBtnUnFinish;
  private RadioButton rBtnFinished;
  private RadioButton rBtnSynchronized;
  private EditText etSearch;
  private TextView tvIsCompletedIndicator;
  private ImageButton imageBtnMore;
  private ImageButton imageBtnSearch;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_inspection_dispatch, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    this.occInspectionApiService = OccInspectionApiService.getInstance();
    this.occInspectionDispatchRepository = OccInspectionDispatchRepository.getInstance(view.getContext());
    this.occInspectionTaskRepository = OccInspectionTaskRepository.getInstance(view.getContext());

    this.sp = view.getContext().getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    this.muniCode = this.sp.getInt(SP_KEY_MUNICIPALITY_CODE, -1);
    this.authPeriodId = this.sp.getInt(SP_KEY_AUTH_PERIOD_ID, -1);
    this.loginUserToken = this.sp.getString(SP_KEY_USER_LOGIN_TOKEN, null);
    this.isOnline = this.sp.getBoolean(SP_KEY_IS_ONLINE, false);

    this.btnFetchDispatch = view.findViewById(R.id.btn_inspection_fetch_dispatch);
    this.rvInspectionList = view.findViewById(R.id.rv_inspection_list);
    this.dialog = new AlertDialog.Builder(view.getContext());
    this.radioGroup = view.findViewById(R.id.rg_inspection_is_finish_or_not);
    this.rBtnUnFinish = view.findViewById(R.id.rb_inspection_un_finish);
    this.rBtnFinished = view.findViewById(R.id.rb_inspection_finished);
    this.rBtnSynchronized = view.findViewById(R.id.rb_inspection_synchronized);
    this.etSearch = view.findViewById(R.id.et_inspection_search);
    this.tvIsCompletedIndicator = view.findViewById(R.id.tv_inspection_is_completed_indicator);
    this.imageBtnMore = view.findViewById(R.id.imageBtn_occ_inspection_more_icon);
    this.imageBtnSearch = view.findViewById(R.id.imageBtn_occ_inspection_search_icon);
    this.rvInspectionList.setLayoutManager(new LinearLayoutManager(getActivity()));

    if (!isOnline) {
      this.btnFetchDispatch.setEnabled(false);
    }

    this.imageBtnSearch.setOnClickListener(v -> {
      if (this.etSearch.getVisibility() == View.VISIBLE) {
        this.etSearch.setVisibility(View.GONE);
      } else {
        this.etSearch.setVisibility(View.VISIBLE);
      }
    });

    this.imageBtnMore.setOnClickListener(v -> {
      BottomSheetDialog bottomSheet = new BottomSheetDialog();
      bottomSheet.show(getActivity().getSupportFragmentManager(), "ModalBottomSheet");
    });

    this.etSearch.setOnFocusChangeListener((v, hasFocus) -> {
      if (hasFocus) {
        return;
      }
      hideKeyboard(v);
    });

    this.btnFetchDispatch.setOnClickListener(v -> {
      this.dialog.setTitle("Confirm");
      this.dialog.setMessage("Are you sure to re-load inspection dispatches?");
      this.dialog.setPositiveButton("Yes", (dialog, which) -> {
         new FetchOccInspectionDispatch(getContext(), occInspectionApiService, occInspectionTaskRepository, loginUserToken, authPeriodId, muniCode).execute();
      });
      this.dialog.setNegativeButton("Dismiss", (dialog, which) -> dialog.dismiss());
      AlertDialog alertDialog = this.dialog.create();
      alertDialog.show();
    });

    this.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
      if (checkedId == R.id.rb_inspection_un_finish) {
        rBtnUnFinish.setTextColor(getActivity().getColor(R.color.on_switch_font));
        rBtnUnFinish.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background));
        rBtnFinished.setTextColor(getActivity().getColor(R.color.off_switch_font));
        rBtnFinished.setBackground(null);
        rBtnSynchronized.setTextColor(getActivity().getColor(R.color.off_switch_font));
        rBtnSynchronized.setBackground(null);
      } else if (checkedId == R.id.rb_inspection_finished) {
        rBtnFinished.setTextColor(getActivity().getColor(R.color.on_switch_font));
        rBtnFinished.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background));
        rBtnUnFinish.setTextColor(getActivity().getColor(R.color.off_switch_font));
        rBtnUnFinish.setBackground(null);
        rBtnSynchronized.setTextColor(getActivity().getColor(R.color.off_switch_font));
        rBtnSynchronized.setBackground(null);
        tvIsCompletedIndicator.setVisibility(View.GONE);
      } else if (checkedId == R.id.rb_inspection_synchronized) {
        rBtnSynchronized.setTextColor(getActivity().getColor(R.color.on_switch_font));
        rBtnSynchronized.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background));
        rBtnUnFinish.setTextColor(getActivity().getColor(R.color.off_switch_font));
        rBtnUnFinish.setBackground(null);
        rBtnFinished.setTextColor(getActivity().getColor(R.color.off_switch_font));
        rBtnFinished.setBackground(null);
        tvIsCompletedIndicator.setVisibility(View.GONE);
      }
      loadingInspectionDispatch();
    });

    loadingInspectionDispatch();

  }

  private void loadingInspectionDispatch() {
    new LoadingOccInspectionDispatchRecycleView(occInspectionDispatchRepository, muniCode, getContext(),
        rBtnFinished.isChecked() ? Category.FINISHED : rBtnUnFinish.isChecked() ? Category.UN_FINISH : Category.SYNCHRONIZED, rvInspectionList, tvIsCompletedIndicator, etSearch).execute();
  }



  public static class LoadingOccInspectionDispatchRecycleView extends AsyncTask<Void, Void, InspectionAdapter> {

    private OccInspectionDispatchRepository occInspectionDispatchRepository;
    private RecyclerView recyclerView;
    private Context context;
    private Category category;
    private Integer muniCode;
    private EditText etSearch;
    private TextView tvIsCompletedIndicator;

    public LoadingOccInspectionDispatchRecycleView(OccInspectionDispatchRepository occInspectionDispatchRepository, Integer muniCode, Context context,
        Category category, RecyclerView recyclerView, TextView tvIsCompletedIndicator, EditText etSearch) {
      this.occInspectionDispatchRepository = occInspectionDispatchRepository;
      this.muniCode = muniCode;
      this.context = context;
      this.category = category;
      this.recyclerView = recyclerView;
      this.tvIsCompletedIndicator = tvIsCompletedIndicator;
      this.etSearch = etSearch;
    }

    @Override
    protected InspectionAdapter doInBackground(Void... voids) {
      InspectionAdapter ret = null;

      List<OccInspectionDispatchHeavy> synchronizedInspectionDispatchHeavyList = this.occInspectionDispatchRepository.getSynchronizedInspectionDispatchHeavy(this.muniCode);
      List<OccInspectionDispatchHeavy> unSynchronizeInspectionDispatchHeavyList = this.occInspectionDispatchRepository.getUnSynchronizeInspectionDispatchHeavy(this.muniCode);
      InspectionAdapter synchronizedOccInspectionDispatchAdapter = new InspectionAdapter(this.context, synchronizedInspectionDispatchHeavyList, true);

      Map<OccInspectionDispatchRepository.Category, List<OccInspectionDispatchHeavy>> occInspectionDispatchHeavyListMap = this.occInspectionDispatchRepository.getOccInspectionDispatchHeavyListMap(
          unSynchronizeInspectionDispatchHeavyList);
      List<OccInspectionDispatchHeavy> finishedInspectionDispatchHeavyList = occInspectionDispatchHeavyListMap.get(OccInspectionDispatchRepository.Category.FINISHED);
      List<OccInspectionDispatchHeavy> unFinishInspectionDispatchHeavyList = occInspectionDispatchHeavyListMap.get(OccInspectionDispatchRepository.Category.UN_FINISH);

      InspectionAdapter finishedOccInspectionDispatchAdapter = new InspectionAdapter(context, finishedInspectionDispatchHeavyList, false);
      InspectionAdapter unFinishOccInspectionDispatchAdapter = new InspectionAdapter(context, unFinishInspectionDispatchHeavyList, false);

      finishedOccInspectionDispatchAdapter.setFinished(true);
      unFinishOccInspectionDispatchAdapter.setFinished(false);

      switch (category) {
        case FINISHED:
          ret = finishedOccInspectionDispatchAdapter;
          break;
        case UN_FINISH:
          ret = unFinishOccInspectionDispatchAdapter;
          break;
        case SYNCHRONIZED:
          ret = synchronizedOccInspectionDispatchAdapter;
          break;
      }
      return ret;
    }

    @Override
    protected void onPostExecute(InspectionAdapter inspectionAdapter) {
      super.onPostExecute(inspectionAdapter);

      if (inspectionAdapter == null) {
        return;
      }
      recyclerView.setAdapter(inspectionAdapter);
      if (inspectionAdapter.getItemCount() != 0) {
        tvIsCompletedIndicator.setVisibility(View.GONE);
      } else {
        tvIsCompletedIndicator.setVisibility(View.VISIBLE);
      }

      this.etSearch.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
          inspectionAdapter.getFilter().filter(s.toString());
          recyclerView.setAdapter(inspectionAdapter);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
      });
    }
  }

  // TODO CASE TO IGNORE VS CASE TO REPLACE
  public static class FetchOccInspectionDispatch extends AsyncTask<Void, Void, InspectionAdapter> {
    private Context context;
    private OccInspectionApiService occInspectionApiService;
    private OccInspectionTaskRepository occInspectionTaskRepository;
    private String loginUserToken;
    private Integer authPeriodId;
    private Integer muniCode;

    public FetchOccInspectionDispatch(Context context, OccInspectionApiService occInspectionApiService, OccInspectionTaskRepository occInspectionTaskRepository, String loginUserToken,
        Integer authPeriodId, Integer muniCode) {
      this.context = context;
      this.occInspectionApiService = occInspectionApiService;
      this.occInspectionTaskRepository = occInspectionTaskRepository;
      this.loginUserToken = loginUserToken;
      this.authPeriodId = authPeriodId;
      this.muniCode = muniCode;
    }

    @Override
    protected InspectionAdapter doInBackground(Void... voids) {
      OccInspectionTasks occInspectionTasks = null;
      try {
        occInspectionTasks = this.occInspectionApiService.getOccInspectionDispatch(loginUserToken, String.valueOf(authPeriodId), String.valueOf(muniCode), null);
        this.occInspectionTaskRepository.insertOccInspectionTask(occInspectionTasks);
        context.startActivity(new Intent(context, HomeActivity.class));
      } catch (HttpBadRequestException
          | HttpServerErrorException
          | HttpUnknownErrorException
          | HttpNoFoundException
          | HttpUnAuthorizedException
          | IOException e) {
        Log.e(TAG, String.format(e.toString()));
      }
      return null;
    }

    @Override
    protected void onPostExecute(InspectionAdapter inspectionAdapter) {
      super.onPostExecute(inspectionAdapter);
    }
  }

  private void hideKeyboard(View view) {
    InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }
}