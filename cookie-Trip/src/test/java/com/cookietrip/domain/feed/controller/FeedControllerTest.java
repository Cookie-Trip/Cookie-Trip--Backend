package com.cookietrip.domain.feed.controller;

import com.cookietrip.domain.feed.dto.FeedDto;
import com.cookietrip.domain.feed.dto.FeedWithReviewsDto;
import com.cookietrip.domain.feed.exception.FeedNotFoundException;
import com.cookietrip.domain.feed.service.FeedService;
import com.cookietrip.global.fixture.FeedFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.cookietrip.domain.feed.exception.FeedExceptionCode.FEED_NOT_FOUND_EXCEPTION;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("[API Test] - Feed")
@AutoConfigureRestDocs
@WithMockUser("tester")
@WebMvcTest(FeedController.class)
class FeedControllerTest {

    private final MockMvc mvc;

    @MockBean
    FeedService feedService;

    FeedControllerTest(
            @Autowired MockMvc mvc
    ) {
        this.mvc = mvc;
    }

    private static final String BASIC_URL = "/api/v1/feeds";

    @DisplayName("[GET 성공 테스트] - 특정 위치를 기반으로 추천 피드 리스트를 조회")
    @Test
    void getFeeds_success_test() throws Exception {
        // Given
        String searchLocation = "부산시 금정구 남산동";
        List<FeedDto> feedDtosByLocation = FeedFixture.getFeedDtosByLocation(searchLocation);

        given(feedService.findFeedsByLocation(searchLocation))
                .willReturn(feedDtosByLocation);

        // When & Then
        mvc.perform(
                        get(BASIC_URL)
                                .queryParam("search-location", searchLocation)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data[0].feedId").value(feedDtosByLocation.get(0).id()))
                .andExpect(jsonPath("$.data[0].feedTitle").value(feedDtosByLocation.get(0).title()))
                .andExpect(jsonPath("$.data[0].searchLocation").value(feedDtosByLocation.get(0).searchLocation()))
                .andExpect(jsonPath("$.data[0].feedImages").isNotEmpty())
                .andExpect(jsonPath("$.data[0].feedImages[0].imageUrl").value(feedDtosByLocation.get(0).placeImages().get(0).imageUrl()))
                .andDo(print())
                .andDo(document("feed/getFeeds",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("search-location").description("사용자의 현재 위치 ex) \"부산시 금정구 남산동\"")
                        )
                ));

        verify(feedService).findFeedsByLocation(searchLocation);
    }

    @DisplayName("[GET 성공 테스트] - 특정 피드의 정보를 상세 조회")
    @Test
    void getFeed_success_test() throws Exception {
        // Given
        Long feedId = 1L;
        FeedWithReviewsDto serviceResult = FeedFixture.getFeedWithReviewDtoByFeedId(feedId);

        given(feedService.getFeed(feedId))
                .willReturn(serviceResult);

        // When & Then
        mvc.perform(
                        get(BASIC_URL + "/{feedId}", feedId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data.feedId").value(serviceResult.id()))
                .andExpect(jsonPath("$.data.feedTitle").value(serviceResult.title()))
                .andExpect(jsonPath("$.data.feedContent").value(serviceResult.content()))
                .andExpect(jsonPath("$.data.placeName").value(serviceResult.placeName()))
                .andExpect(jsonPath("$.data.placeLocation").value(serviceResult.placeLocation()))
                .andExpect(jsonPath("$.data.placePhoneNumber").value(serviceResult.placePhoneNumber()))
                .andExpect(jsonPath("$.data.placeRating").value(serviceResult.placeRating()))
                .andExpect(jsonPath("$.data.placeCategory").value(serviceResult.placeCategory().name()))
                .andExpect(jsonPath("$.data.searchLocation").value(serviceResult.searchLocation()))
                .andExpect(jsonPath("$.data.placeImages[0].imageUrl").value(serviceResult.placeImages().get(0).imageUrl()))
                .andDo(print())
                .andDo(document("feed/getFeed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));

        verify(feedService).getFeed(feedId);
    }

    @DisplayName("[GET 예외 테스트] - 존재하지 않는 피드 상세 정보 조회")
    @Test
    void getFeed_exception_test_feedNotFound() throws Exception {
        // Given
        Long wrongFeedId = -1L;

        // When
        when(feedService.getFeed(wrongFeedId)).thenThrow(new FeedNotFoundException());

        // Then
        mvc.perform(
                        get(BASIC_URL + "/" + wrongFeedId)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.code").value(FEED_NOT_FOUND_EXCEPTION.getCode()))
                .andExpect(jsonPath("$.message").value(FEED_NOT_FOUND_EXCEPTION.getMessage()))
                .andExpect(jsonPath("$.timestamp").exists())
                .andDo(print());
    }
}