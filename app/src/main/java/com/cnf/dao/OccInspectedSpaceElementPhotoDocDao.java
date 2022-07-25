package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cnf.domain.OccInspectedSpaceElementPhotoDoc;

import com.cnf.domain.PhotoDoc;
import java.util.List;

@Dao
public interface OccInspectedSpaceElementPhotoDocDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOccInspectedSpaceElementPhotoDocList(List<OccInspectedSpaceElementPhotoDoc> OccInspectedSpaceElementPhotoDocList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOccInspectedSpaceElementPhotoDocList(OccInspectedSpaceElementPhotoDoc OccInspectedSpaceElementPhotoDoc);

    @Query("SELECT * FROM OccInspectedSpaceElementPhotoDoc")
    List<OccInspectedSpaceElementPhotoDoc> selectOccInspectedSpaceElementPhotoDocList();

    @Query("DELETE FROM OccInspectedSpaceElementPhotoDoc WHERE photodoc_photodocid = :photoDocId")
    void deleteOccInspectedSpaceElementPhotoDocList(String photoDocId);


}
