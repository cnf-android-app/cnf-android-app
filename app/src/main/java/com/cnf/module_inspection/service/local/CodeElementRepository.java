package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.infra.CodeElementDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra.CodeElement;
import java.util.List;

public class CodeElementRepository {

  private CodeElementDao codeElementDao;

  private static final CodeElementRepository INSTANCE = new CodeElementRepository();

  private CodeElementRepository() {
  }

  public static CodeElementRepository getInstance(Context context) {
    INSTANCE.codeElementDao = InspectionDatabase.getInstance(context).getCodeElementDao();
    return INSTANCE;
  }

  public List<CodeElement> getCodeElementListFromSQLite() {
    return this.codeElementDao.selectCodeElementList();
  }

  public void insertCodeElementList(List<CodeElement> codeElementList) {

    this.codeElementDao.insertCodeElementList(codeElementList);
  }


  public void deleteAllCodeElementList() {
    this.codeElementDao.deleteAllCodeElementList();
  }

}
