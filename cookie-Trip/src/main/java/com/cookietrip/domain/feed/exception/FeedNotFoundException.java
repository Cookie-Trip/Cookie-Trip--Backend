package com.cookietrip.domain.feed.exception;

import com.cookietrip.global.exception.ExceptionCode;
import com.cookietrip.global.exception.ResourceNotFoundException;

import static com.cookietrip.domain.feed.exception.FeedExceptionCode.FEED_NOT_FOUND_EXCEPTION;

public class FeedNotFoundException
        extends ResourceNotFoundException {

    public FeedNotFoundException() {
        super(FEED_NOT_FOUND_EXCEPTION);
    }

    @Override
    public ExceptionCode getExceptionCode() {
        return super.getExceptionCode();
    }
}