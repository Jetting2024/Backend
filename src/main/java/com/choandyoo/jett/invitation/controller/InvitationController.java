package com.choandyoo.jett.invitation.controller;

import com.choandyoo.jett.common.CustomApiResponse;
import com.choandyoo.jett.config.CustomUserDetails;
import com.choandyoo.jett.invitation.servie.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invite")
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping("/{travelId}/invitation")
    public ResponseEntity<CustomApiResponse<String>> generateInvitation(@PathVariable("travelId") Long travelId) {
        String invitation = invitationService.generateInvitation(travelId);
        String inviteUrl = "http://localhost:3000/invite/" + invitation;
        System.out.println(inviteUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomApiResponse.onSuccess(inviteUrl));
    }

    @PostMapping("/{travelId}/{invitation}/response")
    public ResponseEntity<CustomApiResponse<String>> respondToInvite(
            @PathVariable("travelId") Long travelId,
            @PathVariable("invitation") String invitation,
            @RequestParam("status") boolean accept,
            @AuthenticationPrincipal CustomUserDetails customUserDetails)
    {
        if(accept) {
            invitationService.respondToInvite(travelId, invitation, customUserDetails);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(customUserDetails.getUsername() + "님이 초대를 거절하였습니다."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(customUserDetails.getUsername() +"님이 성공적으로 추가되었습니다."));
    }
}
