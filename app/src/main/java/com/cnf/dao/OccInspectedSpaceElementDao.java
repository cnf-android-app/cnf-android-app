package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.domain.OccInspectedSpaceElementHeavy;

import java.util.List;

@Dao
public interface OccInspectedSpaceElementDao {

    @Insert
    void insertOccInspectedSpaceElementList(List<OccInspectedSpaceElement> OccInspectedSpaceElementList);

    @Query("SELECT * FROM OccInspectedSpaceElement WHERE inspectedspace_inspectedspaceid = :inspectedSpaceId")
    List<OccInspectedSpaceElement> selectAllOccInspectedSpaceElementList(int inspectedSpaceId);

    @Query("DELETE FROM OccInspectedSpaceElement")
    void deleteAllOccInspectedSpaceElementList();

    @Query("select * from occinspectedspaceelement join occchecklistspacetypeelement o on occinspectedspaceelement.occchecklistspacetypeelement_elementid = o.spaceelementid join codeelement c on o.codeelement_id = c.elementid where inspectedspace_inspectedspaceid = :inspectedSpaceId")
    List<OccInspectedSpaceElementHeavy> selectAllOccInspectedSpaceElementHeavyList(int inspectedSpaceId);
}
