package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.OccLocationDescription;

import java.util.List;

@Dao
public interface OccLocationDescriptionDao {
    @Insert
    void insertOccLocationDescriptionList(List<OccLocationDescription> occLocationDescriptionList);

    @Query("SELECT * FROM OccLocationDescription")
    List<OccLocationDescription> selectAllOccLocationDescriptionList();

    @Query("DELETE FROM OccLocationDescription")
    void deleteAllOccLocationDescriptionList();
}
