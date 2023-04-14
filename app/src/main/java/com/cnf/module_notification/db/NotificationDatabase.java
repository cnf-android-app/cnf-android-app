package com.cnf.module_notification.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.cnf.module_notification.dao.InspectionDispatchNotificationDao;
import com.cnf.module_notification.entity.InspectionDispatchNotification;

@Database(entities = {InspectionDispatchNotification.class}, version = 1)
public abstract class NotificationDatabase extends RoomDatabase {

  private static final String NOTIFICATION_DATABASE = "NOTIFICATION_DATABASE";

  private static NotificationDatabase INSTANCE;

  public static NotificationDatabase getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NotificationDatabase.class, NOTIFICATION_DATABASE).build();
    }
    return INSTANCE;
  }

  public abstract InspectionDispatchNotificationDao getInspectionDispatchNotificationDao();

}
