package com.cookietrip.domain.auth.exception;

import com.cookietrip.global.exception.ExceptionCode;

import static com.cookietrip.domain.auth.exception.AuthExceptionCode.INVALID_REDIRECT_URI;

public class InvalidRedirectUriException
        extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public InvalidRedirectUriException() {
        super(INVALID_REDIRECT_URI.getMessage());
        this.exceptionCode = INVALID_REDIRECT_URI;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}