package com.choandyoo.jett.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor

public class OnlyTravelResponseDto {
    private LocalDateTime date;
    private String location;
}
