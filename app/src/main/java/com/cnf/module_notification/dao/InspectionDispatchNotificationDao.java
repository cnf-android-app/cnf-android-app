package com.cnf.module_notification.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.cnf.module_notification.entity.InspectionDispatchNotification;
import java.util.List;

@Dao
public interface InspectionDispatchNotificationDao {

  @Insert
  long insertInspectionDispatchNotification(InspectionDispatchNotification inspectionDispatchNotification);

  @Query("SELECT * FROM InspectionDispatchNotification WHERE isArchived = 1")
  List<InspectionDispatchNotification> selectInspectionDispatchArchivedNotificationList();

  @Query("SELECT * FROM InspectionDispatchNotification WHERE isArchived = 0")
  List<InspectionDispatchNotification> selectInspectionDispatchNotArchivedNotificationList();

  @Update
  int updateInspectionDispatchDTO(InspectionDispatchNotification InspectionDispatchNotification);

  @Delete
  void deleteInspectionDispatchNotification(InspectionDispatchNotification InspectionDispatchNotification);

}
