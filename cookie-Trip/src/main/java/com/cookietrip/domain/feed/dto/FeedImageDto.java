package com.cookietrip.domain.feed.dto;

import com.cookietrip.domain.feed.model.entity.FeedImage;

public record FeedImageDto(
        String imageUrl
) {
    public static FeedImageDto fromEntity(
            FeedImage feedImage
    ) {
        return new FeedImageDto(feedImage.getImageUrl());
    }
}