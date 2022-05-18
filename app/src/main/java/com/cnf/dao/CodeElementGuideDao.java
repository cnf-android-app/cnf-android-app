package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.CodeElementGuide;

import java.util.List;

@Dao
public interface CodeElementGuideDao {

    @Insert
    void insertCodeElementGuides(List<CodeElementGuide> CodeElementGuideList);

    @Query("SELECT * FROM CodeElementGuide")
    List<CodeElementGuide> selectCodeElementGuides();

    @Query("DELETE FROM CodeElementGuide")
    void deleteAllCodeElementGuides();

}
