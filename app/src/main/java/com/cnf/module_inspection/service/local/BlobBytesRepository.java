package com.cnf.module_inspection.service.local;

import android.content.Context;
import com.cnf.module_inspection.dao.BlobBytesDao;
import com.cnf.module_inspection.db.InspectionDatabase;
import com.cnf.module_inspection.entity.BlobBytes;
import java.util.List;

public class BlobBytesRepository {

  private BlobBytesDao blobBytesDao;

  private static final BlobBytesRepository INSTANCE = new BlobBytesRepository();

  private BlobBytesRepository() {
  }

  public static BlobBytesRepository getInstance(Context context) {
    INSTANCE.blobBytesDao = InspectionDatabase.getInstance(context).getBlobBytesDao();
    return INSTANCE;
  }

  public void deleteBlobByte(BlobBytes blobBytes) {
    this.blobBytesDao.deleteBlobByte(blobBytes);
  }

  public void insertBlobByteList(List<BlobBytes> blobBytesList) {
    this.blobBytesDao.insertBlobByteList(blobBytesList);
  }

  public long insertBlobBytes(BlobBytes blobBytes) {
    return this.blobBytesDao.insertBlobByteList(blobBytes);
  }

  public List<BlobBytes> getInspectedPhotoBlobBytesList(String inspectedSpaceElementId) {
    return this.blobBytesDao.selectBlobByteListByInspectedElementId(inspectedSpaceElementId);
  }

  public BlobBytes getBlobByte(String byteId) {
    return this.blobBytesDao.selectBlobByteById(byteId);
  }
}
