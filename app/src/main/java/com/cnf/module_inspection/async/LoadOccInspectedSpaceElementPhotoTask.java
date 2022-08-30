package com.cnf.module_inspection.async;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.cnf.R;
import com.cnf.module_inspection.activity.InspectionContainerActivity;
import com.cnf.module_inspection.adapter.InspectionInspectedSpaceElementPhotoAdapter;
import com.cnf.module_inspection.entity.BlobBytes;
import com.cnf.module_inspection.service.local.BlobBytesRepository;

import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository.ElementStatus;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class LoadOccInspectedSpaceElementPhotoTask extends AsyncTask<Void, Void, List<BlobBytes>> {

  private final WeakReference<Fragment> fragmentWeakReference;
  private final WeakReference<InspectionContainerActivity> activityWeakReference;
  private int position;
  private String occInspectedSpaceElementId;
  private ElementStatus elementStatus;

  public LoadOccInspectedSpaceElementPhotoTask(int position, String occInspectedSpaceElementId, ElementStatus elementStatus, @NonNull Fragment fragment) {
    this.activityWeakReference = new WeakReference<>((InspectionContainerActivity) fragment.getActivity());
    this.fragmentWeakReference = new WeakReference<>(fragment);
    this.position = position;
    this.occInspectedSpaceElementId = occInspectedSpaceElementId;
    this.elementStatus = elementStatus;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();

  }

  @Override
  protected List<BlobBytes> doInBackground(Void... voids) {
    List<BlobBytes> list = new ArrayList<>();
    InspectionContainerActivity activity = activityWeakReference.get();
    if (activity == null) {
      return list;
    }
    BlobBytesRepository blobBytesRepository = BlobBytesRepository.getInstance(activity);
    list = blobBytesRepository.getInspectedPhotoBlobBytesList(occInspectedSpaceElementId);

    return list;
  }

  @Override
  protected void onPostExecute(List<BlobBytes> list) {
    super.onPostExecute(list);
    InspectionContainerActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity>");
      return;
    }
    Fragment fragment = fragmentWeakReference.get();
    if (fragment == null) {
      Log.e(TAG, "WeakReference<Fragment>");
      return;
    }
    RecyclerView rv0 = activity.findViewById(R.id.rv_inspection_inspected_space_elements);
    if (rv0 == null) {
      Log.e(TAG, "statusIndicator || recyclerView null");
      return;
    }
    ViewHolder viewHolder = rv0.findViewHolderForAdapterPosition(position);
    if (viewHolder == null) {
      Log.e(TAG, "viewHolder null");
      return;
    }
    RecyclerView rv1 = null;
    switch (elementStatus) {
      case PASS:
        rv1 = viewHolder.itemView.findViewById(R.id.rv_inspection_occ_inspected_space_element_item_pass_photo);
        break;
      case VIOLATION:
        rv1 = viewHolder.itemView.findViewById(R.id.rv_inspection_occ_inspected_space_element_item_violation_photo);
        break;
    }
    InspectionInspectedSpaceElementPhotoAdapter adapter = (InspectionInspectedSpaceElementPhotoAdapter) rv1.getAdapter();
    if (adapter == null) {
      adapter = new InspectionInspectedSpaceElementPhotoAdapter(fragment, list);
      rv1.setAdapter(adapter);
      return;
    }
    adapter.setBlobBytesList(list);
    adapter.notifyDataSetChanged();
    System.out.println("finish update!!!: " + list.size());
  }
}
