package com.cnf.adapter;

import static android.content.Context.MODE_PRIVATE;
import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY;
import static com.cnf.utils.AppConstants.SP_KEY_USER_ID;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.room.Room;
import com.cnf.R;
import com.cnf.adapter.InspectionOccInspectedSpaceElementAdapter.InspectionOccInspectedSpaceElementHolder;
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.BlobBytes;
import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.domain.OccInspectedSpaceElementHeavy;
import com.cnf.domain.infra.IntensityClass;
import com.cnf.domain.infra.OccCheckList;
import com.cnf.domain.infra_heavy.OccInspectionDispatchHeavy;
import com.cnf.domain.tasks.OccInspection;
import com.cnf.domain.tasks.OccInspectionDispatch;
import com.cnf.domain.tasks.Person;
import com.cnf.domain.tasks.Property;
import com.cnf.service.local.OccInspectionSpaceElementService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InspectionOccInspectedSpaceElementAdapter extends RecyclerView.Adapter<InspectionOccInspectedSpaceElementHolder> implements Filterable {

  private final Handler handler = new Handler();

  private Activity context;
  private Fragment fragment;
  private RecyclerView.RecycledViewPool viewPool;
  private List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList;
  private List<OccInspectedSpaceElementHeavy> filterOccInspectedSpaceElementHeavyList;
  private OccInspectionSpaceElementService occInspectionSpaceElementService;
  private List<IntensityClass> intensityClassList;

  private InspectionDatabase inspectionDB;

  public InspectionOccInspectedSpaceElementAdapter(Activity context, Fragment fragment, List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList,
      List<IntensityClass> intensityClassList) {
    this.context = context;
    this.fragment = fragment;
    this.occInspectedSpaceElementHeavyList = occInspectedSpaceElementHeavyList;
    this.filterOccInspectedSpaceElementHeavyList = occInspectedSpaceElementHeavyList;
    this.intensityClassList = intensityClassList;
    this.occInspectionSpaceElementService = OccInspectionSpaceElementService.getInstance();
    this.viewPool = new RecyclerView.RecycledViewPool();
    this.inspectionDB = Room.databaseBuilder(context, InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();
  }

  @Override
  public InspectionOccInspectedSpaceElementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new InspectionOccInspectedSpaceElementHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inspection_inspected_space_element_item, parent, false));
  }


  private void buildOccInspectedSpaceElementPhotoDocSectionRV(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
    List<BlobBytes> photoBlobBytesList = occInspectedSpaceElementHeavy.getBlobBytesList();
    LinearLayoutManager layoutManagerPass = new LinearLayoutManager(holder.inspectedSpaceElementPassPhotoRv.getContext(), LinearLayoutManager.VERTICAL, false);
    LinearLayoutManager layoutManagerViolate = new LinearLayoutManager(holder.inspectedSpaceElementViolatePhotoRv.getContext(), LinearLayoutManager.VERTICAL, false);
    layoutManagerPass.setInitialPrefetchItemCount(photoBlobBytesList.size());
    layoutManagerViolate.setInitialPrefetchItemCount(photoBlobBytesList.size());
    InspectionInspectedSpaceElementPhotoAdapter inspectionInspectedSpaceElementPhotoAdapter = new InspectionInspectedSpaceElementPhotoAdapter(context, photoBlobBytesList);

    holder.inspectedSpaceElementPassPhotoRv.setLayoutManager(layoutManagerPass);
    holder.inspectedSpaceElementPassPhotoRv.setAdapter(inspectionInspectedSpaceElementPhotoAdapter);
    holder.inspectedSpaceElementPassPhotoRv.setRecycledViewPool(viewPool);

    holder.inspectedSpaceElementViolatePhotoRv.setLayoutManager(layoutManagerViolate);
    holder.inspectedSpaceElementViolatePhotoRv.setAdapter(inspectionInspectedSpaceElementPhotoAdapter);
    holder.inspectedSpaceElementViolatePhotoRv.setRecycledViewPool(viewPool);
  }

  private void buildOccInspectedSpaceElementMainCard(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
    String sectionNumber = occInspectionSpaceElementService.getOrdinanceHeaderSectionNumber(occInspectedSpaceElementHeavy);
    String sectionTitle = occInspectionSpaceElementService.getOrdinanceHeaderSectionTitle(occInspectedSpaceElementHeavy);

    holder.inspectedElementSectionNumberTv.setText(sectionNumber);
    holder.inspectedElementSectionTitleTv.setText(sectionTitle);
  }

  @Override
  public Filter getFilter() {
    return new Filter() {
      @Override
      protected FilterResults performFiltering(CharSequence charSequence) {
        String charString = charSequence.toString();
        if(charString.isEmpty()) {
          filterOccInspectedSpaceElementHeavyList = occInspectedSpaceElementHeavyList;
        } else {
          List<OccInspectedSpaceElementHeavy> filteredList = new ArrayList<>();
          for (int i = 0; i < occInspectedSpaceElementHeavyList.size(); i++) {
            OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy = occInspectedSpaceElementHeavyList.get(i);
            String sectionNumber = occInspectionSpaceElementService.getOrdinanceHeaderSectionNumber(occInspectedSpaceElementHeavy);
            String sectionTitle = occInspectionSpaceElementService.getOrdinanceHeaderSectionTitle(occInspectedSpaceElementHeavy);

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

  class SaveNotInspected implements Runnable {

    private InspectionOccInspectedSpaceElementHolder holder;
    private OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy;

    public SaveNotInspected(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
      this.holder = holder;
      this.occInspectedSpaceElementHeavy = occInspectedSpaceElementHeavy;
    }

    @Override
    public void run() {
      OccInspectedSpaceElement occInspectedSpaceElement = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
      SharedPreferences sp = context.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, MODE_PRIVATE);
      int userId = sp.getInt(SP_KEY_USER_ID, 0);
      occInspectedSpaceElementHeavy = occInspectionSpaceElementService.inspectionAction_configureElementForNotInspected(occInspectedSpaceElementHeavy, userId);
      occInspectionSpaceElementService.configureOccInspectedSpaceElementStatus(occInspectedSpaceElement);
      occInspectionSpaceElementService.updateOccInspectedSpaceElement(inspectionDB, occInspectedSpaceElement);
      handler.post(() -> closeExpandableCard(holder, occInspectedSpaceElementHeavy));
    }
  }

  private void closeExpandableCard(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {

    occInspectedSpaceElementHeavy.setNotInspectedExpand(false);
    occInspectedSpaceElementHeavy.setPassExpand(false);
    occInspectedSpaceElementHeavy.setViolationExpand(false);

    holder.passRl.setVisibility(occInspectedSpaceElementHeavy.isPassExpand() ? View.VISIBLE : View.GONE);
    holder.violationRl.setVisibility(occInspectedSpaceElementHeavy.isViolationExpand() ? View.VISIBLE : View.GONE);
    holder.notInspectedRl.setVisibility(occInspectedSpaceElementHeavy.isNotInspectedExpand() ? View.VISIBLE : View.GONE);

    if (occInspectionSpaceElementService.isInspectedSpaceElementPass(occInspectedSpaceElementHeavy)) {
      holder.notInspectedRadioBtn.setChecked(false);
      holder.passRadioBtn.setChecked(true);
      holder.violationRadioBtn.setChecked(false);
    } else if (occInspectionSpaceElementService.isInspectedSpaceElementNotInspected(occInspectedSpaceElementHeavy)) {
      holder.notInspectedRadioBtn.setChecked(true);
      holder.passRadioBtn.setChecked(false);
      holder.violationRadioBtn.setChecked(false);
    } else {
      holder.notInspectedRadioBtn.setChecked(false);
      holder.passRadioBtn.setChecked(false);
      holder.violationRadioBtn.setChecked(true);
    }
  }

  private void expandCard(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy, boolean isNotInspectChecked, boolean isPassChecked,
      boolean isViolationChecked) {

    occInspectedSpaceElementHeavy.setNotInspectedExpand(isNotInspectChecked);
    occInspectedSpaceElementHeavy.setPassExpand(isPassChecked);
    occInspectedSpaceElementHeavy.setViolationExpand(isViolationChecked);

    holder.passRl.setVisibility(occInspectedSpaceElementHeavy.isPassExpand() ? View.VISIBLE : View.GONE);
    holder.violationRl.setVisibility(occInspectedSpaceElementHeavy.isViolationExpand() ? View.VISIBLE : View.GONE);
    holder.notInspectedRl.setVisibility(occInspectedSpaceElementHeavy.isNotInspectedExpand() ? View.VISIBLE : View.GONE);

    holder.notInspectedRadioBtn.setChecked(isNotInspectChecked);
    holder.passRadioBtn.setChecked(isPassChecked);
    holder.violationRadioBtn.setChecked(isViolationChecked);
  }

  private void initCard(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
    if (occInspectedSpaceElementHeavy.isPassExpand() || occInspectedSpaceElementHeavy.isViolationExpand()) { // under editing
      holder.notInspectedRadioBtn.setChecked(false);
      if (occInspectedSpaceElementHeavy.isPassExpand()) {
        holder.passRadioBtn.setChecked(true);
        holder.violationRadioBtn.setChecked(false);
      } else {
        holder.passRadioBtn.setChecked(false);
        holder.violationRadioBtn.setChecked(true);
      }
    } else {
      if (occInspectionSpaceElementService.isInspectedSpaceElementPass(occInspectedSpaceElementHeavy)) {
        holder.passRadioBtn.setChecked(true);
      } else if (occInspectionSpaceElementService.isInspectedSpaceElementNotInspected(occInspectedSpaceElementHeavy)) {
        holder.notInspectedRadioBtn.setChecked(true);
      } else {
        holder.violationRadioBtn.setChecked(true);
      }
    }
    holder.passRl.setVisibility(occInspectedSpaceElementHeavy.isPassExpand() ? View.VISIBLE : View.GONE);
    holder.violationRl.setVisibility(occInspectedSpaceElementHeavy.isViolationExpand() ? View.VISIBLE : View.GONE);
    holder.notInspectedRl.setVisibility(occInspectedSpaceElementHeavy.isNotInspectedExpand() ? View.VISIBLE : View.GONE);
  }


  private void buildNotInspectedExpandableCard(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {

    holder.notInspectedRadioBtn.setOnClickListener(v -> expandCard(holder, occInspectedSpaceElementHeavy, true, false, false));
    holder.saveNotInspectedTv.setOnClickListener(v -> {

      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("Confirm Save");
      builder.setMessage("Are you sure you want to save this record?");
      builder.setPositiveButton("Yes", (dialog, which) -> {
        new Thread(new SaveNotInspected(holder, occInspectedSpaceElementHeavy)).start();
      });
      builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
      AlertDialog alertDialog = builder.create();
      alertDialog.show();
    });

    holder.exitNotInspectedTv.setOnClickListener(v -> {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("Confirm Exit");
      builder.setMessage("Are you sure you want exit editing?");

      builder.setPositiveButton("Yes", (dialog, which) -> {
        closeExpandableCard(holder, occInspectedSpaceElementHeavy);
      });
      builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
      AlertDialog alertDialog = builder.create();
      alertDialog.show();
    });
  }

  public class SavePass implements Runnable {

    private InspectionOccInspectedSpaceElementHolder holder;
    private OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy;

    public SavePass(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
      this.holder = holder;
      this.occInspectedSpaceElementHeavy = occInspectedSpaceElementHeavy;
    }

    @Override
    public void run() {
      String passFindingStr = holder.findingPassEt.getText().toString();
      OccInspectedSpaceElement occInspectedSpaceElement = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
      occInspectedSpaceElement.setNotes(passFindingStr);
      SharedPreferences sp = context.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, MODE_PRIVATE);
      int userId = sp.getInt(SP_KEY_USER_ID, 0);
      occInspectedSpaceElementHeavy = occInspectionSpaceElementService.inspectionAction_configureElementForCompliance(occInspectedSpaceElementHeavy, userId);
      occInspectionSpaceElementService.configureOccInspectedSpaceElementStatus(occInspectedSpaceElement);
      occInspectionSpaceElementService.updateOccInspectedSpaceElement(inspectionDB, occInspectedSpaceElement);
      handler.post(() -> closeExpandableCard(holder, occInspectedSpaceElementHeavy));
    }
  }

  private void buildPassExpandableCard(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
    holder.passRadioBtn.setOnClickListener(v -> expandCard(holder, occInspectedSpaceElementHeavy, false, true, false));

    holder.findingPassEt.setOnFocusChangeListener((v, hasFocus) -> {
      if (holder.findingPassEt.getText() != null) {
        occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().setNotes(holder.findingPassEt.getText().toString());
      }
    });

    holder.findingPassEt.setText(occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getNotes());

    holder.takePhotoPassBtn.setOnClickListener(v -> {
      String inspectedSpaceElementId = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getInspectedSpaceElementId();
      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      context.getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY, inspectedSpaceElementId);
      context.getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY, "IMG_" + LocalDateTime.now() + ".JPEG");
      fragment.getActivity().startActivityForResult(intent, 1);
    });

    holder.selectGalleryPassBtn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        String inspectedSpaceElementId = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getInspectedSpaceElementId();
        Intent intent = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        context.getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY, inspectedSpaceElementId);
        context.getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY, "IMG_" + LocalDateTime.now() + ".JPEG");
        fragment.getActivity().startActivityForResult(intent, 3);
      }
    });

    holder.savePassTv.setOnClickListener(v -> {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("Confirm Save");
      builder.setMessage("Are you sure you want to save this record?");
      builder.setPositiveButton("Yes", (dialog, which) -> {
        new Thread(new SavePass(holder, occInspectedSpaceElementHeavy)).start();
      });
      builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
      AlertDialog alertDialog = builder.create();
      alertDialog.show();
    });

    holder.exitPassTv.setOnClickListener(v -> {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("Confirm Exit");
      builder.setMessage("Are you sure you want exit editing?");
      builder.setPositiveButton("Yes", (dialog, which) -> {
        closeExpandableCard(holder, occInspectedSpaceElementHeavy);
      });
      builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
      AlertDialog alertDialog = builder.create();
      alertDialog.show();
    });
  }

  class SaveViolation implements Runnable {

    private InspectionOccInspectedSpaceElementHolder holder;
    private OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy;

    public SaveViolation(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
      this.holder = holder;
      this.occInspectedSpaceElementHeavy = occInspectedSpaceElementHeavy;
    }

    @Override
    public void run() {
      String findingNotes = holder.findingViolateEt.getText().toString();
      OccInspectedSpaceElement occInspectedSpaceElement = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement();
      occInspectedSpaceElement.setNotes(findingNotes);
      SharedPreferences sp = context.getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, MODE_PRIVATE);
      int userId = sp.getInt(SP_KEY_USER_ID, 0);
      occInspectedSpaceElementHeavy = occInspectionSpaceElementService.inspectionAction_configureElementForInspectionNoCompliance(occInspectedSpaceElementHeavy, userId,
          occInspectedSpaceElement.isToUseDefaultDescription());
      occInspectionSpaceElementService.configureOccInspectedSpaceElementStatus(occInspectedSpaceElement);
      occInspectionSpaceElementService.updateOccInspectedSpaceElement(inspectionDB, occInspectedSpaceElement);
      handler.post(() -> closeExpandableCard(holder, occInspectedSpaceElementHeavy));
    }
  }

  private void buildIntensityClass(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
    ArrayAdapter<IntensityClass> intensityClassArrayAdapter = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, intensityClassList);
    // default

    IntensityClass intensityClass = new IntensityClass();
    intensityClass.setTitle("Violation Severity");
    intensityClassList.add(0, intensityClass);

    intensityClassArrayAdapter.setDropDownViewResource(R.layout.drop_down_item);

    holder.severityS.setAdapter(intensityClassArrayAdapter);
    holder.severityS.setPrompt("Violation Severity");
    for (int i = 0; i < intensityClassList.size(); i++) {
      if (intensityClassList.get(i).getClassId() == occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getFailureSeverityIntensityClassId()) {
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

  private void buildViolationExpandableCard(InspectionOccInspectedSpaceElementHolder holder, OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy) {
    holder.violationRadioBtn.setOnClickListener(v -> expandCard(holder, occInspectedSpaceElementHeavy, false, false, true));
    holder.findingViolateEt.setText(occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getNotes());
    holder.findingViolateEt.setOnFocusChangeListener((v, hasFocus) -> {
      if (holder.findingViolateEt.getText() != null) {
        occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().setNotes(holder.findingViolateEt.getText().toString());
      }
    });
    holder.defaultViolationDesSw.setOnCheckedChangeListener((buttonView, isChecked) -> {
      if (isChecked) {
        occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().setToUseDefaultDescription(true);
      } else {
        occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().setToUseDefaultDescription(false);
      }
    });

    holder.includeInCNFSw.setOnCheckedChangeListener((buttonView, isChecked) -> {
      if (isChecked) {
        occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().setMigrateToCECaseOnFail(true);
      } else {
        occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().setMigrateToCECaseOnFail(false);
      }
    });

    holder.takePhotoViolateBtn.setOnClickListener(v -> {
      String inspectedSpaceElementId = occInspectedSpaceElementHeavy.getOccInspectedSpaceElement().getInspectedSpaceElementId();
      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      context.getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY, inspectedSpaceElementId);
      context.getIntent().putExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY, "IMG_" + LocalDateTime.now() + ".JPEG");
      fragment.getActivity().startActivityForResult(intent, 1);
    });

    holder.saveViolationTv.setOnClickListener(v -> {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("Confirm Save");
      builder.setMessage("Are you sure you want to save this record?");
      builder.setPositiveButton("Yes", (dialog, which) -> {
        new Thread(new SaveViolation(holder, occInspectedSpaceElementHeavy)).start();
      });
      builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
      AlertDialog alertDialog = builder.create();
      alertDialog.show();
    });

    holder.exitViolationTv.setOnClickListener(v -> {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("Confirm Exit");
      builder.setMessage("Are you sure you want exit editing?");
      builder.setPositiveButton("Yes", (dialog, which) -> {
        closeExpandableCard(holder, occInspectedSpaceElementHeavy);
      });
      builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
      AlertDialog alertDialog = builder.create();
      alertDialog.show();
    });
  }


  @Override
  public void onBindViewHolder(@NonNull InspectionOccInspectedSpaceElementHolder holder, int position) {
    OccInspectedSpaceElementHeavy occInspectedSpaceElementHeavy = filterOccInspectedSpaceElementHeavyList.get(position);

    buildOccInspectedSpaceElementPhotoDocSectionRV(holder, occInspectedSpaceElementHeavy);
    buildOccInspectedSpaceElementMainCard(holder, occInspectedSpaceElementHeavy);

    initCard(holder, occInspectedSpaceElementHeavy);

    buildNotInspectedExpandableCard(holder, occInspectedSpaceElementHeavy);
    buildPassExpandableCard(holder, occInspectedSpaceElementHeavy);
    buildViolationExpandableCard(holder, occInspectedSpaceElementHeavy);
    buildIntensityClass(holder, occInspectedSpaceElementHeavy);
  }

  @Override
  public int getItemCount() {
    return filterOccInspectedSpaceElementHeavyList.size();
  }

  class InspectionOccInspectedSpaceElementHolder extends RecyclerView.ViewHolder {

    RelativeLayout passRl, violationRl, notInspectedRl;
    RadioButton notInspectedRadioBtn, passRadioBtn, violationRadioBtn;
    TextView inspectedElementSectionNumberTv, inspectedElementSectionTitleTv;
    TextView savePassTv, saveViolationTv, saveNotInspectedTv, exitPassTv, exitViolationTv, exitNotInspectedTv;
    Button takePhotoPassBtn, takePhotoViolateBtn, selectGalleryPassBtn;
    RecyclerView inspectedSpaceElementPassPhotoRv, inspectedSpaceElementViolatePhotoRv;
    EditText findingPassEt, findingViolateEt;
    Spinner severityS;
    Switch defaultViolationDesSw, includeInCNFSw;

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
      severityS = itemView.findViewById(R.id.et_inspection_occ_inspected_space_element_item_violation_spinner);
      defaultViolationDesSw = itemView.findViewById(R.id.sw_inspection_occ_inspected_space_element_item_violation_default_description);
      includeInCNFSw = itemView.findViewById(R.id.sw_inspection_occ_inspected_space_element_item_violation_include_cnf_case);
    }
  }

  public void setOccInspectedSpaceElementHeavyList(List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList) {
    this.occInspectedSpaceElementHeavyList = occInspectedSpaceElementHeavyList;
  }

}
