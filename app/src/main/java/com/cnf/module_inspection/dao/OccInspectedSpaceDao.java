package com.cnf.module_inspection.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cnf.module_inspection.entity.OccInspectedSpace;
import com.cnf.module_inspection.entity.OccInspectedSpaceHeavy;

import java.util.List;

@Dao
public interface OccInspectedSpaceDao {

  @Insert
  long insertInspectedSpace(OccInspectedSpace occInspectedSpace);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertOccInspectedSpaceList(List<OccInspectedSpace> OccInspectedSpaceList);

  @Query("SELECT * FROM OccInspectedSpace")
  List<OccInspectedSpace> selectAllOccInspectedSpaceList();

  @Query("DELETE FROM OccInspectedSpace")
  void deleteAllOccInspectedSpaceList();

  @Delete
  void deleteOccInspectedSpace(OccInspectedSpace occInspectedSpace);

  @Query("SELECT * FROM OccInspectedSpace WHERE inspectedspaceid = :inspectedSpaceId")
  OccInspectedSpace selectOneOccInspectedSpaceById(String inspectedSpaceId);

  @Query("SELECT * "
      + "FROM occinspectedspace "
      + "LEFT OUTER JOIN OccLocationDescription d "
      + "ON occinspectedspace.occlocationdescription_descid = d.locationdescriptionid "
      + "INNER JOIN occchecklistspacetype o "
      + "ON occinspectedspace.occchecklistspacetype_chklstspctypid = o.checklistspacetypeid "
      + "INNER JOIN occspacetype o2 "
      +"ON o.spacetype_typeid = o2.spacetypeid "
      +"WHERE occinspection_inspectionid = :inspectionId")
  List<OccInspectedSpaceHeavy> selectAllOccInspectedSpaceHeavyListByInspectionId(int inspectionId);


  @Query("SELECT * FROM occinspectedspace WHERE occinspection_inspectionid = :inspectionId")
  List<OccInspectedSpace> selectAllOccInspectedSpaceList(int inspectionId);

  @Query("UPDATE occinspectedspace SET occlocationdescription_descid = :occLocationDescriptionId  WHERE inspectedspaceid = :occInspectedSpaceId")
  void updateLocationDesForOccInspectedSpace(String occInspectedSpaceId, int occLocationDescriptionId);

}
