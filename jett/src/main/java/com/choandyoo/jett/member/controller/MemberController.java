package com.choandyoo.jett.member.controller;

import com.choandyoo.jett.member.dto.TokenResponseDto;
import com.choandyoo.jett.common.CustomApiResponse;
import com.choandyoo.jett.member.dto.LoginRequestDto;
import com.choandyoo.jett.member.dto.MemberInfoRequestDto;
import com.choandyoo.jett.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "회원 관련 API")
@RequestMapping("/member")
@RestController
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원 가입", description = "새로운 회원을 등록합니다.")
    @PostMapping("/signUp")
    public ResponseEntity<CustomApiResponse<Long>> signUp(@RequestBody MemberInfoRequestDto memberInfoRequestDto) {
        Long memberId = memberService.signUp(memberInfoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomApiResponse.onSuccess(memberId));
    }

    @Operation(summary = "로그인", description = "회원 로그인을 처리합니다.")
    @PostMapping("/login")
    public ResponseEntity<CustomApiResponse<TokenResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {
        TokenResponseDto tokenResponseDto = memberService.login(loginRequestDto);
        memberService.updateLastLoginDate(loginRequestDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(tokenResponseDto));
    }
}
