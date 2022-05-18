package com.cnf.adapter;

import static android.content.Context.MODE_PRIVATE;
import static com.cnf.utils.AppConstants.INTENSITY_SCHEMA_VIOLATION_SEVERITY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_ID_NAME;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_PHOTO_NAME;
import static com.cnf.utils.AppConstants.USER_ID_KEY;
import static com.cnf.utils.AppConstants.USER_SESSION_SHARE_PREFERENCE_NAME;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.domain.BlobBytes;
import com.cnf.domain.IntensityClass;
import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.domain.OccInspectedSpaceElementHeavy;
import com.cnf.service.occ.OccInspectionSpaceElementService;

import java.time.LocalDateTime;
import java.util.List;

public class InspectionInspectedSpaceElementAdapter extends RecyclerView.Adapter<InspectionInspectedSpaceElementAdapter.LinearViewHolder> {


    private Activity context;
    private Fragment fragment;
    private RecyclerView.RecycledViewPool viewPool;
    private List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList;
    private OccInspectionSpaceElementService occInspectionSpaceElementService;
    private List<BlobBytes> photoBlobBytesList;
    private List<IntensityClass> intensityClassList;

    public InspectionInspectedSpaceElementAdapter(Activity context, Fragment fragment, List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList) {
        this.context = context;
        this.fragment = fragment;
        this.occInspectedSpaceElementHeavyList = occInspectedSpaceElementHeavyList;
        this.occInspectionSpaceElementService = OccInspectionSpaceElementService.getInstance(context);
        this.viewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inspection_inspected_space_element_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        OccInspectedSpaceElementHeavy e = occInspectedSpaceElementHeavyList.get(position);
        int inspectedSpaceElementId = e.getOccInspectedSpaceElement().getInspectedspaceelementid();
        Thread t = new Thread() {
            @Override
            public void run() {
                photoBlobBytesList = occInspectionSpaceElementService.getInspectedPhotoBlobBytesList(e.getOccInspectedSpaceElement().getInspectedspaceelementid());
                intensityClassList = occInspectionSpaceElementService.selectAllIntensityClassListBySchemaLabel(INTENSITY_SCHEMA_VIOLATION_SEVERITY);
            }
        };
        t.start();

        try {
            t.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

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


        String sectionNumber = occInspectionSpaceElementService.getOrdinanceHeaderSectionNumber(e);
        String sectionTitle = occInspectionSpaceElementService.getOrdinanceHeaderSectionTitle(e);

        holder.inspectedElementSectionNumberTv.setText(sectionNumber);
        holder.inspectedElementSectionTitleTv.setText(sectionTitle);


        ArrayAdapter<IntensityClass> intensityClassArrayAdapter = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, intensityClassList);
        IntensityClass intensityClass = new IntensityClass();
        intensityClass.setTitle("Violation Severity");
        intensityClassList.add(0, intensityClass);

        intensityClassArrayAdapter.setDropDownViewResource(R.layout.drop_down_item);
        holder.severityS.setAdapter(intensityClassArrayAdapter);
        holder.severityS.setPrompt("Violation Severity");
        holder.severityS.setSelection(0);
        holder.severityS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (e.isPassExpand() || e.isViolationExpand()) { // under editing
            holder.notInspectedRadioBtn.setChecked(false);
            if (e.isPassExpand()) {
                holder.passRadioBtn.setChecked(true);
                holder.violationRadioBtn.setChecked(false);
            } else {
                holder.passRadioBtn.setChecked(false);
                holder.violationRadioBtn.setChecked(true);
            }
        } else {
            if (occInspectionSpaceElementService.isInspectedSpaceElementPass(e)) {
                holder.passRadioBtn.setChecked(true);
            } else if (occInspectionSpaceElementService.isInspectedSpaceElementNotInspected(e)) {
                holder.notInspectedRadioBtn.setChecked(true);
            } else {
                holder.violationRadioBtn.setChecked(true);
            }
        }

        holder.passRl.setVisibility(e.isPassExpand() ? View.VISIBLE : View.GONE);
        holder.violationRl.setVisibility(e.isViolationExpand() ? View.VISIBLE : View.GONE);
        holder.notInspectedRl.setVisibility(e.isNotInspectedExpand() ? View.VISIBLE : View.GONE);

        holder.passRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                e.setPassExpand(true);

                holder.passRadioBtn.setChecked(true);
                holder.notInspectedRadioBtn.setChecked(false);
                holder.violationRadioBtn.setChecked(false);

                holder.passRl.setVisibility(e.isPassExpand() ? View.VISIBLE : View.GONE);
                holder.violationRl.setVisibility(e.isViolationExpand() ? View.VISIBLE : View.GONE);
                holder.notInspectedRl.setVisibility(e.isNotInspectedExpand() ? View.VISIBLE : View.GONE);
            }
        });


        holder.notInspectedRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e.setNotInspectedExpand(true);

                holder.notInspectedRadioBtn.setChecked(true);
                holder.passRadioBtn.setChecked(false);
                holder.violationRadioBtn.setChecked(false);

                holder.passRl.setVisibility(e.isPassExpand() ? View.VISIBLE : View.GONE);
                holder.violationRl.setVisibility(e.isViolationExpand() ? View.VISIBLE : View.GONE);
                holder.notInspectedRl.setVisibility(e.isNotInspectedExpand() ? View.VISIBLE : View.GONE);
            }
        });

        holder.violationRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                e.setViolationExpand(true);

                holder.violationRadioBtn.setChecked(true);
                holder.notInspectedRadioBtn.setChecked(false);
                holder.passRadioBtn.setChecked(false);

                holder.passRl.setVisibility(e.isPassExpand() ? View.VISIBLE : View.GONE);
                holder.violationRl.setVisibility(e.isViolationExpand() ? View.VISIBLE : View.GONE);
                holder.notInspectedRl.setVisibility(e.isNotInspectedExpand() ? View.VISIBLE : View.GONE);
            }
        });

        holder.takePhotoPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inspectedSpaceElementId = e.getOccInspectedSpaceElement().getInspectedspaceelementid();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                context.getIntent().putExtra(INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_ID_NAME, e.getOccInspectedSpaceElement().getInspectedspaceelementid());
                context.getIntent().putExtra(INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_PHOTO_NAME, "IMG_" + LocalDateTime.now() + ".JPEG");
                fragment.getActivity().startActivityForResult(intent, 1);
            }
        });

        holder.takePhotoViolateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inspectedSpaceElementId = e.getOccInspectedSpaceElement().getInspectedspaceelementid();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                context.getIntent().putExtra(INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_ID_NAME, e.getOccInspectedSpaceElement().getInspectedspaceelementid());
                context.getIntent().putExtra(INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_PHOTO_NAME, "IMG_" + LocalDateTime.now() + ".JPEG");
                fragment.getActivity().startActivityForResult(intent, 1);
            }
        });

        //TODO
        holder.saveNotInspectedTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm Save");
                builder.setMessage("Are you sure you want to save this record?");

                builder.setPositiveButton("Yes", (dialog, which) -> {
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            OccInspectedSpaceElement oice = e.getOccInspectedSpaceElement();
                            SharedPreferences sp = context.getSharedPreferences(USER_SESSION_SHARE_PREFERENCE_NAME, MODE_PRIVATE);
                            int userId = sp.getInt(USER_ID_KEY, 0);
                            oice = occInspectionSpaceElementService.inspectionAction_configureElementForNotInspected(oice, userId);
                            occInspectionSpaceElementService.configureOccInspectedSpaceElementStatus(oice);
                            occInspectionSpaceElementService.updateOccInspectedSpaceElement(oice);
                        }
                    };
                    t.start();

                    try {
                        t.join();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    e.setNotInspectedExpand(false);
                    holder.passRl.setVisibility(e.isPassExpand() ? View.VISIBLE : View.GONE);
                    holder.violationRl.setVisibility(e.isViolationExpand() ? View.VISIBLE : View.GONE);
                    holder.notInspectedRl.setVisibility(e.isNotInspectedExpand() ? View.VISIBLE : View.GONE);
                });

                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        holder.savePassTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm Save");
                builder.setMessage("Are you sure you want to save this record?");

                builder.setPositiveButton("Yes", (dialog, which) -> {
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            String findingNotes = holder.findingPassEt.getText().toString();
                            OccInspectedSpaceElement oice = e.getOccInspectedSpaceElement();
                            oice.setNotes(findingNotes);
                            SharedPreferences sp = context.getSharedPreferences(USER_SESSION_SHARE_PREFERENCE_NAME, MODE_PRIVATE);
                            int userId = sp.getInt(USER_ID_KEY, 0);
                            oice = occInspectionSpaceElementService.inspectionAction_configureElementForCompliance(oice, userId);
                            occInspectionSpaceElementService.configureOccInspectedSpaceElementStatus(oice);
                            occInspectionSpaceElementService.updateOccInspectedSpaceElement(oice);
                        }
                    };
                    t.start();

                    try {
                        t.join();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    e.setPassExpand(false);
                    holder.passRl.setVisibility(e.isPassExpand() ? View.VISIBLE : View.GONE);
                    holder.violationRl.setVisibility(e.isViolationExpand() ? View.VISIBLE : View.GONE);
                    holder.notInspectedRl.setVisibility(e.isNotInspectedExpand() ? View.VISIBLE : View.GONE);
                });

                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        holder.saveViolationTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm Save");
                builder.setMessage("Are you sure you want to save this record?");

                builder.setPositiveButton("Yes", (dialog, which) -> {
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            String findingNotes = holder.findingViolateEt.getText().toString();
                            OccInspectedSpaceElement oice = e.getOccInspectedSpaceElement();
                            oice.setNotes(findingNotes);
                            SharedPreferences sp = context.getSharedPreferences(USER_SESSION_SHARE_PREFERENCE_NAME, MODE_PRIVATE);
                            int userId = sp.getInt(USER_ID_KEY, 0);
                            //TODO change hardcode useDefFindOnFail
                            oice = occInspectionSpaceElementService.inspectionAction_configureElementForInspectionNoCompliance(oice, userId, false);
                            occInspectionSpaceElementService.configureOccInspectedSpaceElementStatus(oice);
                            occInspectionSpaceElementService.updateOccInspectedSpaceElement(oice);
                        }
                    };
                    t.start();
                    try {
                        t.join();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    e.setViolationExpand(false);
                    holder.passRl.setVisibility(e.isPassExpand() ? View.VISIBLE : View.GONE);
                    holder.violationRl.setVisibility(e.isViolationExpand() ? View.VISIBLE : View.GONE);
                    holder.notInspectedRl.setVisibility(e.isNotInspectedExpand() ? View.VISIBLE : View.GONE);
                });

                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        holder.exitPassTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm Exit");
                builder.setMessage("Are you sure you want exit editing?");

                builder.setPositiveButton("Yes", (dialog, which) -> {
                    e.setPassExpand(false);

                    if (occInspectionSpaceElementService.isInspectedSpaceElementPass(e)) {
                        holder.notInspectedRadioBtn.setChecked(false);
                        holder.passRadioBtn.setChecked(true);
                        holder.violationRadioBtn.setChecked(false);
                    } else if (occInspectionSpaceElementService.isInspectedSpaceElementNotInspected(e)) {
                        holder.notInspectedRadioBtn.setChecked(true);
                        holder.passRadioBtn.setChecked(false);
                        holder.violationRadioBtn.setChecked(false);
                    } else {
                        holder.notInspectedRadioBtn.setChecked(false);
                        holder.passRadioBtn.setChecked(false);
                        holder.violationRadioBtn.setChecked(true);
                    }

                    holder.passRl.setVisibility(e.isPassExpand() ? View.VISIBLE : View.GONE);
                    holder.violationRl.setVisibility(e.isViolationExpand() ? View.VISIBLE : View.GONE);
                    holder.notInspectedRl.setVisibility(e.isNotInspectedExpand() ? View.VISIBLE : View.GONE);
                });

                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        holder.exitViolationTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm Exit");
                builder.setMessage("Are you sure you want exit editing?");

                builder.setPositiveButton("Yes", (dialog, which) -> {
                    e.setViolationExpand(false);

                    if (occInspectionSpaceElementService.isInspectedSpaceElementPass(e)) {
                        holder.notInspectedRadioBtn.setChecked(false);
                        holder.passRadioBtn.setChecked(true);
                        holder.violationRadioBtn.setChecked(false);
                    } else if (occInspectionSpaceElementService.isInspectedSpaceElementNotInspected(e)) {
                        holder.notInspectedRadioBtn.setChecked(true);
                        holder.passRadioBtn.setChecked(false);
                        holder.violationRadioBtn.setChecked(false);
                    } else {
                        holder.notInspectedRadioBtn.setChecked(false);
                        holder.passRadioBtn.setChecked(false);
                        holder.violationRadioBtn.setChecked(true);
                    }

                    holder.passRl.setVisibility(e.isPassExpand() ? View.VISIBLE : View.GONE);
                    holder.violationRl.setVisibility(e.isViolationExpand() ? View.VISIBLE : View.GONE);
                    holder.notInspectedRl.setVisibility(e.isNotInspectedExpand() ? View.VISIBLE : View.GONE);
                });

                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        holder.exitNotInspectedTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm Exit");
                builder.setMessage("Are you sure you want exit editing?");

                builder.setPositiveButton("Yes", (dialog, which) -> {
                    e.setNotInspectedExpand(false);

                    if (occInspectionSpaceElementService.isInspectedSpaceElementPass(e)) {
                        holder.notInspectedRadioBtn.setChecked(false);
                        holder.passRadioBtn.setChecked(true);
                        holder.violationRadioBtn.setChecked(false);
                    } else if (occInspectionSpaceElementService.isInspectedSpaceElementNotInspected(e)) {
                        holder.notInspectedRadioBtn.setChecked(true);
                        holder.passRadioBtn.setChecked(false);
                        holder.violationRadioBtn.setChecked(false);
                    } else {
                        holder.notInspectedRadioBtn.setChecked(false);
                        holder.passRadioBtn.setChecked(false);
                        holder.violationRadioBtn.setChecked(true);
                    }

                    holder.passRl.setVisibility(e.isPassExpand() ? View.VISIBLE : View.GONE);
                    holder.violationRl.setVisibility(e.isViolationExpand() ? View.VISIBLE : View.GONE);
                    holder.notInspectedRl.setVisibility(e.isNotInspectedExpand() ? View.VISIBLE : View.GONE);
                });

                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return occInspectedSpaceElementHeavyList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout passRl, violationRl, notInspectedRl;
        RadioButton notInspectedRadioBtn, passRadioBtn, violationRadioBtn;
        TextView inspectedElementSectionNumberTv, inspectedElementSectionTitleTv;
        TextView savePassTv, saveViolationTv , saveNotInspectedTv, exitPassTv, exitViolationTv, exitNotInspectedTv;
        Button takePhotoPassBtn, takePhotoViolateBtn;
        RecyclerView inspectedSpaceElementPassPhotoRv, inspectedSpaceElementViolatePhotoRv;
        EditText findingPassEt, findingViolateEt;
        Spinner severityS;


        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            inspectedElementSectionNumberTv = itemView.findViewById(R.id.tv_inspection_inspected_space_element_item_section_number);
            inspectedElementSectionTitleTv = itemView.findViewById(R.id.tv_inspection_inspected_space_element_item_section_title);
            notInspectedRadioBtn = itemView.findViewById(R.id.rBtn_inspection_inspected_space_element_item_not_inspected);
            passRadioBtn = itemView.findViewById(R.id.rBtn_inspection_inspected_space_element_item_pass);
            violationRadioBtn = itemView.findViewById(R.id.rBtn_inspection_inspected_space_element_item_violation);
            passRl = itemView.findViewById(R.id.rl_inspection_inspected_space_element_item_pass_expand);
            violationRl = itemView.findViewById(R.id.rl_inspection_inspected_space_element_item_violation_expand);
            notInspectedRl = itemView.findViewById(R.id.rl_inspection_inspected_space_element_item_not_inspected_expand);
            takePhotoPassBtn = itemView.findViewById(R.id.btn_inspection_inspected_space_element_item_pass_take_photo);
            takePhotoViolateBtn = itemView.findViewById(R.id.btn_inspection_inspected_space_element_item_violation_take_photo);
            inspectedSpaceElementPassPhotoRv = itemView.findViewById(R.id.rv_inspection_inspected_space_element_item_pass_photo);
            inspectedSpaceElementViolatePhotoRv = itemView.findViewById(R.id.rv_inspection_inspected_space_element_item_violation_photo);
            savePassTv = itemView.findViewById(R.id.tv_inspection_inspected_space_element_item_pass_save);
            saveViolationTv = itemView.findViewById(R.id.tv_inspection_inspected_space_element_item_violation_save);
            saveNotInspectedTv = itemView.findViewById(R.id.tv_inspection_inspected_space_element_item_not_inspected_save);
            findingPassEt = itemView.findViewById(R.id.et_inspection_inspected_space_element_item_pass_finding);
            findingViolateEt = itemView.findViewById(R.id.et_inspection_inspected_space_element_item_violation_finding);
            severityS = itemView.findViewById(R.id.et_inspection_inspected_space_element_item_violation_spinner);
            exitPassTv = itemView.findViewById(R.id.tv_inspection_inspected_space_element_item_pass_exit);
            exitViolationTv = itemView.findViewById(R.id.tv_inspection_inspected_space_element_item_violation_exit);
            exitNotInspectedTv = itemView.findViewById(R.id.tv_inspection_inspected_space_element_item_not_inspected_exit);
        }
    }

    public void setOccInspectedSpaceElementHeavyList(List<OccInspectedSpaceElementHeavy> occInspectedSpaceElementHeavyList) {
        this.occInspectedSpaceElementHeavyList = occInspectedSpaceElementHeavyList;
    }


}
