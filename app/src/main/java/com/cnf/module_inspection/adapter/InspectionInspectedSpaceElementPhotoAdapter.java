package com.cnf.module_inspection.adapter;

import android.app.AlertDialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.module_inspection.adapter.InspectionInspectedSpaceElementPhotoAdapter.OccInspectedSpaceElementPhotoDocHolder;
import com.cnf.module_inspection.async.DeleteOccInspectedSpaceElementPhotoDocTask;
import com.cnf.module_inspection.entity.BlobBytes;

import java.util.Base64;
import java.util.List;
import java.util.Locale;

public class InspectionInspectedSpaceElementPhotoAdapter extends RecyclerView.Adapter<OccInspectedSpaceElementPhotoDocHolder> {

  private List<BlobBytes> blobBytesList;
  private final Fragment fragment;

  public InspectionInspectedSpaceElementPhotoAdapter(Fragment fragment, List<BlobBytes> blobBytesList) {
    this.fragment = fragment;
    this.blobBytesList = blobBytesList;
  }

  @NonNull
  @Override
  public OccInspectedSpaceElementPhotoDocHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new OccInspectedSpaceElementPhotoDocHolder(LayoutInflater.from(fragment.getActivity()).inflate(R.layout.layout_inspection_occ_inspected_space_element_photo_item, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull OccInspectedSpaceElementPhotoDocHolder holder, int position) {
    BlobBytes blobBytes = blobBytesList.get(position);
    String blobStr = blobBytes.getBlob();
    byte[] blob = Base64.getDecoder().decode(blobStr);
    BitmapFactory.Options opts = new BitmapFactory.Options();
    opts.inJustDecodeBounds = false;
    Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length, opts);
    String fileName = blobBytes.getFileName();
    if (fileName == null) {
      fileName = "Empty";
    } else {
      fileName = blobBytes.getFileName().toUpperCase(Locale.ROOT);
    }
    holder.photoNameTv.setText(fileName);
    holder.photoIv.setImageBitmap(bitmap);

    holder.deleteBtn.setOnClickListener(v -> new AlertDialog.Builder(fragment.getActivity())
        .setTitle("Confirm Delete")
        .setMessage("Are you sure you want to delete this photo?")
        .setPositiveButton("Yes", (dialog, which) -> {
          DeleteOccInspectedSpaceElementPhotoDocTask task = new DeleteOccInspectedSpaceElementPhotoDocTask(blobBytes, fragment);
          task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        })
        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
        .create()
        .show());

  }

  @Override
  public int getItemCount() {
    return blobBytesList.size();
  }

  static class OccInspectedSpaceElementPhotoDocHolder extends RecyclerView.ViewHolder {

    TextView photoNameTv;
    ImageView photoIv;
    ImageButton deleteBtn;

    public OccInspectedSpaceElementPhotoDocHolder(@NonNull View itemView) {
      super(itemView);
      photoNameTv = itemView.findViewById(R.id.tv_inspection_inspected_space_elements_photo_name);
      photoIv = itemView.findViewById(R.id.img_inspection_inspected_space_elements_photo);
      deleteBtn = itemView.findViewById(R.id.btn_inspection_inspected_space_elements_photo_delete);
    }
  }

  public List<BlobBytes> getBlobBytesList() {
    return blobBytesList;
  }

  public void setBlobBytesList(List<BlobBytes> blobBytesList) {
    this.blobBytesList = blobBytesList;
  }
}
