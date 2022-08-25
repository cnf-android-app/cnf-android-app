package com.cnf.module_inspection.async;

import static android.content.ContentValues.TAG;

import android.app.Fragment;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.cnf.R;
import com.cnf.module_inspection.activity.InspectionContainerActivity;
import com.cnf.module_inspection.adapter.InspectionOccInspectedSpaceAdapter;
import com.cnf.module_inspection.entity.OccInspectedSpace;
import com.cnf.module_inspection.entity.OccInspectedSpaceElement;
import com.cnf.module_inspection.entity.OccInspectedSpaceHeavy;
import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository;
import com.cnf.module_inspection.service.local.OccInspectionSpaceRepository;
import com.cnf.module_inspection.service.local.OccInspectionSpaceRepository.Category;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoadOccInspectedSpaceTask extends AsyncTask<Void, Void, List<OccInspectedSpaceHeavy>> {

  private final WeakReference<Fragment> fragmentWeakReference;
  private final WeakReference<InspectionContainerActivity> activityWeakReference;
  private final int inspectionId;
  private final Category category;

  public LoadOccInspectedSpaceTask(int inspectionId, @NonNull Fragment fragment, @NonNull Category category) {
    this.fragmentWeakReference = new WeakReference<>(fragment);
    this.activityWeakReference = new WeakReference<>((InspectionContainerActivity) fragment.getActivity());
    this.inspectionId = inspectionId;
    this.category = category;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  @Override
  protected List<OccInspectedSpaceHeavy> doInBackground(Void... voids) {
    List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList = new ArrayList<>();
    InspectionContainerActivity activity = activityWeakReference.get();
    if (activity == null) {
      return occInspectedSpaceHeavyList;
    }
    OccInspectionSpaceRepository occInspectionSpaceRepository = OccInspectionSpaceRepository.getInstance(activity);
    OccInspectionSpaceElementRepository occInspectionSpaceElementRepository = OccInspectionSpaceElementRepository.getInstance(activity);
    occInspectedSpaceHeavyList = occInspectionSpaceRepository.getOccInspectedSpaceHeavyListByInspectionId(inspectionId);
    if (occInspectedSpaceHeavyList.isEmpty()) {
      return occInspectedSpaceHeavyList;
    }
    for (OccInspectedSpaceHeavy occInspectedSpaceHeavy : occInspectedSpaceHeavyList) {
      String inspectedSpaceId;
      OccInspectedSpace occInspectedSpace = occInspectedSpaceHeavy.getOccInspectedSpace();
      if (occInspectedSpace == null || (inspectedSpaceId = occInspectedSpace.getInspectedSpaceId()) == null) {
        continue;
      }
      List<OccInspectedSpaceElement> occInspectedSpaceElementList = occInspectionSpaceElementRepository.getOccInspectedSpaceElementList(inspectedSpaceId);
      occInspectedSpaceHeavy.setOccInspectedSpaceElementList(occInspectedSpaceElementList);
    }
    Map<Category, List<OccInspectedSpaceHeavy>> occInspectedSpaceHeavyListMap = occInspectionSpaceRepository.getOccInspectedSpaceHeavyListMap(occInspectedSpaceHeavyList);
    List<OccInspectedSpaceHeavy> list = new ArrayList<>();
    switch (category) {
      case FINISHED:
        list = occInspectedSpaceHeavyListMap.get(Category.FINISHED);
        break;
      case UN_FINISH:
        list = occInspectedSpaceHeavyListMap.get(Category.UN_FINISH);
    }
    return list;
  }

  @Override
  protected void onPostExecute(List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList) {
    super.onPostExecute(occInspectedSpaceHeavyList);
    InspectionContainerActivity inspectionContainerActivity = activityWeakReference.get();
    if (inspectionContainerActivity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return;
    }
    TextView statusIndicator = inspectionContainerActivity.findViewById(R.id.tv_inspection_space_is_completed_indicator);
    RecyclerView rvOccInspectedSpace = inspectionContainerActivity.findViewById(R.id.rv_occ_inspected_space);
    if (statusIndicator == null || rvOccInspectedSpace == null) {
      Log.e(TAG, "statusIndicator || rvOccInspectedSpace null");
      return;
    }
    if (occInspectedSpaceHeavyList.isEmpty()) {
      statusIndicator.setText(String.format("No inspection space for " + category.toString() + "category", inspectionId));
      statusIndicator.setGravity(View.TEXT_ALIGNMENT_CENTER);
      statusIndicator.setVisibility(View.VISIBLE);
    } else {
      statusIndicator.setVisibility(View.GONE);
    }
    InspectionOccInspectedSpaceAdapter adapter = (InspectionOccInspectedSpaceAdapter)rvOccInspectedSpace.getAdapter();
    if (adapter == null) {
      adapter = new InspectionOccInspectedSpaceAdapter(occInspectedSpaceHeavyList, fragmentWeakReference.get());
      rvOccInspectedSpace.setAdapter(adapter);
      return;
    }
    adapter.setOccInspectedSpaceHeavyList(occInspectedSpaceHeavyList);
    adapter.notifyDataSetChanged();
  }
}
