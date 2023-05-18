package com.cookietrip.domain.auth.filter;

import com.cookietrip.domain.auth.exception.TokenException;
import com.cookietrip.domain.auth.service.TokenService;
import com.cookietrip.domain.auth.token.AuthToken;
import com.cookietrip.global.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cookietrip.domain.auth.exception.AuthExceptionCode.REQUEST_TOKEN_NOT_FOUND;

@RequiredArgsConstructor
public class TokenAuthenticationFilter
        extends OncePerRequestFilter {

    private final TokenService tokenService;

    private static final String EXCEPTION_ATTRIBUTE_NAME = "exceptionCode";

    private static final String TOKEN_REISSUE_REQUEST_URI = "/api/v1/auth/refresh";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Access Token 값 파싱
        String accessToken = HeaderUtil.getAccessToken(request);

        // 요청에 토큰이 존재하는지 검증
        if (accessToken == null) {
            request.setAttribute(EXCEPTION_ATTRIBUTE_NAME, REQUEST_TOKEN_NOT_FOUND);
            filterChain.doFilter(request, response);
            return;
        }

        AuthToken authAccessToken = tokenService.createAuthTokenOfAccessToken(accessToken);

        // Token 재발급 요청인 경우
        String path = request.getRequestURI();
        if (TOKEN_REISSUE_REQUEST_URI.equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (authAccessToken.validate()) {
                Authentication authentication = tokenService.getAuthentication(authAccessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (TokenException e) {
            request.setAttribute(EXCEPTION_ATTRIBUTE_NAME, e.getExceptionCode());
        }

        filterChain.doFilter(request, response);
    }
}