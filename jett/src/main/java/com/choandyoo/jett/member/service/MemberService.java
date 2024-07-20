package com.choandyoo.jett.member.service;

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

    @Transactional
    public Long signUp(MemberInfoRequestDto memberInfoRequestDto) {
        boolean isDuplicate = memberRepository.findMemberByEmail(memberInfoRequestDto.getEmail()).isPresent();
        if(isDuplicate) {
            throw new RuntimeException("Duplicate Email");
        }
    
        Member savedMember = memberRepository.save(memberInfoRequestDto.toSaveMember());
        return savedMember.getId();
    }

    @Transactional
    public Long login(LoginRequestDto loginRequestDto) {
        Member member = memberRepository.findMemberByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new RuntimeException("no user"));
        return member.getId();
    }

    @Transactional
    public void updateLastLoginDate(String email) {
        Member member = memberRepository.findMemberByEmail(email).orElseThrow(() -> new RuntimeException("no user"));
        member.updateLastLoginDate();
    }
}
