package com.cnf.module_inspection.service.exception;

import androidx.annotation.Nullable;

public class HttpUnknownErrorException extends Throwable {
    public HttpUnknownErrorException(@Nullable String message) {
        super(message);
    }
}
