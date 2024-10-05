package com.choandyoo.jett.member.service;

import com.choandyoo.jett.config.KakaoOAuth2Config;
import com.choandyoo.jett.jwt.JwtToken;
import com.choandyoo.jett.jwt.JwtTokenProvider;
import com.choandyoo.jett.member.dto.TokenResponseDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.choandyoo.jett.member.dto.LoginRequestDto;
import com.choandyoo.jett.member.dto.MemberInfoRequestDto;
import com.choandyoo.jett.member.entity.Member;
import com.choandyoo.jett.member.exception.DuplicateEmailException;
import com.choandyoo.jett.member.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoOAuth2Config kakaoOAuth2Config;

    @Transactional
    public Long signUp(MemberInfoRequestDto memberInfoRequestDto) {
    boolean isDuplicate =
    memberRepository.findMemberByEmail(memberInfoRequestDto.getEmail()).isPresent();
    if (isDuplicate) {
    throw new DuplicateEmailException("Duplicate Email");
    }
    memberInfoRequestDto.encodePassword(passwordEncoder.encode(memberInfoRequestDto.getPassword()));
    Member savedMember =
    memberRepository.save(memberInfoRequestDto.toSaveMember());
    return savedMember.getId();
    }

    @Transactional
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequestDto.getEmail(), loginRequestDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
        Member member = memberRepository.findMemberByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("no user"));
        return new TokenResponseDto(member.getId(), jwtToken);
    }

    @Transactional
    public String kakaoConnect() {
        String client_id = kakaoOAuth2Config.getClientId();
        String redirect_uri = kakaoOAuth2Config.getRedirectUri();
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=" + client_id);
        url.append("&redirect_uri="+redirect_uri);
        url.append("&response_type=code");
        return url.toString();
    }

    @Transactional
    public String getKakaoAccessToken(String code) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoOAuth2Config.getClientId());
        params.add("redirect_uri", kakaoOAuth2Config.getRedirectUri());
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                httpEntity,
                Object.class
        );

        Map<String, String> responseBody = (Map<String, String>) response.getBody();

        String accessToken = responseBody.get("access_token");

        getKakaoUser(accessToken);
        return "kakao login success";
    }

    @Transactional
    public void getKakaoUser(String accessToken) {
        //Kakao 유저 가져오기
    }

    @Transactional
    public void updateLastLoginDate(String email) {
        Member member = memberRepository.findMemberByEmail(email).orElseThrow(() -> new RuntimeException("no user"));
        member.updateLastLoginDate();
    }
}
