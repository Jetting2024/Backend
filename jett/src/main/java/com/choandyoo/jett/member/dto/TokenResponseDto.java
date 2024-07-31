package com.choandyoo.jett.member.dto;

import com.choandyoo.jett.jwt.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@Builder
@AllArgsConstructor
public class TokenResponseDto {
    private long idx;
    private JwtToken jwtToken;
}
