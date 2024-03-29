package com.cnf.module_inspection.dao.infra;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.module_inspection.entity.infra.CodeElementGuide;

import java.util.List;

@Dao
public interface CodeElementGuideDao {

    @Insert
    void insertCodeElementGuideList(List<CodeElementGuide> CodeElementGuideList);

    @Query("SELECT * FROM CodeElementGuide")
    List<CodeElementGuide> selectCodeElementGuideList();

    @Query("DELETE FROM CodeElementGuide")
    void deleteAllCodeElementGuideList();

}
