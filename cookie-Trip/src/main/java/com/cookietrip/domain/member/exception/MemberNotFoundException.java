package com.cookietrip.domain.member.exception;

import com.cookietrip.global.exception.ExceptionCode;
import com.cookietrip.global.exception.ResourceNotFoundException;

import static com.cookietrip.domain.member.exception.MemberExceptionCode.MEMBER_NOT_FOUND;

public class MemberNotFoundException
        extends ResourceNotFoundException {

    private final ExceptionCode exceptionCode;

    public MemberNotFoundException() {
        super(MEMBER_NOT_FOUND);
        exceptionCode = MEMBER_NOT_FOUND;
    }

    @Override
    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}