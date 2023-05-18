package com.cookietrip.domain.entity.Repository;

import com.cookietrip.domain.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkRepository extends JpaRepository<Bookmark, Long> {
}
