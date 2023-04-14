package com.cnf.module_inspection.dao.infra;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.module_inspection.entity.infra_heavy.OccChecklistSpaceTypeElementHeavy;
import com.cnf.module_inspection.entity.infra.OccChecklistSpaceTypeElement;

import java.util.List;

@Dao
public interface OccChecklistSpaceTypeElementDao {

  @Insert
  void insertOccChecklistSpaceTypeElementList(List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList);

  @Query("SELECT * FROM OccChecklistSpaceTypeElement")
  List<OccChecklistSpaceTypeElement> selectAllOccChecklistSpaceTypeElementList();

  @Query("DELETE FROM OccChecklistSpaceTypeElement")
  void deleteAllOccChecklistSpaceTypeElementList();

  @Query("SELECT * FROM occchecklistspacetypeelement " +
      "JOIN codesetelement c " +
      "ON occchecklistspacetypeelement.codesetelement_seteleid = c.codesetelementid " +
      "JOIN codeelement c2 " +
      "ON codelement_elementid = c2.elementid "
      + "LEFT JOIN codeelementguide c3 ON c2.guideentryid = c3.guideentryid " +
      "JOIN occchecklistspacetype o " +
      "ON occchecklistspacetypeelement.checklistspacetype_typeid = o.checklistspacetypeid " +
      "WHERE checklistspacetypeid = :CSTId")
  List<OccChecklistSpaceTypeElementHeavy> selectAllOccChecklistSpaceTypeElementListDetailsByCSTId(int CSTId);

  @Query("SELECT * "
      + "FROM occchecklistspacetypeelement "
      + "INNER JOIN codesetelement c ON occchecklistspacetypeelement.codesetelement_seteleid = c.codesetelementid "
      + "INNER JOIN codeelement c1 ON  c.codelement_elementid = c1.elementid "
      + "LEFT JOIN codeelementguide c2 ON c1.guideentryid = c2.guideentryid "
      + "WHERE checklistspacetype_typeid = :occChecklistSpaceTypeId")
  List<OccChecklistSpaceTypeElement> selectOccChecklistSpaceTypeElementListByCSTId(int occChecklistSpaceTypeId);

}
