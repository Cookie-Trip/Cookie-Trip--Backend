package com.cookietrip.domain.feed.Repository;

import com.cookietrip.domain.feed.model.entity.FeedReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedReviewRepository
        extends JpaRepository<FeedReview, Long> {
}