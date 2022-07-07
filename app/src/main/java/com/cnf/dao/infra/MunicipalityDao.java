package com.cnf.dao.infra;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.infra.Municipality;

import java.util.List;


@Dao
public interface MunicipalityDao {

    @Insert
    void insertMunicipalityList(List<Municipality> municipalityList);

    @Query("SELECT * FROM Municipality")
    List<Municipality> selectMunicipalityList();

    @Query("DELETE FROM Municipality")
    void deleteAllMunicipalityList();


}
