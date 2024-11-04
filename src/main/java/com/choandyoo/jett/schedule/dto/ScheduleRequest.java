package com.choandyoo.jett.schedule.dto;

import com.choandyoo.jett.schedule.entity.Schedule;
import com.choandyoo.jett.travel.entity.Travel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ScheduleRequest {

    private String contactNumber;
    private String placeLocation;
    private String placeName;
    private String placeUrl;
    private LocalDateTime date;
    private String image;

    public Schedule toSaveSchedule(Travel travel, ScheduleRequest scheduleRequest) {
        return Schedule.builder()
                .travel(travel)
                .contactNumber(scheduleRequest.getContactNumber())
                .date(scheduleRequest.getDate())
                .placeName(scheduleRequest.getPlaceName())
                .placeLocation(scheduleRequest.getPlaceLocation())
                .placeUrl(scheduleRequest.getPlaceUrl())
                .image(scheduleRequest.getImage())
                .build();
    }
}
