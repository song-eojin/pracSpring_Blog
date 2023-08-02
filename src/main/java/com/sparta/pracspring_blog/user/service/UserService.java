package com.sparta.pracspring_blog.user.service;

import com.sparta.pracspring_blog.user.dto.UserRequestDto;
import com.sparta.pracspring_blog.user.entity.User;
import com.sparta.pracspring_blog.user.entity.UserRoleEnum;
import com.sparta.pracspring_blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /* 회원가입 기능 */
    public void signUp(UserRequestDto userRequestDto) {
        // UserRequestDto 에서 필요한 정보를 추출하여 UserRepository 를 통해 사용자 정보를 저장
        String username = userRequestDto.getUsername();
        String nickname = userRequestDto.getNickname();
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        UserRoleEnum role = userRequestDto.getRole();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        // 비밀번호 확인은 비밀번호와 정확하게 일치하기
        if (userRequestDto.getPassword().equals(userRequestDto.getPwConfirm())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // 비밀번호에 아이디와 같은 값이 포함된 경우 회원가입에 실패로 만들기
        if (userRequestDto.getPassword().contains(userRequestDto.getUsername())) {
            throw new IllegalArgumentException("비밀번호에 아이디를 사용할 수 없습니다.");
        }

        User user = new User(username, nickname, password, role);
        userRepository.save(user);
    }


    /* 로그인 기능 */
//    public void login(UserRequestDto userRequestDto) {
//        String username = userRequestDto.getUsername();
//        String password = userRequestDto.getPassword();
//
//        //사용자 확인 (username 이 없는 경우)
//        User user = userRepository.findByUsername(username).orElseThrow(
//                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
//        );
//
//        //비밀번호 확인 (password 가 다른 경우)
//        if(!passwordEncoder.matches(password, user.getPassword())) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//    }
}
