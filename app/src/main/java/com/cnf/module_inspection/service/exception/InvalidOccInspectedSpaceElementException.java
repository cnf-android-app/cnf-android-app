package com.cnf.module_inspection.service.exception;

import androidx.annotation.Nullable;

public class InvalidOccInspectedSpaceElementException extends Throwable {

  public InvalidOccInspectedSpaceElementException(@Nullable String message, @Nullable Throwable cause) {
    super(message, cause);
  }

  public InvalidOccInspectedSpaceElementException() {
  }
}
