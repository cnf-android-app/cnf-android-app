package com.cnf.module_inspection.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cnf.module_inspection.dto.InspectionTaskDTO;

import java.util.List;

@Dao
public interface InspectionTaskDTODao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertInspectionTaskDTOList(List<InspectionTaskDTO> inspectionTaskDTOList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInspectionTaskDTOListReplaceAll(List<InspectionTaskDTO> inspectionTaskDTOList);

    @Query("SELECT * FROM InspectionTaskDTO where uploaded = 0 and periodId = :authPeriodId")
    List<InspectionTaskDTO> selectAllUnFinishInspectionTaskDTOList(int authPeriodId);

    @Query("SELECT * FROM InspectionTaskDTO where uploaded = 1 and periodId = :authPeriodId")
    List<InspectionTaskDTO> selectAllFinishedInspectionTaskDTOList(int authPeriodId);


    @Query("DELETE FROM InspectionTaskDTO")
    void deleteAllInspectionTaskDTOList();

    @Update
    int updateInspectionTaskDTO(InspectionTaskDTO inspectionTaskDTO);

}
