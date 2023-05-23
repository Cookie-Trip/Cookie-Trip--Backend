package com.cookietrip.domain.feed.model.entity;

import com.cookietrip.global.config.audit.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "location")
@Entity
public class Location
        extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false, unique = true)
    private String title;

    private Location(String title) {
        this.title = title;
    }

    public static Location of(String locationTitle) {
        return new Location(locationTitle);
    }
}