package com.cookietrip.domain.auth.exception;

import io.jsonwebtoken.Claims;

public class TokenException
        extends RuntimeException {

    private final AuthExceptionCode authExceptionCode;
    private Claims expiredTokenClaims;

    public TokenException(AuthExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.authExceptionCode = exceptionCode;
    }

    public TokenException(
            AuthExceptionCode exceptionCode,
            Claims expiredTokenClaims
    ) {
        super(exceptionCode.getMessage());
        this.authExceptionCode = exceptionCode;
        this.expiredTokenClaims = expiredTokenClaims;
    }

    public AuthExceptionCode getAuthExceptionCode() {
        return authExceptionCode;
    }
    public Claims getExpiredTokenClaims() {
        return expiredTokenClaims;
    }
}