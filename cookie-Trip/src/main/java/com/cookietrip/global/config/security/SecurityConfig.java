package com.cookietrip.global.config.security;

import com.cookietrip.domain.auth.exception.CustomAuthenticationEntryPoint;
import com.cookietrip.domain.auth.filter.TokenAuthenticationFilter;
import com.cookietrip.domain.auth.handler.OAuth2AuthenticationFailureHandler;
import com.cookietrip.domain.auth.handler.OAuth2AuthenticationSuccessHandler;
import com.cookietrip.domain.auth.handler.TokenAccessDeniedHandler;
import com.cookietrip.domain.auth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.cookietrip.domain.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final TokenService tokenService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository;
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {

        // CSRF & Form Login 기능 비활성화
        httpSecurity
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable();

        // 세션 STATELESS 설정
        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 권한별 접근 요청 설정
        httpSecurity
                .authorizeHttpRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()

                // Auth API
                .antMatchers(POST, "/api/v1/auth/refresh").permitAll()

                // Spring REST Docs
                .antMatchers(GET, "/docs/**").permitAll()

                .anyRequest().authenticated();

        // JWT 검증 필터 및 예외처리 핸들러 등록
        httpSecurity
                .addFilterBefore(
                        new TokenAuthenticationFilter(tokenService),
                        UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(tokenAccessDeniedHandler);

        // Front 에서 Login 시 요청할 URL
        httpSecurity
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/api/v1/oauth2/authorization")
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository);

        // OAuth Server Redirect 주소
        httpSecurity
                .oauth2Login()
                .redirectionEndpoint()
                .baseUri("/*/oauth2/code/*");

        // 인증 후 user 정보를 파싱할 서비스
        httpSecurity
                .oauth2Login()
                .userInfoEndpoint()
                .userService(oAuth2UserService);

        // OAuth 인증 성공/실패 핸들러
        httpSecurity.oauth2Login()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);

        return httpSecurity.build();
    }
}