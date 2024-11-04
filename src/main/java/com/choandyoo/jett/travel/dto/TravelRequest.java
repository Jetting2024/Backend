package com.choandyoo.jett.travel.dto;

import com.choandyoo.jett.member.entity.Member;
import com.choandyoo.jett.member.enums.Role;
import com.choandyoo.jett.travel.entity.Travel;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelRequest {
    private String travelName;
    private LocalDate startDate;
    private LocalDate endDate;



    public Travel toSaveTravel() {
        return Travel.builder()
                .travelName(travelName)
                .startDate(startDate)
                .endDate(endDate)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isDeleted(false)
                .build();
    }

}
