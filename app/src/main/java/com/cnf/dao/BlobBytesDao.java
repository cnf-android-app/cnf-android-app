package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Delete;
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
    List<BlobBytes> selectBlobBytesByInspectedElementId(int inspectedElementId);

    @Query("select * from blobbytes " +
            "join photodoc p " +
            "on blobbytes.bytesid = p.blobbytes_bytesid " +
            "join occinspectedspaceelementphotodoc o " +
            "on p.photodocid = o.photodoc_photodocid " +
            "where o.inspectedspaceelement_elementid = :inspectedElementId")
    int countBlobBytesByInspectedElementId(int inspectedElementId);

}
