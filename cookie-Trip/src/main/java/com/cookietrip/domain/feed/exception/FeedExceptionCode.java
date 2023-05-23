package com.cookietrip.domain.feed.exception;

import com.cookietrip.global.exception.ExceptionCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum FeedExceptionCode
        implements ExceptionCode {

    FEED_NOT_FOUND_EXCEPTION(NOT_FOUND, "FD-C-001", "존재하지 않는 피드입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    FeedExceptionCode(
            HttpStatus httpStatus,
            String code,
            String message
    ) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}