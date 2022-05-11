package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.OccChecklistSpaceTypeHeavy;
import com.cnf.domain.OccInspectedSpace;
import com.cnf.domain.OccInspectedSpaceHeavy;

import java.util.List;

@Dao
public interface OccInspectedSpaceDao {

    @Insert
    void insertInspectedSpace(OccInspectedSpace occInspectedSpace);

    @Insert
    void insertOccInspectedSpaceList(List<OccInspectedSpace> OccInspectedSpaceList);

    @Query("SELECT * FROM OccInspectedSpace")
    List<OccInspectedSpace> selectAllOccInspectedSpaceList();


    @Query("DELETE FROM OccInspectedSpace")
    void deleteAllOccInspectedSpaceList();

    @Query("select * from occinspectedspace join occchecklistspacetype o on occinspectedspace.occchecklistspacetype_chklstspctypid = o.checklistspacetypeid join occspacetype o2 on o.spacetype_typeid = o2.spacetypeid")
    List<OccInspectedSpaceHeavy> selectAllOccInspectedSpaceHeavyList();


    @Query("SELECT * FROM OccInspectedSpace WHERE inspectedspaceid = :inspectedSpaceId")
    OccInspectedSpace selectOneOccInspectedSpaceById(int inspectedSpaceId);

    @Query("select * from occinspectedspace join occchecklistspacetype o on occinspectedspace.occchecklistspacetype_chklstspctypid = o.checklistspacetypeid join occspacetype o2 on o.spacetype_typeid = o2.spacetypeid where occinspection_inspectionid = :inspectionId")
    List<OccInspectedSpaceHeavy> selectAllOccInspectedSpaceHeavyListByInspectionId(int inspectionId);



}
