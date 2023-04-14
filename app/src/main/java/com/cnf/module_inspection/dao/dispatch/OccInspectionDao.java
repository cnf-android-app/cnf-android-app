package com.cnf.module_inspection.dao.dispatch;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.cnf.module_inspection.entity.tasks.OccInspection;
import java.util.List;

@Dao
public interface OccInspectionDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void insertOccInspectionList(List<OccInspection> OccInspectionList);

  @Query("SELECT * FROM OccInspection")
  List<OccInspection> selectOccInspectionList();

  @Query("DELETE FROM OccInspection")
  void deleteAllOccInspectionList();

  @Query("SELECT * FROM OccInspection WHERE inspectionid = :occInspectionId")
  OccInspection selectOccInspection(int occInspectionId);

  @Query("UPDATE OccInspection SET isInit = 1 WHERE inspectionid = :occInspectionId")
  void markOccInspectionInitialized(int occInspectionId);


}
