package com.cookietrip.domain.auth.controller;

import com.cookietrip.domain.auth.dto.ReissueTokenResponse;
import com.cookietrip.domain.auth.exception.TokenException;
import com.cookietrip.domain.auth.service.TokenService;
import com.cookietrip.global.response.ResponseService;
import com.cookietrip.global.response.SingleResult;
import com.cookietrip.global.util.CookieUtil;
import com.cookietrip.global.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.cookietrip.domain.auth.exception.AuthExceptionCode.REQUEST_TOKEN_NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final TokenService tokenService;
    private final ResponseService responseService;

    @Value("${app.jwt.expiry.refresh-token-expiry}")
    private long refreshTokenExpiry;
    @Value("${app.cookie.refresh-token-cookie-name}")
    private String refreshTokenCookieName;

    /**
     * Access Token 재발급
     */
    @PostMapping("/refresh")
    public SingleResult<ReissueTokenResponse> reissueAccessToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String accessToken = HeaderUtil.getAccessToken(request);

        if (accessToken == null) {
            throw new TokenException(REQUEST_TOKEN_NOT_FOUND);
        }

        String refreshToken = CookieUtil.getCookie(request, refreshTokenCookieName)
                .map(Cookie::getValue)
                .orElse(null);

        Map<String, String> newTokens = tokenService.reissueToken(accessToken, refreshToken);

        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtil.deleteCookie(request, response, refreshTokenCookieName);
        CookieUtil.addCookie(
                response,
                refreshTokenCookieName,
                newTokens.get("refreshToken"),
                cookieMaxAge
        );

        return responseService.getSingleResult(
                OK.value(),
                "성공적으로 토큰이 재발급되었습니다.",
                ReissueTokenResponse.of(newTokens.get("accessToken"))
        );
    }
}