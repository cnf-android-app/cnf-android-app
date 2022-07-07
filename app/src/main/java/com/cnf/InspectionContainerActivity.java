package com.cnf;

import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTION_ID_KEY;
import static com.cnf.utils.AppConstants.SP_KEY_MUNICIPALITY_CODE;
import static com.cnf.utils.AppConstants.SP_KEY_USER_ID;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;

import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;

import com.cnf.db.InspectionDatabase;
import com.cnf.domain.BlobBytes;
import com.cnf.domain.OccInspectedSpaceElementPhotoDoc;
import com.cnf.domain.PhotoDoc;
import com.cnf.fragment.InspectionSelectOccInspectedSpaceFragment;
import com.cnf.service.local.OccInspectedPhotoService;

import java.io.ByteArrayOutputStream;
import java.time.OffsetDateTime;

public class InspectionContainerActivity extends AppCompatActivity {

  private final Handler handler = new Handler();

  private InspectionSelectOccInspectedSpaceFragment inspectionSelectOccInspectedSpaceFragment;
  private OccInspectedPhotoService occInspectedPhotoService;
  private InspectionDatabase inspectionDB;

  private int inspectionId;

  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_inspection_container);

    this.inspectionDB = Room.databaseBuilder(this, InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();
    this.occInspectedPhotoService = OccInspectedPhotoService.getInstance();
    this.toolbar = findViewById(R.id.tb_occ_inspection_container_nav);
    setSupportActionBar(this.toolbar);
    this.inspectionId = this.getIntent().getIntExtra(INTENT_EXTRA_INSPECTION_ID_KEY, 0);
    toolbar.setNavigationOnClickListener(v -> {
      Intent intent = new Intent(InspectionContainerActivity.this, InspectionActivity.class);
      startActivity(intent);
    });

    toolbar.setOnMenuItemClickListener(item -> {
      if (item.getItemId() == R.id.occ_inspection_config) {
        BottomSheetDialog bottomSheet = new BottomSheetDialog();
        bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
        return true;
      }
      return true;
    });

    inspectionSelectOccInspectedSpaceFragment = new InspectionSelectOccInspectedSpaceFragment();
    getFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccInspectedSpaceFragment).commitAllowingStateLoss();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == 1 && resultCode == RESULT_OK) {
      Bundle extras = data.getExtras();
      Bitmap imageBitmap = (Bitmap) extras.get("data");
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
      byte[] bArray = bos.toByteArray();
      Thread thread = new Thread(new SaveOccInspectedSpaceElementPhotoDoc(bArray));
      thread.start();
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  class SaveOccInspectedSpaceElementPhotoDoc implements Runnable {

    private byte[] bArray;

    public SaveOccInspectedSpaceElementPhotoDoc(byte[] bArray) {
      this.bArray = bArray;
    }

    @Override
    public void run() {
      SharedPreferences sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, MODE_PRIVATE);
      int userId = sp.getInt(SP_KEY_USER_ID, 0);
      int muniId = sp.getInt(SP_KEY_MUNICIPALITY_CODE, 0);
      int inspectedSpaceElementId = getIntent().getIntExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY, 0);
      String inspectedSpaceElementPhotoName = getIntent().getStringExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY);
      String createTime = OffsetDateTime.now().toString();
      BlobBytes blobBytes = new BlobBytes(null, createTime, bArray, userId, inspectedSpaceElementPhotoName);
      long blobBytesId = occInspectedPhotoService.insertBlobBytes(inspectionDB, blobBytes);
      PhotoDoc photoDoc = new PhotoDoc();
      photoDoc.setPhotoDocId(null);
      photoDoc.setPhotoDocDescription(null);
      photoDoc.setPhotoDocCommitted(true);
      photoDoc.setBlobBytesId((int) blobBytesId);
      photoDoc.setMunicode(muniId);
      photoDoc.setBlobTypeId(null);
      photoDoc.setMetaDataMap(null);
      photoDoc.setTitle(null);
      photoDoc.setCreatedTS(createTime);
      photoDoc.setCreatedByUserid(userId);
      photoDoc.setLastUpdatedTS(createTime);
      photoDoc.setLastUpdatedByUserId(userId);
      long photoDocId = occInspectedPhotoService.insertPhotoDoc(inspectionDB, photoDoc);
      OccInspectedSpaceElementPhotoDoc occInspectedSpaceElementPhotoDoc = new OccInspectedSpaceElementPhotoDoc((int) photoDocId, inspectedSpaceElementId);
      long i = occInspectedPhotoService.insertOccInspectedSpaceElementPhotoDoc(inspectionDB, occInspectedSpaceElementPhotoDoc);
    }
  }

  private void backToLogin() {
    Intent intent = new Intent(InspectionContainerActivity.this, MainActivity.class);
    SharedPreferences sp = getSharedPreferences(SHARE_PREFERENCE_USER_OCC_SESSION, MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();
    editor.putString(SP_KEY_USER_LOGIN_TOKEN, null);
    editor.commit();
    startActivity(intent);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.inspection_menu, menu);
    return true;
  }

}