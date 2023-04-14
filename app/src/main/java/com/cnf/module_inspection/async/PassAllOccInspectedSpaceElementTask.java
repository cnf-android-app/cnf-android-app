package com.cnf.module_inspection.async;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_USER_ID;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.cnf.R;
import com.cnf.module_inspection.activity.InspectionContainerActivity;
import com.cnf.module_inspection.adapter.InspectionOccInspectedSpaceElementAdapter;
import com.cnf.module_inspection.entity.OccInspectedSpaceElementHeavy;
import com.cnf.module_inspection.service.local.BlobBytesRepository;
import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PassAllOccInspectedSpaceElementTask extends AsyncTask<Void, Void, List<OccInspectedSpaceElementHeavy>> {

  private final WeakReference<Fragment> fragmentWeakReference;
  private final WeakReference<InspectionContainerActivity> activityWeakReference;
  private final String inspectedSpaceId;
  private final int codeElementGuideId;

  public PassAllOccInspectedSpaceElementTask(String inspectedSpaceId, int codeElementGuideId, @NonNull Fragment fragment) {
    this.fragmentWeakReference = new WeakReference<>(fragment);
    this.activityWeakReference = new WeakReference<>((InspectionContainerActivity) fragment.getActivity());
    this.inspectedSpaceId = inspectedSpaceId;
    this.codeElementGuideId = codeElementGuideId;
  }

  @Override
  protected List<OccInspectedSpaceElementHeavy> doInBackground(Void... voids) {
    List<OccInspectedSpaceElementHeavy> list = new ArrayList<>();
    InspectionContainerActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return list;
    }
    SharedPreferences sp = activity.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, MODE_PRIVATE);
    int userId = sp.getInt(SP_KEY_USER_ID, 0);
    OccInspectionSpaceElementRepository occInspectionSpaceElementRepository = OccInspectionSpaceElementRepository.getInstance(activity);
    list = occInspectionSpaceElementRepository.getOccInspectedSpaceElementHeavyListByInspectedSpaceId(codeElementGuideId, inspectedSpaceId);
    list = occInspectionSpaceElementRepository.configureOccInspectedSpaceElementHeavyListStatus(list);
    for (OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy : list) {
      occInspectedSpaceElementHeavy = occInspectionSpaceElementRepository.configureElementForCompliance(occInspectedSpaceElementHeavy, userId);
      occInspectionSpaceElementRepository.configureOccInspectedSpaceElementStatus(occInspectedSpaceElementHeavy.getOccInspectedSpaceElement());
      occInspectionSpaceElementRepository.updateOccInspectedSpaceElement(occInspectedSpaceElementHeavy.getOccInspectedSpaceElement());
    }
    return list;
  }

  @Override
  protected void onPostExecute(List<OccInspectedSpaceElementHeavy> list) {
    super.onPostExecute(list);
    InspectionContainerActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return;
    }
    Fragment fragment = fragmentWeakReference.get();
    if (fragment == null) {
      return;
    }
    RecyclerView recyclerView = activity.findViewById(R.id.rv_inspection_inspected_space_elements);
    if (recyclerView == null) {
      Log.e(TAG, "statusIndicator || recyclerView null");
      return;
    }
    InspectionOccInspectedSpaceElementAdapter adapter = (InspectionOccInspectedSpaceElementAdapter) recyclerView.getAdapter();
    if (adapter == null) {
      return;
    }
    adapter.setFilterOccInspectedSpaceElementHeavyList(list);
    adapter.notifyDataSetChanged();
  }
}
