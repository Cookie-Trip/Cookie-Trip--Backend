package com.cookietrip.domain.feed.dto.response;

import com.cookietrip.domain.feed.dto.FeedImageDto;
import com.cookietrip.domain.feed.dto.FeedReviewDto;
import com.cookietrip.domain.feed.dto.FeedWithReviewsDto;
import com.cookietrip.domain.feed.model.constant.PlaceCategory;

import java.util.List;

public record FeedWithReviewsResponse(
        Long feedId,
        String feedTitle,
        String feedContent,
        String placeName,
        String placeLocation,
        String placePhoneNumber,
        Double placeRating,
        PlaceCategory placeCategory,
        String searchLocation,
        List<FeedImageDto> placeImages,
        List<FeedReviewDto> reviews
) {
    public static FeedWithReviewsResponse of(FeedWithReviewsDto feedWithReviewsDto) {
        return new FeedWithReviewsResponse(
                feedWithReviewsDto.id(),
                feedWithReviewsDto.title(),
                feedWithReviewsDto.content(),
                feedWithReviewsDto.placeName(),
                feedWithReviewsDto.placeLocation(),
                feedWithReviewsDto.placePhoneNumber(),
                feedWithReviewsDto.placeRating(),
                feedWithReviewsDto.placeCategory(),
                feedWithReviewsDto.searchLocation(),
                feedWithReviewsDto.placeImages(),
                feedWithReviewsDto.reviews()
        );
    }
}