package com.cookietrip.domain.feed.service;

import com.cookietrip.domain.feed.dto.FeedDto;
import com.cookietrip.domain.feed.dto.FeedWithReviewsDto;

import java.util.List;

public interface FeedService {

    /**
     * 특정 지역의 피드를 모두 DB 에서 조회
     */
    List<FeedDto> findFeedsByLocation(String searchLocation);

    /**
     * 특정 피드를 리뷰와 함께 조회
     */
    FeedWithReviewsDto getFeed(Long feedId);
}