package com.cnf.module_inspection.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cnf.module_inspection.entity.BlobBytes;

import java.util.List;

@Dao
public interface BlobBytesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertBlobByteList(List<BlobBytes> BlobBytesList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertBlobByteList(BlobBytes BlobBytes);

    @Query("SELECT * FROM BlobBytes")
    List<BlobBytes> selectBlobByteList();

    @Query("SELECT * FROM BlobBytes WHERE bytesid = :bytesId")
    BlobBytes selectBlobByteById(String bytesId);

    @Delete
    void deleteBlobByte(BlobBytes BlobBytes);

    @Query("DELETE FROM BlobBytes")
    void deleteAllBlobBytes();

    @Query("select * from blobbytes " +
            "join photodoc p " +
            "on blobbytes.bytesid = p.blobbytes_bytesid " +
            "join occinspectedspaceelementphotodoc o " +
            "on p.photodocid = o.photodoc_photodocid " +
            "where o.inspectedspaceelement_elementid = :inspectedElementId")
    List<BlobBytes> selectBlobByteListByInspectedElementId(String inspectedElementId);

    @Query("select * from blobbytes " +
            "join photodoc p " +
            "on blobbytes.bytesid = p.blobbytes_bytesid " +
            "join occinspectedspaceelementphotodoc o " +
            "on p.photodocid = o.photodoc_photodocid " +
            "where o.inspectedspaceelement_elementid = :inspectedElementId")
    int countBlobBytesByInspectedElementId(String inspectedElementId);

}
