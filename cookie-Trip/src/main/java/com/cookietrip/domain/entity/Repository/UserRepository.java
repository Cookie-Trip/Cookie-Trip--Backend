package com.cookietrip.domain.entity.Repository;

import com.cookietrip.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, Long> {
}
