package com.cookietrip.domain.feed.model.entity;

import com.cookietrip.global.config.audit.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "feed_image")
@Entity
public class FeedImage
        extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Feed feed;

    private FeedImage(
            String imageUrl,
            Feed feed
    ) {
        this.imageUrl = imageUrl;
        this.feed = feed;
    }

    public static FeedImage of(
            String imageUrl,
            Feed feed
    ) {
        return new FeedImage(
                imageUrl,
                feed);
    }
}