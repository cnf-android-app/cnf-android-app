package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.PhotoDoc;

import java.util.List;

@Dao
public interface PhotoDocDao {


    @Insert
    void insertPhotoDoc(List<PhotoDoc> PhotoDocList);

    @Insert
    long insertPhotoDoc(PhotoDoc PhotoDoc);

    @Query("SELECT * FROM PhotoDoc")
    List<PhotoDoc> selectPhotoDoc();

    @Query("DELETE FROM PhotoDoc")
    void deleteAllPhotoDoc();

    @Query("SELECT * FROM PhotoDoc WHERE blobbytes_bytesid = :blobByteId")
    PhotoDoc selectOnePhotoDoc(int blobByteId);

}
