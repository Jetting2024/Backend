package com.choandyoo.jett.invitation.controller;

import com.choandyoo.jett.common.CustomApiResponse;
import com.choandyoo.jett.invitation.servie.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
