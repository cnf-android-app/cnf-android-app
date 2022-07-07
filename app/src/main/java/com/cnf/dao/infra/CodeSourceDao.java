package com.cnf.dao.infra;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.infra.CodeSource;

import java.util.List;

@Dao
public interface CodeSourceDao {

    @Insert
    void insertCodeSourceList(List<CodeSource> codeSourceList);

    @Query("SELECT * FROM CodeSource")
    List<CodeSource> selectAllCodeSourceList();

    @Query("DELETE FROM CodeSource")
    void deleteAllCodeSourceList();

}
