package com.choandyoo.jett.schedule.dto;

import com.choandyoo.jett.schedule.entity.Schedule;
import com.choandyoo.jett.travel.entity.Travel;
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

public class ScheduleRequest {

    private String placeLocation;
    private String placeName;
    private String placeUrl ;
    private LocalDate dayNum;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal latitude;
    private BigDecimal longitude;


    public Schedule toSaveSchedule(Travel travel) {
        return Schedule.builder()
                .travel(travel)
                .placeName(placeName)
                .placeLocation(placeLocation)
                .placeUrl(placeUrl)
                .dayNum(dayNum)
                .startTime(startTime)
                .endTime(endTime)
                .latitude(latitude)
                .longitude(longitude)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
