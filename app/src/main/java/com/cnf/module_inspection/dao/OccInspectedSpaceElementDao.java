package com.cnf.module_inspection.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cnf.module_inspection.entity.OccInspectedSpaceElement;
import com.cnf.module_inspection.entity.OccInspectedSpaceElementHeavy;

import java.util.List;

@Dao
public interface OccInspectedSpaceElementDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertOccInspectedSpaceElementList(List<OccInspectedSpaceElement> OccInspectedSpaceElementList);

  @Query("SELECT * FROM OccInspectedSpaceElement WHERE inspectedspace_inspectedspaceid = :inspectedSpaceId")
  List<OccInspectedSpaceElement> selectOccInspectedSpaceElementListByInspectedSpaceId(String inspectedSpaceId);

  @Query("DELETE FROM OccInspectedSpaceElement")
  void deleteAllOccInspectedSpaceElementList();

  @Delete
  void deleteOccInspectedSpaceElement(OccInspectedSpaceElement occInspectedSpaceElement);

  @Update
  void updateOccInspectedSpaceElementList(OccInspectedSpaceElement occInspectedSpaceElement);

  @Query("SELECT * "
      + "FROM occinspectedspaceelement "
      + "JOIN occchecklistspacetypeelement o "
      + "ON occinspectedspaceelement.occchecklistspacetypeelement_elementid = o.spaceelementid "
      + "JOIN codesetelement c "
      + "ON o.codesetelement_seteleid = c.codesetelementid "
      + "JOIN codeelement c2 "
      + "ON c.codelement_elementid = c2.elementid "
      + "JOIN codesource c3 "
      + "ON c2.codesource_sourceid = c3.sourceid "
      + "WHERE  inspectedspace_inspectedspaceid = :inspectedSpaceId")
  List<OccInspectedSpaceElementHeavy> selectOccInspectedSpaceElementHeavyList(String inspectedSpaceId);

//  @Query("SELECT * FROM occinspectedspaceelement "
//      + "INNER JOIN occchecklistspacetypeelement o "
//      + "ON occinspectedspaceelement.occchecklistspacetypeelement_elementid = o.spaceelementid "
//      + "INNER JOIN codesetelement c "
//      + "ON o.codesetelement_seteleid = c.codesetelementid "
//      + "INNER JOIN codeelement c2 "
//      + "ON c.codelement_elementid = c2.elementid "
//      + "INNER JOIN codesource c3 "
//      + "ON c2.codesource_sourceid = c3.sourceid "
//      + "INNER JOIN codeelementguide c4 "
//      + "ON c2.guideentryid = c4.guideentryid "
//      + "WHERE inspectedspace_inspectedspaceid = :inspectedSpaceId")
//  Map<CodeElementGuide, List<OccInspectedSpaceElementHeavy>> selectAllOccInspectedSpaceElementHeavyMap(int inspectedSpaceId);

  @Query("SELECT * "
      + "FROM occinspectedspaceelement "
      + "JOIN occchecklistspacetypeelement o "
      + "ON occinspectedspaceelement.occchecklistspacetypeelement_elementid = o.spaceelementid "
      + "JOIN codesetelement c "
      + "ON o.codesetelement_seteleid = c.codesetelementid "
      + "JOIN codeelement c2 "
      + "ON c.codelement_elementid = c2.elementid "
      + "JOIN codesource c3 "
      + "ON c2.codesource_sourceid = c3.sourceid "
      + "JOIN codeelementguide c4 "
      + "ON c2.guideentryid = c4.guideentryid "
      + "WHERE  inspectedspace_inspectedspaceid = :inspectedSpaceId "
      + "AND c2.guideentryid == :inspectedSpaceElementGuideId")
  List<OccInspectedSpaceElementHeavy> selectOccInspectedSpaceElementHeavyList(int inspectedSpaceElementGuideId, String inspectedSpaceId);

  @Query("SELECT * "
      + "FROM occinspectedspaceelement "
      + "JOIN occchecklistspacetypeelement o "
      + "ON occinspectedspaceelement.occchecklistspacetypeelement_elementid = o.spaceelementid "
      + "JOIN codesetelement c "
      + "ON o.codesetelement_seteleid = c.codesetelementid "
      + "JOIN codeelement c2 "
      + "ON c.codelement_elementid = c2.elementid "
      + "JOIN codesource c3 "
      + "ON c2.codesource_sourceid = c3.sourceid "
      + "LEFT JOIN codeelementguide c4 "
      + "ON c2.guideentryid = c4.guideentryid "
      + "WHERE  inspectedspace_inspectedspaceid = :inspectedSpaceId "
      + "AND c2.guideentryid IS NULL")
  List<OccInspectedSpaceElementHeavy> selectAllUnCategoryOccInspectedSpaceElementHeavyList(String inspectedSpaceId);
}
