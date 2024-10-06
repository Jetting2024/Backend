package com.choandyoo.jett.user.dto;


import com.choandyoo.jett.user.entity.UserEntity;


import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class MemberInfoRequestDto {
    private String name;
    private String email;
    private String password;

    public UserEntity toSaveMember() {
        return UserEntity.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .build();
    }

    public void encodePassword(String encodingPassword) {
        this.password = encodingPassword;
    }
}