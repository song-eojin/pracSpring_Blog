package com.sparta.pracspring_blog.user.dto;

import com.sparta.pracspring_blog.user.entity.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;

@Setter
@Getter
public class UserRequestDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]{3,10}$", message = "최소 3자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9) 로 구성되어야 합니다.")
    private String username;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]{8,15}$", message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9) 로 구성되어야 하며, 비밀번호에 아이디가 포함해서는 안 됩니다.")
    private String password;

    @NotBlank(message = "비밀번호를 확인해주세요.")
    private String pwConfirm;

    private UserRoleEnum role; // 회원 권한 (ADMIN, USER)
}