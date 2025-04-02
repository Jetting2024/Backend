package com.jett.domain.member.dto;

import java.time.LocalDateTime;

import com.jett.domain.member.entity.Member;
import com.jett.domain.member.enums.Role;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@Builder
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

    public void encodePassword(String encodingPassword) {
        this.password = encodingPassword;
    }
}
