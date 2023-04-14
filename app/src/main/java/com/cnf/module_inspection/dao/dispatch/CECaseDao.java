package com.cnf.module_inspection.dao.dispatch;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.cnf.module_inspection.entity.tasks.CECase;
import java.util.List;

@Dao
public interface CECaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCECaseList(List<CECase> CECaseList);

    @Query("SELECT * FROM CECase")
    List<CECase> selectCECaseList();

    @Query("DELETE FROM CECase")
    void deleteAllCECaseList();

}
