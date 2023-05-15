package com.cookietrip.domain.auth.exception;

import com.cookietrip.global.exception.ExceptionCode;

import static com.cookietrip.domain.auth.exception.AuthExceptionCode.INVALID_REFRESH_TOKEN;

public class InvalidRedirectUriException
        extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public InvalidRedirectUriException() {
        super(INVALID_REFRESH_TOKEN.getMessage());
        this.exceptionCode = INVALID_REFRESH_TOKEN;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}