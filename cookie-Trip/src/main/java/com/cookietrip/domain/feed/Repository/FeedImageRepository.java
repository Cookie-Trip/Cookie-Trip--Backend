package com.cookietrip.domain.feed.Repository;

import com.cookietrip.domain.feed.model.entity.FeedImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedImageRepository
        extends JpaRepository<FeedImage, Long> {
}