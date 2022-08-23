package com.cnf.module_inspection.service.exception;

import androidx.annotation.Nullable;

public class OccInspectionInfraEmptyException extends Throwable {

  public OccInspectionInfraEmptyException(@Nullable String message) {
    super(message);
  }
}
