package com.sparta.pracspring_blog.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDto {
    private String username;
    private String password;
    private String email;

    public UserRequestDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
