package com.cookietrip.domain.auth.exception;

import com.cookietrip.global.exception.ExceptionCode;

import static com.cookietrip.domain.auth.exception.AuthExceptionCode.*;

public class InvalidLoginProviderException
        extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public InvalidLoginProviderException() {
        super(INVALID_LOGIN_PROVIDER.getMessage());
        exceptionCode = INVALID_LOGIN_PROVIDER;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}