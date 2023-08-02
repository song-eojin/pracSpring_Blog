package com.sparta.pracspring_blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // TimeStamped의 AuditingEntityListner를 제대로 동작하게 하여, 생성일시를 createdAt 필드에 넣어주고 수정일시를 modifiedAt 필드에 넣어주도록, JpaAuditing을 활성화시켜주는 애너테이션
@SpringBootApplication
public class PracSpringBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracSpringBlogApplication.class, args);
	}

}
