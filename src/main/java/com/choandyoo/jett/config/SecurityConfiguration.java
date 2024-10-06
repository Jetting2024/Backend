package com.choandyoo.jett.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {
    private final CorsConfigurationSource corsConfigurationSource; // CORS 설정 소스

    // URL 허용 목록
    private final String[] ALLOW_URL = {"/", "/member/login", "/member/signUp", "/swagger-ui/**", "/v3/api-docs/**"};
    private final String[] AUTHENTICATED_URL = {"/member/**"}; // 인증이 필요한 URL 목록

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource)) // CORS 설정 추가
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // X-Frame-Options 비활성화
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(ALLOW_URL).permitAll() // 허용된 URL
                                .requestMatchers(AUTHENTICATED_URL).authenticated() // 인증이 필요한 URL
                                .anyRequest().permitAll() // 나머지 요청 허용
                )
                .formLogin(AbstractHttpConfigurer::disable) // 기본 폼 로그인 비활성화
                .logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 요청 URL 설정
                                .invalidateHttpSession(true) // 세션 무효화
                                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)) // 로그아웃 성공 시 HTTP 상태 반환
                                .deleteCookies("JSESSIONID") // 로그아웃 시 JSESSIONID 쿠키 삭제
                );

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // 비밀번호 인코더 설정
    }
}