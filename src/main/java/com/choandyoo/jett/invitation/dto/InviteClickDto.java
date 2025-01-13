package com.choandyoo.jett.invitation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InviteClickDto {
    private Long travelId;
    private Long inviteeId;
    private String invitation;

}
