package com.sparta.pracspring_blog.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
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

        String token = jwtTokenProvider.createToken(username, role);
        response.addHeader(JwtTokenProvider.AUTHORIZATION_HEADER, token);
        response.setStatus(HttpStatus.OK.value()); // 로그인 성공 상태 코드
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 응답의 Content-Type을 JSON으로 설정

        // "로그인 성공" 문자열을 응답
        String successMessage = "로그인 성공";
        response.getWriter().write(successMessage);
        response.getWriter().flush();
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.error("로그인 실패");
        String failureMessage = "로그인 실패";
        response.setStatus(401);
        response.getWriter().write(failureMessage);
        response.getWriter().flush();
    }
}