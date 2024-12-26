package com.choandyoo.jett.schedule.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ScheduleResponse {

    private Long scheduleId;
    private String placeName;
    private String placeLocation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}