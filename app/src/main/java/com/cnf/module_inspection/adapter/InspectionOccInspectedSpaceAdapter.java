package com.cnf.module_inspection.adapter;

import static com.cnf.utils.AppConstants.FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE_CATEGORY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ID_NAME;

import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.module_inspection.adapter.InspectionOccInspectedSpaceAdapter.OccInspectedSpaceHolder;
import com.cnf.module_inspection.async.DeleteOccInspectedSpaceTask;
import com.cnf.module_inspection.entity.OccInspectedSpace;
import com.cnf.module_inspection.entity.OccInspectedSpaceElement;
import com.cnf.module_inspection.entity.OccInspectedSpaceHeavy;
import com.cnf.module_inspection.entity.OccInspectedSpaceStatus;
import com.cnf.module_inspection.entity.infra.OccChecklistSpaceType;
import com.cnf.module_inspection.entity.infra.OccLocationDescription;
import com.cnf.module_inspection.entity.infra.OccSpaceType;
import com.cnf.module_inspection.fragment.InspectionSelectOccInspectedSpaceElementCategoryFragment;
import com.cnf.module_inspection.service.local.OccInspectionSpaceElementRepository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class InspectionOccInspectedSpaceAdapter extends RecyclerView.Adapter<OccInspectedSpaceHolder> {

  private List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList;
  private final Fragment fragment;

  private final static String IS_REQUIRED = "Required";
  private final static String NOT_REQUIRED = "Not Required";

  public InspectionOccInspectedSpaceAdapter(List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList, Fragment fragment) {
    this.occInspectedSpaceHeavyList = occInspectedSpaceHeavyList;
    this.fragment = fragment;
  }

  @NonNull
  @Override
  public OccInspectedSpaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new OccInspectedSpaceHolder(LayoutInflater.from(this.fragment.getActivity()).inflate(R.layout.layout_inspected_space_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull OccInspectedSpaceHolder holder, int position) {

    OccInspectedSpaceHeavy occInspectedSpaceHeavy = this.occInspectedSpaceHeavyList.get(position);
    if (occInspectedSpaceHeavy == null) {
      return;
    }

    OccInspectedSpace occInspectedSpace = occInspectedSpaceHeavy.getOccInspectedSpace();
    OccChecklistSpaceType occChecklistSpaceType = occInspectedSpaceHeavy.getOccChecklistSpaceType();
    List<OccInspectedSpaceElement> occInspectedSpaceElementList = occInspectedSpaceHeavy.getOccInspectedSpaceElementList();
    OccSpaceType occSpaceType = occInspectedSpaceHeavy.getOccSpaceType();
    OccLocationDescription occLocationDescription = occInspectedSpaceHeavy.getOccLocationDescription();

    if (occInspectedSpace == null || occInspectedSpaceElementList == null) {
      return;
    }

    String completeStatus = calculateStatus(occInspectedSpaceHeavy);

    String inspectedSpaceTypeTile = null;
    String inspectedLocationDescription = null;
    String requiredStatus = null;

    if (occChecklistSpaceType != null) {
      inspectedSpaceTypeTile = occSpaceType.getSpaceTitle();
    }

    if (occLocationDescription != null) {
      inspectedLocationDescription = occLocationDescription.getDescription();
    }

    if (occChecklistSpaceType != null && occChecklistSpaceType.getRequired() != null) {
      requiredStatus = occChecklistSpaceType.getRequired() ? IS_REQUIRED : NOT_REQUIRED;
    }

    holder.tvOccInspectedSpaceCompleteStatus.setText(completeStatus);
    holder.tvOccInspectedSpaceTypeTitle.setText(inspectedSpaceTypeTile);
    holder.tvOccInspectedSpaceLocationDescription.setText(inspectedLocationDescription);
    holder.tvOccInspectedSpaceIsRequired.setText(requiredStatus);
    holder.progressBar.setMax(100);
    holder.progressBar.setProgress(calculateStatusPercentage(occInspectedSpaceHeavy));
    holder.editBtn.setOnClickListener(view -> {
      if (fragment.getActivity() == null) {
        return;
      }
      fragment.getActivity().getIntent().putExtra(INTENT_EXTRA_INSPECTED_SPACE_ID_NAME, occInspectedSpace.getInspectedSpaceId());
      Fragment f = fragment.getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE_CATEGORY);
      if (f == null) {
        f = new InspectionSelectOccInspectedSpaceElementCategoryFragment();
      }
      fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, f).commit();
    });

    holder.deleteBtn.setOnClickListener(v -> {
      PopupMenu popup = new PopupMenu(fragment.getActivity(), v);
      popup.setOnMenuItemClickListener(item -> {
        if (item.getItemId() == R.id.menu_inspected_space_delete) {
          DeleteOccInspectedSpaceTask task = new DeleteOccInspectedSpaceTask(occInspectedSpaceHeavy, fragment);
          task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
          return true;
        }
        return false;
      });

      popup.getMenuInflater().inflate(R.menu.inspection_space_recycleview_menu, popup.getMenu());
      popup.setGravity(Gravity.END);

      try {
        Field[] fields = popup.getClass().getDeclaredFields();
        for (Field field : fields) {
          if ("mPopup".equals(field.getName())) {
            field.setAccessible(true);
            Object menuPopupHelper = field.get(popup);
            Class<?> classPopupHelper = Class.forName(menuPopupHelper
                .getClass().getName());
            Method setForceIcons = classPopupHelper.getMethod(
                "setForceShowIcon", boolean.class);
            setForceIcons.invoke(menuPopupHelper, true);
            break;
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      popup.show();
    });
  }

  @Override
  public int getItemCount() {
    return occInspectedSpaceHeavyList.size();
  }

  static class OccInspectedSpaceHolder extends RecyclerView.ViewHolder {

    TextView tvOccInspectedSpaceTypeTitle, tvOccInspectedSpaceLocationDescription, tvOccInspectedSpaceCompleteStatus, tvOccInspectedSpaceIsRequired;
    ImageButton editBtn, deleteBtn;
    LinearLayout clOccInspectedSpaceItem;
    ProgressBar progressBar;

    public OccInspectedSpaceHolder(@NonNull View itemView) {
      super(itemView);
      tvOccInspectedSpaceTypeTitle = itemView.findViewById(R.id.tv_occ_inspected_space_item_title);
      tvOccInspectedSpaceLocationDescription = itemView.findViewById(R.id.tv_occ_inspected_space_item_location_description);
      tvOccInspectedSpaceCompleteStatus = itemView.findViewById(R.id.tv_occ_inspected_space_item_status);
      tvOccInspectedSpaceIsRequired = itemView.findViewById(R.id.tv_occ_inspected_space_item_is_required);
      deleteBtn = itemView.findViewById(R.id.imageBtn_occ_inspected_space_item_more_button);
      editBtn = itemView.findViewById(R.id.imageBtn_occ_inspected_space_item_edit_button);
      progressBar = itemView.findViewById(R.id.pb_occ_inspection_space_item_progress_bar);
      //clOccInspectedSpaceItem = itemView.findViewById(R.id.cl_occ_inspected_space_item);
    }
  }

  public void setOccInspectedSpaceHeavyList(List<OccInspectedSpaceHeavy> occInspectedSpaceHeavyList) {
    this.occInspectedSpaceHeavyList = occInspectedSpaceHeavyList;
  }

  public List<OccInspectedSpaceHeavy> getOccInspectedSpaceHeavyList() {
    return occInspectedSpaceHeavyList;
  }

  private String calculateStatus(OccInspectedSpaceHeavy occInspectedSpaceHeavy) {
    OccInspectedSpaceStatus occInspectedSpaceStatus = OccInspectionSpaceElementRepository.getInstance(fragment.getActivity()).getOccInspectedSpaceStatus(occInspectedSpaceHeavy);
    int totalInspectedSpaceElement = occInspectedSpaceStatus.getFinishedInspectedSpaceElementCount() + occInspectedSpaceStatus.getUnFinishInspectedSpaceElementCount();
    int completedInspectedSpaceElement = occInspectedSpaceStatus.getFinishedInspectedSpaceElementCount();
    return String.format("%s / %s", completedInspectedSpaceElement, totalInspectedSpaceElement);
  }

  private int calculateStatusPercentage(OccInspectedSpaceHeavy occInspectedSpaceHeavy) {
    OccInspectedSpaceStatus occInspectedSpaceStatus = OccInspectionSpaceElementRepository.getInstance(fragment.getActivity()).getOccInspectedSpaceStatus(occInspectedSpaceHeavy);
    int totalInspectedSpaceElement = occInspectedSpaceStatus.getFinishedInspectedSpaceElementCount() + occInspectedSpaceStatus.getUnFinishInspectedSpaceElementCount();
    int completedInspectedSpaceElement = occInspectedSpaceStatus.getFinishedInspectedSpaceElementCount();
    if (totalInspectedSpaceElement == 0) return 100;
    return (int)(completedInspectedSpaceElement * 100 / totalInspectedSpaceElement);
  }
}
