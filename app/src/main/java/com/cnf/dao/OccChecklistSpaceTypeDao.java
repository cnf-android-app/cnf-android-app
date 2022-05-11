package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.OccChecklistSpaceType;
import com.cnf.domain.OccChecklistSpaceTypeHeavy;

import java.util.List;

@Dao
public interface OccChecklistSpaceTypeDao {

    @Insert
    void insertOccChecklistSpaceTypeList(List<OccChecklistSpaceType> occChecklistSpaceTypeList);

    @Query("SELECT * FROM OccChecklistSpaceType")
    List<OccChecklistSpaceType> selectAllOccChecklistSpaceTypeList();

    @Query("DELETE FROM OccChecklistSpaceType")
    void deleteAllOccChecklistSpaceTypeList();

    @Query("SELECT * FROM occchecklistspacetype JOIN occspacetype o ON occchecklistspacetype.spacetype_typeid = o.spacetypeid")
    List<OccChecklistSpaceTypeHeavy> selectAllOccChecklistSpaceTypeHeavyList();

    @Query("SELECT * FROM occchecklistspacetype JOIN occspacetype o ON occchecklistspacetype.spacetype_typeid = o.spacetypeid where checklistspacetypeid = :CSTId")
    OccChecklistSpaceTypeHeavy selectOccChecklistSpaceTypeHeavy(int CSTId);

}
