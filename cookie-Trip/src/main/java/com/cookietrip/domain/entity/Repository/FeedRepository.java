package com.cookietrip.domain.entity.Repository;

import com.cookietrip.domain.entity.Feed;
import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}
