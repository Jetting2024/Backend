package com.choandyoo.jett.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Data
public class KakaoTokenResponseDto {
    private String tokenType;
    private String accessToken;
    private String accessTokenExpiresIn;
    private String refreshToken;
    private String refreshTokenExpiresIn;
}
