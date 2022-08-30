package com.cnf.module_inspection.async;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.INTENSITY_SCHEMA_VIOLATION_SEVERITY;

import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.cnf.R;
import com.cnf.module_inspection.activity.InspectionContainerActivity;
import com.cnf.module_inspection.adapter.InspectionOccInspectedSpaceElementAdapter;
import com.cnf.module_inspection.async.LoadOccInspectedSpaceElementTask.Item;
import com.cnf.module_inspection.entity.BlobBytes;
import com.cnf.module_inspection.entity.OccInspectedSpaceElementHeavy;
import com.cnf.module_inspection.entity.infra.IntensityClass;
import com.cnf.module_inspection.service.local.BlobBytesRepository;
import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository;
import java.lang.ref.WeakReference;
import java.util.List;

public class LoadOccInspectedSpaceElementTask extends AsyncTask<Void, Void, Item> {

  private final WeakReference<Fragment> fragmentWeakReference;
  private final WeakReference<InspectionContainerActivity> activityWeakReference;
  private final String inspectedSpaceId;
  private final int codeElementGuideId;

  static class Item {

    private final List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList;
    private final List<IntensityClass> intensityClassList;

    public Item(List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList, List<IntensityClass> intensityClassList) {
      this.occInspectedSpaceElementHeavyList = occInspectedSpaceElementHeavyList;
      this.intensityClassList = intensityClassList;
    }
  }

  public LoadOccInspectedSpaceElementTask(String inspectedSpaceId, int codeElementGuideId, @NonNull Fragment fragment) {
    this.fragmentWeakReference = new WeakReference<>(fragment);
    this.activityWeakReference = new WeakReference<>((InspectionContainerActivity) fragment.getActivity());
    this.inspectedSpaceId = inspectedSpaceId;
    this.codeElementGuideId = codeElementGuideId;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  @Override
  protected Item doInBackground(Void... voids) {
    List<OccInspectedSpaceElementHeavy> list;
    InspectionContainerActivity activity = activityWeakReference.get();
    if (activity == null) {
      return null;
    }
    OccInspectionSpaceElementRepository occInspectionSpaceElementRepository = OccInspectionSpaceElementRepository.getInstance(activity);
    BlobBytesRepository blobBytesRepository = BlobBytesRepository.getInstance(activity);
    list = occInspectionSpaceElementRepository.getOccInspectedSpaceElementHeavyListByInspectedSpaceId(codeElementGuideId, inspectedSpaceId);
    list = occInspectionSpaceElementRepository.configureOccInspectedSpaceElementHeavyListStatus(list);
    List<IntensityClass> intensityClassList = occInspectionSpaceElementRepository.selectAllIntensityClassListBySchemaLabel(INTENSITY_SCHEMA_VIOLATION_SEVERITY);
    for (OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy : list) {
      List<BlobBytes> blobBytesList = blobBytesRepository.getInspectedPhotoBlobBytesList(
          occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getInspectedSpaceElementId());
      occInspectedSpaceElementHeavy.setBlobBytesList(blobBytesList);
    }
    return new LoadOccInspectedSpaceElementTask.Item(list, intensityClassList);
  }

  @Override
  protected void onPostExecute(Item item) {
    super.onPostExecute(item);

    InspectionContainerActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return;
    }
    Fragment fragment = fragmentWeakReference.get();
    if (fragment == null) {
      return;
    }
    //TextView statusIndicator = activity.findViewById(R.id.tv_inspection_space_element_is_completed_indicator);
    RecyclerView recyclerView = activity.findViewById(R.id.rv_inspection_inspected_space_elements);
    if (recyclerView == null) {
      Log.e(TAG, "statusIndicator || recyclerView null");
      return;
    }
    InspectionOccInspectedSpaceElementAdapter adapter = (InspectionOccInspectedSpaceElementAdapter) recyclerView.getAdapter();
    if (adapter == null) {
      adapter = new InspectionOccInspectedSpaceElementAdapter(fragment, item.occInspectedSpaceElementHeavyList, item.intensityClassList);
      recyclerView.setAdapter(adapter);
      return;
    }
    adapter.setFilterOccInspectedSpaceElementHeavyList(item.occInspectedSpaceElementHeavyList);
    adapter.notifyDataSetChanged();

  }
}
