package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.OccInspectedSpaceElementPhotoDoc;

import java.util.List;
@Dao
public interface OccInspectedSpaceElementPhotoDocDao {

    @Insert
    void insertOccInspectedSpaceElementPhotoDoc(List<OccInspectedSpaceElementPhotoDoc> OccInspectedSpaceElementPhotoDocList);

    @Insert
    long insertOccInspectedSpaceElementPhotoDoc(OccInspectedSpaceElementPhotoDoc OccInspectedSpaceElementPhotoDoc);

    @Query("SELECT * FROM OccInspectedSpaceElementPhotoDoc")
    List<OccInspectedSpaceElementPhotoDoc> selectOccInspectedSpaceElementPhotoDoc();

    @Query("DELETE FROM OccInspectedSpaceElementPhotoDoc")
    void deleteAllOccInspectedSpaceElementPhotoDoc();
}
