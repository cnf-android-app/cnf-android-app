package com.cnf.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cnf.dao.BlobBytesDao;
import com.cnf.dao.CodeElementDao;
import com.cnf.dao.CodeElementGuideDao;
import com.cnf.dao.CodeSourceDao;
import com.cnf.dao.InspectionTaskDao;
import com.cnf.dao.IntensityClassDao;
import com.cnf.dao.OccChecklistSpaceTypeDao;
import com.cnf.dao.OccChecklistSpaceTypeElementDao;
import com.cnf.dao.OccInspectedSpaceDao;
import com.cnf.dao.OccInspectedSpaceElementDao;
import com.cnf.dao.OccInspectedSpaceElementPhotoDocDao;
import com.cnf.dao.OccLocationDescriptionDao;
import com.cnf.dao.OccSpaceTypeDao;
import com.cnf.dao.PhotoDocDao;
import com.cnf.domain.BlobBytes;
import com.cnf.domain.CodeElement;
import com.cnf.domain.CodeElementGuide;
import com.cnf.domain.CodeSource;
import com.cnf.domain.IntensityClass;
import com.cnf.domain.OccChecklistSpaceType;
import com.cnf.domain.OccChecklistSpaceTypeElement;
import com.cnf.domain.OccInspectedSpace;
import com.cnf.domain.OccInspectedSpaceElement;
import com.cnf.domain.OccInspectedSpaceElementPhotoDoc;
import com.cnf.domain.OccLocationDescription;
import com.cnf.domain.OccSpaceType;
import com.cnf.domain.PhotoDoc;
import com.cnf.dto.InspectionTaskDTO;

@Database(entities = {InspectionTaskDTO.class, IntensityClass.class, CodeSource.class, CodeElement.class, CodeElementGuide.class, OccChecklistSpaceType.class, OccChecklistSpaceTypeElement.class, OccSpaceType.class, OccLocationDescription.class, OccInspectedSpace.class, OccInspectedSpaceElement.class, BlobBytes.class, PhotoDoc.class, OccInspectedSpaceElementPhotoDoc.class}, version = 1, exportSchema = true)
public abstract class InspectionDatabase extends RoomDatabase {
    public abstract InspectionTaskDao getInspectionTaskDao();

    public abstract CodeSourceDao getCodeSourceDao();

    public abstract CodeElementDao getCodeElementDao();

    public abstract CodeElementGuideDao getCodeElementGuideDao();

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

}
