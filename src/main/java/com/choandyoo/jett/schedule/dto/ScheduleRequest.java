package com.choandyoo.jett.schedule.dto;

import com.choandyoo.jett.schedule.entity.Schedule;
import com.choandyoo.jett.travel.entity.Travel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ScheduleRequest {

    private String placeLocation;
    private String placeName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    public Schedule toSaveSchedule(Travel travel) {
        return Schedule.builder()
                .travel(travel)
                .placeName(placeName)
                .placeLocation(placeLocation)
                .startTime(startTime)
                .endTime(endTime)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
