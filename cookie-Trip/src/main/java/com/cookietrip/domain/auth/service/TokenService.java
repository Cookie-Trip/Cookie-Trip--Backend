package com.cookietrip.domain.auth.service;

import com.cookietrip.domain.auth.principal.UserPrincipal;
import com.cookietrip.domain.auth.token.AuthToken;
import com.cookietrip.domain.auth.token.AuthTokenProvider;
import com.cookietrip.domain.member.exception.MemberNotFoundException;
import com.cookietrip.domain.member.model.entity.Member;
import com.cookietrip.domain.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final MemberRepository memberRepository;
    private final AuthTokenProvider tokenProvider;

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
    @Transactional(readOnly = true)
    public Authentication getAuthentication(AuthToken accessToken) {
        // access token 검증
        accessToken.validate();

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
}