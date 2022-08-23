package com.cnf.module_notification.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.cnf.R;
import com.cnf.module_error.ErrorActivity;
import com.cnf.module_notification.entity.InspectionDispatchNotification;
import com.cnf.module_notification.adapter.InspectionNotificationAdapter;
import com.cnf.module_notification.enums.InspectionDispatchNotificationCategory;
import com.cnf.module_notification.enums.InspectionDispatchNotificationOperation;
import com.cnf.module_notification.exceptions.DataSourceNotFoundException;
import com.cnf.module_notification.service.IOccInspectionDispatchNotificationRepository;
import com.cnf.module_notification.service.impl.OccInspectionDispatchNotificationRepository;
import java.util.ArrayList;
import java.util.List;

public class InspectionNotificationFragment extends Fragment {

  private RadioButton rBtnInspectionDispatchNotificationNotArchive;
  private RadioButton rBtnArchiveNotification;
  private RadioGroup radioGroup;
  private RecyclerView rvOccInspectionNotification;
  private IOccInspectionDispatchNotificationRepository inspectionDispatchNotificationRepository;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    try {
      this.inspectionDispatchNotificationRepository = OccInspectionDispatchNotificationRepository.getInstance(getContext());
    } catch (DataSourceNotFoundException e) {
      Log.i(TAG, e.getMessage());
      Intent intent = new Intent(getActivity(), ErrorActivity.class);
      startActivity(intent);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_notification, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.radioGroup = view.findViewById(R.id.rg_inspection_notification_category_radio_group);
    this.rBtnInspectionDispatchNotificationNotArchive = view.findViewById(R.id.rBtn_inspection_dispatch_notification_category_not_archive);
    this.rBtnArchiveNotification = view.findViewById(R.id.rBtn_inspection_dispatch_notification_category_archive);
    this.rvOccInspectionNotification = view.findViewById(R.id.rv_occ_inspection_notification);
    this.rvOccInspectionNotification.setLayoutManager(new LinearLayoutManager(view.getContext()));
    this.rBtnInspectionDispatchNotificationNotArchive.setChecked(true);
    this.rBtnArchiveNotification.setChecked(false);
    this.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
      if (checkedId == R.id.rBtn_inspection_dispatch_notification_category_not_archive) {
        updateUIAfterClickOnNotArchivedNotificationBtn();
      } else if (checkedId == R.id.rBtn_inspection_dispatch_notification_category_archive) {
        updateUIAfterClickOnArchivedNotificationBtn();
      }
    });
  }

  @Override
  public void onStart() {
    super.onStart();
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callBackMethod);
    itemTouchHelper.attachToRecyclerView(rvOccInspectionNotification);
    if (rBtnInspectionDispatchNotificationNotArchive.isChecked()) {
      updateUIAfterClickOnNotArchivedNotificationBtn();
    } else {
      updateUIAfterClickOnArchivedNotificationBtn();
    }
  }

  private static ItemTouchHelper.SimpleCallback callBackMethod = new SimpleCallback(100, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder, @NonNull ViewHolder target) {
      return false;
    }

    @Override
    public void onSwiped(@NonNull ViewHolder viewHolder, int direction) {
    }

    @Override
    public float getSwipeThreshold(@NonNull ViewHolder viewHolder) {
      return Integer.MAX_VALUE;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
      return Integer.MAX_VALUE;
    }

    int mCurrentScrollX;

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
      if (viewHolder instanceof InspectionNotificationAdapter.OccInspectionNotificationHolder) {
        InspectionNotificationAdapter.OccInspectionNotificationHolder holder = (InspectionNotificationAdapter.OccInspectionNotificationHolder) viewHolder;
        float actionWidth = holder.tvNotificationItemDelete.getWidth();
        if (dX == 0) {
          mCurrentScrollX = viewHolder.itemView.getScrollX();
        }
        if (isCurrentlyActive) {
          holder.itemView.scrollTo(mCurrentScrollX + (int) -dX, 0);
          if (viewHolder.itemView.getScrollX() > actionWidth) {
            viewHolder.itemView.scrollTo((int) actionWidth, 0);
          } else if (viewHolder.itemView.getScrollX() < 0) {
            viewHolder.itemView.scrollTo(0, 0);
          }
        } else {
          if (viewHolder.itemView.getScrollX() > 0) {
            viewHolder.itemView.scrollTo((int) actionWidth, 0);
          }
        }
      }
    }
  };

  private void updateUIAfterClickOnNotArchivedNotificationBtn() {
    rBtnInspectionDispatchNotificationNotArchive.setChecked(true);
    rBtnArchiveNotification.setChecked(false);
    rBtnInspectionDispatchNotificationNotArchive.setTextColor(getActivity().getColor(R.color.on_switch_font));
    rBtnInspectionDispatchNotificationNotArchive.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background));
    rBtnArchiveNotification.setTextColor(getActivity().getColor(R.color.off_switch_font));
    rBtnArchiveNotification.setBackground(null);
    new LoadInspectionDispatchNotificationRecycleView.Builder()
        .recyclerView(this.rvOccInspectionNotification)
        .inspectionDispatchNotificationRepository(this.inspectionDispatchNotificationRepository)
        .category(InspectionDispatchNotificationCategory.NOT_ARCHIVED).build().execute();
  }

  private void updateUIAfterClickOnArchivedNotificationBtn() {
    rBtnInspectionDispatchNotificationNotArchive.setChecked(false);
    rBtnArchiveNotification.setChecked(true);
    rBtnArchiveNotification.setTextColor(getActivity().getColor(R.color.on_switch_font));
    rBtnArchiveNotification.setBackground(getActivity().getDrawable(R.drawable.toggle_widget_background));
    rBtnInspectionDispatchNotificationNotArchive.setTextColor(getActivity().getColor(R.color.off_switch_font));
    rBtnInspectionDispatchNotificationNotArchive.setBackground(null);
    new LoadInspectionDispatchNotificationRecycleView.Builder()
        .recyclerView(this.rvOccInspectionNotification)
        .inspectionDispatchNotificationRepository(this.inspectionDispatchNotificationRepository)
        .category(InspectionDispatchNotificationCategory.IS_ARCHIVED).build().execute();
  }

  private static class LoadInspectionDispatchNotificationRecycleView extends AsyncTask<Void, Void, List<InspectionDispatchNotification>> {

    private final IOccInspectionDispatchNotificationRepository inspectionDispatchNotificationRepository;
    private final InspectionDispatchNotificationCategory category;
    private final RecyclerView recyclerView;

    public static class Builder {

      private IOccInspectionDispatchNotificationRepository inspectionDispatchNotificationRepository;
      private InspectionDispatchNotificationCategory category;
      private RecyclerView recyclerView;

      public Builder() {
      }

      public Builder inspectionDispatchNotificationRepository(IOccInspectionDispatchNotificationRepository inspectionDispatchNotificationRepository) {
        this.inspectionDispatchNotificationRepository = inspectionDispatchNotificationRepository;
        return this;
      }

      public Builder category(InspectionDispatchNotificationCategory category) {
        this.category = category;
        return this;
      }

      public Builder recyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        return this;
      }

      public LoadInspectionDispatchNotificationRecycleView build() {
        return new LoadInspectionDispatchNotificationRecycleView(this);
      }
    }

    public LoadInspectionDispatchNotificationRecycleView(Builder builder) {
      this.recyclerView = builder.recyclerView;
      this.inspectionDispatchNotificationRepository = builder.inspectionDispatchNotificationRepository;
      this.category = builder.category;
    }

    @Override
    protected List<InspectionDispatchNotification> doInBackground(Void... voids) {
      List<InspectionDispatchNotification> inspectionDispatchNotificationList = new ArrayList<>();
      switch (category) {
        case IS_ARCHIVED:
          inspectionDispatchNotificationList = inspectionDispatchNotificationRepository.getArchivedOccInspectionDispatchNotificationList();
          break;
        case NOT_ARCHIVED:
          inspectionDispatchNotificationList = inspectionDispatchNotificationRepository.getNotArchivedOccInspectionDispatchNotificationList();
          break;
      }
      return inspectionDispatchNotificationList;
    }

    @Override
    protected void onPostExecute(List<InspectionDispatchNotification> inspectionDispatchNotificationList) {
      super.onPostExecute(inspectionDispatchNotificationList);
      InspectionNotificationAdapter inspectionNotificationAdapter = new InspectionNotificationAdapter(inspectionDispatchNotificationList,
          i -> {
            i.setArchived(category != InspectionDispatchNotificationCategory.IS_ARCHIVED);
            new UpdateInspectionDispatchNotification
                .Builder()
                .inspectionDispatchNotificationRepository(inspectionDispatchNotificationRepository)
                .inspectionDispatchNotification(i)
                .recyclerView(recyclerView)
                .category(category)
                .operation(InspectionDispatchNotificationOperation.UPDATE)
                .build()
                .execute();
          });
      recyclerView.setAdapter(inspectionNotificationAdapter);
    }
  }

  public static class UpdateInspectionDispatchNotification extends AsyncTask<Void, Void, List<InspectionDispatchNotification>> {

    private final RecyclerView recyclerView;
    private final IOccInspectionDispatchNotificationRepository inspectionDispatchNotificationRepository;
    private final InspectionDispatchNotification inspectionDispatchNotification;
    private final InspectionDispatchNotificationOperation operation;
    private final InspectionDispatchNotificationCategory category;

    public static class Builder {

      private IOccInspectionDispatchNotificationRepository inspectionDispatchNotificationRepository;
      private InspectionDispatchNotification inspectionDispatchNotification;
      private InspectionDispatchNotificationOperation operation;
      private InspectionDispatchNotificationCategory category;
      private RecyclerView recyclerView;

      public Builder() {

      }

      public Builder inspectionDispatchNotificationRepository(IOccInspectionDispatchNotificationRepository inspectionDispatchNotificationRepository) {
        this.inspectionDispatchNotificationRepository = inspectionDispatchNotificationRepository;
        return this;
      }

      public Builder inspectionDispatchNotification(InspectionDispatchNotification inspectionDispatchNotification) {
        this.inspectionDispatchNotification = inspectionDispatchNotification;
        return this;
      }

      public Builder operation(InspectionDispatchNotificationOperation operation) {
        this.operation = operation;
        return this;
      }

      public Builder category(InspectionDispatchNotificationCategory category) {
        this.category = category;
        return this;
      }

      public Builder recyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        return this;
      }

      public UpdateInspectionDispatchNotification build() {
        return new UpdateInspectionDispatchNotification(this);
      }
    }

    private UpdateInspectionDispatchNotification(Builder builder) {
      this.inspectionDispatchNotificationRepository = builder.inspectionDispatchNotificationRepository;
      this.inspectionDispatchNotification = builder.inspectionDispatchNotification;
      this.recyclerView = builder.recyclerView;
      this.operation = builder.operation;
      this.category = builder.category;
    }

    @Override
    protected List<InspectionDispatchNotification> doInBackground(Void... voids) {
      switch (operation) {
        case DELETE:
          inspectionDispatchNotificationRepository.deleteOccInspectionDispatchNotification(inspectionDispatchNotification);
          break;
        case UPDATE:
          inspectionDispatchNotificationRepository.updateOccInspectionDispatchNotification(inspectionDispatchNotification);
          break;
        case INNER_INSERT:
          inspectionDispatchNotificationRepository.insertOccInspectionDispatchNotification(inspectionDispatchNotification);
          break;
        case OUTER_INSERT:
          break;
      }
      List<InspectionDispatchNotification> inspectionDispatchNotificationList = new ArrayList<>();
      switch (category) {
        case IS_ARCHIVED:
          inspectionDispatchNotificationList = inspectionDispatchNotificationRepository.getArchivedOccInspectionDispatchNotificationList();
          break;
        case NOT_ARCHIVED:
          inspectionDispatchNotificationList = inspectionDispatchNotificationRepository.getNotArchivedOccInspectionDispatchNotificationList();
          break;
      }
      return inspectionDispatchNotificationList;
    }

    @Override
    protected void onPostExecute(List<InspectionDispatchNotification> inspectionDispatchNotificationList) {
      super.onPostExecute(inspectionDispatchNotificationList);
      InspectionNotificationAdapter adapter = (InspectionNotificationAdapter) recyclerView.getAdapter();
      if (adapter != null) {
        adapter.setInspectionDispatchNotificationList(inspectionDispatchNotificationList);
        recyclerView.setAdapter(adapter);
      }
    }
  }

  public RecyclerView getRvOccInspectionNotification() {
    return rvOccInspectionNotification;
  }

  public InspectionDispatchNotificationCategory getCategory() {
    if (rBtnInspectionDispatchNotificationNotArchive == null) {
      return null;
    }
    return rBtnInspectionDispatchNotificationNotArchive.isChecked() ?
        InspectionDispatchNotificationCategory.NOT_ARCHIVED : InspectionDispatchNotificationCategory.IS_ARCHIVED;
  }
}