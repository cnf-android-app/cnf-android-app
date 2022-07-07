package com.cnf.dao.dispatch;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.cnf.domain.tasks.OccPeriod;
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
