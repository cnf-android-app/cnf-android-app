package com.cnf.adapter;

import static android.content.Context.MODE_PRIVATE;
import static com.cnf.utils.AppConstants.USER_ID_KEY;
import static com.cnf.utils.AppConstants.USER_SESSION_SHARE_PREFERENCE_NAME;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cnf.R;
import com.cnf.domain.BlobBytes;
import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.service.occ.OccInspectionPhotoService;

import java.util.List;
import java.util.Locale;

public class InspectionInspectedSpaceElementPhotoAdapter extends RecyclerView.Adapter<InspectionInspectedSpaceElementPhotoAdapter.RecyclerViewHolder> {

    private List<BlobBytes> blobBytesList;
    private Context context;

    public InspectionInspectedSpaceElementPhotoAdapter(Context context, List<BlobBytes> blobBytesList) {
        this.context = context;
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
        holder.photoNameTv.setText(blobBytes.getFilename().toUpperCase(Locale.ROOT));
        holder.photoIv.setImageBitmap(bitmap);

        // TODO refresh the page?
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm Delete");
                builder.setMessage("Are you sure you want to delete this photo?");

                builder.setPositiveButton("Yes", (dialog, which) -> {
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            // TODO requirement of delete the association table? photoId ?
                            OccInspectionPhotoService.getInstance(context).deleteOccInspectedPhotoBlobByte(blobBytes);
                        }
                    };
                    t.start();

                    try {
                        t.join();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    blobBytesList.remove(blobBytes);
                    notifyDataSetChanged();
                });

                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        });

    }

    @Override
    public int getItemCount() {
        return blobBytesList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView photoNameTv;
        private ImageView photoIv;
        private Button deleteBtn;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            photoNameTv = itemView.findViewById(R.id.tv_inspection_inspected_space_elements_photo_name);
            photoIv = itemView.findViewById(R.id.img_inspection_inspected_space_elements_photo);
            deleteBtn = itemView.findViewById(R.id.btn_inspection_inspected_space_elements_photo_delete);
        }
    }
}
