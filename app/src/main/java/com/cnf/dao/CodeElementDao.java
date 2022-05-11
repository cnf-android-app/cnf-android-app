package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.CodeElement;
import com.cnf.domain.CodeSource;

import java.util.List;

@Dao
public interface CodeElementDao {

    @Insert
    void insertCodeElements(List<CodeElement> codeElementList);

    @Query("SELECT * FROM CodeElement")
    List<CodeElement> selectCodeElements();

    @Query("DELETE FROM CodeElement")
    void deleteAllCodeElements();

}
