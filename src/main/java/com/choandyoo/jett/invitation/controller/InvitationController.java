package com.choandyoo.jett.invitation.controller;

import com.choandyoo.jett.common.CustomApiResponse;
import com.choandyoo.jett.invitation.dto.InviteClickDto;
import com.choandyoo.jett.invitation.dto.InviteStatusDto;
import com.choandyoo.jett.invitation.servie.InvitationService;
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
            template.convertAndSend("/alert/" + inviteClickDto.getTravelId(), inviteClickDto);
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
        template.convertAndSend("/alert/" + inviteStatusDto.getTravelId(), inviteStatusDto);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess("success response"));
    }

}
