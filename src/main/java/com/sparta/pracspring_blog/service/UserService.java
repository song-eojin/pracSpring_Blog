package com.sparta.pracspring_blog.service;

import com.sparta.pracspring_blog.dto.UserRequestDto;
import com.sparta.pracspring_blog.entity.User;
import com.sparta.pracspring_blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 기능
    public void signUp(UserRequestDto userRequestDto) {
        // UserRequestDto 에서 필요한 정보를 추출하여 UserRepository 를 통해 사용자 정보를 저장
        String username = userRequestDto.getUsername();
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        String email = userRequestDto.getEmail();

        User user = new User(username, password, email);
        userRepository.save(user);
    }

    
    //security 에서 제공하는 login 기능으로 대체
    //public String login(UserRequestDto userRequestDto) {
    //}
}

