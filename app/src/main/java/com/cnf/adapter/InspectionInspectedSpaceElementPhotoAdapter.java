package com.cnf.adapter;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.cnf.R;
import com.cnf.adapter.InspectionInspectedSpaceElementPhotoAdapter.OccInspectedSpaceElementPhotoDocHolder;
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.BlobBytes;
import com.cnf.service.local.OccInspectedPhotoService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

public class InspectionInspectedSpaceElementPhotoAdapter extends RecyclerView.Adapter<OccInspectedSpaceElementPhotoDocHolder> {

  // TODO IMPLEMENT PHOTO POOL FEATURE (SUGGESTION: SELECTION PHOTOES FROM MOBILE)
  private final Handler handler = new Handler();

  private List<BlobBytes> blobBytesList;
  private Context context;
  private OccInspectedPhotoService occInspectedPhotoService;
  private InspectionDatabase inspectionDB;

  public InspectionInspectedSpaceElementPhotoAdapter(Context context, List<BlobBytes> blobBytesList) {
    this.context = context;
    this.blobBytesList = blobBytesList;
    this.occInspectedPhotoService = OccInspectedPhotoService.getInstance();
    this.inspectionDB = Room.databaseBuilder(context, InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();
  }

  @NonNull
  @Override
  public OccInspectedSpaceElementPhotoDocHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new OccInspectedSpaceElementPhotoDocHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inspection_occ_inspected_space_element_photo_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull OccInspectedSpaceElementPhotoDocHolder holder, int position) {
    BlobBytes blobBytes = blobBytesList.get(position);
    String blobStr = blobBytes.getBlob();
    byte[] blob = Base64.getDecoder().decode(blobStr);
    BitmapFactory.Options opts = new BitmapFactory.Options();
    opts.inJustDecodeBounds=false;
    Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length, opts);

    String fileName = blobBytes.getFileName();
    if (fileName == null) {
      fileName = "Empty";
    } else {
      fileName = blobBytes.getFileName().toUpperCase(Locale.ROOT);
    }
    holder.photoNameTv.setText(fileName);
    holder.photoIv.setImageBitmap(bitmap);

    holder.deleteBtn.setOnClickListener(v -> {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("Confirm Delete");
      builder.setMessage("Are you sure you want to delete this photo?");
      builder.setPositiveButton("Yes", (dialog, which) -> {
        new Thread(new DeleteOccInspectedSpaceElementPhotoDoc(blobBytes)).start();

      });
      builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
      AlertDialog alertDialog = builder.create();
      alertDialog.show();
    });

//    holder.downloadBtn.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        generatePDF(bitmap);
//      }
//    });
  }

  class DeleteOccInspectedSpaceElementPhotoDoc implements Runnable {

    private BlobBytes blobBytes;

    public DeleteOccInspectedSpaceElementPhotoDoc(BlobBytes blobBytes) {
      this.blobBytes = blobBytes;
    }

    @Override
    public void run() {
      // TODO requirement of delete the association table? photoId ?
      occInspectedPhotoService.deleteOccInspectedPhotoBlobByte(inspectionDB, blobBytes);
      handler.post(new Runnable() {
        @Override
        public void run() {
          blobBytesList.remove(blobBytes);
          notifyDataSetChanged();
        }
      });
    }
  }

  @Override
  public int getItemCount() {
    return blobBytesList.size();
  }

  class OccInspectedSpaceElementPhotoDocHolder extends RecyclerView.ViewHolder {

    private TextView photoNameTv;
    private ImageView photoIv;
    private Button deleteBtn;
    private Button downloadBtn;

    public OccInspectedSpaceElementPhotoDocHolder(@NonNull View itemView) {
      super(itemView);
      photoNameTv = itemView.findViewById(R.id.tv_inspection_inspected_space_elements_photo_name);
      photoIv = itemView.findViewById(R.id.img_inspection_inspected_space_elements_photo);
      deleteBtn = itemView.findViewById(R.id.btn_inspection_inspected_space_elements_photo_delete);
      //downloadBtn = itemView.findViewById(R.id.btn_inspection_inspected_space_elements_photo_download);
    }
  }

  int pageHeight = 1120;
  int pagewidth = 792;

  // creating a bitmap variable
  // for storing our images
  // Bitmap bmp, scaledbmp;

  // constant code for runtime permissions
  private static final int PERMISSION_REQUEST_CODE = 200;

  private void generatePDF(Bitmap scaledbmp) {
    // creating an object variable
    // for our PDF document.
    PdfDocument pdfDocument = new PdfDocument();

    // two variables for paint "paint" is used
    // for drawing shapes and we will use "title"
    // for adding text in our PDF file.
    Paint paint = new Paint();
    Paint title = new Paint();

    // we are adding page info to our PDF file
    // in which we will be passing our pageWidth,
    // pageHeight and number of pages and after that
    // we are calling it to create our PDF.
    PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

    // below line is used for setting
    // start page for our PDF file.
    PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

    // creating a variable for canvas
    // from our page of PDF.
    Canvas canvas = myPage.getCanvas();

    // below line is used to draw our image on our PDF file.
    // the first parameter of our drawbitmap method is
    // our bitmap
    // second parameter is position from left
    // third parameter is position from top and last
    // one is our variable for paint.
    canvas.drawBitmap(scaledbmp, 56, 40, paint);

    // below line is used for adding typeface for
    // our text which we will be adding in our PDF file.
    title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

    // below line is used for setting text size
    // which we will be displaying in our PDF file.
    title.setTextSize(15);

    // below line is sued for setting color
    // of our text inside our PDF file.
    title.setColor(ContextCompat.getColor(context, R.color.purple_200));

    // below line is used to draw text in our PDF file.
    // the first parameter is our text, second parameter
    // is position from start, third parameter is position from top
    // and then we are passing our variable of paint which is title.
    canvas.drawText("A portal for IT professionals.", 209, 100, title);
    canvas.drawText("Geeks for Geeks", 209, 80, title);

    // similarly we are creating another text and in this
    // we are aligning this text to center of our PDF file.
    title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    title.setColor(ContextCompat.getColor(context, R.color.purple_200));
    title.setTextSize(15);

    // below line is used for setting
    // our text to center of PDF.
    title.setTextAlign(Paint.Align.CENTER);
    canvas.drawText("This is sample document which we have created.", 396, 560, title);

    // after adding all attributes to our
    // PDF file we will be finishing our page.
    pdfDocument.finishPage(myPage);

    // below line is used to set the name of
    // our PDF file and its path.
    File file = new File(Environment.getExternalStorageDirectory(), "GFG.pdf");

    try {
      // after creating a file name we will
      // write our PDF file to that location.
      pdfDocument.writeTo(new FileOutputStream(file));

      // below line is to print toast message
      // on completion of PDF generation.
      Toast.makeText(context, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
    } catch (IOException e) {
      // below line is used
      // to handle error
      e.printStackTrace();
    }
    // after storing our pdf to that
    // location we are closing our PDF file.
    pdfDocument.close();
  }


  private boolean checkPermission() {
    // checking of permissions.
    int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
    int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
    return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
  }

  private void requestPermission() {
    // requesting permissions if not provided.
    ActivityCompat.requestPermissions((Activity) context, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
  }

  
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == PERMISSION_REQUEST_CODE) {
      if (grantResults.length > 0) {

        // after requesting permissions we are showing
        // users a toast message of permission granted.
        boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

        if (writeStorage && readStorage) {
          Toast.makeText(context, "Permission Granted..", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(context, "Permission Denied.", Toast.LENGTH_SHORT).show();
          //context.finish();
        }
      }
    }
  }
}
