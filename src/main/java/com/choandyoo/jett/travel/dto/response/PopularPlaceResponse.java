package com.choandyoo.jett.travel.dto.response;

import lombok.AllArgsConstructor;

import lombok.Getter;

@AllArgsConstructor
@Getter
public class PopularPlaceResponse {
    private String title;
    private String address;
    private String imageUrl;

}
