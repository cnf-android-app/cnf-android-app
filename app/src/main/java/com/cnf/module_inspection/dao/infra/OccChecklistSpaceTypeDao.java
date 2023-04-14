package com.cnf.module_inspection.dao.infra;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.module_inspection.entity.infra_heavy.OccChecklistSpaceTypeHeavy;
import com.cnf.module_inspection.entity.infra.OccChecklistSpaceType;

import java.util.List;

@Dao
public interface OccChecklistSpaceTypeDao {

  @Insert
  void insertOccChecklistSpaceTypeList(List<OccChecklistSpaceType> occChecklistSpaceTypeList);

  @Query("SELECT * FROM OccChecklistSpaceType")
  List<OccChecklistSpaceType> selectAllOccChecklistSpaceTypeList();

  @Query("DELETE FROM OccChecklistSpaceType")
  void deleteAllOccChecklistSpaceTypeList();

  @Query("SELECT * "
      + "FROM occchecklistspacetype "
      + "INNER JOIN occchecklist o "
      + "ON occchecklistspacetype.checklist_id = o.checklistid "
      + "INNER JOIN occspacetype o1 "
      + "ON occchecklistspacetype.spacetype_typeid = o1.spacetypeid "
      + "WHERE o.checklistid = :occCheckListId")
  List<OccChecklistSpaceTypeHeavy> selectAllOccChecklistSpaceTypeHeavyList(int occCheckListId);

  @Query("SELECT * FROM OccChecklistSpaceType WHERE checklist_id = :occCheckListId AND required = 1")
  List<OccChecklistSpaceType> selectAllOccChecklistSpaceTypeList(int occCheckListId);

//    @Query("SELECT * "
//        + "FROM occchecklistspacetype "
//        + "INNER JOIN occchecklist o "
//        + "ON occchecklistspacetype.checklist_id = o.checklistid "
//        + "JOIN occspacetype o1 "
//        + "ON occchecklistspacetype.spacetype_typeid = o1.spacetypeid where checklistspacetypeid = :CSTId")
//    OccChecklistSpaceTypeHeavy selectOccChecklistSpaceTypeHeavy(int CSTId);

}
