package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.CodeSource;
import com.cnf.dto.InspectionTaskDTO;

import java.util.List;

@Dao
public interface CodeSourceDao {

    @Insert
    void insertCodeSources(List<CodeSource> codeSourceList);

    @Query("SELECT * FROM CodeSource")
    List<CodeSource> selectAllCodeSources();

    @Query("DELETE FROM CodeSource")
    void deleteAllCodeSources();

}
