package com.cookietrip.domain.auth.repository;

import com.cookietrip.global.util.CookieUtil;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OAuth2AuthorizationRequestBasedOnCookieRepository
        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    @Value("${app.cookie.oauth2-authorization-request-cookie-name}")
    private String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME;

    @Value("${app.cookie.redirect-uri-param-cookie-name}")
    private String REDIRECT_URI_PARAM_COOKIE_NAME;

    @Value("${app.cookie.refresh-token-cookie-name}")
    private String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    @Value("${app.cookie.cookie-expire-seconds}")
    private int COOKIE_EXPIRE_SECONDS;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtil.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(
            OAuth2AuthorizationRequest authorizationRequest,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (authorizationRequest == null) {
            CookieUtil.deleteCookie(
                    request,
                    response,
                    OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME
            );
            CookieUtil.deleteCookie(
                    request,
                    response,
                    REDIRECT_URI_PARAM_COOKIE_NAME
            );
            CookieUtil.deleteCookie(
                    request,
                    response,
                    REFRESH_TOKEN_COOKIE_NAME
            );
            return;
        }

        CookieUtil.addCookie(
                response,
                OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
                CookieUtil.serialize(authorizationRequest),
                COOKIE_EXPIRE_SECONDS
        );
        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtil.addCookie(
                    response,
                    REDIRECT_URI_PARAM_COOKIE_NAME,
                    redirectUriAfterLogin,
                    COOKIE_EXPIRE_SECONDS
            );
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return this.loadAuthorizationRequest(request);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        CookieUtil.deleteCookie(
                request,
                response,
                OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME
        );
        CookieUtil.deleteCookie(
                request,
                response,
                REDIRECT_URI_PARAM_COOKIE_NAME
        );
        CookieUtil.deleteCookie(
                request,
                response,
                REFRESH_TOKEN_COOKIE_NAME
        );
    }
}