package com.cookietrip.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Table(name ="User")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(nullable = false, length = 100)
    private String personalId;
    @Column(nullable = false, length = 100)
    private String email;
    @Column(nullable = false, length = 100)
    private String nickname;
    @Column(nullable = false, length = 50)
    private String providerType;
    @Column(nullable = false, length = 50)
    private String role;
    @Column(nullable = false, length =50)
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    private Bookmark bookmark;

    public Member(
            String personalId,
            String email,
            String nickname,
            String providerType,
            String role,
            String status,
            Bookmark bookmark)
    {
        this.personalId = personalId;
        this.email = email;
        this.nickname = nickname;
        this.providerType = providerType;
        this.role = role;
        this.status = status;
        this.bookmark = bookmark;
    }
    public static Member of(
            String personId,
            String email,
            String nickname,
            String providerType,
            String role,
            String status,
            Bookmark bookmark){
    return new Member(
            personId,
            email,
            nickname,
            providerType,
            role,
            status,
            bookmark
    );
    }
}
