package com.choandyoo.jett.member.controller;

import com.choandyoo.jett.member.dto.TokenResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.choandyoo.jett.common.ApiResponse;
import com.choandyoo.jett.member.dto.LoginRequestDto;
import com.choandyoo.jett.member.dto.MemberInfoRequestDto;
import com.choandyoo.jett.member.service.MemberService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/member")
@RestController
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse<Long>> signUp(@RequestBody MemberInfoRequestDto memberInfoRequestDto) {
        Long memberId = memberService.signUp(memberInfoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess(memberId));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {
        TokenResponseDto tokenResponseDto = memberService.login(loginRequestDto);
        memberService.updateLastLoginDate(loginRequestDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.onSuccess(tokenResponseDto));
    }    
}
