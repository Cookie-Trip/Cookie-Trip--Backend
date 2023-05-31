package com.cookietrip.domain.feed.dto.response;

import com.cookietrip.domain.feed.dto.FeedDto;
import com.cookietrip.domain.feed.dto.FeedImageDto;

import java.util.List;

public record FeedResponse(
        Long feedId,
        String feedTitle,
        String placeName,
        String searchLocation,
        List<FeedImageDto> feedImages
) {
    public static FeedResponse of(FeedDto feedDto) {
        return new FeedResponse(
                feedDto.id(),
                feedDto.title(),
                feedDto.placeName(),
                feedDto.searchLocation(),
                feedDto.placeImages()
        );
    }
}