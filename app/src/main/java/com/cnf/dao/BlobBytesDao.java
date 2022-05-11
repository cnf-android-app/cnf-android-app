package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.BlobBytes;

import java.util.List;

@Dao
public interface BlobBytesDao {


    @Insert
    void insertBlobBytes(List<BlobBytes> BlobBytesList);

    @Insert
    long insertBlobBytes(BlobBytes BlobBytes);

    @Query("SELECT * FROM BlobBytes")
    List<BlobBytes> selectBlobBytes();

    @Query("DELETE FROM BlobBytes")
    void deleteAllBlobBytes();

//    @Query("SELECT * FROM BlobBytes WHERE ")
//    List<BlobBytes> selectBlobBytesByInspectedElementId(int inspectedElementId);

}
