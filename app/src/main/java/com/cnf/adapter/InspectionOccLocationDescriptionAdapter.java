package com.cnf.adapter;

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
import com.cnf.adapter.InspectionOccLocationDescriptionAdapter.OccLocationDescriptionHolder;
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.OccInspectedSpace;
import com.cnf.domain.infra.OccLocationDescription;
import com.cnf.fragment.InspectionSelectOccInspectedSpaceElementCategoryFragment;
import com.cnf.fragment.InspectionSelectOccInspectedSpaceFragment;
import com.cnf.service.local.OccInspectedSpaceService;

import com.cnf.service.local.OccInspectionSpaceElementService;
import java.time.OffsetDateTime;
import java.util.List;

public class InspectionOccLocationDescriptionAdapter extends RecyclerView.Adapter<OccLocationDescriptionHolder> {

  private final Handler textHandler = new Handler();

  private int occChecklistSpaceTypeId;
  private Fragment fragment;
  private Context context;
  private List<OccLocationDescription> occLocationDescriptionList;
  private InspectionDatabase inspectionDB;
  private OccInspectedSpaceService occInspectedSpaceService;
  private OccInspectionSpaceElementService occInspectionSpaceElementService;

  private int userId;
  private int inspectionId;
  private Integer occInspectedSpaceId;

  public InspectionOccLocationDescriptionAdapter(Fragment fragment, Context context, List<OccLocationDescription> occLocationDescriptionList, Integer occInspectedSpaceId) {
    this.occInspectedSpaceId = occInspectedSpaceId;
    this.context = context;
    this.fragment = fragment;
    this.occLocationDescriptionList = occLocationDescriptionList;
    this.inspectionDB = Room.databaseBuilder(context, InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();
    this.occInspectedSpaceService = OccInspectedSpaceService.getInstance();
    this.occInspectionSpaceElementService = OccInspectionSpaceElementService.getInstance();
  }

  public InspectionOccLocationDescriptionAdapter(Fragment fragment, int occChecklistSpaceTypeId, Context context, List<OccLocationDescription> occLocationDescriptionList) {
    this.occChecklistSpaceTypeId = occChecklistSpaceTypeId;
    this.context = context;
    this.fragment = fragment;
    this.occLocationDescriptionList = occLocationDescriptionList;
    this.inspectionDB = Room.databaseBuilder(context, InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();
    this.occInspectedSpaceService = OccInspectedSpaceService.getInstance();
    this.occInspectionSpaceElementService = OccInspectionSpaceElementService.getInstance();
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
        occInspectedSpaceService.updateOccInspectedSpaceLocationDescription(inspectionDB, occInspectedSpaceId, occLocationDescriptionId);
        textHandler.post(() -> {
          fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_INSPECTED_SPACE_ID_NAME, occInspectedSpaceId);
          InspectionSelectOccInspectedSpaceElementCategoryFragment inspectionSelectOccInspectedSpaceElementCategoryFragment = new InspectionSelectOccInspectedSpaceElementCategoryFragment();
          fragment.getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccInspectedSpaceElementCategoryFragment).commit();
        });

      } else {
        OccInspectedSpace occInspectedSpace = new OccInspectedSpace();
        occInspectedSpace.setInspectedSpaceId(null);
        occInspectedSpace.setOccInspectionId(inspectionId);
        occInspectedSpace.setOccLocationDescriptionId(occLocationDescriptionId);
        occInspectedSpace.setAddedToChecklistByUserid(userId);
        occInspectedSpace.setAddedToChecklistTS(OffsetDateTime.now().toString());
        occInspectedSpace.setOccChecklistSpaceTypeId(occChecklistSpaceTypeId);
        long occInspectedSpaceId = occInspectedSpaceService.insertInspectedSpace(inspectionDB, occInspectedSpace);
        occInspectedSpace.setInspectedSpaceId((int) occInspectedSpaceId);

        occInspectionSpaceElementService.createDefaultOccInspectedSpaceElementList(inspectionDB, occInspectedSpace);
        textHandler.post(() -> {
          InspectionSelectOccInspectedSpaceFragment inspectionSelectOccInspectedSpaceFragment = new InspectionSelectOccInspectedSpaceFragment();
          fragment.getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccInspectedSpaceFragment).commit();
        });
      }
    }
  }
}
