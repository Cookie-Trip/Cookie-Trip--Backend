package com.cookietrip.domain.feed.model.entity;

import com.cookietrip.global.config.audit.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "feed_review")
@Entity
public class FeedReview
        extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Feed feed;

    private FeedReview(
            String content,
            Feed feed
    ) {
        this.content = content;
        this.feed = feed;
    }

    static public FeedReview of(
            String content,
            Feed feed
    ) {
        return new FeedReview(content, feed);
    }
}