package com.cnf.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cnf.dao.BlobBytesDao;
import com.cnf.dao.dispatch.CECaseDao;
import com.cnf.dao.dispatch.OccInspectionDispatchDao;
import com.cnf.dao.infra.CodeElementDao;
import com.cnf.dao.infra.CodeElementGuideDao;
import com.cnf.dao.infra.CodeSetElementDao;
import com.cnf.dao.infra.CodeSourceDao;
import com.cnf.dao.InspectionTaskDTODao;
import com.cnf.dao.infra.IntensityClassDao;
import com.cnf.dao.infra.LoginMuniAuthPeriodDao;
import com.cnf.dao.infra.MunicipalityDao;
import com.cnf.dao.infra.OccCheckListDao;
import com.cnf.dao.infra.OccChecklistSpaceTypeDao;
import com.cnf.dao.infra.OccChecklistSpaceTypeElementDao;
import com.cnf.dao.OccInspectedSpaceDao;
import com.cnf.dao.OccInspectedSpaceElementDao;
import com.cnf.dao.OccInspectedSpaceElementPhotoDocDao;
import com.cnf.dao.dispatch.LoginDao;
import com.cnf.dao.dispatch.OccInspectionDao;
import com.cnf.dao.infra.OccLocationDescriptionDao;
import com.cnf.dao.infra.OccSpaceTypeDao;
import com.cnf.dao.PhotoDocDao;
import com.cnf.dao.dispatch.OccPeriodDao;
import com.cnf.dao.dispatch.PersonDao;
import com.cnf.dao.dispatch.PropertyDao;
import com.cnf.dao.dispatch.PropertyUnitDao;
import com.cnf.domain.BlobBytes;
import com.cnf.domain.OccInspectedSpace;
import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.domain.OccInspectedSpaceElementPhotoDoc;
import com.cnf.domain.PhotoDoc;
import com.cnf.domain.tasks.CECase;
import com.cnf.domain.infra.CodeElement;
import com.cnf.domain.infra.CodeElementGuide;
import com.cnf.domain.infra.CodeSetElement;
import com.cnf.domain.infra.CodeSource;
import com.cnf.domain.infra.IntensityClass;
import com.cnf.domain.infra.LoginMuniAuthPeriod;
import com.cnf.domain.infra.Municipality;
import com.cnf.domain.infra.OccCheckList;
import com.cnf.domain.infra.OccChecklistSpaceType;
import com.cnf.domain.infra.OccChecklistSpaceTypeElement;
import com.cnf.domain.tasks.Login;
import com.cnf.domain.tasks.OccInspection;
import com.cnf.domain.infra.OccLocationDescription;
import com.cnf.domain.infra.OccSpaceType;
import com.cnf.domain.tasks.OccInspectionDispatch;
import com.cnf.domain.tasks.OccPeriod;
import com.cnf.domain.tasks.Person;
import com.cnf.domain.tasks.Property;
import com.cnf.domain.tasks.PropertyUnit;
import com.cnf.dto.InspectionTaskDTO;

@Database(entities = {InspectionTaskDTO.class, OccInspection.class, CECase.class, Property.class
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
    , OccPeriod.class, PropertyUnit.class, Login.class, Person.class, OccInspectionDispatch.class
    , OccInspectedSpaceElementPhotoDoc.class
    , LoginMuniAuthPeriod.class}
    , version = 1, exportSchema = true)
public abstract class InspectionDatabase extends RoomDatabase {

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
