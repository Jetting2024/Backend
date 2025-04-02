package com.jett.domain.member.service;

import com.jett.domain.member.dto.LoginRequestDto;
import com.jett.domain.member.dto.MemberDto;
import com.jett.domain.member.dto.MemberInfoRequestDto;
import com.jett.domain.member.dto.TokenResponseDto;

public interface MemberService {
  Long signUp(MemberInfoRequestDto memberInfoRequestDto);

  TokenResponseDto login(LoginRequestDto loginRequestDto);

  String kakaoConnect();

  TokenResponseDto getKakaoToken(String code);

  void updateLastLoginDate(String email);

  TokenResponseDto loginKakaoMember(String accessToken);

  MemberDto getMember(Long idx);





}
