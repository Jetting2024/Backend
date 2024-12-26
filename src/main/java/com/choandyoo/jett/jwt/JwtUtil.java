package com.choandyoo.jett.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public JwtToken generateToken(String email) {
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + 1000 * 60 * 60 * 24); //24시간
        String accessToken = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(accessTokenExpiresIn)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();

        Date refreshTokenExpiresIn = new Date(now + 604800000);
        String refreshToken = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(refreshTokenExpiresIn)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();

        return JwtToken.builder()
                .grantType(jwtProperties.getAuthType())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String extractUseremail(String accessToken) {

        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String accessToken, String email) {
        return email.equals(extractUseremail(accessToken)) && !isTokenExpired(accessToken);
    }

    public boolean isTokenExpired(String accessToken) {

        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(accessToken)
                .getBody()
                .getExpiration()
                .before(new Date());

    }
}

