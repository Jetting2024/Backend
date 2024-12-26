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

    private String contactNumber;
    private String placeLocation;
    private String placeName;
    private String placeUrl;
    private LocalDateTime date;
    private String image;

    public Schedule toSaveSchedule(Travel travel) {
        return Schedule.builder()
                .travel(travel)
                .contactNumber(contactNumber)
                .date(date)
                .placeName(placeName)
                .placeLocation(placeLocation)
                .placeUrl(placeUrl)
                .image(image)
                .build();
    }
}
