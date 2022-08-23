package com.cnf.module_inspection.service.exception;

public class InvalidUserLoginTokenException extends Exception {
    public InvalidUserLoginTokenException(String message) {
        super(message);
    }
}
