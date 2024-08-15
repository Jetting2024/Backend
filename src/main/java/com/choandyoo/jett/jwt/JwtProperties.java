package com.choandyoo.jett.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secretKey;
    private String authType;
    private String authoritiesKey;
    private String accessTokenHeader;
    private String refreshTokenHeader;
    private String accessTokenExpire;
    private String refreshTokenExpire;
}
