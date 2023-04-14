package com.cnf.module_notification.service;

import com.cnf.module_notification.entity.InspectionDispatchNotification;
import java.util.List;

public interface IOccInspectionDispatchNotificationRepository {

  List<InspectionDispatchNotification> getArchivedOccInspectionDispatchNotificationList();

  List<InspectionDispatchNotification> getNotArchivedOccInspectionDispatchNotificationList();

  void insertOccInspectionDispatchNotification(InspectionDispatchNotification inspectionDispatchNotification);

  void updateOccInspectionDispatchNotification(InspectionDispatchNotification inspectionDispatchNotification);

  void deleteOccInspectionDispatchNotification(InspectionDispatchNotification inspectionDispatchNotification);
}
