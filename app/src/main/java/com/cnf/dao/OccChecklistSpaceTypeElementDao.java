package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.OccChecklistSpaceType;
import com.cnf.domain.OccChecklistSpaceTypeElement;
import com.cnf.domain.OccChecklistSpaceTypeElementHeavyDetails;

import java.util.List;

@Dao
public interface OccChecklistSpaceTypeElementDao {

    @Insert
    void insertOccChecklistSpaceTypeElementList(List<OccChecklistSpaceTypeElement> occChecklistSpaceTypeElementList);

    @Query("SELECT * FROM OccChecklistSpaceTypeElement")
    List<OccChecklistSpaceTypeElement> selectAllOccChecklistSpaceTypeElementList();

    @Query("DELETE FROM OccChecklistSpaceTypeElement")
    void deleteAllOccChecklistSpaceTypeElementList();

    @Query("SELECT * FROM occchecklistspacetypeelement join codeelement c on occchecklistspacetypeelement.codeelement_id = c.elementid join occchecklistspacetype o on occchecklistspacetypeelement.checklistspacetype_typeid = o.checklistspacetypeid where checklistspacetypeid = :CSTId")
    List<OccChecklistSpaceTypeElementHeavyDetails> selectAllOccChecklistSpaceTypeElementListDetailsByCSTId(int CSTId);

    @Query("SELECT * FROM occchecklistspacetypeelement WHERE checklistspacetype_typeid = :CSTId")
    List<OccChecklistSpaceTypeElement> selectAllOccChecklistSpaceTypeElementListByCSTId(int CSTId);

}
