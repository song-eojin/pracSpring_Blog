package com.sparta.pracspring_blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PracSpringBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracSpringBlogApplication.class, args);
	}

}
