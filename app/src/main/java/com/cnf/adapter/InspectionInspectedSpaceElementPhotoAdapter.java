package com.cnf.adapter;

import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.cnf.R;
import com.cnf.adapter.InspectionInspectedSpaceElementPhotoAdapter.OccInspectedSpaceElementPhotoDocHolder;
import com.cnf.db.InspectionDatabase;
import com.cnf.domain.BlobBytes;
import com.cnf.service.local.OccInspectedPhotoService;

import java.util.List;
import java.util.Locale;

public class InspectionInspectedSpaceElementPhotoAdapter extends RecyclerView.Adapter<OccInspectedSpaceElementPhotoDocHolder> {

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
    byte[] blob = blobBytes.getBlob();
    Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
    holder.photoNameTv.setText(blobBytes.getFileName().toUpperCase(Locale.ROOT));
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

    public OccInspectedSpaceElementPhotoDocHolder(@NonNull View itemView) {
      super(itemView);
      photoNameTv = itemView.findViewById(R.id.tv_inspection_inspected_space_elements_photo_name);
      photoIv = itemView.findViewById(R.id.img_inspection_inspected_space_elements_photo);
      deleteBtn = itemView.findViewById(R.id.btn_inspection_inspected_space_elements_photo_delete);
    }
  }
}
