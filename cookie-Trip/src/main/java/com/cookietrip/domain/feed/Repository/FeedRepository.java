package com.cookietrip.domain.feed.Repository;

import com.cookietrip.domain.feed.model.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository
        extends JpaRepository<Feed, Long> {
}