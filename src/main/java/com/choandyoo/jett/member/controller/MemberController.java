package com.choandyoo.jett.member.controller;

import com.choandyoo.jett.config.CustomUserDetails;
import com.choandyoo.jett.member.dto.*;
import com.choandyoo.jett.common.CustomApiResponse;
import com.choandyoo.jett.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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

    @PostMapping("/test")
    public ResponseEntity<CustomApiResponse<String>> test() {
        System.out.println("hhhhh");
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess("success"));
    }

    @Operation(summary = "카카오 로그인", description = "카카오 로그인을 처리합니다.")
    @GetMapping("/kakao")
    public RedirectView kakaoConnect() {
        String url = memberService.kakaoConnect();
        return new RedirectView(url);
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<CustomApiResponse<TokenResponseDto>> kakaoLogin(@RequestParam("code") String code) {
        TokenResponseDto tokenResponseDto = memberService.getKakaoToken(code);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(tokenResponseDto));
    }

    @Operation(summary = "사용자 정보 가져오기", description = "사용자 정보를 가져옵니다.")
    @GetMapping("/info/{idx}")
    public ResponseEntity<CustomApiResponse<MemberDto>> getMember(@PathVariable("idx")Long idx) {
        MemberDto memberDto = memberService.getMember(idx);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(memberDto));
    }
    @Operation(summary = "사용자 정보 가져오기", description = "사용자 정보를 가져옵니다.")
    @GetMapping("/testInfo")
    public ResponseEntity<CustomApiResponse<MemberDto>> getMember(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId=customUserDetails.getId();
        MemberDto memberDto = memberService.getMember(userId);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(memberDto));
    }

}
