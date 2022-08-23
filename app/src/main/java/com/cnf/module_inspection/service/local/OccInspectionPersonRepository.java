package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.dispatch.PersonDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.tasks.Person;
import java.util.List;

public class OccInspectionPersonRepository {

  private PersonDao personDao;

  private static final OccInspectionPersonRepository INSTANCE = new OccInspectionPersonRepository();

  private OccInspectionPersonRepository() {
  }

  public static OccInspectionPersonRepository getInstance(Context context) {
    INSTANCE.personDao = InspectionDatabase.getInstance(context).getPersonDao();
    return INSTANCE;
  }

  public List<Person> getPersonList() {
    return this.personDao.selectPersonList();
  }

  public void insertPersonList(List<Person> personList) {
    this.personDao.insertPersonList(personList);
  }

  public void deleteAllPersonList() {
    this.personDao.deleteAllPersonList();
  }

}
