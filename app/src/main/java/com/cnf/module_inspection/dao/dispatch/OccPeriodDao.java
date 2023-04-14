package com.cnf.module_inspection.dao.dispatch;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.cnf.module_inspection.entity.tasks.OccPeriod;
import java.util.List;

@Dao
public interface OccPeriodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOccPeriodList(List<OccPeriod> OccPeriodList);

    @Query("SELECT * FROM OccPeriod")
    List<OccPeriod> selectOccPeriodList();

    @Query("DELETE FROM OccPeriod")
    void deleteAllOccPeriodList();

}
