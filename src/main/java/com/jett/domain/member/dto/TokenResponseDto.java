package com.jett.domain.member.dto;

import com.jett.global.config.jwt.JwtToken;
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
