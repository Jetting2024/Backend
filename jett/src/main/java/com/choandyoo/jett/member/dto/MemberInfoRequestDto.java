package com.choandyoo.jett.member.dto;

import java.time.LocalDateTime;

import com.choandyoo.jett.member.entity.Member;
import com.choandyoo.jett.member.enums.Role;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class MemberInfoRequestDto {
    private String name;
    private String email;
    private String password;

    public Member toSaveMember() {
        return Member.builder()
            .name(this.name)
            .email(this.email)
            .password(this.password)
            .createdDate(LocalDateTime.now())
            .lastLoginDate(LocalDateTime.now())
            .role(Role.ROLE_USER)
            .build();
    }
}
