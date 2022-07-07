package com.cnf.dao.infra;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.cnf.domain.infra.OccCheckList;
import java.util.List;

@Dao
public interface OccCheckListDao {

    @Insert
    void insertOccCheckListList(List<OccCheckList> OccCheckListList);

    @Query("SELECT * FROM OccCheckList")
    List<OccCheckList> selectAllOccCheckListList();

    @Query("DELETE FROM OccCheckList")
    void deleteAllOccCheckListList();

}
