package com.cnf.module_auth.activity;

import static android.content.ContentValues.TAG;
import static com.cnf.utils.AppConstants.FIREBASE_NOTIFICATION_REF_KEY;
import static com.cnf.utils.AppConstants.FRAGMENT_INSPECTION;
import static com.cnf.utils.AppConstants.FRAGMENT_NOTIFICATION;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;
import static com.cnf.utils.AppConstants.SP_KEY_USER_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.cnf.R;
import com.cnf.module_error.ErrorActivity;
import com.cnf.module_notification.entity.InspectionDispatchNotification;
import com.cnf.module_inspection.fragment.InspectionDispatchFragment;
import com.cnf.module_notification.enums.InspectionDispatchNotificationOperation;
import com.cnf.module_notification.exceptions.DataSourceNotFoundException;
import com.cnf.module_notification.fragment.InspectionNotificationFragment;
import com.cnf.module_notification.service.impl.OccInspectionDispatchNotificationRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

  private final String CHANNEL_ID = "CNF_NOTIFICATION_CHANNEL";
  private NotificationManagerCompat notificationManagerCompat;

  private BottomNavigationView bottomNavigationView;
  private InspectionDispatchFragment inspectionFragment;
  private InspectionNotificationFragment notificationFragment;
  private OccInspectionDispatchNotificationRepository occInspectionDispatchNotificationRepository;
  private int userId;
  private SharedPreferences sp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    try {
      this.occInspectionDispatchNotificationRepository = OccInspectionDispatchNotificationRepository.getInstance(this);
    } catch (DataSourceNotFoundException e) {
      Log.i(TAG, e.getMessage());
      Intent intent = new Intent(this, ErrorActivity.class);
      startActivity(intent);
    }
    this.inspectionFragment = new InspectionDispatchFragment();
    this.notificationFragment = new InspectionNotificationFragment();
    this.bottomNavigationView = findViewById(R.id.bottomNavigationView);
    this.bottomNavigationView.setOnNavigationItemSelectedListener(this);
    this.bottomNavigationView.setSelectedItemId(R.id.menu_home_inspection);
    this.sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, MODE_PRIVATE);
    this.userId = sp.getInt(SP_KEY_USER_ID, 0);

    createNotificationChannel();
    notificationManagerCompat = NotificationManagerCompat.from(this);

//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    database.getReference(FIREBASE_NOTIFICATION_REF_KEY).child(String.valueOf(userId)).addChildEventListener(
//        new ChildEventListener() {
//          @Override
//          public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
//            GenericTypeIndicator<InspectionDispatchNotification> genericTypeIndicator = new GenericTypeIndicator<InspectionDispatchNotification>() {
//            };
//            InspectionDispatchNotification inspectionDispatchNotification = dataSnapshot.getValue(genericTypeIndicator);
//            if (inspectionDispatchNotification == null || inspectionDispatchNotification.getNotified()) {
//              return;
//            }
//            String key = dataSnapshot.getKey();
//            if (key != null) {
//              database.getReference(FIREBASE_NOTIFICATION_REF_KEY).child(String.valueOf(userId)).child(key).setValue(null);
//            }
//            new Thread(() -> {
//              inspectionDispatchNotification.setArchived(false);
//              occInspectionDispatchNotificationRepository.insertOccInspectionDispatchNotification(inspectionDispatchNotification);
//              InspectionNotificationFragment notificationFragment = (InspectionNotificationFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_NOTIFICATION);
//              if (notificationFragment != null) {
//                new InspectionNotificationFragment.UpdateInspectionDispatchNotification
//                    .Builder()
//                    .recyclerView(notificationFragment.getRvOccInspectionNotification())
//                    .operation(InspectionDispatchNotificationOperation.OUTER_INSERT)
//                    .category(notificationFragment.getCategory())
//                    .inspectionDispatchNotification(inspectionDispatchNotification)
//                    .inspectionDispatchNotificationRepository(occInspectionDispatchNotificationRepository)
//                    .build()
//                    .execute();
//              }
//            }).start();
//            sendOnChannel(inspectionDispatchNotification);
//          }
//
//          @Override
//          public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
//          }
//
//          @Override
//          public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//          }
//
//          @Override
//          public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//          }
//
//          @Override
//          public void onCancelled(@NonNull DatabaseError databaseError) {
//            Toast.makeText(getApplicationContext()
//                , "DBError: " + databaseError, Toast.LENGTH_SHORT).show();
//          }
//        }
//    );
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_home_inspection:
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, inspectionFragment, FRAGMENT_INSPECTION).commit();
        return true;
      case R.id.menu_home_notification:
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, notificationFragment, FRAGMENT_NOTIFICATION).commit();
        return true;
    }
    return false;
  }

  private void createNotificationChannel() {
    CharSequence name = CHANNEL_ID;
    String description = "This channel will show notification for people who received inspection dispatches";
    int importance = NotificationManager.IMPORTANCE_HIGH;
    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
    channel.setDescription(description);
    NotificationManager notificationManager = getSystemService(NotificationManager.class);
    notificationManager.createNotificationChannel(channel);
  }

  // TODO content
  private void sendOnChannel(InspectionDispatchNotification inspectionDispatchNotification) {
    String inspectionId = inspectionDispatchNotification.getInspectionId();
    Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.camera)
        .setContentTitle("New Inspection Dispatch Notification")
        .setContentText(String.format("You have a new inspection dispatch. The dispatch id is: %s ", inspectionId))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true).build();
    notificationManagerCompat.notify(1, notification);
  }
}