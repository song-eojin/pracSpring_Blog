package com.sparta.pracspring_blog.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.pracspring_blog.common.dto.MsgResponseDto;
import com.sparta.pracspring_blog.common.security.UserDetailsImpl;
import com.sparta.pracspring_blog.user.dto.UserRequestDto;
import com.sparta.pracspring_blog.user.entity.UserRoleEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

/* JwtAuthenticationFilter는 JWT (JSON Web Token) 기반의 인증을 처리하는 필터 */
@Slf4j(topic = "JwtAuthenticationFilter : 로그인 인증 및 생성된 JWT 토큰을 응답 헤더에 추가하는 역할의 필터")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        setFilterProcessesUrl("/api/users/signIn");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            log.info("로그인 로직 시작");
            UserRequestDto userRequestDto = new ObjectMapper().readValue(request.getInputStream(), UserRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequestDto.getUsername(),
                            userRequestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();


        /* 로그인 성공 시, 로그인에 성공한 유저의 정보를 JWT를 활용하여 클라이언트에게 Cookie로 전달하기 */

        // JWT 토큰을 생성하고 관리하는 클래스를 사용하여 토큰 생성
        String token = jwtTokenProvider.createToken(username, role);

        // JWT 토큰을 HTTP 응답 헤더에 추가하고, 이를 통해 클라이언트는 로그인에 성공한 사용자를 식별할 수 있는 토큰을 받게 된다.
        response.addHeader(JwtTokenProvider.AUTHORIZATION_HEADER, token);

        // 로그인 성공 상태 코드로 로그인이 성공적으로 처리되었음을 클라이언트에게 알려주는 역할
        response.setStatus(HttpStatus.OK.value());

        // HTTP 응답의 Content-Type을 JSON으로 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


        // "로그인 성공" 문자열을 응답
        String successMessage = "로그인 성공";
        response.getWriter().write(successMessage);
        response.getWriter().flush();
    }


    /* JWT가 유효하지 않거나 인증에 실패한 경우 */

        // HttpServletRequest request : 클라이언트로부터 온 HTTP 요청 객체
        // HttpServletResponse response : 서버가 클라이언트로 보낼 HTTP 응답 객체
        // AuthenticationException failed: 인증에 실패한 예외 정보를 담고 있는 객체
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(401); //HTTP 응답 상태 코드를 401(Unauthorized)로 설정, 이는 인증이 실패한 상태임을 클라이언트에게 알려주는 역할을 한다.

        log.error("로그인 실패");

        String failureMessage = "로그인 실패"; //로그인 실패"라는 문자열을 failureMessage 변수에 저장
        response.getWriter().write(failureMessage);
        returnResponse(response, "아이디 또는 패스워드를 확인해주세요.");
        response.getWriter().flush(); //응답을 완전히 보내고 버퍼를 비우기
        response.getWriter().close();
    }


    public void returnResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        MsgResponseDto msgResponseDto = new MsgResponseDto(message, response.getStatus());
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(msgResponseDto);
        response.getWriter().write(result);
    }
}