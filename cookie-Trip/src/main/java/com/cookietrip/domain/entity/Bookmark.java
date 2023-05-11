package com.cookietrip.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bookmark")
@Entity
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Bookmark(Feed feed, Member member) {
        this.feed = feed;
        this.member = member;
    }

   public static Bookmark of(Feed feed, Member member){
        return new Bookmark(
                feed,
                member);
   }

}
