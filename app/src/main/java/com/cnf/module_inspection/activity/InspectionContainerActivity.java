package com.cnf.module_inspection.activity;

import static com.cnf.utils.AppConstants.INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY;
import static com.cnf.utils.AppConstants.SP_KEY_MUNICIPALITY_CODE;
import static com.cnf.utils.AppConstants.SP_KEY_USER_ID;
import static com.cnf.utils.AppConstants.SP_KEY_USER_LOGIN_TOKEN;
import static com.cnf.utils.AppConstants.SHARE_PREFERENCE_USER_OCC_SESSION;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;

import com.cnf.module_inspection.fragment.BottomSheetDialog;
import com.cnf.module_auth.activity.HomeActivity;
import com.cnf.module_auth.activity.MainActivity;
import com.cnf.R;
import com.cnf.module_inspection.entity.BlobBytes;
import com.cnf.module_inspection.entity.OccInspectedSpaceElementPhotoDoc;
import com.cnf.module_inspection.entity.PhotoDoc;
import com.cnf.module_inspection.fragment.InspectionSelectOccInspectedSpaceFragment;
import com.cnf.module_inspection.service.local.BlobBytesRepository;
import com.cnf.module_inspection.service.local.OccInspectedSpaceElementPhotoDocRepository;
import com.cnf.module_inspection.service.local.OccInspectionPhotoRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.UUID;

public class InspectionContainerActivity extends AppCompatActivity {

  private InspectionSelectOccInspectedSpaceFragment inspectionSelectOccInspectedSpaceFragment;
  private BlobBytesRepository blobBytesRepository;
  private OccInspectionPhotoRepository occInspectionPhotoRepository;
  private OccInspectedSpaceElementPhotoDocRepository occInspectedSpaceElementPhotoDocRepository;

  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_inspection_container);

    this.blobBytesRepository = BlobBytesRepository.getInstance(this);
    this.occInspectionPhotoRepository = OccInspectionPhotoRepository.getInstance(this);
    this.occInspectedSpaceElementPhotoDocRepository = OccInspectedSpaceElementPhotoDocRepository.getInstance(this);
    this.toolbar = findViewById(R.id.tb_occ_inspection_container_nav);
    setSupportActionBar(this.toolbar);
    toolbar.setNavigationOnClickListener(v -> {
      Intent intent = new Intent(InspectionContainerActivity.this, HomeActivity.class);
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
      if(extras == null) {
        return;
      }
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
    } else if (requestCode == 3 && resultCode == RESULT_OK) {
      ClipData clipData = data.getClipData();
      for (int i = 0; i < clipData.getItemCount(); i++ ) {
        Item item = clipData.getItemAt(i);
        Uri imageUri = item.getUri();
        try {
          Bitmap imageBitmap = Media.getBitmap(this.getContentResolver(), imageUri);
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
        } catch (IOException e) {
          e.printStackTrace();
        }
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
      String inspectedSpaceElementId = getIntent().getStringExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY);
      String inspectedSpaceElementPhotoName = getIntent().getStringExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY);
      String createTime = OffsetDateTime.now().toString();
      String s = Base64.getEncoder().encodeToString(bArray);
      String blobBytesId = UUID.randomUUID().toString();
      BlobBytes blobBytes = new BlobBytes(blobBytesId, createTime, s, userId, inspectedSpaceElementPhotoName);
      blobBytesRepository.insertBlobBytes(blobBytes);
      String photoDocId = UUID.randomUUID().toString();
      PhotoDoc photoDoc = new PhotoDoc();
      photoDoc.setPhotoDocId(photoDocId);
      photoDoc.setPhotoDocDescription(null);
      photoDoc.setPhotoDocCommitted(true);
      photoDoc.setBlobBytesId(blobBytesId);
      photoDoc.setMunicode(muniId);
      // TODO HARDCODE
      photoDoc.setBlobTypeId(201);
      photoDoc.setMetaDataMap(null);
      photoDoc.setTitle(null);
      photoDoc.setCreatedTS(createTime);
      photoDoc.setCreatedByUserid(userId);
      photoDoc.setLastUpdatedTS(createTime);
      photoDoc.setLastUpdatedByUserId(userId);
      occInspectionPhotoRepository.insertPhotoDoc(photoDoc);
      OccInspectedSpaceElementPhotoDoc occInspectedSpaceElementPhotoDoc = new OccInspectedSpaceElementPhotoDoc(photoDocId, inspectedSpaceElementId);
      occInspectedSpaceElementPhotoDocRepository.insertOccInspectedSpaceElementPhotoDoc( occInspectedSpaceElementPhotoDoc);
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