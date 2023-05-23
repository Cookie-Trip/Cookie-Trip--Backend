package com.cookietrip.domain.feed.dto;

import com.cookietrip.domain.feed.model.constant.PlaceCategory;
import com.cookietrip.domain.feed.model.entity.Feed;

import java.util.List;

public record FeedWithReviewsDto(
        Long id,
        String title,
        String content,
        String placeName,
        String placeLocation,
        String placePhoneNumber,
        Double placeRating,
        PlaceCategory placeCategory,
        String searchLocation,
        List<FeedImageDto> placeImages,
        List<FeedReviewDto> reviews
) {
    public static FeedWithReviewsDto of(
            Feed feed,
            List<FeedImageDto> feedImages,
            List<FeedReviewDto> reviews
    ) {
        return new FeedWithReviewsDto(
                feed.getId(),
                feed.getTitle(),
                feed.getContent(),
                feed.getPlaceName(),
                feed.getPlaceLocation(),
                feed.getPlacePhoneNumber(),
                feed.getPlaceRating(),
                feed.getPlaceCategory(),
                feed.getLocation().getTitle(),
                feedImages,
                reviews
        );
    }
}