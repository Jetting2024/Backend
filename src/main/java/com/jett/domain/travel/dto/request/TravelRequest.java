package com.jett.domain.travel.dto.request;

import com.jett.domain.travel.entity.Travel;
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
