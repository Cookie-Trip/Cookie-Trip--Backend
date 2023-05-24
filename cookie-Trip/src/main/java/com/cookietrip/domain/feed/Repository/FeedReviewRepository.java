package com.cookietrip.domain.feed.Repository;

import com.cookietrip.domain.feed.model.entity.Feed;
import com.cookietrip.domain.feed.model.entity.FeedReview;

import java.util.List;

public interface FeedReviewRepository {
    List<FeedReview> findAllByFeed(Feed feed);
}