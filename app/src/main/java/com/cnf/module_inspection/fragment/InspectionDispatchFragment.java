package com.cnf.module_inspection.fragment;

import static com.cnf.module_inspection.service.local.OccInspectionDispatchRepository.Category.FINISHED;
import static com.cnf.module_inspection.service.local.OccInspectionDispatchRepository.Category.SYNCHRONIZED;
import static com.cnf.module_inspection.service.local.OccInspectionDispatchRepository.Category.UN_FINISH;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_IS_ONLINE;

import android.app.AlertDialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cnf.R;
import com.cnf.module_inspection.adapter.InspectionAdapter;
import com.cnf.module_inspection.async.FetchOccInspectionDispatchTask;
import com.cnf.module_inspection.async.LoadOccInspectionDispatchTask;
import com.cnf.module_inspection.service.local.OccInspectionDispatchRepository.Category;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InspectionDispatchFragment extends Fragment {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_inspection_dispatch, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    SharedPreferences sp = view.getContext().getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    boolean isOnline = sp.getBoolean(SP_KEY_IS_ONLINE, false);

    FloatingActionButton btnFetchDispatch = view.findViewById(R.id.btn_inspection_fetch_dispatch);
    RecyclerView rvInspectionList = view.findViewById(R.id.rv_inspection_list);

    RadioGroup radioGroup = view.findViewById(R.id.rg_inspection_is_finish_or_not);
    RadioButton rBtnUnFinish = view.findViewById(R.id.rb_inspection_un_finish);
    RadioButton rBtnFinished = view.findViewById(R.id.rb_inspection_finished);
    RadioButton rBtnSynchronized = view.findViewById(R.id.rb_inspection_synchronized);
    EditText etSearch = view.findViewById(R.id.et_inspection_search);

    ImageButton imageBtnMore = view.findViewById(R.id.imageBtn_occ_inspection_more_icon);
    ImageButton imageBtnSearch = view.findViewById(R.id.imageBtn_occ_inspection_search_icon);
    ImageView isOnlineIcon = view.findViewById(R.id.image_occ_inspection_online_icon);
    rvInspectionList.setLayoutManager(new LinearLayoutManager(getActivity()));


    if (!isOnline) {
      isOnlineIcon.setImageResource(R.drawable.ic_baseline_wifi_off_24);
      btnFetchDispatch.setImageResource(R.drawable.ic_baseline_cloud_off_30);
      btnFetchDispatch.setEnabled(false);
    } else {
      isOnlineIcon.setImageResource(R.drawable.ic_baseline_wifi_24);
      btnFetchDispatch.setImageResource(R.drawable.icon_cloud_download);
      btnFetchDispatch.setEnabled(true);
    }

    imageBtnSearch.setOnClickListener(v -> {
      if (etSearch.getVisibility() == View.VISIBLE) {
        etSearch.setVisibility(View.GONE);
        return;
      }
      etSearch.setVisibility(View.VISIBLE);
    });

    imageBtnMore.setOnClickListener(v -> {
      BottomSheetDialog bottomSheet = new BottomSheetDialog();
      bottomSheet.show(getActivity().getSupportFragmentManager(), "ModalBottomSheet");
    });

    etSearch.setOnFocusChangeListener((v, hasFocus) -> {
      if (hasFocus) {
        return;
      }
      hideKeyboard(v);
    });

    etSearch.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        InspectionAdapter adapter = (InspectionAdapter) rvInspectionList.getAdapter();
        if (adapter == null) {
          return;
        }
        adapter.getFilter().filter(s.toString());
        rvInspectionList.setAdapter(adapter);
      }

      @Override
      public void afterTextChanged(Editable s) {
      }
    });

    btnFetchDispatch.setOnClickListener(v -> new AlertDialog
        .Builder(getActivity())
        .setTitle("Confirm")
        .setMessage("Are you sure to re-load inspection dispatches?")
        .setPositiveButton("Yes", (dialog, which) -> {
          FetchOccInspectionDispatchTask task = new FetchOccInspectionDispatchTask(InspectionDispatchFragment.this);
          task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        })
        .setNegativeButton("Dismiss", (dialog, which) -> {
        })
        .create()
        .show());

    radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
      switch (checkedId) {
        case R.id.rb_inspection_un_finish:
          configAfterClickOnUnFinishBtn(rBtnUnFinish, rBtnFinished, rBtnSynchronized);
          break;
        case R.id.rb_inspection_finished:
          configAfterClickOnFinishedBtn(rBtnUnFinish, rBtnFinished, rBtnSynchronized);
          break;
        case R.id.rb_inspection_synchronized:
          configAfterClickOnSynchronizedBtn(rBtnUnFinish, rBtnFinished, rBtnSynchronized);
          break;
      }
    });
    LoadOccInspectionDispatchTask task = new LoadOccInspectionDispatchTask(getCategory(radioGroup), InspectionDispatchFragment.this);
    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

  private Category getCategory(RadioGroup radioGroup) {
    int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
    Category category = UN_FINISH;
    switch (checkedRadioButtonId) {
      case R.id.rb_inspection_un_finish:
        break;
      case R.id.rb_inspection_finished:
        category = FINISHED;
        break;
      case R.id.rb_inspection_synchronized:
        category = SYNCHRONIZED;
        break;
    }
    return category;
  }

  private void configAfterClickOnUnFinishBtn(RadioButton rBtnUnFinish, RadioButton rBtnFinished, RadioButton rBtnSynchronized) {
    if (getActivity() == null) {
      return;
    }
    rBtnUnFinish.setTextColor(getActivity().getColor(R.color.on_switch_font_v0));
    rBtnUnFinish.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background_v0));
    rBtnFinished.setTextColor(getActivity().getColor(R.color.off_switch_font_v0));
    rBtnFinished.setBackground(null);
    rBtnSynchronized.setTextColor(getActivity().getColor(R.color.off_switch_font_v0));
    rBtnSynchronized.setBackground(null);
    LoadOccInspectionDispatchTask task = new LoadOccInspectionDispatchTask(UN_FINISH, InspectionDispatchFragment.this);
    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

  private void configAfterClickOnFinishedBtn(RadioButton rBtnUnFinish, RadioButton rBtnFinished, RadioButton rBtnSynchronized) {
    if (getActivity() == null) {
      return;
    }
    rBtnFinished.setTextColor(getActivity().getColor(R.color.on_switch_font_v0));
    rBtnFinished.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background_v0));
    rBtnUnFinish.setTextColor(getActivity().getColor(R.color.off_switch_font_v0));
    rBtnUnFinish.setBackground(null);
    rBtnSynchronized.setTextColor(getActivity().getColor(R.color.off_switch_font_v0));
    rBtnSynchronized.setBackground(null);
    LoadOccInspectionDispatchTask task = new LoadOccInspectionDispatchTask(Category.FINISHED, InspectionDispatchFragment.this);
    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

  private void configAfterClickOnSynchronizedBtn(RadioButton rBtnUnFinish, RadioButton rBtnFinished, RadioButton rBtnSynchronized) {
    if (getActivity() == null) {
      return;
    }
    rBtnSynchronized.setTextColor(getActivity().getColor(R.color.on_switch_font_v0));
    rBtnSynchronized.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background_v0));
    rBtnUnFinish.setTextColor(getActivity().getColor(R.color.off_switch_font_v0));
    rBtnUnFinish.setBackground(null);
    rBtnFinished.setTextColor(getActivity().getColor(R.color.off_switch_font_v0));
    rBtnFinished.setBackground(null);
    LoadOccInspectionDispatchTask task = new LoadOccInspectionDispatchTask(Category.SYNCHRONIZED, InspectionDispatchFragment.this);
    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

  private void hideKeyboard(View view) {
    InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }
}