package com.cnf.module_notification.service.impl;

import android.content.Context;
import com.cnf.module_notification.dao.InspectionDispatchNotificationDao;
import com.cnf.module_notification.db.NotificationDatabase;
import com.cnf.module_notification.entity.InspectionDispatchNotification;
import com.cnf.module_notification.exceptions.DataSourceNotFoundException;
import com.cnf.module_notification.service.IOccInspectionDispatchNotificationRepository;
import java.util.ArrayList;
import java.util.List;

public class OccInspectionDispatchNotificationRepository implements IOccInspectionDispatchNotificationRepository {

  private final InspectionDispatchNotificationDao inspectionDispatchNotificationDao;

  private static OccInspectionDispatchNotificationRepository INSTANCE = null;

  public static OccInspectionDispatchNotificationRepository getInstance(Context context) throws DataSourceNotFoundException {
    if (INSTANCE == null) {
      INSTANCE = new OccInspectionDispatchNotificationRepository(context);
    }
    return INSTANCE;
  }

  private OccInspectionDispatchNotificationRepository(Context context) throws DataSourceNotFoundException {
    this.inspectionDispatchNotificationDao = NotificationDatabase.getInstance(context).getInspectionDispatchNotificationDao();
    if (inspectionDispatchNotificationDao == null) {
      throw new DataSourceNotFoundException("Notification Database", new NullPointerException());
    }
  }

  @Override
  public List<InspectionDispatchNotification> getArchivedOccInspectionDispatchNotificationList() {
    List<InspectionDispatchNotification> inspectionDispatchNotificationList = this.inspectionDispatchNotificationDao.selectInspectionDispatchArchivedNotificationList();
    if (inspectionDispatchNotificationList == null) {
      return new ArrayList<>();
    }
    return inspectionDispatchNotificationList;
  }

  @Override
  public List<InspectionDispatchNotification> getNotArchivedOccInspectionDispatchNotificationList() {
    List<InspectionDispatchNotification> inspectionDispatchNotificationList = this.inspectionDispatchNotificationDao.selectInspectionDispatchNotArchivedNotificationList();
    if (inspectionDispatchNotificationList == null) {
      return new ArrayList<>();
    }
    return inspectionDispatchNotificationList;
  }

  @Override
  public void insertOccInspectionDispatchNotification(InspectionDispatchNotification inspectionDispatchNotification) {
    this.inspectionDispatchNotificationDao.insertInspectionDispatchNotification(inspectionDispatchNotification);
  }

  @Override
  public void updateOccInspectionDispatchNotification(InspectionDispatchNotification inspectionDispatchNotification) {
    this.inspectionDispatchNotificationDao.updateInspectionDispatchDTO(inspectionDispatchNotification);
  }

  @Override
  public void deleteOccInspectionDispatchNotification(InspectionDispatchNotification inspectionDispatchNotification) {
    this.inspectionDispatchNotificationDao.deleteInspectionDispatchNotification(inspectionDispatchNotification);
  }

}
