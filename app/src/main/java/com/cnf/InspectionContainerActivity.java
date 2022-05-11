package com.cnf;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.cnf.domain.BlobBytes;
import com.cnf.domain.OccInspectedSpaceElementPhotoDoc;
import com.cnf.domain.PhotoDoc;
import com.cnf.fragment.InspectionAddSpaceFragment;
import com.cnf.fragment.InspectionInspectedSpaceElementsFragment;
import com.cnf.service.InspectionActivityService;

import java.io.ByteArrayOutputStream;
import java.time.OffsetDateTime;

public class InspectionContainerActivity extends AppCompatActivity {

    private InspectionAddSpaceFragment inspectionAddSpaceFragment;
    private InspectionActivityService inspectionActivityService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        int inspectionId = getIntent().getIntExtra("inspectionId", 0);
        setContentView(R.layout.activity_inspection_container);
        inspectionAddSpaceFragment = new InspectionAddSpaceFragment();
        getFragmentManager().beginTransaction().add(R.id.fl_inspection_container, inspectionAddSpaceFragment).commitAllowingStateLoss();
        inspectionActivityService = new InspectionActivityService(InspectionContainerActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("destroy");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();

            SharedPreferences sp = getSharedPreferences("user_session", MODE_PRIVATE);
            int userId = sp.getInt("user_id", 0);
            int muniId = sp.getInt("muni_id", 0);
            int elementID = this.getIntent().getIntExtra("inspectedSpaceElementId", 0);
            System.out.println("elementID " + elementID);

            Thread t = new Thread() {
                @Override
                public void run() {
                    String createTime = OffsetDateTime.now().toString();
                    BlobBytes blobBytes = new BlobBytes(createTime, bArray, userId, "");
                    long blobBytesId = inspectionActivityService.addBlobBytes(blobBytes);
                    PhotoDoc photoDoc = new PhotoDoc(null, true, (int) blobBytesId, muniId, null, null, "", userId, createTime);
                    long photoDocId = inspectionActivityService.addPhotoDoc(photoDoc);
                    OccInspectedSpaceElementPhotoDoc occInspectedSpaceElementPhotoDoc = new OccInspectedSpaceElementPhotoDoc((int) photoDocId, elementID);
                    long i = inspectionActivityService.addOccInspectedSpaceElementPhotoDoc(occInspectedSpaceElementPhotoDoc);
                    System.out.println("phote id" + i);

                }
            };
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            InspectionInspectedSpaceElementsFragment inspectionInspectedSpaceElementsFragment = new InspectionInspectedSpaceElementsFragment(elementID);
            getFragmentManager().beginTransaction().add(R.id.fl_inspection_container, inspectionInspectedSpaceElementsFragment).commit();



//            finish();
//            startActivity(getIntent());










//            System.out.println(imageBitmap);
//            System.out.println("done----------------------");
//
//            System.out.println("HAHAHHAHAHAHHA"+);
//            //System.out.println(data.getIntExtra("idid", 0));
//
//            System.out.println("elementID: "+elementID);
//            ImageView imageView = findViewById(R.id.img_inspection_inspected_space_elements_photo);
//            imageView.setImageBitmap(imageBitmap);
        }
    }
}