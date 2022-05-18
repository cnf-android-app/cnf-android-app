package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cnf.domain.CodeElementGuide;
import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.domain.OccInspectedSpaceElementHeavy;

import java.util.List;
import java.util.Map;

@Dao
public interface OccInspectedSpaceElementDao {

    @Insert
    void insertOccInspectedSpaceElementList(List<OccInspectedSpaceElement> OccInspectedSpaceElementList);

    @Query("SELECT * FROM OccInspectedSpaceElement WHERE inspectedspace_inspectedspaceid = :inspectedSpaceId")
    List<OccInspectedSpaceElement> selectAllOccInspectedSpaceElementList(int inspectedSpaceId);

    @Query("DELETE FROM OccInspectedSpaceElement")
    void deleteAllOccInspectedSpaceElementList();

    @Update
    void updateOccInspectedSpaceElementList(OccInspectedSpaceElement occInspectedSpaceElement);

    @Query("select * from occinspectedspaceelement " +
            "join occchecklistspacetypeelement o " +
            "on occinspectedspaceelement.occchecklistspacetypeelement_elementid = o.spaceelementid " +
            "join codeelement c " +
            "on o.codeelement_id = c.elementid " +
            "where inspectedspace_inspectedspaceid = :inspectedSpaceId")
    List<OccInspectedSpaceElementHeavy> selectAllOccInspectedSpaceElementHeavyList(int inspectedSpaceId);

    @Query("select * from occinspectedspaceelement " +
            "join occchecklistspacetypeelement o " +
            "on occinspectedspaceelement.occchecklistspacetypeelement_elementid = o.spaceelementid " +
            "join codeelement c " +
            "on o.codeelement_id = c.elementid " +
            "join codeelementguide g " +
            "on c.guideentryid = g.guideentryid " +
            "where inspectedspace_inspectedspaceid = :inspectedSpaceId")
    Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> selectAllOccInspectedSpaceElementHeavyMap(int inspectedSpaceId);


    @Query("select * from occinspectedspaceelement " +
            "join occchecklistspacetypeelement o " +
            "on occinspectedspaceelement.occchecklistspacetypeelement_elementid = o.spaceelementid " +
            "join codeelement c " +
            "on o.codeelement_id = c.elementid " +
            "join codeelementguide g " +
            "on c.guideentryid = g.guideentryid " +
            "join codesource c2 on c. codesource_sourceid = c2.sourceid " +
            "where inspectedspace_inspectedspaceid = :inspectedSpaceId and g.guideentryid == :inspectedSpaceElementGuideId")
    List<OccInspectedSpaceElementHeavy> selectAllOccInspectedSpaceElementHeavyList(int inspectedSpaceElementGuideId, int inspectedSpaceId);





}
