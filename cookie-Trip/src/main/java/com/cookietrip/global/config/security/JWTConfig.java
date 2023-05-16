package com.cookietrip.global.config.security;

import com.cookietrip.domain.auth.exception.CustomAuthenticationEntryPoint;
import com.cookietrip.domain.auth.handler.TokenAccessDeniedHandler;
import com.cookietrip.domain.auth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JWTConfig {

    @Value("${app.jwt.secret.access-token-secret-key}")
    private String accessTokenSecretKey;
    @Value("${app.jwt.expiry.access-token-expiry}")
    private long accessTokenExpiry;
    @Value("${app.jwt.secret.refresh-token-secret-key}")
    private String refreshTokenSecretKey;
    @Value("${app.jwt.expiry.refresh-token-expiry}")
    private long refreshTokenExpiry;

    /**
     * Token Provider
     */
    @Bean
    public AuthTokenProvider authTokenProvider() {
        return new AuthTokenProvider(
                accessTokenSecretKey,
                accessTokenExpiry,
                refreshTokenSecretKey,
                refreshTokenExpiry
        );
    }

    /**
     * Authentication Entry Point
     */
    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    /**
     * 토큰 검증 오류 처리 핸들러
     */
    @Bean
    public TokenAccessDeniedHandler tokenAccessDeniedHandler() {
        return new TokenAccessDeniedHandler();
    }
}