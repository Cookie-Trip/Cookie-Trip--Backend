package com.cookietrip.domain.feed.service;

import com.cookietrip.domain.feed.Repository.FeedImageRepository;
import com.cookietrip.domain.feed.Repository.FeedRepository;
import com.cookietrip.domain.feed.Repository.FeedReviewRepository;
import com.cookietrip.domain.feed.dto.FeedDto;
import com.cookietrip.domain.feed.dto.FeedWithReviewsDto;
import com.cookietrip.domain.feed.exception.FeedNotFoundException;
import com.cookietrip.domain.feed.model.entity.Feed;
import com.cookietrip.domain.feed.service.impl.FeedServiceImpl;
import com.cookietrip.global.fixture.FeedFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.cookietrip.domain.feed.exception.FeedExceptionCode.FEED_NOT_FOUND_EXCEPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("[Business Test] - Feed")
@ExtendWith(MockitoExtension.class)
class FeedServiceTest {

    @InjectMocks
    private FeedServiceImpl sut;

    @Mock
    private FeedRepository feedRepository;
    @Mock
    private FeedImageRepository feedImageRepository;
    @Mock
    private FeedReviewRepository feedReviewRepository;

    @DisplayName("[성공 테스트] - 현재 위치를 기반으로 추천 피드 리스트를 조회")
    @Test
    void findFeedsByLocation_success_test() throws Exception {
        // Given
        String searchLocation = "부산시 금정구 남산동";
        Feed feed = FeedFixture.getFeedByLocation(searchLocation);

        given(feedRepository.findAllByLocation_Title(searchLocation))
                .willReturn(FeedFixture.getFeeds());
        given(feedImageRepository.findAllByFeed(feed))
                .willReturn(FeedFixture.getFeedImagesByFeed(feed));

        // When
        List<FeedDto> feedDtos = sut.findFeedsByLocation(searchLocation);

        // Then
        feedDtos
                .forEach(
                        feedDto -> assertThat(feedDto.searchLocation()).isEqualTo(searchLocation)
                );

        then(feedRepository).should().findAllByLocation_Title(searchLocation);
        then(feedImageRepository).should().findAllByFeed(feed);
    }

    @DisplayName("[성공 테스트] - 특정 피드의 상세 정보를 조회")
    @Test
    void getFeed_success_test() throws Exception {
        // Given
        Long feedId = 1L;
        Feed feed = FeedFixture.getFeedById(feedId);

        given(feedRepository.findById(feedId))
                .willReturn(Optional.of(feed));
        given(feedImageRepository.findAllByFeed(feed))
                .willReturn(FeedFixture.getFeedImagesByFeed(feed));
        given(feedReviewRepository.findAllByFeed(feed))
                .willReturn(FeedFixture.getFeedReviewsByFeed(feed));

        // When
        FeedWithReviewsDto feedWithReviewsDto = sut.getFeed(feedId);

        // Then
        assertThat(feedWithReviewsDto.id()).isEqualTo(feedId);

        then(feedRepository).should().findById(feedId);
        then(feedImageRepository).should().findAllByFeed(feed);
        then(feedReviewRepository).should().findAllByFeed(feed);
    }

    @DisplayName("[예외 테스트] - 존재하지 않는 피드")
    @Test
    void getFeed_exception_test_feedNotFound() throws Exception {
        // Given
        Long wrongFeedId = -1L;

        given(feedRepository.findById(wrongFeedId))
                .willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> sut.getFeed(wrongFeedId));

        // Then
        assertThat(t)
                .isInstanceOf(FeedNotFoundException.class)
                .hasMessage(FEED_NOT_FOUND_EXCEPTION.getMessage());
    }
}