package com.choandyoo.jett.travel.dto.response;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class PlaceInfoResponse {
    private LocalDateTime travelDateTime;
    private String road_address_name;
    private String category_group_code;
    private String phone;
    private String place_name;
}
