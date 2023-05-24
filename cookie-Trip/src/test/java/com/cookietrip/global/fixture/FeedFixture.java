package com.cookietrip.global.fixture;

import com.cookietrip.domain.feed.dto.FeedDto;
import com.cookietrip.domain.feed.dto.FeedImageDto;
import com.cookietrip.domain.feed.dto.FeedReviewDto;
import com.cookietrip.domain.feed.dto.FeedWithReviewsDto;
import com.cookietrip.domain.feed.model.entity.Feed;
import com.cookietrip.domain.feed.model.entity.FeedImage;
import com.cookietrip.domain.feed.model.entity.FeedReview;
import com.cookietrip.domain.feed.model.entity.Location;

import java.util.List;

import static com.cookietrip.domain.feed.model.constant.PlaceCategory.CAFE;
import static com.cookietrip.domain.feed.model.constant.PlaceCategory.RESTAURANT;

public class FeedFixture {

    // Location Mock Data List
    private final static List<Location> LOCATIONS = List.of(
            Location.of(
                    "부산시 금정구 남산동"
            )
    );

    // Feed Mock Data List
    private final static List<Feed> FEEDS = List.of(
            Feed.of(
                    1L,
                    "남산동 존맛카페 - 어벤더치 남산점",
                    "커피를 마시고 싶다? 어벤더치 ㄱ (근데 난 저기 간얼음이라 시름. 니나 가라)",
                    "어벤더치 남산점",
                    "부산 금정구 금강로 679 태인상가 1층",
                    "010-1234-1234",
                    4.7,
                    CAFE,
                    LOCATIONS.get(0)
            ),
            Feed.of(
                    2L,
                    "남산동 존맛 돈까스 - 냥까스",
                    "고양이가 돈까스를 먹으면? 냥~까스",
                    "냥까스",
                    "부산 금정구 학산로13번길 1 1층",
                    "010-3434-1232",
                    4.68,
                    RESTAURANT,
                    LOCATIONS.get(0)
            ),
            Feed.of(
                    3L,
                    "솔직히 난 가본적 없음 - 구포촌국수",
                    "국수 먹으려고 많이들 간다는 구포촌국수 (여긴 구포가 아니라 남산인데)",
                    "구포촌국수",
                    "부산 금정구 금샘로 490",
                    "010-3424-8744",
                    4.3,
                    RESTAURANT,
                    LOCATIONS.get(0)
            ),
            Feed.of(
                    4L,
                    "브런치가 맛있는 - 셀라스",
                    "브런치가 맛있는 복합문화주거어쩌고, 셀라스 카페!",
                    "셀라스 범어사점",
                    "부산 금정구 금샘로 538",
                    "010-3285-3759",
                    4.4,
                    CAFE,
                    LOCATIONS.get(0)
            ),
            Feed.of(
                    5L,
                    "다방다방 - 금생다방",
                    "다방이면 쌍화차 파나요? ㄴ 그런거 없는 금샘다방",
                    "금샘다방",
                    "부산 금정구 금샘로 574",
                    "010-1234-1343",
                    4.53,
                    CAFE,
                    LOCATIONS.get(0)
            )
    );

    // Feed Reviews Mock Data List
    private final static List<FeedReview> FEED_REVIEWS = List.of(
            FeedReview.of(
                    "그냥 그럼",
                    FEEDS.get(0)
            ),
            FeedReview.of(
                    "쏘쏘",
                    FEEDS.get(0)
            ),
            FeedReview.of(
                    "굿굿",
                    FEEDS.get(0)
            ),
            FeedReview.of(
                    "그냥 그럼",
                    FEEDS.get(1)
            ),
            FeedReview.of(
                    "쏘쏘",
                    FEEDS.get(1)
            ),
            FeedReview.of(
                    "굿굿",
                    FEEDS.get(1)
            ),
            FeedReview.of(
                    "그냥 그럼",
                    FEEDS.get(2)
            ),
            FeedReview.of(
                    "쏘쏘",
                    FEEDS.get(2)
            ),
            FeedReview.of(
                    "굿굿",
                    FEEDS.get(2)
            ),
            FeedReview.of(
                    "그냥 그럼",
                    FEEDS.get(3)
            ),
            FeedReview.of(
                    "쏘쏘",
                    FEEDS.get(3)
            ),
            FeedReview.of(
                    "굿굿",
                    FEEDS.get(3)
            ),
            FeedReview.of(
                    "그냥 그럼",
                    FEEDS.get(4)
            ),
            FeedReview.of(
                    "쏘쏘",
                    FEEDS.get(4)
            ),
            FeedReview.of(
                    "굿굿",
                    FEEDS.get(4)
            )
    );

