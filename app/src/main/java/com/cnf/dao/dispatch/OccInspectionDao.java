package com.cnf.dao.dispatch;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.cnf.domain.infra_heavy.OccInspectionDispatchHeavy;
import com.cnf.domain.tasks.OccInspection;
import java.util.List;

@Dao
public interface OccInspectionDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
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
