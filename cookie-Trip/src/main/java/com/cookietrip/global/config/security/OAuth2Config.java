package com.cookietrip.global.config.security;

import com.cookietrip.domain.auth.handler.OAuth2AuthenticationFailureHandler;
import com.cookietrip.domain.auth.handler.OAuth2AuthenticationSuccessHandler;
import com.cookietrip.domain.auth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.cookietrip.domain.auth.repository.RefreshTokenRepository;
import com.cookietrip.domain.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class OAuth2Config {

    private final TokenService tokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.oauth2.authorized-redirect-uris}")
    private List<String> authorizedRedirectUris;
    @Value("${app.oauth2.cookie-max-age}")
    private Integer cookieMaxAge;

    /**
     * 쿠키 기반 인가 Repository
     */
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    /**
     * OAuth2 인증 성공 핸들러
     */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenService,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                authorizedRedirectUris,
                cookieMaxAge
        );
    }

    /**
     * OAuth2 인증 실패 핸들러
     */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(
                oAuth2AuthorizationRequestBasedOnCookieRepository()
        );
    }
}