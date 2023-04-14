package com.cnf.module_inspection.adapter;

import static com.cnf.utils.AppConstants.INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_KEY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_STATUS_KEY;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.module_inspection.adapter.InspectionOccInspectedSpaceElementAdapter.InspectionOccInspectedSpaceElementHolder;
import com.cnf.module_inspection.async.LoadOccInspectedSpaceElementPhotoTask;
import com.cnf.module_inspection.async.SaveOccInspectedSpaceElementTask;

import com.cnf.module_inspection.entity.OccInspectedSpaceElementHeavy;
import com.cnf.module_inspection.entity.OccInspectionStatusEnum;
import com.cnf.module_inspection.entity.infra.IntensityClass;
import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository;

import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository.ElementStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InspectionOccInspectedSpaceElementAdapter extends RecyclerView.Adapter<InspectionOccInspectedSpaceElementHolder> implements Filterable {

  private final Fragment fragment;

  private final List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList;
  private final List<IntensityClass> intensityClassList;
  private List<OccInspectedSpaceElementHeavy> filterOccInspectedSpaceElementHeavyList;

  public InspectionOccInspectedSpaceElementAdapter(Fragment fragment, List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList, List<IntensityClass> intensityClassList) {
    this.fragment = fragment;
    this.occInspectedSpaceElementHeavyList = occInspectedSpaceElementHeavyList;
    this.filterOccInspectedSpaceElementHeavyList = occInspectedSpaceElementHeavyList;
    this.intensityClassList = intensityClassList;
  }

  @NonNull
  @Override
  public InspectionOccInspectedSpaceElementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new InspectionOccInspectedSpaceElementHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inspection_inspected_space_element_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull InspectionOccInspectedSpaceElementHolder holder, int position) {
    OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy = filterOccInspectedSpaceElementHeavyList.get(position);
    OccInspectionSpaceElementRepository repository = OccInspectionSpaceElementRepository.getInstance(fragment.getActivity());

    String sectionNumber = repository.getOrdinanceHeaderSectionNumber(occInspectedSpaceElementHeavy);
    String sectionTitle = repository.getOrdinanceHeaderSectionTitle(occInspectedSpaceElementHeavy);
    holder.inspectedElementSectionNumberTv.setText(sectionNumber);
    holder.inspectedElementSectionTitleTv.setText(sectionTitle);

    buildPhotoDocRecycleView(holder);
    initPanel(holder, occInspectedSpaceElementHeavy);

    buildNotInspectedExpandableCard(holder, occInspectedSpaceElementHeavy);
    buildPassExpandablePanel(holder, occInspectedSpaceElementHeavy);
    buildViolationExpandablePanel(holder, occInspectedSpaceElementHeavy);
    buildIntensityClass(holder, occInspectedSpaceElementHeavy);
  }

  @Override
  public Filter getFilter() {
    return new Filter() {
      @Override
      protected FilterResults performFiltering(CharSequence charSequence) {
        String charString = charSequence.toString();
        if (charString.isEmpty()) {
          filterOccInspectedSpaceElementHeavyList = occInspectedSpaceElementHeavyList;
        } else {
          List<OccInspectedSpaceElementHeavy> filteredList = new ArrayList<>();
          for (int i = 0; i < occInspectedSpaceElementHeavyList.size(); i++) {
            OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy = occInspectedSpaceElementHeavyList.get(i);
            OccInspectionSpaceElementRepository repository = OccInspectionSpaceElementRepository.getInstance(fragment.getActivity());
            String sectionNumber = repository.getOrdinanceHeaderSectionNumber(occInspectedSpaceElementHeavy);
            String sectionTitle = repository.getOrdinanceHeaderSectionTitle(occInspectedSpaceElementHeavy);
            if (sectionNumber.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT))
                || sectionTitle.toLowerCase(Locale.ROOT).contains(charString.toLowerCase(Locale.ROOT))) {
              filteredList.add(occInspectedSpaceElementHeavy);
            }
          }
          filterOccInspectedSpaceElementHeavyList = filteredList;
        }
        FilterResults filterResults = new FilterResults();
        filterResults.values = filterOccInspectedSpaceElementHeavyList;
        return filterResults;
      }

      @Override
      protected void publishResults(CharSequence constraint, FilterResults results) {
        filterOccInspectedSpaceElementHeavyList = (List<OccInspectedSpaceElementHeavy>) results.values;
        notifyDataSetChanged();
      }
    };
  }

  @Override
  public int getItemCount() {
    return filterOccInspectedSpaceElementHeavyList.size();
  }

  static class InspectionOccInspectedSpaceElementHolder extends RecyclerView.ViewHolder {

    public RelativeLayout passRl, violationRl, notInspectedRl;
    public RadioButton notInspectedRadioBtn, passRadioBtn, violationRadioBtn;
    public TextView inspectedElementSectionNumberTv, inspectedElementSectionTitleTv;
    public TextView savePassTv, saveViolationTv, saveNotInspectedTv, exitPassTv, exitViolationTv, exitNotInspectedTv;
    public TextView tvDefaultViolationDesLink;
    public Button takePhotoPassBtn, takePhotoViolateBtn, selectGalleryPassBtn, selectGalleryViolateBtn;
    public RecyclerView inspectedSpaceElementPassPhotoRv, inspectedSpaceElementViolatePhotoRv;
    public EditText findingPassEt, findingViolateEt;
    public Spinner severityS;
    public CheckBox includeInCNFSw;

    public InspectionOccInspectedSpaceElementHolder(@NonNull View itemView) {
      super(itemView);
      // all
      inspectedElementSectionNumberTv = itemView.findViewById(R.id.tv_inspection_occ_inspected_space_element_item_section_number);
      inspectedElementSectionTitleTv = itemView.findViewById(R.id.tv_inspection_occ_inspected_space_element_item_section_title);
      // not inspect
      notInspectedRadioBtn = itemView.findViewById(R.id.rBtn_inspection_occ_inspected_space_element_item_not_inspected);
      notInspectedRl = itemView.findViewById(R.id.rl_inspection_occ_inspected_space_element_item_not_inspected_expand);
      saveNotInspectedTv = itemView.findViewById(R.id.tv_inspection_occ_inspected_space_element_item_not_inspected_save);
      exitNotInspectedTv = itemView.findViewById(R.id.tv_inspection_occ_inspected_space_element_item_not_inspected_exit);
      // pass
      passRadioBtn = itemView.findViewById(R.id.rBtn_inspection_occ_inspected_space_element_item_pass);
      passRl = itemView.findViewById(R.id.rl_inspection_occ_inspected_space_element_item_pass_expand);
      takePhotoPassBtn = itemView.findViewById(R.id.btn_inspection_occ_inspected_space_element_item_pass_take_photo);
      inspectedSpaceElementPassPhotoRv = itemView.findViewById(R.id.rv_inspection_occ_inspected_space_element_item_pass_photo);
      savePassTv = itemView.findViewById(R.id.tv_inspection_occ_inspected_space_element_item_pass_save);
      exitPassTv = itemView.findViewById(R.id.tv_inspection_occ_inspected_space_element_item_pass_exit);
      findingPassEt = itemView.findViewById(R.id.et_inspection_occ_inspected_space_element_item_pass_finding);
      selectGalleryPassBtn = itemView.findViewById(R.id.btn_inspection_occ_inspected_space_element_item_pass_select_from_gallery);
      // violate
      violationRadioBtn = itemView.findViewById(R.id.rBtn_inspection_occ_inspected_space_element_item_violation);
      violationRl = itemView.findViewById(R.id.rl_inspection_occ_inspected_space_element_item_violation_expand);
      takePhotoViolateBtn = itemView.findViewById(R.id.btn_inspection_occ_inspected_space_element_item_violation_take_photo);
      inspectedSpaceElementViolatePhotoRv = itemView.findViewById(R.id.rv_inspection_occ_inspected_space_element_item_violation_photo);
      saveViolationTv = itemView.findViewById(R.id.tv_inspection_occ_inspected_space_element_item_violation_save);
      exitViolationTv = itemView.findViewById(R.id.tv_inspection_occ_inspected_space_element_item_violation_exit);
      findingViolateEt = itemView.findViewById(R.id.et_inspection_occ_inspected_space_element_item_violation_finding);
      severityS = itemView.findViewById(R.id.sp_inspection_occ_inspected_space_element_item_violation_spinner);
      tvDefaultViolationDesLink = itemView.findViewById(R.id.sw_inspection_occ_inspected_space_element_item_violation_default_description);
      includeInCNFSw = itemView.findViewById(R.id.sw_inspection_occ_inspected_space_element_item_violation_include_cnf_case);
      selectGalleryViolateBtn = itemView.findViewById(R.id.btn_inspection_occ_inspected_space_element_item_violation_select_from_gallery);
    }
  }

  public void setFilterOccInspectedSpaceElementHeavyList(List<OccInspectedSpaceElementHeavy> filterOccInspectedSpaceElementHeavyList) {
    this.filterOccInspectedSpaceElementHeavyList = filterOccInspectedSpaceElementHeavyList;
  }

  public void hideKeyboard(View view) {
    if (fragment.getActivity() == null) {
      return;
    }
    InputMethodManager inputMethodManager = (InputMethodManager) fragment.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }

  private void buildPhotoDocRecycleView(InspectionOccInspectedSpaceElementHolder holder) {
    RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    LinearLayoutManager layoutManagerPass = new LinearLayoutManager(holder.inspectedSpaceElementPassPhotoRv.getContext(), LinearLayoutManager.VERTICAL, false);
    LinearLayoutManager layoutManagerViolation = new LinearLayoutManager(holder.inspectedSpaceElementPassPhotoRv.getContext(), LinearLayoutManager.VERTICAL, false);
    holder.inspectedSpaceElementPassPhotoRv.setLayoutManager(layoutManagerPass);
    holder.inspectedSpaceElementPassPhotoRv.setRecycledViewPool(viewPool);
    holder.inspectedSpaceElementViolatePhotoRv.setLayoutManager(layoutManagerViolation);
    holder.inspectedSpaceElementViolatePhotoRv.setRecycledViewPool(viewPool);
  }

  private void initPanel(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
    closeExpandablePanel(holder);
    if (occInspectedSpaceElementHeavy == null
        || occInspectedSpaceElementHeavy.getOccInspectedSpaceElement() == null
        || occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getStatus() == null) {
      return;
    }
    OccInspectionStatusEnum status = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getStatus().getStatusEnum();
    switch (status) {
      case NOTINSPECTED:
        holder.notInspectedRadioBtn.setChecked(true);
        break;
      case PASS:
        holder.passRadioBtn.setChecked(true);
        break;
      case VIOLATION:
        holder.violationRadioBtn.setChecked(true);
        break;
    }
  }

  private void closeExpandablePanel(InspectionOccInspectedSpaceElementHolder holder) {
    holder.passRl.setVisibility(View.GONE);
    holder.violationRl.setVisibility(View.GONE);
    holder.notInspectedRl.setVisibility(View.GONE);
  }

  private void expandCard(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy, ElementStatus elementStatus) {
    int position = holder.getLayoutPosition();
    if (occInspectedSpaceElementHeavy == null || occInspectedSpaceElementHeavy.getOccInspectedSpaceElement() == null) {
      return;
    }
    String occInspectedSpaceElementId = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getInspectedSpaceElementId();
    LoadOccInspectedSpaceElementPhotoTask task;
    switch (elementStatus) {
      case PASS:
        holder.passRadioBtn.setChecked(true);
        holder.passRl.setVisibility(View.VISIBLE);
        holder.violationRl.setVisibility(View.GONE);
        holder.notInspectedRl.setVisibility(View.GONE);
        task = new LoadOccInspectedSpaceElementPhotoTask(
            position,
            occInspectedSpaceElementId,
            ElementStatus.PASS,
            fragment);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        break;
      case NOT_INSPECT:
        holder.notInspectedRadioBtn.setChecked(true);
        holder.passRl.setVisibility(View.GONE);
        holder.violationRl.setVisibility(View.GONE);
        holder.notInspectedRl.setVisibility(View.VISIBLE);
        break;
      case VIOLATION:
        holder.violationRadioBtn.setChecked(true);
        holder.passRl.setVisibility(View.GONE);
        holder.violationRl.setVisibility(View.VISIBLE);
        holder.notInspectedRl.setVisibility(View.GONE);
        task = new LoadOccInspectedSpaceElementPhotoTask(
            position,
            occInspectedSpaceElementId,
            ElementStatus.VIOLATION,
            fragment);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        break;
    }
  }

  private void buildNotInspectedExpandableCard(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
    holder.notInspectedRadioBtn.setOnClickListener(v -> expandCard(holder, occInspectedSpaceElementHeavy, ElementStatus.NOT_INSPECT));
    holder.saveNotInspectedTv.setOnClickListener(v -> new AlertDialog.Builder(fragment.getActivity())
        .setTitle("Confirm Save")
        .setMessage("Are you sure you want to save this record?")
        .setPositiveButton("Yes", (dialog, which) -> {
          SaveOccInspectedSpaceElementTask task = new SaveOccInspectedSpaceElementTask(ElementStatus.NOT_INSPECT, occInspectedSpaceElementHeavy, fragment);
          task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        })
        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
        .create()
        .show());
    holder.exitNotInspectedTv.setOnClickListener(v -> new AlertDialog
        .Builder(fragment.getActivity())
        .setTitle("Confirm Exit")
        .setMessage("Are you sure you want exit editing?")
        .setPositiveButton("Yes", (dialog, which) -> initPanel(holder, occInspectedSpaceElementHeavy))
        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
        .create()
        .show());
  }

  private void buildPassExpandablePanel(InspectionOccInspectedSpaceElementHolder holder, @NonNull OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
    holder.passRadioBtn.setOnClickListener(v -> expandCard(holder, occInspectedSpaceElementHeavy, ElementStatus.PASS));
    holder.findingPassEt.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().setNotes(holder.findingPassEt.getText().toString());
      }
    });

    holder.findingPassEt.setOnFocusChangeListener((v, hasFocus) -> {
      if (!hasFocus) {
        hideKeyboard(v);
      }
    });

    holder.findingPassEt.setText(occInspectedSpaceElementHeavy.getOccInspectedSpaceElement() == null ?
        "" : occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getNotes());

    holder.takePhotoPassBtn.setOnClickListener(v -> {
      if (fragment == null || fragment.getActivity() == null) {
        return;
      }
      String inspectedSpaceElementId = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getInspectedSpaceElementId();
      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY, inspectedSpaceElementId);
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY, "IMG_" + LocalDateTime.now() + ".JPEG");
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_KEY, holder.getLayoutPosition());
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_STATUS_KEY, ElementStatus.PASS);
      fragment.getActivity().startActivityForResult(intent, 1);
    });

    holder.selectGalleryPassBtn.setOnClickListener(v -> {
      if (fragment == null || fragment.getActivity() == null) {
        return;
      }
      String inspectedSpaceElementId = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getInspectedSpaceElementId();
      Intent intent = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
      intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY, inspectedSpaceElementId);
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY, "IMG_" + LocalDateTime.now() + ".JPEG");
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_KEY, holder.getLayoutPosition());
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_STATUS_KEY, ElementStatus.PASS);
      fragment.getActivity().startActivityForResult(intent, 3);
    });

    holder.savePassTv.setOnClickListener(v -> new AlertDialog.Builder(fragment.getActivity())
        .setTitle("Confirm Save")
        .setMessage("Are you sure you want to save this record?")
        .setPositiveButton("Yes", (dialog, which) -> {
          SaveOccInspectedSpaceElementTask task = new SaveOccInspectedSpaceElementTask(ElementStatus.PASS, occInspectedSpaceElementHeavy, fragment);
          task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
          closeExpandablePanel(holder);
        })
        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
        .create()
        .show());

    holder.exitPassTv.setOnClickListener(v -> new AlertDialog
        .Builder(fragment.getActivity())
        .setTitle("Confirm Exit")
        .setMessage("Are you sure you want exit editing?")
        .setPositiveButton("Yes", (dialog, which) -> initPanel(holder, occInspectedSpaceElementHeavy))
        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
        .create()
        .show());
  }

  private void buildIntensityClass(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
    ArrayAdapter<IntensityClass> intensityClassArrayAdapter = new ArrayAdapter<>(fragment.getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, intensityClassList);
    //IntensityClass intensityClass = new IntensityClass();
    //intensityClass.setTitle("Violation Severity");
   // intensityClassList.add(0, intensityClass);

    intensityClassArrayAdapter.setDropDownViewResource(R.layout.drop_down_item);

    holder.severityS.setAdapter(intensityClassArrayAdapter);
    holder.severityS.setPrompt("Violation Severity");
    holder.severityS.setSelection(0);
    for (int i = 0; i < intensityClassList.size(); i++) {
      if (intensityClassList.get(i).getClassId() == (occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getFailureSeverityIntensityClassId())) {
        holder.severityS.setSelection(i);
      }
    }
    holder.severityS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().setFailureSeverityIntensityClassId(intensityClassList.get(position).getClassId());
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().setFailureSeverityIntensityClassId(null);
      }
    });
  }

  private void buildViolationExpandablePanel(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
    holder.violationRadioBtn.setOnClickListener(v -> expandCard(holder, occInspectedSpaceElementHeavy, ElementStatus.VIOLATION));
    holder.findingViolateEt.setText(occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getNotes());
    holder.findingViolateEt.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        System.out.println(holder.findingViolateEt.getText().toString());
        occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().setNotes(holder.findingViolateEt.getText().toString());
      }
    });

    holder.findingViolateEt.setOnFocusChangeListener((v, hasFocus) -> {
      if (!hasFocus) {
        hideKeyboard(v);
      }
    });

    boolean toUseDefaultDescription = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().isToUseDefaultDescription();

    //holder.tvDefaultViolationDesLink.setChecked(toUseDefaultDescription);

    Boolean migrateToCECaseOnFail = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getMigrateToCECaseOnFail();
    if (migrateToCECaseOnFail == null || migrateToCECaseOnFail == false) {
      holder.includeInCNFSw.setChecked(false);
    }else {
      holder.includeInCNFSw.setChecked(true);
    }

    holder.tvDefaultViolationDesLink.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        //todo load default description
      }
    });

    holder.includeInCNFSw.setOnCheckedChangeListener((buttonView, isChecked) -> occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().setMigrateToCECaseOnFail(isChecked));

    holder.takePhotoViolateBtn.setOnClickListener(v -> {
      if (fragment.getActivity() == null) {
        return;
      }
      String inspectedSpaceElementId = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getInspectedSpaceElementId();
      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY, inspectedSpaceElementId);
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY, "IMG_" + LocalDateTime.now() + ".JPEG");
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_KEY, holder.getLayoutPosition());
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_STATUS_KEY, ElementStatus.VIOLATION);
      fragment.getActivity().startActivityForResult(intent, 1);
    });

    holder.selectGalleryViolateBtn.setOnClickListener(v -> {
      if (fragment.getActivity() == null) {
        //TODO
        return;
      }
      String inspectedSpaceElementId = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getInspectedSpaceElementId();
      Intent intent = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
      intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY, inspectedSpaceElementId);
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY, "IMG_" + LocalDateTime.now() + ".JPEG");
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_KEY, holder.getLayoutPosition());
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_POSITION_STATUS_KEY, ElementStatus.VIOLATION);
      fragment.getActivity().startActivityForResult(intent, 3);
    });

    holder.saveViolationTv.setOnClickListener(v -> new AlertDialog.Builder(fragment.getActivity())
        .setTitle("Confirm Save")
        .setMessage("Are you sure you want to save this record?")
        .setPositiveButton("Yes", (dialog, which) -> {
          SaveOccInspectedSpaceElementTask task = new SaveOccInspectedSpaceElementTask(ElementStatus.VIOLATION, occInspectedSpaceElementHeavy, fragment);
          task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
          closeExpandablePanel(holder);
        })
        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
        .create()
        .show());

    holder.exitViolationTv.setOnClickListener(v -> new AlertDialog.Builder(fragment.getActivity())
        .setTitle("Confirm Exit")
        .setMessage("Are you sure you want exit editing?")
        .setPositiveButton("Yes", (dialog, which) -> initPanel(holder, occInspectedSpaceElementHeavy))
        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
        .create()
        .show());
  }

}
