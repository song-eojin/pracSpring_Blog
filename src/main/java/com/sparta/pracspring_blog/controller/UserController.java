package com.sparta.pracspring_blog.controller;

import com.sparta.pracspring_blog.dto.MsgResponseDto;
import com.sparta.pracspring_blog.dto.UserRequestDto;
import com.sparta.pracspring_blog.jwt.JwtUtil;
import com.sparta.pracspring_blog.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    //회원가입 기능
    @PostMapping("/signup")
    public ResponseEntity<MsgResponseDto> signUp(@Valid @RequestBody UserRequestDto userRequestDto) {
        try {
            userService.signUp(userRequestDto);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto("중복된 username 입니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(201).body(new MsgResponseDto("회원가입에 성공하셨습니다.", HttpStatus.CREATED.value()));
        //ResponseEntity: Spring Framework 에서 제공하는 클래스로 HTTP 응답 역할을 하며, 이를 사용하면 상태코드, 응답본문, 헤더 등을 추가적으로 설정해줄 수 있다. 
    }

    //로그인 기능
    @PostMapping("/login")
    public ResponseEntity<MsgResponseDto> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {

        try {
            userService.login(userRequestDto);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto("입력하신 회원 정보를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST.value()));
        }

        //JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userRequestDto.getUsername(), userRequestDto.getRole()));

        return ResponseEntity.ok().body(new MsgResponseDto("로그인에 성공하셨습니다.", HttpStatus.CREATED.value()));
        //ok(): ResponseEntity 에서 제공하는 메서드로 상태코드 200을 생성하는 메서드
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<MsgResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        MsgResponseDto msgResponseDto = new MsgResponseDto(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(
                // HTTP body
                msgResponseDto,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
}
