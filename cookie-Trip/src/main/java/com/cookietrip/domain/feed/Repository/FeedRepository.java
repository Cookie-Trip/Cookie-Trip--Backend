package com.cookietrip.domain.feed.Repository;

import com.cookietrip.domain.feed.model.entity.Feed;

import java.util.List;
import java.util.Optional;

public interface FeedRepository {
    List<Feed> findAllByLocation_Title(String placeLocation);

    Optional<Feed> findById(Long feedId);
}