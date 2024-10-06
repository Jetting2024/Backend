package com.choandyoo.jett.schedule.controller;

import com.choandyoo.jett.schedule.service.ScheduleService;
import com.choandyoo.jett.travel.dto.AllTravelResponseDto;
import com.choandyoo.jett.travel.dto.OnlyTravelResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Travel",description = "여행관련API")
@RestController
@RequestMapping("/api/v1/travel")
public class ScheduleController {
    private final ScheduleService scheduleService;
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;

    }
    @Operation(summary = "선택 여행계획 조회",description = "선택 여행계획 조회합니다")
    @GetMapping("/lists/{travelId}") // travelId로 변경
    public ResponseEntity<List<OnlyTravelResponseDto>> travel(@PathVariable("travelId") Long travelId) { // travelId로 변경
        List<OnlyTravelResponseDto> travelList = scheduleService.getAllTravelsByUserId(travelId);
        // 응답 반환
        return ResponseEntity.ok(travelList);
    }

}
