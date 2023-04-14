package com.cnf.module_inspection.async;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_KEY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_STATUS_KEY;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_MUNICIPALITY_CODE;
import static com.cnf.utils.AppConstants.SP_KEY_USER_ID;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.cnf.R;
import com.cnf.module_inspection.activity.InspectionContainerActivity;
import com.cnf.module_inspection.adapter.InspectionInspectedSpaceElementPhotoAdapter;
import com.cnf.module_inspection.entity.BlobBytes;
import com.cnf.module_inspection.entity.OccInspectedSpaceElementPhotoDoc;
import com.cnf.module_inspection.entity.PhotoDoc;
import com.cnf.module_inspection.service.local.BlobBytesRepository;
import com.cnf.module_inspection.service.local.OccInspectedSpaceElementPhotoDocRepository;
import com.cnf.module_inspection.service.local.OccInspectionPhotoRepository;
import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository.ElementStatus;
import java.lang.ref.WeakReference;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class SaveOccInspectedSpaceElementPhotoDocTask extends AsyncTask<Void, Void, List<BlobBytes>> {

  private final WeakReference<InspectionContainerActivity> activityWeakReference;
  private final List<byte[]> bArrayList;

  public SaveOccInspectedSpaceElementPhotoDocTask(List<byte[]> bArrayList, @NonNull InspectionContainerActivity activity) {
    this.activityWeakReference = new WeakReference<>(activity);
    this.bArrayList = bArrayList;
  }

  @Override
  protected List<BlobBytes>  doInBackground(Void... voids) {
    InspectionContainerActivity activity = activityWeakReference.get();
    List<BlobBytes> list = new ArrayList<>();
    if (activity == null) {
      return null;
    }
    SharedPreferences sp = activity.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, MODE_PRIVATE);
    int userId = sp.getInt(SP_KEY_USER_ID, 0);
    int muniCode = sp.getInt(SP_KEY_MUNICIPALITY_CODE, 0);
    String inspectedSpaceElementId = activity.getIntent().getStringExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY);
    String inspectedSpaceElementPhotoName = activity.getIntent().getStringExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY);

    for (byte[] bArray : bArrayList) {

      String createTime = OffsetDateTime.now().toString();
      String s = Base64.getEncoder().encodeToString(bArray);
      String blobBytesId = UUID.randomUUID().toString();
      BlobBytes blobBytes = new BlobBytes(blobBytesId, createTime, s, userId, inspectedSpaceElementPhotoName);
      BlobBytesRepository blobBytesRepository = BlobBytesRepository.getInstance(activity);
      blobBytesRepository.insertBlobBytes(blobBytes);
      list.add(blobBytes);
      String photoDocId = UUID.randomUUID().toString();
      PhotoDoc photoDoc = new PhotoDoc();
      photoDoc.setPhotoDocId(photoDocId);
      photoDoc.setPhotoDocDescription(null);
      photoDoc.setPhotoDocCommitted(true);
      photoDoc.setBlobBytesId(blobBytesId);
      photoDoc.setMunicode(muniCode);
      // TODO HARDCODE
      photoDoc.setBlobTypeId(201);
      photoDoc.setMetaDataMap(null);
      photoDoc.setTitle(null);
      photoDoc.setCreatedTS(createTime);
      photoDoc.setCreatedByUserid(userId);
      photoDoc.setLastUpdatedTS(createTime);
      photoDoc.setLastUpdatedByUserId(userId);
      OccInspectionPhotoRepository occInspectionPhotoRepository = OccInspectionPhotoRepository.getInstance(activity);
      occInspectionPhotoRepository.insertPhotoDoc(photoDoc);
      OccInspectedSpaceElementPhotoDocRepository occInspectedSpaceElementPhotoDocRepository = OccInspectedSpaceElementPhotoDocRepository.getInstance(activity);
      OccInspectedSpaceElementPhotoDoc occInspectedSpaceElementPhotoDoc = new OccInspectedSpaceElementPhotoDoc(photoDocId, inspectedSpaceElementId);
      occInspectedSpaceElementPhotoDocRepository.insertOccInspectedSpaceElementPhotoDoc(occInspectedSpaceElementPhotoDoc);

    }
    return list;
  }

  @Override
  protected void onPostExecute(List<BlobBytes> list) {
    super.onPostExecute(list);
    InspectionContainerActivity activity = activityWeakReference.get();

    if (activity == null) {
      return;
    }

    int position = activity.getIntent().getIntExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_KEY, -1);
    ElementStatus elementStatus = (ElementStatus) activity.getIntent().getSerializableExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_STATUS_KEY);
    String occInspectedSpaceElementId = activity.getIntent().getStringExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY);
    if (position == -1 || occInspectedSpaceElementId == null) {

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
      return;
    }
    adapter.getBlobBytesList().addAll(list);
    adapter.notifyDataSetChanged();
  }
}
