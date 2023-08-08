package com.sparta.pracspring_blog.user.controller;

import com.sparta.pracspring_blog.common.dto.MsgResponseDto;
import com.sparta.pracspring_blog.user.dto.UserRequestDto;
import com.sparta.pracspring_blog.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  /* 회원가입 기능 */
  // 1) @RequestBody : HTTP Request의 Body에 있는 데이터를 자바 객체인 UserRequestDto로 변환
  // 2) @Valid : 객체의 유효성 검사
  // 3) 유효성 검사 결과를 BindingResult 객체에 저장
  @PostMapping("/signup")
  public ResponseEntity<MsgResponseDto> signup(@Valid @RequestBody UserRequestDto userRequestDto,
      BindingResult bindingResult) {

    // 4) 유효성 검사 결과 처리

    List<FieldError> fieldErrors = bindingResult.getFieldErrors(); // BindingResult 객체에 필드 형태로 저장된 모든 오류 가져오기

    if (fieldErrors.size() > 0) { // 에러가 있는 경우
      for (FieldError fieldError : bindingResult.getFieldErrors()) {
        log.error(fieldError.getField() + " 에러가 발생한 필드의 메세지 : " + fieldError.getDefaultMessage());
      }

      // 5) 검증 실패시 응답
      return ResponseEntity.status(201)
          .body(new MsgResponseDto("회원가입 실패", HttpStatus.CREATED.value()));
    }

    // 6) 검증 성공시 UserService를 통해 회원가입 비지니스 로직 처리
    userService.signUp(userRequestDto);

    // 7) 검증 성공시 응답
    return ResponseEntity.status(201)
        .body(new MsgResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
  }


  /* 로그인 기능 -> security 로 대체 */
//    @PostMapping("/signIn")
//    public ResponseEntity<MsgResponseDto> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
//        userService.login(userRequestDto);
//
//        //JWT 생성 －＞ Cookie에 저장 －＞ Response 객체에 Cookie를 추가
//        response.addHeader(jwtTokenProvider.AUTHORIZATION_HEADER, jwtTokenProvider.createToken(userRequestDto.getUsername(), userRequestDto.getRole()));
//
//        return ResponseEntity.ok().body(new MsgResponseDto("로그인 성공", HttpStatus.CREATED.value()));
//    }
}
