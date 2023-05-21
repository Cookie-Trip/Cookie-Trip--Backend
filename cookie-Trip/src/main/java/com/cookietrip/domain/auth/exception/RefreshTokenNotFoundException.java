package com.cookietrip.domain.auth.exception;

import com.cookietrip.global.exception.ExceptionCode;
import com.cookietrip.global.exception.ResourceNotFoundException;

import static com.cookietrip.domain.auth.exception.AuthExceptionCode.INVALID_REFRESH_TOKEN;

public class RefreshTokenNotFoundException
        extends ResourceNotFoundException {

    private final ExceptionCode exceptionCode;

    public RefreshTokenNotFoundException() {
        super(INVALID_REFRESH_TOKEN);
        exceptionCode = INVALID_REFRESH_TOKEN;
    }

    @Override
    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}