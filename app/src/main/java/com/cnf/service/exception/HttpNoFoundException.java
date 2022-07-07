package com.cnf.service.exception;

import androidx.annotation.Nullable;

public class HttpNoFoundException extends Throwable {
    public HttpNoFoundException(@Nullable String message) {
        super(message);
    }
}
