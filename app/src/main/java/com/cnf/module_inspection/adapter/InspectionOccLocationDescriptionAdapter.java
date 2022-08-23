package com.cnf.module_inspection.adapter;

import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ID_NAME;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_ID_KEY;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_USER_ID;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.cnf.R;
import com.cnf.module_inspection.adapter.InspectionOccLocationDescriptionAdapter.OccLocationDescriptionHolder;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.OccInspectedSpace;
import com.cnf.module_inspection.entity.infra.OccLocationDescription;
import com.cnf.module_inspection.fragment.InspectionSelectOccInspectedSpaceElementCategoryFragment;
import com.cnf.module_inspection.fragment.InspectionSelectOccInspectedSpaceFragment;
import com.cnf.module_inspection.service.exception.InvalidOccInspectedSpaceException;
import com.cnf.module_inspection.service.local.OccInspectionSpaceRepository;

import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class InspectionOccLocationDescriptionAdapter extends RecyclerView.Adapter<OccLocationDescriptionHolder> {

  private final Handler textHandler = new Handler();

  private int occChecklistSpaceTypeId;
  private Fragment fragment;
  private Context context;
  private List<OccLocationDescription> occLocationDescriptionList;
  private OccInspectionSpaceRepository occInspectionSpaceRepository;
  private OccInspectionSpaceElementRepository occInspectionSpaceElementRepository;

  private int userId;
  private int inspectionId;
  private String occInspectedSpaceId;

  public InspectionOccLocationDescriptionAdapter(Fragment fragment, Context context, List<OccLocationDescription> occLocationDescriptionList, String occInspectedSpaceId) {
    this.occInspectedSpaceId = occInspectedSpaceId;
    this.context = context;
    this.fragment = fragment;
    this.occLocationDescriptionList = occLocationDescriptionList;
    this.occInspectionSpaceRepository = OccInspectionSpaceRepository.getInstance(context);
    this.occInspectionSpaceElementRepository = OccInspectionSpaceElementRepository.getInstance(context);
  }

  public InspectionOccLocationDescriptionAdapter(Fragment fragment, int occChecklistSpaceTypeId, Context context, List<OccLocationDescription> occLocationDescriptionList) {
    this.occChecklistSpaceTypeId = occChecklistSpaceTypeId;
    this.context = context;
    this.fragment = fragment;
    this.occLocationDescriptionList = occLocationDescriptionList;
    this.occInspectionSpaceRepository = OccInspectionSpaceRepository.getInstance(context);
    this.occInspectionSpaceElementRepository = OccInspectionSpaceElementRepository.getInstance(context);
  }

  @NonNull
  @Override
  public OccLocationDescriptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new OccLocationDescriptionHolder(LayoutInflater.from(context).inflate(R.layout.layout_inspection_occ_location_description_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull OccLocationDescriptionHolder holder, int position) {
    OccLocationDescription occLocationDescription = occLocationDescriptionList.get(position);
    String locationDescription = occLocationDescription.getDescription();
    SharedPreferences sp = context.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, context.MODE_PRIVATE);
    userId = sp.getInt(SP_KEY_USER_ID, -1);
    inspectionId = fragment.getActivity().getIntent().getIntExtra(INTENT_EXTRA_INSPECTION_ID_KEY, -1);

    holder.tvDescription.setText(locationDescription);
    holder.btnCreate.setOnClickListener(view -> {
      new Thread(new CreateOccInspectedSpace(occLocationDescription.getLocationDescriptionId())).start();
    });
  }

  @Override
  public int getItemCount() {
    return occLocationDescriptionList.size();
  }

  class OccLocationDescriptionHolder extends RecyclerView.ViewHolder {

    private TextView tvDescription;
    private Button btnCreate;

    public OccLocationDescriptionHolder(@NonNull View itemView) {
      super(itemView);
      tvDescription = itemView.findViewById(R.id.tv_inspection_occ_location_description);
      btnCreate = itemView.findViewById(R.id.btn_inspection_occ_location_description_create);
    }
  }

  class CreateOccInspectedSpace implements Runnable {

    private int occLocationDescriptionId;

    public CreateOccInspectedSpace(int occLocationDescriptionId) {
      this.occLocationDescriptionId = occLocationDescriptionId;
    }

    @Override
    public void run() {
      if (occInspectedSpaceId != null) {
        occInspectionSpaceRepository.updateOccInspectedSpaceLocationDescription( occInspectedSpaceId, occLocationDescriptionId);
        textHandler.post(() -> {
          fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_INSPECTED_SPACE_ID_NAME, occInspectedSpaceId);
          InspectionSelectOccInspectedSpaceElementCategoryFragment inspectionSelectOccInspectedSpaceElementCategoryFragment = new InspectionSelectOccInspectedSpaceElementCategoryFragment();
          fragment.getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccInspectedSpaceElementCategoryFragment).commit();
        });

      } else {
        OccInspectedSpace occInspectedSpace = new OccInspectedSpace();
        String occInspectedSpaceId = UUID.randomUUID().toString();
        occInspectedSpace.setInspectedSpaceId(occInspectedSpaceId);
        occInspectedSpace.setOccInspectionId(inspectionId);
        occInspectedSpace.setOccLocationDescriptionId(occLocationDescriptionId);
        occInspectedSpace.setAddedToChecklistByUserid(userId);
        occInspectedSpace.setAddedToChecklistTS(OffsetDateTime.now().toString());
        occInspectedSpace.setOccChecklistSpaceTypeId(occChecklistSpaceTypeId);
        occInspectionSpaceRepository.insertOccInspectedSpace( occInspectedSpace);
        try {
          occInspectionSpaceElementRepository.createDefaultOccInspectedSpaceElementList( occInspectedSpace);
        } catch (InvalidOccInspectedSpaceException e) {
          e.printStackTrace();
        }
        textHandler.post(() -> {
          InspectionSelectOccInspectedSpaceFragment inspectionSelectOccInspectedSpaceFragment = new InspectionSelectOccInspectedSpaceFragment();
          fragment.getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccInspectedSpaceFragment).commit();
        });
      }
    }
  }
}
