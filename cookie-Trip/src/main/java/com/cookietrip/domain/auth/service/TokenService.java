package com.cookietrip.domain.auth.service;

import com.cookietrip.domain.auth.exception.RefreshTokenNotFoundException;
import com.cookietrip.domain.auth.exception.TokenException;
import com.cookietrip.domain.auth.principal.UserPrincipal;
import com.cookietrip.domain.auth.repository.RefreshTokenRepository;
import com.cookietrip.domain.auth.token.AuthToken;
import com.cookietrip.domain.auth.token.AuthTokenProvider;
import com.cookietrip.domain.auth.token.RefreshToken;
import com.cookietrip.domain.member.exception.MemberNotFoundException;
import com.cookietrip.domain.member.model.entity.Member;
import com.cookietrip.domain.member.repository.MemberRepository;
import com.cookietrip.global.util.CookieUtil;
import com.cookietrip.global.util.HeaderUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cookietrip.domain.auth.exception.AuthExceptionCode.*;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final AuthTokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.cookie.refresh-token-cookie-name}")
    private String refreshTokenCookieName;

    /**
     * Access Token 생성
     */
    public AuthToken createAccessToken(
            String memberPersonalId,
            Collection<? extends GrantedAuthority> memberRoles
    ) {
        return tokenProvider.generateAccessToken(memberPersonalId, memberRoles);
    }

    /**
     * Refresh Token 생성
     */
    public AuthToken createRefreshToken(
            String memberPersonalId,
            Collection<? extends GrantedAuthority> memberRoles
    ) {
        return tokenProvider.generateRefreshToken(memberPersonalId, memberRoles);
    }

    /**
     * Access Token Value -> AuthToken
     */
    public AuthToken createAuthTokenOfAccessToken(String accessToken) {
        return tokenProvider.createAuthTokenOfAccessToken(accessToken);
    }

    /**
     * Refresh Token Value -> AuthToken
     */
    public AuthToken createAuthTokenOfRefreshToken(String refreshToken) {
        return tokenProvider.createAuthTokenOfRefreshToken(refreshToken);
    }

    /**
     * 인가된 사용자 인증 정보 조회
     */
    public Authentication getAuthentication(AuthToken accessToken) {
        // access token 검증
        tokenValidate(accessToken);

        // access token claims 조회
        Claims claims = accessToken.getTokenClaims();

        // claims 값을 기반으로 Member Entity 조회
        Member member = memberRepository.findByPersonalId(claims.getSubject())
                .orElseThrow(MemberNotFoundException::new);

        // claims 값을 기반으로 사용자의 권한 조회
        Collection<? extends GrantedAuthority> roles = getMemberAuthority(
                claims.get("roles", String.class)
        );
        UserPrincipal userPrincipal = UserPrincipal.from(member, roles);

        return new UsernamePasswordAuthenticationToken(
                userPrincipal,
                accessToken,
                userPrincipal.getAuthorities()
        );
    }

    // 토큰에서 파싱한 사용자 권한 리스트 문자열을 각각 분리해서 GrantedAuthority List 로 반환
    private Collection<? extends GrantedAuthority> getMemberAuthority(String memberRoles) {
        return Arrays.stream(memberRoles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * Refresh Token 확인 후 토큰 전체 재발급
     */
    @Transactional
    public Map<String, String> reissueToken(
            String expiredAccessToken,
            String refreshToken
    ) {

        AuthToken authTokenOfAccessToken = createAuthTokenOfAccessToken(expiredAccessToken);
        Claims expiredTokenClaims = authTokenOfAccessToken.getExpiredTokenClaims();
        String memberPersonalId = expiredTokenClaims.getSubject();
        Collection<? extends GrantedAuthority> memberRoles = getMemberAuthority(expiredTokenClaims.get("roles", String.class));

        AuthToken authTokenOfRefreshToken = createAuthTokenOfRefreshToken(refreshToken);

        // Refresh Token 검증
        try {
            tokenValidate(authTokenOfRefreshToken);
        } catch (TokenException e) {
            throw new TokenException(INVALID_REFRESH_TOKEN);
        }

        RefreshToken storedRefreshToken = refreshTokenRepository.findByMemberPersonalIdAndValue(memberPersonalId, refreshToken)
                .orElseThrow(RefreshTokenNotFoundException::new);

        AuthToken newAccessToken = createAccessToken(memberPersonalId, memberRoles);

        AuthToken newRefreshToken = createRefreshToken(memberPersonalId, memberRoles);
        storedRefreshToken.changeTokenValue(newRefreshToken.getValue());

        return Map.of(
                "accessToken", newAccessToken.getValue(),
                "refreshToken", newRefreshToken.getValue()
        );
    }

    /**
     * Token 유효성 검증
     */
    public void tokenValidate(AuthToken token) {
        Claims tokenClaims = token.getTokenClaims();
        if (tokenClaims == null)
            throw new TokenException(INVALID_TOKEN);

        if (!refreshTokenRepository.existsByMemberPersonalId(tokenClaims.getSubject()))
            throw new TokenException(LOGGED_OUT_TOKEN);
    }

    /**
     * Logout
     */
    @Transactional
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        refreshTokenRepository.deleteAllByMemberPersonalId(
                createAuthTokenOfAccessToken(
                        HeaderUtil.getAccessToken(request)
                ).getTokenClaims().getSubject()
        );

        CookieUtil.deleteCookie(request, response, refreshTokenCookieName);
    }
}