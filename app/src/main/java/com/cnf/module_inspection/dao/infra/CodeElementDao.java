package com.cnf.module_inspection.dao.infra;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.module_inspection.entity.infra.CodeElement;

import java.util.List;

@Dao
public interface CodeElementDao {

    @Insert
    void insertCodeElementList(List<CodeElement> codeElementList);

    @Query("SELECT * FROM CodeElement")
    List<CodeElement> selectCodeElementList();

    @Query("DELETE FROM CodeElement")
    void deleteAllCodeElementList();

}
