package com.choandyoo.jett.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class MemberDto {
    private Long id;
    private String email;
    private String name;
}
