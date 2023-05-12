package com.cookietrip.domain.auth.exception;

import com.cookietrip.global.exception.ExceptionCode;
import com.cookietrip.global.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler {

    /**
     * TokenException handling (Custom Exception)
     */
    @ExceptionHandler(TokenException.class)
    protected ResponseEntity<ExceptionResponse> handleTokenException(
            TokenException e
    ) {
        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode, exceptionCode.getMessage()),
                HttpStatus.valueOf(exceptionCode.getHttpStatus().value())
        );
    }

    /**
     * InvalidLoginProviderException handling (Custom Exception)
     */
    @ExceptionHandler(InvalidLoginProviderException.class)
    protected ResponseEntity<ExceptionResponse> handleInvalidLoginProviderException(
            InvalidLoginProviderException e
    ) {
        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode, exceptionCode.getMessage()),
                HttpStatus.valueOf(exceptionCode.getHttpStatus().value())
        );
    }
}