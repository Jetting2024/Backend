package com.choandyoo.jett.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class AllTravelResponseDto {
    private Long travelId;
    private String travelName;
    private LocalDateTime createdAt;

}


