package com.cnf.module_inspection.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cnf.module_inspection.dao.BlobBytesDao;
import com.cnf.module_inspection.dao.dispatch.CECaseDao;
import com.cnf.module_inspection.dao.dispatch.OccInspectionDispatchDao;
import com.cnf.module_inspection.dao.infra.CodeElementDao;
import com.cnf.module_inspection.dao.infra.CodeElementGuideDao;
import com.cnf.module_inspection.dao.infra.CodeSetElementDao;
import com.cnf.module_inspection.dao.infra.CodeSourceDao;
import com.cnf.module_inspection.dao.InspectionTaskDTODao;
import com.cnf.module_inspection.dao.infra.IntensityClassDao;
import com.cnf.module_inspection.dao.infra.LoginMuniAuthPeriodDao;
import com.cnf.module_inspection.dao.infra.MunicipalityDao;
import com.cnf.module_inspection.dao.infra.OccCheckListDao;
import com.cnf.module_inspection.dao.infra.OccChecklistSpaceTypeDao;
import com.cnf.module_inspection.dao.infra.OccChecklistSpaceTypeElementDao;
import com.cnf.module_inspection.dao.OccInspectedSpaceDao;
import com.cnf.module_inspection.dao.OccInspectedSpaceElementDao;
import com.cnf.module_inspection.dao.OccInspectedSpaceElementPhotoDocDao;
import com.cnf.module_inspection.dao.dispatch.LoginDao;
import com.cnf.module_inspection.dao.dispatch.OccInspectionDao;
import com.cnf.module_inspection.dao.infra.OccLocationDescriptionDao;
import com.cnf.module_inspection.dao.infra.OccSpaceTypeDao;
import com.cnf.module_inspection.dao.PhotoDocDao;
import com.cnf.module_inspection.dao.dispatch.OccPeriodDao;
import com.cnf.module_inspection.dao.dispatch.PersonDao;
import com.cnf.module_inspection.dao.dispatch.PropertyDao;
import com.cnf.module_inspection.dao.dispatch.PropertyUnitDao;
import com.cnf.module_inspection.entity.BlobBytes;
import com.cnf.module_inspection.entity.OccInspectedSpace;
import com.cnf.module_inspection.entity.OccInspectedSpaceElement;
import com.cnf.module_inspection.entity.OccInspectedSpaceElementPhotoDoc;
import com.cnf.module_inspection.entity.PhotoDoc;
import com.cnf.module_inspection.entity.tasks.CECase;
import com.cnf.module_inspection.entity.infra.CodeElement;
import com.cnf.module_inspection.entity.infra.CodeElementGuide;
import com.cnf.module_inspection.entity.infra.CodeSetElement;
import com.cnf.module_inspection.entity.infra.CodeSource;
import com.cnf.module_inspection.entity.infra.IntensityClass;
import com.cnf.module_inspection.entity.infra.LoginMuniAuthPeriod;
import com.cnf.module_inspection.entity.infra.Municipality;
import com.cnf.module_inspection.entity.infra.OccCheckList;
import com.cnf.module_inspection.entity.infra.OccChecklistSpaceType;
import com.cnf.module_inspection.entity.infra.OccChecklistSpaceTypeElement;
import com.cnf.module_inspection.entity.tasks.Login;
import com.cnf.module_inspection.entity.tasks.OccInspection;
import com.cnf.module_inspection.entity.infra.OccLocationDescription;
import com.cnf.module_inspection.entity.infra.OccSpaceType;
import com.cnf.module_inspection.entity.tasks.OccInspectionDispatch;
import com.cnf.module_inspection.entity.tasks.OccPeriod;
import com.cnf.module_inspection.entity.tasks.Person;
import com.cnf.module_inspection.entity.tasks.Property;
import com.cnf.module_inspection.entity.tasks.PropertyUnit;
import com.cnf.module_inspection.dto.InspectionTaskDTO;

@Database(entities = {InspectionTaskDTO.class
    , OccInspection.class
    , CECase.class
    , Property.class
    , Municipality.class
    , IntensityClass.class
    , CodeSource.class
    , CodeElement.class
    , CodeSetElement.class
    , CodeElementGuide.class
    , OccCheckList.class
    , OccChecklistSpaceType.class
    , OccChecklistSpaceTypeElement.class
    , OccSpaceType.class
    , OccLocationDescription.class
    , OccInspectedSpace.class
    , OccInspectedSpaceElement.class
    , BlobBytes.class
    , PhotoDoc.class
    , OccPeriod.class
    , PropertyUnit.class
    , Login.class
    , Person.class
    , OccInspectionDispatch.class
    , OccInspectedSpaceElementPhotoDoc.class
    , LoginMuniAuthPeriod.class}
    , version = 1)
public abstract class InspectionDatabase extends RoomDatabase {

  private static final String INSPECTION_DATABASE = "INSPECTION_DATABASE";

  private static InspectionDatabase INSTANCE;

  public static InspectionDatabase getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE = Room.databaseBuilder(context.getApplicationContext(), InspectionDatabase.class, INSPECTION_DATABASE).build();
    }
    return INSTANCE;
  }

  public abstract InspectionTaskDTODao getInspectionTaskDao();

  public abstract CodeSourceDao getCodeSourceDao();

  public abstract CodeElementDao getCodeElementDao();

  public abstract CodeSetElementDao getCodeSetElementDao();

  public abstract CodeElementGuideDao getCodeElementGuideDao();

  public abstract OccCheckListDao getOccCheckListDao();

  public abstract OccChecklistSpaceTypeDao getOccChecklistSpaceTypeDao();

  public abstract OccChecklistSpaceTypeElementDao getOccChecklistSpaceTypeElementDao();

  public abstract OccSpaceTypeDao getOccSpaceTypeDao();

  public abstract OccLocationDescriptionDao getOccLocationDescriptionDao();

  public abstract OccInspectedSpaceDao getOccInspectedSpaceDao();

  public abstract OccInspectedSpaceElementDao getOccInspectedSpaceElementDao();

  public abstract BlobBytesDao getBlobBytesDao();

  public abstract PhotoDocDao getPhotoDocDao();

  public abstract OccInspectedSpaceElementPhotoDocDao getOccInspectedSpaceElementPhotoDocDao();

  public abstract IntensityClassDao getIntensityClassDao();

  public abstract LoginMuniAuthPeriodDao getLoginMuniAuthPeriodDao();

  public abstract MunicipalityDao getMunicipalityDao();

  public abstract OccInspectionDao getOccInspectionDao();

  public abstract CECaseDao getCECaseDao();

  public abstract PropertyDao getPropertyDao();

  public abstract OccPeriodDao getOccPeriodDao();

  public abstract PropertyUnitDao getPropertyUnitDao();

  public abstract LoginDao getLoginDao();

  public abstract PersonDao getPersonDao();

  public abstract OccInspectionDispatchDao getOccInspectionDispatchDao();

}
