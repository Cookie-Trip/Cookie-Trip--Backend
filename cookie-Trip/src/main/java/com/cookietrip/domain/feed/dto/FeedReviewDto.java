package com.cookietrip.domain.feed.dto;

import com.cookietrip.domain.feed.model.entity.FeedReview;

public record FeedReviewDto(
        String content
) {
    public static FeedReviewDto fromEntity(FeedReview feedReview) {
        return new FeedReviewDto(feedReview.getContent());
    }
}