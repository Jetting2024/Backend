package com.choandyoo.jett.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class TravelResponseDto {
    private Long travelId;
    private String travelName;
    private LocalDateTime createdAt;

}
