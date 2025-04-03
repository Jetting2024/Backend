package com.jett.domain.schedule.dto;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ScheduleResponse {

    private Long scheduleId;
    private String placeName;
    private String placeLocation;
    private String placeUrl;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDate dayNum;

}