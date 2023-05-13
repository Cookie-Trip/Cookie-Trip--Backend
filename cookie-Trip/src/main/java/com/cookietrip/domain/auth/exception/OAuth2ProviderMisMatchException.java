package com.cookietrip.domain.auth.exception;

import com.cookietrip.global.exception.ExceptionCode;

import static com.cookietrip.domain.auth.exception.AuthExceptionCode.*;

public class OAuth2ProviderMisMatchException
        extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public OAuth2ProviderMisMatchException(String loginProvider) {
        super(LOGIN_PROVIDER_MISMATCH.getMessage() + "이미 " + loginProvider + "계정으로 가입되어 있습니다.");
        exceptionCode = LOGIN_PROVIDER_MISMATCH;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}