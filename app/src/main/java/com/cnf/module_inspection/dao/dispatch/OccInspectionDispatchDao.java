package com.cnf.module_inspection.dao.dispatch;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.cnf.module_inspection.entity.infra_heavy.OccInspectionDispatchHeavy;
import com.cnf.module_inspection.entity.tasks.OccInspectionDispatch;
import java.util.List;

@Dao
public interface OccInspectionDispatchDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertOccInspectionDispatchList(List<OccInspectionDispatch> OccInspectionDispatchList);

  @Query("SELECT * FROM OccInspectionDispatch")
  List<OccInspectionDispatch> selectOccInspectionDispatchList();

  @Update()
  void updateOccInspectionDispatch(OccInspectionDispatch occInspectionDispatch);

  @Query("DELETE FROM OccInspectionDispatch")
  void deleteAllOccInspectionDispatchList();

  //TODO HOW TO GET MUNICODE
  @Query("SELECT * FROM occinspectiondispatch "
      + "INNER JOIN occinspection o ON occinspectiondispatch.inspection_inspectionid = o.inspectionid "
      + "INNER JOIN cecase c ON o.cecase_caseid=c.caseid "
      + "INNER JOIN property p ON c.cecase_property_propertyid = p.propertyid "
      + "INNER JOIN login l ON o.inspector_userid = l.userid "
      + "INNER JOIN person p3 ON l.personlink = p3.personid "
      + "INNER JOIN occchecklist o1 ON o.occchecklist_checklistlistid = o1.checklistid "
      + "WHERE p.municipality_municode = :muniCode AND occinspectiondispatch.synchronizationts IS NOT NULL;")
  List<OccInspectionDispatchHeavy> selectSynchronizedInspectionDispatchHeavy(int muniCode);

  @Query("SELECT * FROM occinspectiondispatch "
      + "INNER JOIN occinspection o ON occinspectiondispatch.inspection_inspectionid = o.inspectionid "
      + "INNER JOIN cecase c ON o.cecase_caseid=c.caseid "
      + "INNER JOIN property p ON c.cecase_property_propertyid = p.propertyid "
      + "INNER JOIN login l ON o.inspector_userid = l.userid "
      + "INNER JOIN person p3 ON l.personlink = p3.personid "
      + "INNER JOIN occchecklist o1 ON o.occchecklist_checklistlistid = o1.checklistid "
      + "WHERE p.municipality_municode = :muniCode AND occinspectiondispatch.synchronizationts IS NULL;")
  List<OccInspectionDispatchHeavy> selectUnSynchronizeInspectionDispatchHeavy(int muniCode);
}
