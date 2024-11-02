package com.choandyoo.jett.travel.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelResponse {
    private Long travelId;
    private String travelName;
    private LocalDateTime createdAt;
}


