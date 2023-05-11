package com.cookietrip.domain.member.model.entity;

import com.cookietrip.domain.member.model.constant.LoginProvider;
import com.cookietrip.domain.member.model.constant.MemberRole;
import com.cookietrip.domain.member.model.constant.MemberStatus;
import com.cookietrip.global.config.audit.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.cookietrip.domain.member.model.constant.MemberRole.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true, updatable = false)
    private String personalId;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private LoginProvider loginProviderType;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private MemberRole role = ROLE_MEMBER;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private MemberStatus status = MemberStatus.ACTIVE;
}