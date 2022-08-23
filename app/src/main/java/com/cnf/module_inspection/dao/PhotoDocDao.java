package com.cnf.module_inspection.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cnf.module_inspection.entity.PhotoDoc;

import java.util.List;

@Dao
public interface PhotoDocDao {


  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertPhotoDoc(List<PhotoDoc> PhotoDocList);

  @Insert
  long insertPhotoDoc(PhotoDoc PhotoDoc);

  @Query("SELECT * FROM PhotoDoc")
  List<PhotoDoc> selectPhotoDoc();

  @Query("DELETE FROM PhotoDoc")
  void deleteAllPhotoDoc();

  @Delete
  void deletePhotoDoc(PhotoDoc photoDoc);

  @Query("SELECT * FROM PhotoDoc WHERE blobbytes_bytesid = :blobByteId")
  PhotoDoc selectOnePhotoDoc(String blobByteId);

  @Query("SELECT * "
      + "FROM photodoc "
      + "INNER JOIN occinspectedspaceelementphotodoc "
      + "ON photodoc.photodocid = occinspectedspaceelementphotodoc.photodoc_photodocid "
      + "WHERE occinspectedspaceelementphotodoc.inspectedspaceelement_elementid = :occInspectedSpaceTypeElementId")
  List<PhotoDoc> selectAllPhotoDocListByOccInspectedSpaceTypeElementId(String occInspectedSpaceTypeElementId);

}
