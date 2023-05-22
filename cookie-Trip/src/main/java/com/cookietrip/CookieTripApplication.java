package com.cookietrip;

import com.cookietrip.domain.entity.Feed;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CookieTripApplication {

	public static void main(String[] args) {
		SpringApplication.run(CookieTripApplication.class, args);
	}
}