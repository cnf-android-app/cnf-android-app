package com.cnf.service.exception;

import androidx.annotation.Nullable;

public class HttpBadRequestException extends Throwable {
    public HttpBadRequestException(@Nullable String message) {
        super(message);
    }
}
