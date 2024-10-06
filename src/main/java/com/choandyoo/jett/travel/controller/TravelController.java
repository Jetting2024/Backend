package com.choandyoo.jett.travel.controller;


import com.choandyoo.jett.travel.dto.TravelResponseDto;
import com.choandyoo.jett.travel.service.TravelService;
import com.choandyoo.jett.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="Travel",description = "여행관련API")
@RestController
@RequestMapping("/api/v1/travel")
public class TravelController {
    private TravelService travelService;

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @Operation(summary = "여행조회",description = "모든 여행 일정 조회합니다")
    @GetMapping("/{user_id}/travel")
    public ResponseEntity travel(@PathVariable("user_id") Long user) {
        List<TravelResponseDto> travelList = travelService.getAllTravelsByUserId(user);
        // 응답 반환
        return ResponseEntity.ok(travelList);
    }
}