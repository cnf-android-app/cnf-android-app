package com.cnf.module_inspection.dao.infra;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.module_inspection.entity.infra.OccSpaceType;

import java.util.List;

@Dao
public interface OccSpaceTypeDao {

    @Insert
    void insertOccSpaceTypeList(List<OccSpaceType> occSpaceTypeList);

    @Query("SELECT * FROM OccSpaceType")
    List<OccSpaceType> selectAllOccSpaceTypeList();

    @Query("DELETE FROM OccSpaceType")
    void deleteAllOccSpaceTypeList();

}
