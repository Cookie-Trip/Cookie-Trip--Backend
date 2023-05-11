package com.cookietrip.domain.member.model.constant;

import lombok.Getter;

@Getter
public enum MemberRole {

    ROLE_ADMIN("관리자"),
    ROLE_MANAGER("매니저"),
    ROLE_MEMBER("일반사용자");

    private final String description;

    MemberRole(String description) {
        this.description = description;
    }
}