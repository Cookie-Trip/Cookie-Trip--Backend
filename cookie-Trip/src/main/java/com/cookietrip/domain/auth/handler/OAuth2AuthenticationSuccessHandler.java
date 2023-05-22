package com.cookietrip.domain.auth.handler;

import com.cookietrip.domain.auth.exception.InvalidRedirectUriException;
import com.cookietrip.domain.auth.info.OAuth2UserInfo;
import com.cookietrip.domain.auth.info.OAuth2UserInfoFactory;
import com.cookietrip.domain.auth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.cookietrip.domain.auth.repository.RefreshTokenRepository;
import com.cookietrip.domain.auth.service.TokenService;
import com.cookietrip.domain.auth.token.AuthToken;
import com.cookietrip.domain.auth.token.RefreshToken;
import com.cookietrip.domain.member.model.constant.LoginProvider;
import com.cookietrip.global.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final List<String> authorizedRedirectUris;
    private final Integer cookieMaxAge;

    @Value("${app.cookie.redirect-uri-param-cookie-name}")
    private String REDIRECT_URI_PARAM_COOKIE_NAME;
    @Value("${app.cookie.refresh-token-cookie-name}")
    private String REFRESH_TOKEN_COOKIE_NAME;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        // 응답 커밋 여부 체크
        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋되었으므로 [\" + targetUrl + \"]로 리다이렉션 할 수 없습니다.");
            return;
        }

        clearAuthenticationAttributes(request, response);

        // 리다이렉트 수행
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    /**
     * 토큰 생성 후 클라이언트에게 리다이렉트를 진행하는 로직
     */
    protected String determineTargetUrl(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        redirectUri.ifPresent(this::validateRedirectUri);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        LoginProvider loginProvider = LoginProvider.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());

        OidcUser user = (OidcUser) authentication.getPrincipal();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(loginProvider, user.getAttributes());
        Collection<? extends GrantedAuthority> authorities = ((OidcUser) authentication.getPrincipal()).getAuthorities();

        // Access Token 생성
        AuthToken accessToken = tokenService.createAccessToken(
                oAuth2UserInfo.getId(),
                authorities
        );

        log.info("[Access-Token] = {}", accessToken.getValue());

        // Refresh Token 생성
        AuthToken refreshToken = tokenService.createRefreshToken(
                oAuth2UserInfo.getId(),
                authorities
        );

        // Refresh Token DB에 저장
        Optional<RefreshToken> savedRefreshToken = refreshTokenRepository.findByMemberPersonalId(oAuth2UserInfo.getId());
        if (savedRefreshToken.isPresent()) {
            savedRefreshToken.get().changeTokenValue(refreshToken.getValue());
        } else {
            refreshTokenRepository.saveAndFlush(
                    RefreshToken.of(
                            oAuth2UserInfo.getId(),
                            refreshToken.getValue()
                    )
            );
        }

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(
                response,
                REFRESH_TOKEN_COOKIE_NAME,
                refreshToken.getValue(),
                cookieMaxAge
        );

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", accessToken.getValue())
                .build()
                .toUriString();
    }

    // 허용된 Redirect URI 가 맞는지 검증
    private void validateRedirectUri(String redirectUri) {
        URI clientRedirectUri = URI.create(redirectUri);

        boolean validateResult = authorizedRedirectUris
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Host & Port 검증
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort();
                });

        if (!validateResult)
            throw new InvalidRedirectUriException();
    }

    /**
     * 인증 속성 초기화
     */
    protected void clearAuthenticationAttributes(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}