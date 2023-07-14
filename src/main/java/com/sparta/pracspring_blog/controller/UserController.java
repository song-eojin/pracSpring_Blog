package com.sparta.pracspring_blog.controller;

import com.sparta.pracspring_blog.dto.UserRequestDto;
import com.sparta.pracspring_blog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입 기능
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserRequestDto userRequestDto) {
        userService.signUp(userRequestDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
        //ResponseEntity: Spring Framework 에서 제공하는 클래스로 HTTP 응답 역할을 하며, 이를 사용하면 상태코드, 응답본문, 헤더 등을 추가적으로 설정해줄 수 있다. 
        //ok(): ResponseEntity 에서 제공하는 메서드로 상태코드 200을 생성하는 메서드
    }

    //로그인 기능 //security 로 대체
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody UserRequestDto userRequestDto) {
//        String token = userService.login(userRequestDto);
//        return ResponseEntity.ok(token);
//    }
}
