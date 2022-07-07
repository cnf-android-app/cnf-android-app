package com.cnf.dao.infra;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.infra_heavy.OccChecklistSpaceTypeElementHeavy;
import com.cnf.domain.infra.OccChecklistSpaceTypeElement;

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
      "ON codelement_elementid = c2.elementid " +
      "JOIN occchecklistspacetype o " +
      "ON occchecklistspacetypeelement.checklistspacetype_typeid = o.checklistspacetypeid " +
      "WHERE checklistspacetypeid = :CSTId")
  List<OccChecklistSpaceTypeElementHeavy> selectAllOccChecklistSpaceTypeElementListDetailsByCSTId(int CSTId);

  @Query("SELECT * "
      + "FROM occchecklistspacetypeelement "
      + "WHERE checklistspacetype_typeid = :occChecklistSpaceTypeId")
  List<OccChecklistSpaceTypeElement> selectAllOccChecklistSpaceTypeElementListByCSTId(int occChecklistSpaceTypeId);

}
