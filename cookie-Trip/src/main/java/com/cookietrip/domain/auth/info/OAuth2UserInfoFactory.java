package com.cookietrip.domain.auth.info;

import com.cookietrip.domain.auth.exception.InvalidLoginProviderException;
import com.cookietrip.domain.member.model.constant.LoginProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(
            LoginProvider loginProvider,
            Map<String, Object> attributes
    ) {
        return switch (loginProvider) {
            case GOOGLE -> new GoogleOAuth2UserInfo(attributes);
            case KAKAO -> new KakaoOAuth2UserInfo(attributes);
            default -> throw new InvalidLoginProviderException();
        };
    }
}