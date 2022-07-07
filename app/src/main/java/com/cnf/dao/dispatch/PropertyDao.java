package com.cnf.dao.dispatch;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.cnf.domain.tasks.Property;
import java.util.List;

@Dao
public interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPropertyList(List<Property> PropertyList);

    @Query("SELECT * FROM Property")
    List<Property> selectPropertyList();

    @Query("DELETE FROM Property")
    void deleteAllPropertyList();

}
