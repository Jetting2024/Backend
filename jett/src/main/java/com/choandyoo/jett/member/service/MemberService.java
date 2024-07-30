package com.choandyoo.jett.member.service;

import com.choandyoo.jett.jwt.JwtToken;
import com.choandyoo.jett.jwt.JwtTokenProvider;
import com.choandyoo.jett.member.dto.TokenResponseDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.choandyoo.jett.member.dto.LoginRequestDto;
import com.choandyoo.jett.member.dto.MemberInfoRequestDto;
import com.choandyoo.jett.member.entity.Member;
import com.choandyoo.jett.member.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Long signUp(MemberInfoRequestDto memberInfoRequestDto) {
        boolean isDuplicate = memberRepository.findMemberByEmail(memberInfoRequestDto.getEmail()).isPresent();
        if(isDuplicate) {
            throw new RuntimeException("Duplicate Email");
        }
        memberInfoRequestDto.encodePassword(passwordEncoder.encode(memberInfoRequestDto.getPassword()));
        Member savedMember = memberRepository.save(memberInfoRequestDto.toSaveMember());
        return savedMember.getId();
    }

    @Transactional
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
        Member member = memberRepository.findMemberByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new RuntimeException("no user"));
        return new TokenResponseDto(member.getId(), jwtToken);
    }

    @Transactional
    public void updateLastLoginDate(String email) {
        Member member = memberRepository.findMemberByEmail(email).orElseThrow(() -> new RuntimeException("no user"));
        member.updateLastLoginDate();
    }
}
