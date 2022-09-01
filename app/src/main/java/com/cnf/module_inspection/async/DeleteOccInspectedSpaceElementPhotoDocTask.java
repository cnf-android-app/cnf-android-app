package com.cnf.module_inspection.async;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.cnf.R;
import com.cnf.module_inspection.activity.InspectionContainerActivity;
import com.cnf.module_inspection.adapter.InspectionInspectedSpaceElementPhotoAdapter;
import com.cnf.module_inspection.entity.BlobBytes;
import com.cnf.module_inspection.service.local.OccInspectionPhotoRepository;
import java.lang.ref.WeakReference;

public class DeleteOccInspectedSpaceElementPhotoDocTask extends AsyncTask<Void, Void, BlobBytes> {

  private final WeakReference<InspectionContainerActivity> activityWeakReference;
  private final BlobBytes blobBytes;

  public DeleteOccInspectedSpaceElementPhotoDocTask(BlobBytes blobBytes, @NonNull Fragment fragment) {
    this.activityWeakReference = new WeakReference<>((InspectionContainerActivity) fragment.getActivity());
    this.blobBytes = blobBytes;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  @Override
  protected BlobBytes doInBackground(Void... voids) {
    InspectionContainerActivity activity = activityWeakReference.get();
    if (activity == null) {
      return null;
    }
    OccInspectionPhotoRepository occInspectionPhotoRepository = OccInspectionPhotoRepository.getInstance(activity);
    occInspectionPhotoRepository.deleteOccInspectedPhotoBlobByte(blobBytes);
    return blobBytes;
  }

  @Override
  protected void onPostExecute(BlobBytes blobBytes) {
    super.onPostExecute(blobBytes);
    InspectionContainerActivity inspectionContainerActivity = activityWeakReference.get();
    if (inspectionContainerActivity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return;
    }
    if (blobBytes == null) {
      return;
    }

    RecyclerView rv0 = inspectionContainerActivity.findViewById(R.id.rv_inspection_occ_inspected_space_element_item_pass_photo);
    RecyclerView rv1 = inspectionContainerActivity.findViewById(R.id.rv_inspection_occ_inspected_space_element_item_violation_photo);

    if (rv0 == null && rv1 == null) {
      Log.e(TAG, "statusIndicator || rvOccInspectedSpace null");
      return;
    }

    if (rv0 != null) {
      InspectionInspectedSpaceElementPhotoAdapter adapter0 = (InspectionInspectedSpaceElementPhotoAdapter) rv0.getAdapter();
      if (adapter0 == null) {
        return;
      }
      adapter0.getBlobBytesList().remove(blobBytes);
      adapter0.notifyDataSetChanged();
    }

    if (rv1 != null) {
      InspectionInspectedSpaceElementPhotoAdapter adapter1 = (InspectionInspectedSpaceElementPhotoAdapter) rv1.getAdapter();
      if (adapter1 == null) {
        return;
      }
      adapter1.getBlobBytesList().remove(blobBytes);
      adapter1.notifyDataSetChanged();
    }
  }
}
