package com.cookietrip.domain.feed.Repository;

import com.cookietrip.domain.feed.model.entity.Feed;
import com.cookietrip.domain.feed.model.entity.FeedImage;

import java.util.List;

public interface FeedImageRepository {

    List<FeedImage> findAllByFeed(Feed feed);
}