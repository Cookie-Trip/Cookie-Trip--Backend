package com.cookietrip.domain.feed.dto;

import com.cookietrip.domain.feed.model.constant.PlaceCategory;
import com.cookietrip.domain.feed.model.entity.Feed;

import java.util.List;

public record FeedDto(
        Long id,
        String title,
        String content,
        String placeName,
        String placeLocation,
        String placePhoneNumber,
        Double placeRating,
        PlaceCategory placeCategory,
        String searchLocation,
        List<FeedImageDto> placeImages
) {
    public static FeedDto of(
            Feed feed,
            List<FeedImageDto> feedImages
    ) {
        return new FeedDto(
                feed.getId(),
                feed.getTitle(),
                feed.getContent(),
                feed.getPlaceName(),
                feed.getPlaceLocation(),
                feed.getPlacePhoneNumber(),
                feed.getPlaceRating(),
                feed.getPlaceCategory(),
                feed.getLocation().getTitle(),
                feedImages
        );
    }
}