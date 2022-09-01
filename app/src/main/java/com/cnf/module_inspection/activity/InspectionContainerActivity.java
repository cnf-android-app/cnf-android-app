package com.cnf.module_inspection.activity;

import static com.cnf.utils.AppConstants.FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;

import com.cnf.module_inspection.async.SaveOccInspectedSpaceElementPhotoDocTask;
import com.cnf.module_inspection.fragment.BottomSheetDialog;
import com.cnf.module_auth.activity.HomeActivity;
import com.cnf.R;
import com.cnf.module_inspection.fragment.InspectionSelectOccInspectedSpaceFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class InspectionContainerActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_inspection_container);
    Toolbar toolbar = findViewById(R.id.tb_occ_inspection_container_nav);
    setSupportActionBar(toolbar);
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
    InspectionSelectOccInspectedSpaceFragment inspectionSelectOccInspectedSpaceFragment = new InspectionSelectOccInspectedSpaceFragment();
    getSupportFragmentManager().beginTransaction().replace(R.id.fl_occ_inspection_container, inspectionSelectOccInspectedSpaceFragment, FRAGMENT_INSPECTION_OCC_INSPECTED_SPACE)
        .commitAllowingStateLoss();
    //todo
    try {
      Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
      field.setAccessible(true);
      field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    String inspectedSpaceElementId = getIntent().getStringExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_ID_KEY);
    String inspectedSpaceElementPhotoName = getIntent().getStringExtra(INTENT_EXTRA_OCC_INSPECTED_SPACE_ELEMENT_PHOTO_NAME_KEY);

    List<byte[]> bArrayList = new ArrayList<>();
    if (requestCode == 1 && resultCode == RESULT_OK) {
      Bundle extras = data.getExtras();
      if (extras == null) {
        return;
      }
      Bitmap imageBitmap = (Bitmap) extras.get("data");
//      ContentResolver resolver = getContentResolver();
//      ContentValues contentValues = new ContentValues();
//      contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "hello");
//      contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
//      contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + "hello");
//      Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//      OutputStream fos = null;
//      try {
//        fos = resolver.openOutputStream(imageUri);
//        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//      } catch (FileNotFoundException e) {
//        e.printStackTrace();
//      } finally {
//        try {
//          fos.flush();
//        } catch (IOException e) {
//          e.printStackTrace();
//        }
//        try {
//          fos.close();
//        } catch (IOException e) {
//          e.printStackTrace();
//        }
//      }



      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
      byte[] bArray = bos.toByteArray();
      bArrayList.add(bArray);
    } else if (requestCode == 3 && resultCode == RESULT_OK) {
      ClipData clipData = data.getClipData();
      for (int i = 0; i < clipData.getItemCount(); i++) {
        Item item = clipData.getItemAt(i);
        Uri imageUri = item.getUri();
        Bitmap imageBitmap = null;
        try {
          imageBitmap = Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
          e.printStackTrace();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bArray = bos.toByteArray();
        bArrayList.add(bArray);
      }

    }
    SaveOccInspectedSpaceElementPhotoDocTask task = new SaveOccInspectedSpaceElementPhotoDocTask(bArrayList, InspectionContainerActivity.this);
    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.inspection_menu, menu);
    return true;
  }
}