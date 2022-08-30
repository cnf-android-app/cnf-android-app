package com.cnf.module_inspection.async;

import static android.content.ContentValues.TAG;

import androidx.fragment.app.Fragment;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cnf.R;
import com.cnf.module_inspection.activity.InspectionContainerActivity;
import com.cnf.module_inspection.adapter.InspectionOccLocationDescriptionAdapter;
import com.cnf.module_inspection.entity.infra.OccLocationDescription;
import com.cnf.module_inspection.service.local.OccLocationDescriptionRepository;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class LoadOccLocationDescriptionTask extends AsyncTask<Void, Void, List<OccLocationDescription>> {

  private final WeakReference<Fragment> fragmentWeakReference;
  private final WeakReference<InspectionContainerActivity> activityWeakReference;


  public LoadOccLocationDescriptionTask( @NonNull Fragment fragment) {
    this.fragmentWeakReference = new WeakReference<>(fragment);
    this.activityWeakReference = new WeakReference<>((InspectionContainerActivity) fragment.getActivity());
  }

  @Override
  protected List<OccLocationDescription> doInBackground(Void... voids) {
    List<OccLocationDescription> list = new ArrayList<>();
    InspectionContainerActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return list;
    }
    OccLocationDescriptionRepository o = OccLocationDescriptionRepository.getInstance(activity);
    list = o.getOccLocationDescriptionListFromSQLite();
    return list;
  }

  @Override
  protected void onPostExecute(List<OccLocationDescription> list) {
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
    RecyclerView recyclerView = activity.findViewById(R.id.rv_inspection_location_description);
    InspectionOccLocationDescriptionAdapter adapter = (InspectionOccLocationDescriptionAdapter) recyclerView.getAdapter();
    if (adapter == null) {
      InspectionOccLocationDescriptionAdapter a = new InspectionOccLocationDescriptionAdapter(fragment, list);
      recyclerView.setAdapter(a);
      return;
    }
    adapter.setOccLocationDescriptionList(list);
    adapter.notifyDataSetChanged();
  }
}
