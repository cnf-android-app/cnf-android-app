package com.cnf.dao.dispatch;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.cnf.domain.tasks.PropertyUnit;
import java.util.List;

@Dao
public interface PropertyUnitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPropertyUnitList(List<PropertyUnit> PropertyUnitList);

    @Query("SELECT * FROM PropertyUnit")
    List<PropertyUnit> selectPropertyUnitList();

    @Query("DELETE FROM PropertyUnit")
    void deleteAllPropertyUnitList();

}
