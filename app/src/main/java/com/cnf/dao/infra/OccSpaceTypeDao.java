package com.cnf.dao.infra;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.infra.OccSpaceType;

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
