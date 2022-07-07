package com.cnf.service.exception;

import androidx.annotation.Nullable;

public class HttpServerErrorException extends Throwable {
    public HttpServerErrorException(@Nullable String message) {
        super(message);
    }
}
