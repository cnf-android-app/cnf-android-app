package com.cnf.module_inspection.dao.infra;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.cnf.module_inspection.entity.infra.CodeSetElement;
import java.util.List;

@Dao
public interface CodeSetElementDao {

    @Insert
    void insertCodeSetElementList(List<CodeSetElement> CodeSetElementList);

    @Query("SELECT * FROM CodeSetElement")
    List<CodeSetElement> selectCodeSetElementList();

    @Query("DELETE FROM CodeSetElement")
    void deleteAllCodeSetElementList();

}
