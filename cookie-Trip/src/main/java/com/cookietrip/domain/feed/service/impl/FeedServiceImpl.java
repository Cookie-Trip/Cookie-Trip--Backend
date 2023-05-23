package com.cookietrip.domain.feed.service.impl;

import com.cookietrip.domain.feed.Repository.FeedImageRepository;
import com.cookietrip.domain.feed.Repository.FeedRepository;
import com.cookietrip.domain.feed.Repository.FeedReviewRepository;
import com.cookietrip.domain.feed.dto.FeedDto;
import com.cookietrip.domain.feed.dto.FeedImageDto;
import com.cookietrip.domain.feed.dto.FeedReviewDto;
import com.cookietrip.domain.feed.dto.FeedWithReviewsDto;
import com.cookietrip.domain.feed.exception.FeedNotFoundException;
import com.cookietrip.domain.feed.model.entity.Feed;
import com.cookietrip.domain.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedServiceImpl
        implements FeedService {

    private final FeedRepository feedRepository;

    private final FeedImageRepository feedImageRepository;

    private final FeedReviewRepository feedReviewRepository;

    @Override
    public List<FeedDto> findFeedsByLocation(String searchLocation) {
        return feedRepository.findAllByLocation_Title(searchLocation)
                .stream()
                .map(feed -> FeedDto.of(feed, findAllFeedImages(feed)))
                .toList();
    }

    @Override
    public FeedWithReviewsDto getFeed(Long feedId) {
        return feedRepository.findById(feedId)
                .map(feed -> FeedWithReviewsDto.of(
                        feed,
                        findAllFeedImages(feed),
                        findAllFeedReviews(feed))
                )
                .orElseThrow(FeedNotFoundException::new);
    }

    private List<FeedImageDto> findAllFeedImages(Feed feed) {
        return feedImageRepository.findAllByFeed(feed)
                .stream()
                .map(FeedImageDto::fromEntity)
                .toList();
    }

    private List<FeedReviewDto> findAllFeedReviews(Feed feed) {
        return feedReviewRepository.findAllByFeed(feed)
                .stream()
                .map(FeedReviewDto::fromEntity)
                .toList();
    }
}