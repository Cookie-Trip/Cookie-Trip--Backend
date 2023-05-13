package com.cookietrip.domain.member.exception;

import com.cookietrip.global.exception.ExceptionCode;
import com.cookietrip.global.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {

    /**
     * MemberNotFoundException handling (Custom Exception)
     */
    @ExceptionHandler(MemberNotFoundException.class)
    protected ResponseEntity<ExceptionResponse> handleMemberNotFoundException(
            MemberNotFoundException e
    ) {
        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode, exceptionCode.getMessage()),
                HttpStatus.valueOf(exceptionCode.getHttpStatus().value())
        );
    }
}