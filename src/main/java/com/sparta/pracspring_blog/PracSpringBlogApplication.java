package com.sparta.pracspring_blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // TimeStamped 클래스의 AuditingEntityListener 가 생성 및 수정 일시를 CreatedAt, ModifiedAt 필드에 넣어줄 수 있도록 활성화시켜주는 JPA 기반 애너테이션

@SpringBootApplication(exclude = SecurityAutoConfiguration.class) //exclude = SecurityAutoConfiguration.class : SpringBoot Security 직접 설정해주기 위한 설정

public class PracSpringBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracSpringBlogApplication.class, args);
	}

}
