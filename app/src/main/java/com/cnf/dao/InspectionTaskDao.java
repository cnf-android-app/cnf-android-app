package com.cnf.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cnf.dto.InspectionTaskDTO;

import java.util.List;

@Dao
public interface InspectionTaskDao {

    @Insert
    void insertInspectionTasks(List<InspectionTaskDTO> inspectionTaskDTOList);

    @Query("SELECT * FROM InspectionTaskDTO")
    List<InspectionTaskDTO> selectAllInspectionTasks();

    @Query("DELETE FROM InspectionTaskDTO")
    void deleteAllInspectionTasks();

}
