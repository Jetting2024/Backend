package com.choandyoo.jett.invitation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class InviteClickDto {
    private Long travelId;
    private Long inviteeId;
    private String inviteeName;
    private String invitation;

}
