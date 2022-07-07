package com.cnf.service.exception;

public class InvalidUserLoginTokenException extends Exception {
    public InvalidUserLoginTokenException(String message) {
        super(message);
    }
}
