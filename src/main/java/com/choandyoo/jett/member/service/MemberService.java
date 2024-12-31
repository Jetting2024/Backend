package com.choandyoo.jett.member.service;

import com.choandyoo.jett.config.KakaoOAuth2Config;
import com.choandyoo.jett.jwt.JwtToken;
import com.choandyoo.jett.jwt.JwtUtil;
import com.choandyoo.jett.member.dto.*;
import com.choandyoo.jett.member.enums.Role;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.choandyoo.jett.member.entity.Member;
import com.choandyoo.jett.member.exception.DuplicateEmailException;
import com.choandyoo.jett.member.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
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
        Member member = memberRepository.findMemberByEmail(loginRequestDto.getEmail()).orElseThrow(()-> new RuntimeException("no user"));
        JwtToken jwtToken = jwtUtil.generateToken(member.getEmail());
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
    public TokenResponseDto getKakaoToken(String code) {
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

        String grantType = responseBody.get("token_type");
        String accessToken = responseBody.get("access_token");
        String refreshToken = responseBody.get("refresh_token");
        TokenResponseDto tokenResponseDto = loginKakaoMember(accessToken);

        return tokenResponseDto;
    }

    @Transactional
    public void updateLastLoginDate(String email) {
        Member member = memberRepository.findMemberByEmail(email).orElseThrow(() -> new RuntimeException("no user"));
        member.updateLastLoginDate();
    }

    @Transactional
    public TokenResponseDto loginKakaoMember(String accessToken) {
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        String id = "";
        String name = "";
        JwtToken jwtToken = new JwtToken();
        try {
            URL url = new URL(reqURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            //요청에 필요한 Header에 포함될 내용
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = connection.getResponseCode();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";
            while((line = reader.readLine()) != null) {
                result += line;
            }

            JsonObject object = (JsonObject) JsonParser.parseString(result);
            JsonObject properties = (JsonObject) object.getAsJsonObject().get("properties");

            name = properties.get("nickname").getAsString();
            //email을 카카오에서 받아올 수 없으니 일단 ID로 테스트용
//            id = object.get("id").getAsString();

            jwtToken = jwtUtil.generateToken(name);

        } catch(IOException e) {
            e.printStackTrace();
        }

        boolean isDuplicate = memberRepository.findMemberByEmail(name).isPresent();
        if(!isDuplicate) {
            Member savedMember = Member.builder()
                    .name(name)
                    .email(name)
                    .password("1111")  //비밀번호 1111로 테스트용
                    .createdDate(LocalDateTime.now())
                    .lastLoginDate(LocalDateTime.now())
                    .role(Role.ROLE_USER)
                    .build();
            memberRepository.save(savedMember);
        } else {
            updateLastLoginDate(name);
        }
        Member member = memberRepository.findMemberByEmail(name).orElseThrow(() -> new RuntimeException("no user"));
        return new TokenResponseDto(member.getId(), jwtToken);
    }

    @Transactional
    public MemberDto getMember(Long idx) {
        Member member = memberRepository.findById(idx).orElseThrow(() -> new RuntimeException("no user"));
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}
