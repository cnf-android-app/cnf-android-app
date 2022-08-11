package com.cnf.adapter;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_CHECKLIST_ID_KEY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_ID_KEY;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_AUTH_PERIOD_ID;
import static com.cnf.utils.AppConstants.SP_KEY_IS_ONLINE;
import static com.cnf.utils.AppConstants.SP_KEY_MUNICIPALITY_CODE;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.cnf.InspectionActivity;
import com.cnf.InspectionContainerActivity;
import com.cnf.MuniActivity;
import com.cnf.R;
import com.cnf.adapter.InspectionAdapter.InspectionDispatchHolder;
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.infra.OccCheckList;
import com.cnf.domain.infra_heavy.OccInspectionDispatchHeavy;
import com.cnf.domain.tasks.OccInspection;
import com.cnf.domain.tasks.OccInspectionDispatch;
import com.cnf.domain.tasks.OccPeriod;
import com.cnf.domain.tasks.Person;
import com.cnf.domain.tasks.Property;
import com.cnf.domain.tasks.PropertyUnit;
import com.cnf.dto.UploadDTO;
import com.cnf.service.exception.HttpBadRequestException;
import com.cnf.service.exception.HttpNoFoundException;
import com.cnf.service.exception.HttpServerErrorException;
import com.cnf.service.exception.HttpUnAuthorizedException;
import com.cnf.service.exception.HttpUnknownErrorException;
import com.cnf.service.local.OccInspectionDispatchService;
import com.cnf.service.remote.OccInspectionApiService;
import com.cnf.service.local.OccInspectionInfraService;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InspectionAdapter extends RecyclerView.Adapter<InspectionDispatchHolder> implements Filterable {



  private Context context;
  private boolean isSynchronized;
  private boolean isFinished;
  private List<OccInspectionDispatchHeavy> occInspectionDispatchHeavyList;
  private InspectionDatabase inspectionDB;
  private OccInspectionApiService occInspectionApiService;
  private OccInspectionInfraService occInspectionInfraService;
  private OccInspectionDispatchService occInspectionDispatchService;
  private List<OccInspectionDispatchHeavy> filterOccInspectionDispatchHeavyList;

  public InspectionAdapter(Context context, List<OccInspectionDispatchHeavy> occInspectionDispatchHeavyList, boolean isSynchronized) {
    this.context = context;
    this.isSynchronized = isSynchronized;
    this.occInspectionDispatchHeavyList = occInspectionDispatchHeavyList;
    this.inspectionDB = Room.databaseBuilder(context, InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();
    this.occInspectionApiService = OccInspectionApiService.getInstance();
    this.occInspectionInfraService = OccInspectionInfraService.getInstance();
    this.occInspectionDispatchService = OccInspectionDispatchService.getInstance();
    this.filterOccInspectionDispatchHeavyList = occInspectionDispatchHeavyList;

  }

  @NonNull
  @Override
  public InspectionDispatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new InspectionDispatchHolder(LayoutInflater.from(context).inflate(R.layout.layout_inspection_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull InspectionDispatchHolder holder, int position) {
    SharedPreferences sp = context.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    Boolean isOnline = sp.getBoolean(SP_KEY_IS_ONLINE, false);
    OccInspectionDispatchHeavy occInspectionDispatchHeavy = filterOccInspectionDispatchHeavyList.get(position);
    if (occInspectionDispatchHeavy == null) {
      return;
    }
    OccInspectionDispatch occInspectionDispatch = occInspectionDispatchHeavy.getOccInspectionDispatch();
    OccInspection occInspection = occInspectionDispatchHeavy.getOccInspection();
    Property property = occInspectionDispatchHeavy.getProperty();
    Person person = occInspectionDispatchHeavy.getPerson();
    OccCheckList occCheckList = occInspectionDispatchHeavy.getOccCheckList();
    if (occInspectionDispatch == null || occInspection == null || property == null || person == null || occCheckList == null) {
      return;
    }
    String title = String.format("Inspection ID: %s", occInspection.getInspectionId());
    String field = property.getAddress();
    String inspector = String.format("%s %s", person.getFirstName(), person.getLastName());
    String checklist = occCheckList.getTitle();
    String cratedDate = occInspection.getCreatedTS();
    holder.tvTitle.setText(title);
    holder.tvField.setText(field);
    holder.tvInspector.setText(inspector);
    holder.tvChecklist.setText(checklist);
    holder.tvCreatedDate.setText(cratedDate);

    holder.ivDirection.setOnClickListener(v -> {
      Uri location = Uri.parse("geo:0,0?q=" + field);
      Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
      try {
        context.startActivity(mapIntent);
      } catch (ActivityNotFoundException e) {
        // Define what your app should do if no activity can handle the intent.
      }
    });

    holder.btnInspect.setOnClickListener(view -> {
      Intent intent = new Intent(context, InspectionContainerActivity.class);
      intent.putExtra(INTENT_EXTRA_INSPECTION_ID_KEY, occInspection.getInspectionId());
      intent.putExtra(INTENT_EXTRA_INSPECTION_CHECKLIST_ID_KEY, occCheckList.getCheckListId());
      context.startActivity(intent);
    });

    holder.btnUpload.setOnClickListener(v -> {
      new Thread(new UploadOccInspection(occInspection.getInspectionId(), occInspectionDispatch)).start();
    });

    if (isSynchronized) {
      holder.btnInspect.setVisibility(View.GONE);
      holder.btnUpload.setVisibility(View.GONE);
    } else if (!isFinished) {
      holder.btnInspect.setVisibility(View.VISIBLE);
      holder.btnUpload.setVisibility(View.GONE);
    } else {
      holder.btnInspect.setVisibility(View.VISIBLE);
      if (isOnline) {
        holder.btnUpload.setVisibility(View.VISIBLE);
      } else {
        holder.btnUpload.setVisibility(View.GONE);
      }
    }
  }

  @Override
  public Filter getFilter() {
    return new Filter() {
      @Override
      protected FilterResults performFiltering(CharSequence charSequence) {
        String charString = charSequence.toString();
        if (charString.isEmpty()) {
          filterOccInspectionDispatchHeavyList = occInspectionDispatchHeavyList;
        } else {
          List<OccInspectionDispatchHeavy> filteredList = new ArrayList<>();
          for (int i = 0; i < occInspectionDispatchHeavyList.size(); i++) {
            OccInspectionDispatchHeavy occInspectionDispatchHeavy = occInspectionDispatchHeavyList.get(i);
            OccInspectionDispatch occInspectionDispatch = occInspectionDispatchHeavy.getOccInspectionDispatch();
            OccInspection occInspection = occInspectionDispatchHeavy.getOccInspection();
            Property property = occInspectionDispatchHeavy.getProperty();
            Person person = occInspectionDispatchHeavy.getPerson();
            OccCheckList occCheckList = occInspectionDispatchHeavy.getOccCheckList();
            if (occInspectionDispatch == null || occInspection == null || property == null || person == null || occCheckList == null) {
              continue;
            }
            String title = String.format("Inspection ID: %s", occInspection.getInspectionId());
            String field = property.getAddress();
            String inspector = String.format("%s %s", person.getFirstName(), person.getLastName());
            String checklist = occCheckList.getTitle();
            String cratedDate = occInspection.getCreatedTS();
            if (title.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT)) ||
                field.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT)) ||
                inspector.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT)) ||
                checklist.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT)) ||
                cratedDate.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT))) {
              filteredList.add(occInspectionDispatchHeavy);
            }
          }
          filterOccInspectionDispatchHeavyList = filteredList;
        }
        FilterResults filterResults = new FilterResults();
        filterResults.values = filterOccInspectionDispatchHeavyList;
        return filterResults;
      }

      @Override
      protected void publishResults(CharSequence constraint, FilterResults results) {
        filterOccInspectionDispatchHeavyList = (List<OccInspectionDispatchHeavy>) results.values;
        notifyDataSetChanged();
      }
    };
  }

  class UploadOccInspection implements Runnable {

    private Integer inspectionId;
    private OccInspectionDispatch occInspectionDispatch;
    private  Handler textHandler = new Handler();
    ProgressDialog progressDialog = new ProgressDialog(context);
    public UploadOccInspection(Integer inspectionId, OccInspectionDispatch occInspectionDispatch) {
      this.inspectionId = inspectionId;
      this.occInspectionDispatch = occInspectionDispatch;
    }

    @Override
    public void run() {

      textHandler.post(new Runnable() {
        @Override
        public void run() {

          progressDialog.setMessage("Uploading inspection...");
          progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
          progressDialog.setIndeterminate(true);
          progressDialog.show();
        }
      });

      UploadDTO uploadDTO = occInspectionInfraService.getUploadDTO(inspectionId, inspectionDB);
      Log.i(TAG, uploadDTO.toString());
      SharedPreferences sp = context.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
      String loginUserToken = sp.getString(SP_KEY_USER_LOGIN_TOKEN, null);
      String muniCode = String.valueOf(sp.getInt(SP_KEY_MUNICIPALITY_CODE, -1));
      String authPeriodId = String.valueOf(sp.getInt(SP_KEY_AUTH_PERIOD_ID, -1));
      try {
        occInspectionApiService.uploadToServer(uploadDTO, loginUserToken, authPeriodId, muniCode);
        occInspectionDispatch.setSynchronizationTS(OffsetDateTime.now().toString());
        occInspectionDispatchService.updateOccInspectionDispatch(inspectionDB, occInspectionDispatch);
        context.startActivity(new Intent(context, InspectionActivity.class));

      } catch (IOException e) {
        e.printStackTrace();
      } catch (HttpUnAuthorizedException e) {
        e.printStackTrace();
      } catch (HttpBadRequestException e) {
        e.printStackTrace();
      } catch (HttpServerErrorException e) {
        e.printStackTrace();
      } catch (HttpUnknownErrorException e) {
        e.printStackTrace();
      } catch (HttpNoFoundException e) {
        e.printStackTrace();
      } finally {
        textHandler.post(new Runnable() {
          @Override
          public void run() {
            progressDialog.dismiss();
          }
        });
      }

    }
  }

  @Override
  public int getItemCount() {
    return filterOccInspectionDispatchHeavyList.size();
  }

  class InspectionDispatchHolder extends RecyclerView.ViewHolder {

    TextView tvTitle, tvField, tvInspector, tvChecklist, tvCreatedDate;
    Button btnInspect, btnUpload;
    ImageView ivDirection;

    public InspectionDispatchHolder(@NonNull View itemView) {
      super(itemView);
      tvTitle = itemView.findViewById(R.id.tv_inspection_inspectionId);
      tvField = itemView.findViewById(R.id.tv_inspection_address_value);
      tvInspector = itemView.findViewById(R.id.tv_inspection_inspector_value);
      tvChecklist = itemView.findViewById(R.id.tv_inspection_checklist_value);
      tvCreatedDate = itemView.findViewById(R.id.tv_inspection_created_date_value);
      btnInspect = itemView.findViewById(R.id.btn_inspection_edit);
      btnUpload = itemView.findViewById(R.id.btn_inspection_upload);
      ivDirection = itemView.findViewById(R.id.iv_inspection_direction);
    }
  }

  public boolean isFinished() {
    return isFinished;
  }

  public void setFinished(boolean finished) {
    isFinished = finished;
  }
}
