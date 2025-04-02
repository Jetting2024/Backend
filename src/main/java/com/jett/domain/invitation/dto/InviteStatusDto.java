package com.jett.domain.invitation.dto;

import lombok.*;

@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
public class InviteStatusDto {
    private Long travelId;
    private Long inviteeId;
    private String status;
}
