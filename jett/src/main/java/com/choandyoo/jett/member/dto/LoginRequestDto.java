package com.choandyoo.jett.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class LoginRequestDto {
    private String email;
    private String password;
}
