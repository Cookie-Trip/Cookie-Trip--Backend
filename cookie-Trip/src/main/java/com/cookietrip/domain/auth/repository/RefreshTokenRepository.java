package com.cookietrip.domain.auth.repository;

import com.cookietrip.domain.auth.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByMemberPersonalId(String memberPersonalId);

    Optional<RefreshToken> findByMemberPersonalIdAndValue(String memberPersonalId, String token);

    boolean existsByMemberPersonalId(String memberPersonalId);

    void deleteAllByMemberPersonalId(String memberPersonalId);
}