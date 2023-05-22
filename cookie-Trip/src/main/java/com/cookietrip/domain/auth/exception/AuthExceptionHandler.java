package com.cookietrip.domain.auth.exception;

import com.cookietrip.global.exception.ExceptionCode;
import com.cookietrip.global.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cookietrip.domain.auth.exception.AuthExceptionCode.INTERNAL_AUTHENTICATION_SERVICE_EXCEPTION;

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

    /**
     * OAuth2ProviderMisMatchException handling (Custom Exception)
     */
    @ExceptionHandler(OAuth2ProviderMisMatchException.class)
    protected ResponseEntity<ExceptionResponse> handleOAuth2ProviderMisMatchException(
            OAuth2ProviderMisMatchException e
    ) {
        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode, exceptionCode.getMessage()),
                HttpStatus.valueOf(exceptionCode.getHttpStatus().value())
        );
    }

    /**
     * InternalAuthenticationServiceException handling (Built-In Exception)
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    protected ResponseEntity<ExceptionResponse> handleInternalAuthenticationServiceException(
            InternalAuthenticationServiceException e
    ) {
        ExceptionCode exceptionCode = INTERNAL_AUTHENTICATION_SERVICE_EXCEPTION;
        log.error("{}", e.getMessage());
        return new ResponseEntity<>(
                ExceptionResponse.of(exceptionCode, exceptionCode.getMessage()),
                HttpStatus.valueOf(exceptionCode.getHttpStatus().value())
        );
    }
}