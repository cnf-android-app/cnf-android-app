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
import com.cnf.module_inspection.adapter.InspectionOccInspectedSpaceAdapter;
import com.cnf.module_inspection.entity.OccInspectedSpaceHeavy;
import com.cnf.module_inspection.service.local.OccInspectionSpaceRepository;
import java.lang.ref.WeakReference;
import java.util.List;

public class DeleteOccInspectedSpaceTask extends AsyncTask<Void, Void, Void> {

  private final WeakReference<Fragment> fragmentWeakReference;
  private final WeakReference<InspectionContainerActivity> activityWeakReference;
  private final OccInspectedSpaceHeavy occInspectedSpaceHeavy;

  public DeleteOccInspectedSpaceTask(OccInspectedSpaceHeavy occInspectedSpaceHeavy, @NonNull Fragment fragment) {
    this.fragmentWeakReference = new WeakReference<>(fragment);
    this.activityWeakReference = new WeakReference<>((InspectionContainerActivity) fragment.getActivity());
    this.occInspectedSpaceHeavy = occInspectedSpaceHeavy;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  @Override
  protected Void doInBackground(Void... voids) {
    InspectionContainerActivity activity = activityWeakReference.get();
    if (activity == null) {
      return null;
    }
    OccInspectionSpaceRepository occInspectionSpaceRepository = OccInspectionSpaceRepository.getInstance(activity);
    occInspectionSpaceRepository.deleteOccInspectedSpace(occInspectedSpaceHeavy.getOccInspectedSpace());
    return null;
  }

  @Override
  protected void onPostExecute(Void unused) {
    super.onPostExecute(unused);
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

    InspectionOccInspectedSpaceAdapter adapter = (InspectionOccInspectedSpaceAdapter) rvOccInspectedSpace.getAdapter();
    if (adapter == null) {
      Log.e(TAG, "InspectionOccInspectedSpaceAdapter null");
      return;
    }
    List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList = adapter.getOccInspectedSpaceHeavyList();
    if (occInspectedSpaceHeavyList == null) {
      return;
    }

    occInspectedSpaceHeavyList.remove(occInspectedSpaceHeavy);
    adapter.notifyDataSetChanged();

    if (occInspectedSpaceHeavyList.isEmpty()) {
      statusIndicator.setText("No inspection space(s)");
      statusIndicator.setGravity(View.TEXT_ALIGNMENT_CENTER);
      statusIndicator.setVisibility(View.VISIBLE);
    } else {
      statusIndicator.setVisibility(View.GONE);
    }
  }
}
