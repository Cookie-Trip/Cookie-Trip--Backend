package com.cookietrip.domain.entity;
import lombok.*;
import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "feed")
@Entity
public class Feed extends BaseTimeEntity {
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

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private PlaceCategory placeCategory;

    private Feed(
            String title,
            String content,
            String placeName,
            String placeLocation,
            PlaceCategory placeCategory
    ) {
        this.title = title;
        this.content = content;
        this.placeName = placeName;
        this.placeLocation = placeLocation;
        this.placeCategory = placeCategory;
    }

    public static Feed of(
            String title,
            String content,
            String placeName,
            String placeLocation,
            PlaceCategory placeCategory
    ) {
        return new Feed(
                title,
                content,
                placeName,
                placeLocation,
                placeCategory
        );
    }
}