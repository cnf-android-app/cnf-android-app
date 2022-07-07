package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.OccInspectedSpaceElementPhotoDoc;

import java.util.List;

@Dao
public interface OccInspectedSpaceElementPhotoDocDao {

    @Insert
    void insertOccInspectedSpaceElementPhotoDocList(List<OccInspectedSpaceElementPhotoDoc> OccInspectedSpaceElementPhotoDocList);

    @Insert
    long insertOccInspectedSpaceElementPhotoDocList(OccInspectedSpaceElementPhotoDoc OccInspectedSpaceElementPhotoDoc);

    @Query("SELECT * FROM OccInspectedSpaceElementPhotoDoc")
    List<OccInspectedSpaceElementPhotoDoc> selectOccInspectedSpaceElementPhotoDocList();

    @Query("DELETE FROM OccInspectedSpaceElementPhotoDoc")
    void deleteAllOccInspectedSpaceElementPhotoDocList();
}
