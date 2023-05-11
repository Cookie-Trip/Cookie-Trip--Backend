package com.cookietrip.domain.auth.repository;

import com.cookietrip.domain.auth.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}