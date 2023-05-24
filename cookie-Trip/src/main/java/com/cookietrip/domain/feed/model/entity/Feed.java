package com.cookietrip.domain.feed.model.entity;
import com.cookietrip.domain.feed.model.constant.PlaceCategory;
import com.cookietrip.global.config.audit.BaseTimeEntity;
import lombok.*;
import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "feed")
@Entity
public class Feed
        extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @Column(length = 100, nullable = false)
    private String placeName;

    @Column(length = 1000, nullable = false)
    private String placeLocation;

    @Column(length = 50, nullable = false)
    private String placePhoneNumber;

    @Column(nullable = false)
    private Double placeRating;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private PlaceCategory placeCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;

    private Feed(
            String title,
            String content,
            String placeName,
            String placeLocation,
            String placePhoneNumber,
            Double placeRating,
            PlaceCategory placeCategory,
            Location location
    ) {
        this.title = title;
        this.content = content;
        this.placeName = placeName;
        this.placeLocation = placeLocation;
        this.placePhoneNumber = placePhoneNumber;
        this.placeRating = placeRating;
        this.placeCategory = placeCategory;
        this.location = location;
    }

    public static Feed of(
            String title,
            String content,
            String placeName,
            String placeLocation,
            String placePhoneNumber,
            Double placeRating,
            PlaceCategory placeCategory,
            Location location
    ) {
        return new Feed(
                title,
                content,
                placeName,
                placeLocation,
                placePhoneNumber,
                placeRating,
                placeCategory,
                location
        );
    }

    public static Feed of(
            String title,
            String content,
            String placeName,
            String placeLocation,
            Double placeRating,
            PlaceCategory placeCategory,
            Location location
    ) {
        return new Feed(
                title,
                content,
                placeName,
                placeLocation,
                "NO_PHONE_NUMBER",
                placeRating,
                placeCategory,
                location
        );
    }

    /**
     * DEV & TEST 생성자 & 펙토리 메서드
     * 향후 DB가 연결되면 삭제한다.
     */
    private Feed(
            Long id,
            String title,
            String content,
            String placeName,
            String placeLocation,
            String placePhoneNumber,
            Double placeRating,
            PlaceCategory placeCategory,
            Location location
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.placeName = placeName;
        this.placeLocation = placeLocation;
        this.placePhoneNumber = placePhoneNumber;
        this.placeRating = placeRating;
        this.placeCategory = placeCategory;
        this.location = location;
    }

    public static Feed of(
            Long id,
            String title,
            String content,
            String placeName,
            String placeLocation,
            String placePhoneNumber,
            Double placeRating,
            PlaceCategory placeCategory,
            Location location
    ) {
        return new Feed(
                id,
                title,
                content,
                placeName,
                placeLocation,
                placePhoneNumber,
                placeRating,
                placeCategory,
                location
        );
    }
}