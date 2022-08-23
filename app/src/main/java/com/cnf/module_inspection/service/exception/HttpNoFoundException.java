package com.cnf.module_inspection.service.exception;

import androidx.annotation.Nullable;

public class HttpNoFoundException extends Throwable {
    public HttpNoFoundException(@Nullable String message) {
        super(message);
    }
}
