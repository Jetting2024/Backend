package com.choandyoo.jett.travel.dto;

import com.choandyoo.jett.member.entity.Member;
import com.choandyoo.jett.member.enums.Role;
import com.choandyoo.jett.travel.entity.Travel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelRequest {
    private String travelName;


    public Travel toSaveTravel(String travelName) {
        return Travel.builder()
                .travelName(travelName)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

}
