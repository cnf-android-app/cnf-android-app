package com.cnf.module_inspection.service.exception;

import androidx.annotation.Nullable;

public class InvalidOccInspectedSpaceException extends Throwable {

  public InvalidOccInspectedSpaceException(@Nullable String message, @Nullable Throwable cause) {
    super(message, cause);
  }

  public InvalidOccInspectedSpaceException() {
  }
}
