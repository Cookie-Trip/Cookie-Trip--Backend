package com.cookietrip.domain.feed.controller;

import com.cookietrip.domain.feed.dto.response.FeedResponse;
import com.cookietrip.domain.feed.dto.response.FeedWithReviewsResponse;
import com.cookietrip.domain.feed.service.FeedService;
import com.cookietrip.global.response.ListResult;
import com.cookietrip.global.response.ResponseService;
import com.cookietrip.global.response.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("/api/v1/feeds")
@RestController
public class FeedController {

    private final FeedService feedService;
    private final ResponseService responseService;

    @GetMapping()
    public ListResult<FeedResponse> getFeeds(
            @RequestParam("search-location") final String searchLocation
    ) {
        return responseService.getListResult(
                OK.value(),
                "성공적으로 피드리스트를 조회하였습니다.",
                feedService.findFeedsByLocation(searchLocation)
                        .stream()
                        .map(FeedResponse::of)
                        .toList()
        );
    }

    @GetMapping("/{feed-id}")
    public SingleResult<FeedWithReviewsResponse> getFeed(
            @PathVariable("feed-id") Long feedId
    ) {
        return responseService.getSingleResult(
                OK.value(),
                "성공적으로 상세 피드 정보를 조회하였습니다.",
                FeedWithReviewsResponse.of(
                        feedService.getFeed(feedId)
                )
        );
    }
}