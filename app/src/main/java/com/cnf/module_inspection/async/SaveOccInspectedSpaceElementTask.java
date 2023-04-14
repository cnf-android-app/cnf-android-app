package com.cnf.module_inspection.async;

import static android.content.Context.MODE_PRIVATE;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_USER_ID;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.cnf.module_inspection.activity.InspectionContainerActivity;
import com.cnf.module_inspection.entity.OccInspectedSpaceElement;
import com.cnf.module_inspection.entity.OccInspectedSpaceElementHeavy;
import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository;
import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository.ElementStatus;
import java.lang.ref.WeakReference;

public class SaveOccInspectedSpaceElementTask extends AsyncTask<Void, Void, Void> {

  private final WeakReference<Fragment> fragmentWeakReference;
  private final WeakReference<InspectionContainerActivity> activityWeakReference;
  private OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy;

  private ElementStatus elementStatus;

  public SaveOccInspectedSpaceElementTask(ElementStatus elementStatus, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy, @NonNull Fragment fragment) {
    this.fragmentWeakReference = new WeakReference<>(fragment);
    this.activityWeakReference = new WeakReference<>((InspectionContainerActivity) fragment.getActivity());
    this.occInspectedSpaceElementHeavy = occInspectedSpaceElementHeavy;
    this.elementStatus = elementStatus;

  }

  @Override
  protected Void doInBackground(Void... voids) {
    InspectionContainerActivity activity = activityWeakReference.get();
    if (activity == null) {
      return null;
    }
    SharedPreferences sp = activity.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, MODE_PRIVATE);
    int userId = sp.getInt(SP_KEY_USER_ID, 0);
    OccInspectedSpaceElement occInspectedSpaceElement = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
    OccInspectionSpaceElementRepository repository = OccInspectionSpaceElementRepository.getInstance(activity);
    switch (elementStatus) {
      case PASS:
        occInspectedSpaceElementHeavy = repository.configureElementForCompliance(occInspectedSpaceElementHeavy, userId);
        break;
      case VIOLATION:
        occInspectedSpaceElementHeavy = repository.configureElementForInspectionNoCompliance(occInspectedSpaceElementHeavy, userId, false);
        break;
      case NOT_INSPECT:
        occInspectedSpaceElementHeavy = repository.configureElementForNotInspected(occInspectedSpaceElementHeavy, userId);
        break;
    }
    repository.configureOccInspectedSpaceElementStatus(occInspectedSpaceElement);
    repository.updateOccInspectedSpaceElement(occInspectedSpaceElement);
    return null;
  }



}
