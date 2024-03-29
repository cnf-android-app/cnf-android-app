package com.cnf.module_inspection.dao.dispatch;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.cnf.module_inspection.entity.tasks.Login;
import java.util.List;

@Dao
public interface LoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLoginList(List<Login> LoginList);

    @Query("SELECT * FROM Login")
    List<Login> selectLoginList();

    @Query("DELETE FROM Login")
    void deleteAllLoginList();

}
