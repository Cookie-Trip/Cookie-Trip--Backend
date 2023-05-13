package com.cookietrip.domain.member.exception;

import com.cookietrip.global.exception.ExceptionCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum MemberExceptionCode
        implements ExceptionCode {
    MEMBER_NOT_FOUND(NOT_FOUND, "MB-C-001", "존재하지 않는 회원입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    MemberExceptionCode(
            HttpStatus httpStatus,
            String code,
            String message
    ) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}