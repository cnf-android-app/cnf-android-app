package com.cnf;

import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_ID_NAME;
import static com.cnf.utils.AppConstants.INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_PHOTO_NAME;
import static com.cnf.utils.AppConstants.MUNICIPALITY_CODE;
import static com.cnf.utils.AppConstants.USER_ID_KEY;
import static com.cnf.utils.AppConstants.USER_SESSION_SHARE_PREFERENCE_NAME;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import com.cnf.domain.BlobBytes;
import com.cnf.domain.OccInspectedSpaceElementPhotoDoc;
import com.cnf.domain.PhotoDoc;
import com.cnf.fragment.InspectionAddSpaceFragment;
import com.cnf.service.api.InspectionActivityService;

import java.io.ByteArrayOutputStream;
import java.time.LocalTime;
import java.time.OffsetDateTime;

public class InspectionContainerActivity extends AppCompatActivity {

    private InspectionAddSpaceFragment inspectionAddSpaceFragment;
    private InspectionActivityService inspectionActivityService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_container);
        inspectionAddSpaceFragment = new InspectionAddSpaceFragment();
        getFragmentManager().beginTransaction().add(R.id.fl_inspection_container, inspectionAddSpaceFragment).commitAllowingStateLoss();
        inspectionActivityService = InspectionActivityService.getInstance(InspectionContainerActivity.this);
    }

    //TODO actions after taking photos
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bArray = bos.toByteArray();

            SharedPreferences sp = getSharedPreferences(USER_SESSION_SHARE_PREFERENCE_NAME, MODE_PRIVATE);
            int userId = sp.getInt(USER_ID_KEY, 0);
            int muniId = sp.getInt(MUNICIPALITY_CODE, 0);
            int inspectedSpaceElementId = this.getIntent().getIntExtra(INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_ID_NAME, 0);
            String inspectedSpaceElementPhotoName = this.getIntent().getStringExtra(INTENT_EXTRA_INSPECTED_SPACE_ELEMENT_PHOTO_NAME);
            Thread t = new Thread() {
                @Override
                public void run() {
                    String createTime = OffsetDateTime.now().toString();
                    BlobBytes blobBytes = new BlobBytes(createTime, bArray, userId, inspectedSpaceElementPhotoName);
                    long blobBytesId = inspectionActivityService.addBlobBytes(blobBytes);
                    PhotoDoc photoDoc = new PhotoDoc(null, true, (int) blobBytesId, muniId, null, null, "", userId, createTime);
                    long photoDocId = inspectionActivityService.addPhotoDoc(photoDoc);
                    OccInspectedSpaceElementPhotoDoc occInspectedSpaceElementPhotoDoc = new OccInspectedSpaceElementPhotoDoc((int) photoDocId, inspectedSpaceElementId);
                    long i = inspectionActivityService.addOccInspectedSpaceElementPhotoDoc(occInspectedSpaceElementPhotoDoc);
                }
            };
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}