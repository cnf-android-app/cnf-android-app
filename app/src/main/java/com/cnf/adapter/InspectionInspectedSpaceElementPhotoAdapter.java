package com.cnf.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.domain.BlobBytes;

import java.util.List;

public class InspectionInspectedSpaceElementPhotoAdapter extends RecyclerView.Adapter<InspectionInspectedSpaceElementPhotoAdapter.RecyclerViewHolder> {

    private List<BlobBytes> blobBytesList;

    public InspectionInspectedSpaceElementPhotoAdapter( List<BlobBytes> blobBytesList) {
        this.blobBytesList = blobBytesList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inspection_inspected_space_element_photo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        BlobBytes blobBytes = blobBytesList.get(position);
        byte[] blob = blobBytes.getBlob();
        Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        holder.imageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return blobBytesList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_inspection_inspected_space_elements_photo);
        }
    }
}
