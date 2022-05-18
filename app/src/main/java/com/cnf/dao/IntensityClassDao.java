package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.IntensityClass;

import java.util.List;

@Dao
public interface IntensityClassDao {

    @Insert
    void insertIntensityClassList(List<IntensityClass> intensityClassList);

    @Query("SELECT * FROM IntensityClass")
    List<IntensityClass> selectAllIntensityClassList();

    @Query("DELETE FROM IntensityClass")
    void deleteAllIntensityClassList();

    @Query("SELECT * FROM IntensityClass WHERE schemaname LIKE :schemaLabel")
    List<IntensityClass> selectAllIntensityClassListBySchemaLabel(String schemaLabel);

}
