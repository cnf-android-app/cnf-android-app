package com.cnf.service.occ;

import static com.cnf.utils.AppConstants.INSPECTION_DATABASE_NAME;

import android.content.Context;

import androidx.room.Room;

import com.cnf.db.InspectionDatabase;
import com.cnf.domain.BlobBytes;

public class OccInspectionPhotoService {

    private static OccInspectionPhotoService INSTANCE = null;
    private InspectionDatabase inspection_database;

    private OccInspectionPhotoService(Context context) {
        this.inspection_database = Room.databaseBuilder(context, InspectionDatabase.class, INSPECTION_DATABASE_NAME).build();
    }

    public static OccInspectionPhotoService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new OccInspectionPhotoService(context);
        }
        return INSTANCE;
    }

    public void deleteOccInspectedPhotoBlobByte(BlobBytes blobBytes) {
        this.inspection_database.getBlobBytesDao().deleteBlobByte(blobBytes);
    }
}
