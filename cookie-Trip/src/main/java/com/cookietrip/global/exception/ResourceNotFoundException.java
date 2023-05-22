package com.cookietrip.global.exception;

import javax.persistence.EntityNotFoundException;

/**
 * 존재하지 않는 자원(Entity) 예외
 */
public class ResourceNotFoundException
        extends EntityNotFoundException {
    private final ExceptionCode exceptionCode;

    public ResourceNotFoundException(
            ExceptionCode exceptionCode
    ) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}