    // Feed Images Mock Data List
    private static final List<FeedImage> FEED_IMAGES = List.of(
            FeedImage.of(
                    "https://spadeworker-storage.s3.ap-northeast-2.amazonaws.com/cookie-trip/mock-feed-image/aa.jpg",
                    FEEDS.get(0)
            ),
            FeedImage.of(
                    "https://spadeworker-storage.s3.ap-northeast-2.amazonaws.com/cookie-trip/mock-feed-image/cookie123123.jpg",
                    FEEDS.get(0)
            ),
            FeedImage.of(
                    "https://spadeworker-storage.s3.ap-northeast-2.amazonaws.com/cookie-trip/mock-feed-image/pepe.jpeg",
                    FEEDS.get(0)
            ),
            FeedImage.of(
                    "https://spadeworker-storage.s3.ap-northeast-2.amazonaws.com/cookie-trip/mock-feed-image/aa.jpg",
                    FEEDS.get(1)
            ),
            FeedImage.of(
                    "https://spadeworker-storage.s3.ap-northeast-2.amazonaws.com/cookie-trip/mock-feed-image/cookie123123.jpg",
                    FEEDS.get(1)
            ),
            FeedImage.of(
                    "https://spadeworker-storage.s3.ap-northeast-2.amazonaws.com/cookie-trip/mock-feed-image/pepe.jpeg",
                    FEEDS.get(1)
            ),
            FeedImage.of(
                    "https://spadeworker-storage.s3.ap-northeast-2.amazonaws.com/cookie-trip/mock-feed-image/aa.jpg",
                    FEEDS.get(2)
            ),
            FeedImage.of(
                    "https://spadeworker-storage.s3.ap-northeast-2.amazonaws.com/cookie-trip/mock-feed-image/cookie123123.jpg",
                    FEEDS.get(2)
            ),
            FeedImage.of(
                    "https://spadeworker-storage.s3.ap-northeast-2.amazonaws.com/cookie-trip/mock-feed-image/pepe.jpeg",
                    FEEDS.get(2)
            ),
            FeedImage.of(
                    "https://spadeworker-storage.s3.ap-northeast-2.amazonaws.com/cookie-trip/mock-feed-image/aa.jpg",
                    FEEDS.get(3)
            ),
            FeedImage.of(
                    "https://spadeworker-storage.s3.ap-northeast-2.amazonaws.com/cookie-trip/mock-feed-image/cookie123123.jpg",
                    FEEDS.get(3)
            ),
            FeedImage.of(
                    "https://spadeworker-storage.s3.ap-northeast-2.amazonaws.com/cookie-trip/mock-feed-image/pepe.jpeg",
                    FEEDS.get(3)
            ),
            FeedImage.of(
                    "https://spadeworker-storage.s3.ap-northeast-2.amazonaws.com/cookie-trip/mock-feed-image/aa.jpg",
                    FEEDS.get(4)
            ),
            FeedImage.of(
                    "https://spadeworker-storage.s3.ap-northeast-2.amazonaws.com/cookie-trip/mock-feed-image/cookie123123.jpg",
                    FEEDS.get(4)
            ),
            FeedImage.of(
                    "https://spadeworker-storage.s3.ap-northeast-2.amazonaws.com/cookie-trip/mock-feed-image/pepe.jpeg",
                    FEEDS.get(4)
            )
    );

    public static List<Feed> getFeeds() {
        return FEEDS;
    }

    public static Feed getFeedById(Long feedId) {

        return FEEDS
                .stream()
                .filter(feed -> feed.getId().equals(feedId))
                .findFirst()
                .get();
    }

    public static Feed getFeedByLocation(String location) {
        return FEEDS
                .stream()
                .filter(feed -> feed.getLocation().getTitle().equals(location))
                .findFirst()
                .get();
    }

    public static FeedDto getFeedDto() {
        return FeedDto.of(FEEDS.get(0), getFeedImageDtos(FEEDS.get(0)));
    }

    public static List<FeedDto> getFeedDtos() {
        return FEEDS
                .stream()
                .map(feed -> FeedDto.of(feed, getFeedImageDtos(feed)))
                .toList();
    }

    public static List<FeedReviewDto> getFeedReviewDtos() {
        return FEED_REVIEWS
                .stream()
                .map(FeedReviewDto::fromEntity)
                .toList();
    }

    public static List<FeedReview> getFeedReviewsByFeed(Feed feed) {
        return FEED_REVIEWS
                .stream()
                .filter(feedReview -> feedReview.getFeed().equals(feed))
                .toList();
    }

    public static List<FeedImage> getFeedImagesByFeed(Feed feed) {
        return FEED_IMAGES
                .stream()
                .filter(feedImage -> feedImage.getFeed().equals(feed))
                .toList();
    }

    public static List<FeedImageDto> getFeedImageDtos() {
        return FEED_IMAGES
                .stream()
                .map(FeedImageDto::fromEntity)
                .toList();
    }

    public static List<FeedImageDto> getFeedImageDtos(Feed feed) {
        return getFeedImagesByFeed(feed)
                .stream()
                .map(FeedImageDto::fromEntity)
                .toList();
    }

    public static FeedWithReviewsDto getFeedWithReviewDto() {
        return FeedWithReviewsDto.of(FEEDS.get(0), getFeedImageDtos(), getFeedReviewDtos());
    }
}