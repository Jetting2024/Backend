package com.choandyoo.jett.travel.dto.response;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelResponse {
    private Long travelId;
    private String travelName;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> participants;
}


