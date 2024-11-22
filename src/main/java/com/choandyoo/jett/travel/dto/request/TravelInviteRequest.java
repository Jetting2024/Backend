package com.choandyoo.jett.travel.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelInviteRequest {
    private List<String> friendEmails;  // 초대할 친구들의 ID 리스트
}
