package com.cnf.module_inspection.dao.dispatch;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.cnf.module_inspection.entity.tasks.Person;
import java.util.List;

@Dao
public interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPersonList(List<Person> PersonList);

    @Query("SELECT * FROM Person")
    List<Person> selectPersonList();

    @Query("DELETE FROM Person")
    void deleteAllPersonList();

}
