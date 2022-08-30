package com.cnf.module_inspection.async;

import static android.content.ContentValues.TAG;

import androidx.fragment.app.Fragment;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cnf.R;
import com.cnf.module_inspection.activity.InspectionContainerActivity;
import com.cnf.module_inspection.adapter.InspectionOccInspectedSpaceElementCategoryAdapter;
import com.cnf.module_inspection.entity.OccInspectedSpaceElementHeavy;
import com.cnf.module_inspection.entity.infra.CodeElementGuide;
import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository;
import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository.Category;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadOccInspectedSpaceElementCategoryTask extends AsyncTask<Void, Void, Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>>> {

  private final WeakReference<Fragment> fragmentWeakReference;
  private final WeakReference<InspectionContainerActivity> activityWeakReference;
  private final String inspectedSpaceId;
  private final Category category;

  public LoadOccInspectedSpaceElementCategoryTask(String inspectedSpaceId, @NonNull Fragment fragment, @NonNull Category category) {
    this.fragmentWeakReference = new WeakReference<>(fragment);
    this.activityWeakReference = new WeakReference<>((InspectionContainerActivity) fragment.getActivity());
    this.inspectedSpaceId = inspectedSpaceId;
    this.category = category;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  @Override
  protected Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> doInBackground(Void... voids) {
    Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> map = new HashMap<>();
    InspectionContainerActivity activity = activityWeakReference.get();
    if (activity == null) {
      return map;
    }
    OccInspectionSpaceElementRepository occInspectionSpaceElementRepository = OccInspectionSpaceElementRepository.getInstance(activity);
    Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> occInspectedSpaceElementHeavyMap = occInspectionSpaceElementRepository.getOccInspectedSpaceElementHeavyMap(inspectedSpaceId);
    Map<Category, Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>>> occInspectedSpaceElementHeavyStatusMap
        = occInspectionSpaceElementRepository.getOccInspectedSpaceElementHeavyStatusMap(occInspectedSpaceElementHeavyMap);
    switch (category) {
      case FINISHED:
        map = occInspectedSpaceElementHeavyStatusMap.get(Category.FINISHED);
        break;
      case UN_FINISH:
        map = occInspectedSpaceElementHeavyStatusMap.get(Category.UN_FINISH);
        break;
    }
    return map;
  }

  @Override
  protected void onPostExecute(Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> map) {
    super.onPostExecute(map);

    InspectionContainerActivity inspectionContainerActivity = activityWeakReference.get();
    if (inspectionContainerActivity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return;
    }

    TextView statusIndicator = inspectionContainerActivity.findViewById(R.id.tv_inspection_space_element_is_completed_indicator);
    RecyclerView recyclerView = inspectionContainerActivity.findViewById(R.id.rv_occ_inspected_space_element_category);
    if (statusIndicator == null || recyclerView == null) {
      Log.e(TAG, "statusIndicator || recyclerView null");
      return;
    }

    if (map.isEmpty()) {
      statusIndicator.setVisibility(View.VISIBLE);
      switch (category) {
        case FINISHED:
          statusIndicator.setText("No inspection space element is inspected..");
          break;
        case UN_FINISH:
          statusIndicator.setText("All inspection space elements are inspected..");
          break;
      }

    } else {
      statusIndicator.setVisibility(View.GONE);
    }
    Fragment fragment = fragmentWeakReference.get();
    if (fragment == null) {
      return;
    }
    InspectionOccInspectedSpaceElementCategoryAdapter adapter = (InspectionOccInspectedSpaceElementCategoryAdapter) recyclerView.getAdapter();
    if (adapter == null) {
      adapter = new InspectionOccInspectedSpaceElementCategoryAdapter(fragment, map);
      recyclerView.setAdapter(adapter);
      return;
    }
    adapter.setOccInspectedSpaceElementHeavyMap(map);
    adapter.setCodeElementGuideList(new ArrayList<>(map.keySet()));
    adapter.notifyDataSetChanged();
  }
}
