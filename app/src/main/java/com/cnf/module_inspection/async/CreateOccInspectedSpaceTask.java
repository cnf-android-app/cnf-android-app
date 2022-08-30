package com.cnf.module_inspection.async;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_CHECKLIST_SPACE_TYPE_ID_KEY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_ID_KEY;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_USER_ID;

import android.content.Context;
import androidx.fragment.app.Fragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import com.cnf.R;
import com.cnf.module_inspection.activity.InspectionContainerActivity;
import com.cnf.module_inspection.entity.OccInspectedSpace;
import com.cnf.module_inspection.fragment.InspectionSelectOccInspectedSpaceFragment;
import com.cnf.module_inspection.service.exception.InvalidOccInspectedSpaceException;
import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository;
import com.cnf.module_inspection.service.local.OccInspectionSpaceRepository;
import java.lang.ref.WeakReference;
import java.time.OffsetDateTime;
import java.util.UUID;

public class CreateOccInspectedSpaceTask extends AsyncTask<Void, Void, Void> {

  private final WeakReference<Fragment> fragmentWeakReference;
  private final WeakReference<InspectionContainerActivity> activityWeakReference;
  private final int occLocationDescriptionId;

  public CreateOccInspectedSpaceTask(int occLocationDescriptionId, @NonNull Fragment fragment) {
    this.fragmentWeakReference = new WeakReference<>(fragment);
    this.activityWeakReference = new WeakReference<>((InspectionContainerActivity) fragment.getActivity());
    this.occLocationDescriptionId =  occLocationDescriptionId;
  }

  @Override
  protected Void doInBackground(Void... voids) {
    InspectionContainerActivity activity = activityWeakReference.get();
    if (activity == null) {
      return null;
    }
    SharedPreferences sp =  activity. getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    int userId = sp.getInt(SP_KEY_USER_ID, -1);
    int inspectionId = activity.getIntent().getIntExtra(INTENT_EXTRA_INSPECTION_ID_KEY, -1);
    int occChecklistSpaceTypeId = activity.getIntent().getIntExtra(INTENT_EXTRA_INSPECTION_CHECKLIST_SPACE_TYPE_ID_KEY, -1);

    OccInspectedSpace occInspectedSpace = new OccInspectedSpace();
    String occInspectedSpaceId = UUID.randomUUID().toString();
    occInspectedSpace.setInspectedSpaceId(occInspectedSpaceId);
    occInspectedSpace.setOccInspectionId(inspectionId);
    occInspectedSpace.setOccLocationDescriptionId(occLocationDescriptionId);
    occInspectedSpace.setAddedToChecklistByUserid(userId);
    occInspectedSpace.setAddedToChecklistTS(OffsetDateTime.now().toString());
    occInspectedSpace.setOccChecklistSpaceTypeId(occChecklistSpaceTypeId);
    OccInspectionSpaceRepository occInspectionSpaceRepository = OccInspectionSpaceRepository.getInstance(activity);
    OccInspectionSpaceElementRepository occInspectionSpaceElementRepository = OccInspectionSpaceElementRepository.getInstance(activity);
    //TODO TRANCTIONAL
    occInspectionSpaceRepository.insertOccInspectedSpace(occInspectedSpace);
    try {
      occInspectionSpaceElementRepository.createDefaultOccInspectedSpaceElementList(occInspectedSpace);
    } catch (InvalidOccInspectedSpaceException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  protected void onPostExecute(Void unused) {
    super.onPostExecute(unused);
    InspectionContainerActivity activity = activityWeakReference.get();
    if (activity == null) {
      Log.e(TAG, "WeakReference<InspectionContainerActivity> null");
      return;
    }
    Fragment fragment = fragmentWeakReference.get();
    if (fragment == null) {
      return;
    }
    Fragment f = activity.getSupportFragmentManager().findFragmentByTag(FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE);
    if (f == null) {
      f = new InspectionSelectOccInspectedSpaceFragment();
    }
    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, f, FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE).commit();
  }
}
