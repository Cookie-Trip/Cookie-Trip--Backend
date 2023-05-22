package com.cookietrip.domain.auth.principal;

import com.cookietrip.domain.member.model.constant.LoginProvider;
import com.cookietrip.domain.member.model.constant.MemberStatus;
import com.cookietrip.domain.member.model.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static com.cookietrip.domain.member.model.constant.MemberStatus.*;

public record UserPrincipal(
        String personalId,
        LoginProvider loginProvider,
        MemberStatus memberStatus,
        Collection<? extends GrantedAuthority> authorities,
        Map<String, Object> oAuth2UserInfoAttributes
) implements UserDetails, OAuth2User, OidcUser {

    public static UserPrincipal from(
            Member member,
            Collection<? extends GrantedAuthority> authorities
    ) {
        return from(member, authorities, null);
    }

    public static UserPrincipal from(
            Member member,
            Collection<? extends GrantedAuthority> authorities,
            Map<String, Object> oAuth2UserInfo
    ) {
        return new UserPrincipal(
                member.getPersonalId(),
                member.getLoginProvider(),
                member.getStatus(),
                authorities,
                oAuth2UserInfo
        );
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2UserInfoAttributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getName() {
        return this.personalId;
    }

    @Override
    public String getUsername() {
        return this.personalId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.memberStatus == ACTIVE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.memberStatus == ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.memberStatus == ACTIVE;
    }

    @Override
    public boolean isEnabled() {
        return this.memberStatus == ACTIVE;
    }

    @Override
    public Map<String, Object> getClaims() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" [");
        sb.append("personalId=").append(this.personalId).append(", ");
        sb.append("providerType=").append(this.loginProvider).append(", ");
        sb.append("memberStatus=").append(this.memberStatus).append(", ");
        sb.append("Granted Authorities=").append(this.authorities).append("], ");
        sb.append("oAuth2UserInfoAttributes=[PROTECTED]");
        return sb.toString();
    }
}