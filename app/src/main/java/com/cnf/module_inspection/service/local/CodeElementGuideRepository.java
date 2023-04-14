package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.infra.CodeElementGuideDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.infra.CodeElementGuide;
import java.util.List;

public class CodeElementGuideRepository {

  private CodeElementGuideDao codeElementGuideDao;

  private static final CodeElementGuideRepository INSTANCE = new CodeElementGuideRepository();

  private CodeElementGuideRepository() {
  }

  public static CodeElementGuideRepository getInstance(Context context) {
    INSTANCE.codeElementGuideDao = InspectionDatabase.getInstance(context).getCodeElementGuideDao();
    return INSTANCE;
  }

  public void insertCodeElementGuideList(List<CodeElementGuide> codeElementGuideList) {
    this.codeElementGuideDao.insertCodeElementGuideList(codeElementGuideList);
  }

  public void deleteAllCodeElementGuideList() {
    this.codeElementGuideDao.deleteAllCodeElementGuideList();
  }
}
