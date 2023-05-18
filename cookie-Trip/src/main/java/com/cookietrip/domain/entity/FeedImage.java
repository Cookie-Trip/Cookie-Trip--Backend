package com.cookietrip.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Entity
@Getter
@Table(name = "feed_image")
public class FeedImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 1000)
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
    public static FeedImage of(String imageUrl, Feed feed){

        return new FeedImage(
                imageUrl,
                feed);
    }


}
