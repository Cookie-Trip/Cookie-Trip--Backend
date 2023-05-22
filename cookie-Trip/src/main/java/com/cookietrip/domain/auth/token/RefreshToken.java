package com.cookietrip.domain.auth.token;

import com.cookietrip.global.config.audit.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "refresh_token")
@Entity
public class RefreshToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true, updatable = false)
    private String memberPersonalId;

    @Column(length = 500, nullable = false)
    private String value;

    private RefreshToken(
            String memberPersonalId,
            String value
    ) {
        this.memberPersonalId = memberPersonalId;
        this.value = value;
    }

    public static RefreshToken of(
            String memberPersonalId,
            String value
    ) {
        return new RefreshToken(memberPersonalId, value);
    }

    /**
     * Token Value 변경
     */
    public void changeTokenValue(String tokenValue) {
        this.value = tokenValue;
    }
}