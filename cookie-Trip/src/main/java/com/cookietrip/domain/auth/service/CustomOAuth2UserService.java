package com.cookietrip.domain.auth.service;

import com.cookietrip.domain.auth.exception.OAuth2ProviderMisMatchException;
import com.cookietrip.domain.auth.info.OAuth2UserInfo;
import com.cookietrip.domain.auth.info.OAuth2UserInfoFactory;
import com.cookietrip.domain.auth.principal.UserPrincipal;
import com.cookietrip.domain.member.model.constant.LoginProvider;
import com.cookietrip.domain.member.model.entity.Member;
import com.cookietrip.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService
        extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Value("${image.default-user-profile-image-url}")
    private String defaultUserProfileImageUrl;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return this.process(userRequest, oAuth2User);
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    /**
     * 메인 로직
     */
    private OAuth2User process(
            OAuth2UserRequest userRequest,
            OAuth2User oAuth2User
    ) {
        LoginProvider loginProvider = LoginProvider.valueOf(
                userRequest
                        .getClientRegistration()
                        .getRegistrationId()
                        .toUpperCase()
        );

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                loginProvider,
                oAuth2User.getAttributes()
        );

        // 존재하지 않는 사용자일 경우 회원 가입을 진행
        Member member = memberRepository.findByPersonalId(oAuth2UserInfo.getId())
                .orElseGet(() -> createMember(oAuth2UserInfo, loginProvider));
        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getRole().toString()));

        // 회원 가입 된 계정의 로그인 유형과 현재 로그인 한 유형이 일치한지 검증
        if (loginProvider != member.getLoginProvider()) {
            throw new OAuth2ProviderMisMatchException(member.getLoginProvider().toString());
        }

        return UserPrincipal.from(member, authorities, oAuth2User.getAttributes());
    }

    // Member 생성
    private Member createMember(
            OAuth2UserInfo userInfo,
            LoginProvider loginProvider
    ) {
        return memberRepository.saveAndFlush(
                Member.of(
                        userInfo.getId(),
                        userInfo.getEmail(),
                        userInfo.getName(),
                        defaultUserProfileImageUrl,
                        loginProvider
                )
        );
    }
}