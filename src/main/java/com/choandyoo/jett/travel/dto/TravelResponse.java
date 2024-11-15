package com.choandyoo.jett.travel.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelResponse {
    private Long travelId;
    private String travelName;
    private LocalDateTime createdAt;
    private List<String> participants;
}


