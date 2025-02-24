package com.choandyoo.jett.invitation.controller;

import com.choandyoo.jett.common.CustomApiResponse;
import com.choandyoo.jett.invitation.dto.InviteClickDto;
import com.choandyoo.jett.invitation.dto.InviteStatusDto;
import com.choandyoo.jett.invitation.servie.InvitationService;
import com.choandyoo.jett.member.dto.MemberDto;
import com.choandyoo.jett.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;
    private final SimpMessagingTemplate template;
    private final MemberService memberService;

    @Operation(summary = "초대 링크 생성", description = "travelId에 따른 초대 링크 생성 코드")
    @PostMapping("/invite/{travelId}/invitation")
    public ResponseEntity<CustomApiResponse<String>> generateInvitation(@PathVariable("travelId") Long travelId) {
        String invitation = invitationService.generateInvitation(travelId);
        String inviteUrl = "http://localhost:3000/invite/" + travelId + "/" + invitation;
        System.out.println(inviteUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomApiResponse.onSuccess(inviteUrl));
    }

    @Operation(summary = "초대 링크 클릭", description = "초대 받은 사용자가 초대 링크 클릭 시 초대 한 사용자에게 알림 전송")
    @MessageMapping("/inviteClick")
    public ResponseEntity<CustomApiResponse<String>> inviteClick(@Payload InviteClickDto inviteClickDto) {
        boolean validInvitation = invitationService.inviteClick(inviteClickDto);
        if(validInvitation) {
            //초대 받은 사용자의 이름 조회
            MemberDto memberDto = memberService.getMember(inviteClickDto.getInviteeId());
            inviteClickDto.builder()
                            .travelId(inviteClickDto.getTravelId())
                            .inviteeId(inviteClickDto.getInviteeId())
                            .inviteeName(memberDto.getName())
                            .invitation(inviteClickDto.getInvitation())
                            .build();
            template.convertAndSend("/sub/alert/" + inviteClickDto.getTravelId(), inviteClickDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess("success click"));
    }

    @Operation(summary = "사용자 수락·거절", description = "초대 받은 사용자를 수락·거절")
    @MessageMapping("/inviteResponse")
    public ResponseEntity<CustomApiResponse<String>> inviteResponse(@Payload InviteStatusDto inviteStatusDto) {
        String status = inviteStatusDto.getStatus();
        if(status.equals("ACCEPT")) {
            invitationService.inviteResponse(inviteStatusDto);
        }
        // 수락 또는 거절 상태를 초대 받은 사용자에게 전달
        template.convertAndSend("/sub/inviteStatus/" + inviteStatusDto.getTravelId(), inviteStatusDto);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess("inviteResponse: " + status));
    }

}
