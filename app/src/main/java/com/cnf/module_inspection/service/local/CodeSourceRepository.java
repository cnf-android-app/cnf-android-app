package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.infra.CodeSourceDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra.CodeSource;
import java.util.List;

public class CodeSourceRepository {

  private CodeSourceDao codeSourceDao;

  private static final CodeSourceRepository INSTANCE = new CodeSourceRepository();

  private CodeSourceRepository() {
  }

  public static CodeSourceRepository getInstance(Context context) {
    INSTANCE.codeSourceDao = InspectionDatabase.getInstance(context).getCodeSourceDao();
    return INSTANCE;
  }

  public void insertCodeSourceList(List<CodeSource> codeSourceList) {
    this.codeSourceDao.insertCodeSourceList(codeSourceList);
  }

  public void deleteAllCodeSourceList() {
    this.codeSourceDao.deleteAllCodeSourceList();
  }

}
