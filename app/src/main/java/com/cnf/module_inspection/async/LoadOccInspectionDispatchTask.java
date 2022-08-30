package com.cnf.module_inspection.async;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_MUNICIPALITY_CODE;

import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cnf.R;
import com.cnf.module_auth.activity.HomeActivity;
import com.cnf.module_inspection.adapter.InspectionAdapter;
import com.cnf.module_inspection.entity.infra_heavy.OccInspectionDispatchHeavy;
import com.cnf.module_inspection.service.local.OccInspectionDispatchRepository;
import com.cnf.module_inspection.service.local.OccInspectionDispatchRepository.Category;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoadOccInspectionDispatchTask extends AsyncTask<Void, Void, List<OccInspectionDispatchHeavy>> {

  private final WeakReference<Fragment> fragmentWeakReference;
  private final WeakReference<HomeActivity> activityWeakReference;
  private final Category category;


  public LoadOccInspectionDispatchTask(Category category, @NonNull Fragment fragment) {
    this.fragmentWeakReference = new WeakReference<>(fragment);
    this.activityWeakReference = new WeakReference<>((HomeActivity) fragment.getActivity());
    this.category = category;
  }


  @Override
  protected List<OccInspectionDispatchHeavy> doInBackground(Void... voids) {
    List<OccInspectionDispatchHeavy> list = new ArrayList<>();
    HomeActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return list;
    }
    OccInspectionDispatchRepository repository = OccInspectionDispatchRepository.getInstance(activity);
    SharedPreferences sp = activity.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    int muniCode = sp.getInt(SP_KEY_MUNICIPALITY_CODE, -1);

    List<OccInspectionDispatchHeavy> unSynchronizeInspectionDispatchHeavyList = repository.getUnSynchronizeInspectionDispatchHeavyList(muniCode);
    Map<Category, List<OccInspectionDispatchHeavy>> occInspectionDispatchHeavyListMap = repository.getOccInspectionDispatchHeavyListMap(
        unSynchronizeInspectionDispatchHeavyList);

    switch (category) {
      case FINISHED:
        list = occInspectionDispatchHeavyListMap.get(OccInspectionDispatchRepository.Category.FINISHED);
        break;
      case UN_FINISH:
        list = occInspectionDispatchHeavyListMap.get(OccInspectionDispatchRepository.Category.UN_FINISH);
        break;
      case SYNCHRONIZED:
        list = repository.getSynchronizedInspectionDispatchHeavyList(muniCode);
        break;
    }
    return list;
  }

  @Override
  protected void onPostExecute(List<OccInspectionDispatchHeavy> list) {
    super.onPostExecute(list);
    HomeActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return;
    }
    Fragment fragment = fragmentWeakReference.get();
    if (fragment == null) {
      return;
    }
    TextView tvIsCompletedIndicator = activity.findViewById(R.id.tv_inspection_is_completed_indicator);
    if (list.isEmpty()) {
      tvIsCompletedIndicator.setVisibility(View.VISIBLE);
      switch (category) {
        case FINISHED:
          tvIsCompletedIndicator.setText("No inspection task is done..");
          break;
        case UN_FINISH:
          tvIsCompletedIndicator.setText("All inspection tasks are done..");
          break;
        case SYNCHRONIZED:
          tvIsCompletedIndicator.setText("No synchronized inspection task exists..");
          break;
      }
    } else {
      tvIsCompletedIndicator.setVisibility(View.GONE);
    }
    RecyclerView recyclerView = activity.findViewById(R.id.rv_inspection_list);
    InspectionAdapter adapter = (InspectionAdapter) recyclerView.getAdapter();
    if (adapter == null) {
      adapter = new InspectionAdapter(fragment, list);
      recyclerView.setAdapter(adapter);
      return;
    }
    adapter.setFilterOccInspectionDispatchHeavyList(list);
    adapter.notifyDataSetChanged();
  }
}

