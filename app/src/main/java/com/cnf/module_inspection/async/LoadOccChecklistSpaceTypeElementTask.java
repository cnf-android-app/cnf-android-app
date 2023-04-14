package com.cnf.module_inspection.async;

import static android.content.ContentValues.TAG;

import androidx.fragment.app.Fragment;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cnf.R;
import com.cnf.module_inspection.activity.InspectionContainerActivity;
import com.cnf.module_inspection.adapter.InspectionSpaceTypeElementAdapter;
import com.cnf.module_inspection.entity.infra_heavy.OccChecklistSpaceTypeElementHeavy;
import com.cnf.module_inspection.service.local.OccInspectionChecklistSpaceTypeElementRepository;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class LoadOccChecklistSpaceTypeElementTask extends AsyncTask<Void, Void, List<OccChecklistSpaceTypeElementHeavy>> {

  private final WeakReference<Fragment> fragmentWeakReference;
  private final WeakReference<InspectionContainerActivity> activityWeakReference;
  private final int checklistSpaceTypeId;

  public LoadOccChecklistSpaceTypeElementTask(int checklistSpaceTypeId, @NonNull Fragment fragment) {
    this.fragmentWeakReference = new WeakReference<>(fragment);
    this.activityWeakReference = new WeakReference<>((InspectionContainerActivity) fragment.getActivity());
    this.checklistSpaceTypeId = checklistSpaceTypeId;
  }

  @Override
  protected List<OccChecklistSpaceTypeElementHeavy> doInBackground(Void... voids) {
    List<OccChecklistSpaceTypeElementHeavy> list = new ArrayList<>();
    InspectionContainerActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return list;
    }
    OccInspectionChecklistSpaceTypeElementRepository occInspectionChecklistSpaceTypeElementRepository = OccInspectionChecklistSpaceTypeElementRepository.getInstance(activity);
    list = occInspectionChecklistSpaceTypeElementRepository.getOccChecklistSpaceTypeElementHeavyList(checklistSpaceTypeId);
    return list;
  }

  @Override
  protected void onPostExecute(List<OccChecklistSpaceTypeElementHeavy> list) {
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
    RecyclerView recyclerView = activity.findViewById(R.id.rv_occ_checklist_space_type_element_detail);
    InspectionSpaceTypeElementAdapter adapter = (InspectionSpaceTypeElementAdapter) recyclerView.getAdapter();
    if (adapter == null) {
      InspectionSpaceTypeElementAdapter inspectionSpaceTypeElementAdapter = new InspectionSpaceTypeElementAdapter(fragment, list);
      recyclerView.setAdapter(inspectionSpaceTypeElementAdapter);
      return;
    }
    adapter.setOccChecklistSpaceTypeElementHeavyList(list);
    adapter.notifyDataSetChanged();
  }
}
