package com.choandyoo.jett.user.controller;

import com.choandyoo.jett.common.CustomApiResponse;
import com.choandyoo.jett.user.service.UserService;
import com.choandyoo.jett.user.dto.MemberInfoRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "회원 관련 API")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "회원 가입", description = "새로운 회원을 등록합니다.")
    @PostMapping("/signUp")
    public ResponseEntity<CustomApiResponse<Long>> signUp(@RequestBody MemberInfoRequestDto memberInfoRequestDto) {
        Long userid= userService.signUp(memberInfoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomApiResponse.onSuccess(userid));
    }

}
