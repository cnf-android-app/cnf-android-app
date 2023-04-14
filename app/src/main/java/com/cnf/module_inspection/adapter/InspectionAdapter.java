package com.cnf.module_inspection.adapter;

import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_CHECKLIST_ID_KEY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_ID_KEY;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;

import static com.cnf.utils.AppConstants.SP_KEY_IS_ONLINE;

import android.os.AsyncTask;
import android.widget.RadioGroup;
import androidx.fragment.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.module_inspection.activity.InspectionContainerActivity;
import com.cnf.R;
import com.cnf.module_inspection.adapter.InspectionAdapter.InspectionDispatchHolder;
import com.cnf.module_inspection.async.UploadOccInspectionTask;
import com.cnf.module_inspection.entity.infra.OccCheckList;
import com.cnf.module_inspection.entity.infra_heavy.OccInspectionDispatchHeavy;
import com.cnf.module_inspection.entity.tasks.OccInspection;
import com.cnf.module_inspection.entity.tasks.OccInspectionDispatch;
import com.cnf.module_inspection.entity.tasks.Person;
import com.cnf.module_inspection.entity.tasks.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InspectionAdapter extends RecyclerView.Adapter<InspectionDispatchHolder> implements Filterable {

  private final Fragment fragment;
  private List<OccInspectionDispatchHeavy> occInspectionDispatchHeavyList;
  private List<OccInspectionDispatchHeavy> filterOccInspectionDispatchHeavyList;

  public InspectionAdapter(Fragment fragment, List<OccInspectionDispatchHeavy> occInspectionDispatchHeavyList) {
    this.fragment = fragment;
    this.occInspectionDispatchHeavyList = occInspectionDispatchHeavyList;
    this.filterOccInspectionDispatchHeavyList = occInspectionDispatchHeavyList;
  }

  @NonNull
  @Override
  public InspectionDispatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new InspectionDispatchHolder(LayoutInflater.from(fragment.getActivity()).inflate(R.layout.layout_inspection_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull InspectionDispatchHolder holder, int position) {
    if (fragment.getActivity() == null) {
      return;
    }
    SharedPreferences sp = fragment.getActivity().getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, Context.MODE_PRIVATE);
    boolean isOnline = sp.getBoolean(SP_KEY_IS_ONLINE, false);
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
        fragment.getActivity().startActivity(mapIntent);
      } catch (ActivityNotFoundException e) {
        // Define what your app should do if no activity can handle the intent.
      }
    });

    holder.btnInspect.setOnClickListener(view -> {
      Intent intent = new Intent(fragment.getActivity(), InspectionContainerActivity.class);
      intent.putExtra(INTENT_EXTRA_INSPECTION_ID_KEY, occInspection.getInspectionId());
      intent.putExtra(INTENT_EXTRA_INSPECTION_CHECKLIST_ID_KEY, occCheckList.getCheckListId());
      fragment.getActivity().startActivity(intent);
    });

    holder.btnUpload.setOnClickListener(v -> {
      UploadOccInspectionTask task = new UploadOccInspectionTask(occInspectionDispatch, fragment);
      task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    });

    if (fragment.getActivity() != null) {
      RadioGroup radioGroup  = fragment.getActivity().findViewById(R.id.rg_inspection_is_finish_or_not);
      int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
      switch (checkedRadioButtonId) {
        case R.id.rb_inspection_un_finish:
          holder.btnInspect.setVisibility(View.VISIBLE);
          holder.btnUpload.setVisibility(View.GONE);
          break;
        case R.id.rb_inspection_finished:
          holder.btnInspect.setVisibility(View.VISIBLE);
          if (isOnline) {
            holder.btnUpload.setVisibility(View.VISIBLE);
          } else {
            holder.btnUpload.setVisibility(View.GONE);
          }
          break;
        case R.id.rb_inspection_synchronized:
          holder.btnInspect.setVisibility(View.GONE);
          holder.btnUpload.setVisibility(View.GONE);
          break;
      }
      return;
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

  @Override
  public int getItemCount() {
    return filterOccInspectionDispatchHeavyList.size();
  }

  static class InspectionDispatchHolder extends RecyclerView.ViewHolder {

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

  public void setFilterOccInspectionDispatchHeavyList(List<OccInspectionDispatchHeavy> filterOccInspectionDispatchHeavyList) {
    this.occInspectionDispatchHeavyList = filterOccInspectionDispatchHeavyList;
    this.filterOccInspectionDispatchHeavyList = filterOccInspectionDispatchHeavyList;
  }
}
