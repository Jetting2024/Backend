package com.choandyoo.jett.invitation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class InviteStatusDto {
    private Long travelId;
    private Long inviteeId;
    private String status;
}
