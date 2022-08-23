package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.infra.CodeSetElementDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra.CodeSetElement;
import java.util.List;

public class CodeSetElementRepository {

  private CodeSetElementDao codeSetElementDao;

  private static final CodeSetElementRepository INSTANCE = new CodeSetElementRepository();

  private CodeSetElementRepository() {
  }

  public static CodeSetElementRepository getInstance(Context context) {
    INSTANCE.codeSetElementDao = InspectionDatabase.getInstance(context).getCodeSetElementDao();
    return INSTANCE;
  }

  public List<CodeSetElement> getCodeSetElementListFromSQLite() {
    return this.codeSetElementDao.selectCodeSetElementList();
  }

  public void insertCodeSetElementList(List<CodeSetElement> codeSetElementList) {
    this.codeSetElementDao.insertCodeSetElementList(codeSetElementList);
  }

  public void deleteAllCodeSetElementList() {
    this.codeSetElementDao.deleteAllCodeSetElementList();
  }

}
