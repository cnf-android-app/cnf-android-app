package com.cnf.module_inspection.async;

import static android.content.ContentValues.TAG;

import androidx.fragment.app.Fragment;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cnf.R;
import com.cnf.module_inspection.activity.InspectionContainerActivity;
import com.cnf.module_inspection.adapter.InspectionOccChecklistSpaceTypeAdapter;
import com.cnf.module_inspection.entity.infra_heavy.OccChecklistSpaceTypeHeavy;
import com.cnf.module_inspection.service.local.OccInspectionChecklistSpaceTypeRepository;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class LoadOccChecklistSpaceTypeTask extends AsyncTask<Void, Void, List<OccChecklistSpaceTypeHeavy>> {

  private final WeakReference<Fragment> fragmentWeakReference;
  private final WeakReference<InspectionContainerActivity> activityWeakReference;
  private final int checklistId;

  public LoadOccChecklistSpaceTypeTask(int checklistId, @NonNull Fragment fragment) {
    this.fragmentWeakReference = new WeakReference<>(fragment);
    this.activityWeakReference = new WeakReference<>((InspectionContainerActivity) fragment.getActivity());
    this.checklistId = checklistId;
  }

  @Override
  protected List<OccChecklistSpaceTypeHeavy> doInBackground(Void... voids) {
    List<OccChecklistSpaceTypeHeavy> list = new ArrayList<>();
    InspectionContainerActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return list;
    }
    OccInspectionChecklistSpaceTypeRepository o = OccInspectionChecklistSpaceTypeRepository.getInstance(activity);
    list = o.getOccChecklistSpaceTypeHeavyList(checklistId);
    return list;
  }

  @Override
  protected void onPostExecute(List<OccChecklistSpaceTypeHeavy> list) {
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
    RecyclerView recyclerView = activity.findViewById(R.id.rv_space_type_list);
    InspectionOccChecklistSpaceTypeAdapter adapter = (InspectionOccChecklistSpaceTypeAdapter) recyclerView.getAdapter();
    if (adapter == null) {
      adapter = new InspectionOccChecklistSpaceTypeAdapter(fragment, list);
      recyclerView.setAdapter(adapter);
      return;
    }
    adapter.setOccChecklistSpaceTypeHeavyList(list);
    adapter.notifyDataSetChanged();
  }
}
