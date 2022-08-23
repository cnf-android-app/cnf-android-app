package com.cnf.module_inspection.service.exception;

import androidx.annotation.Nullable;

public class HttpBadRequestException extends Throwable {
    public HttpBadRequestException(@Nullable String message) {
        super(message);
    }
}
