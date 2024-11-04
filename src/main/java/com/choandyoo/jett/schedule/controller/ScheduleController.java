package com.choandyoo.jett.schedule.controller;

import com.choandyoo.jett.common.CustomApiResponse;
import com.choandyoo.jett.schedule.dto.ScheduleRequest;
import com.choandyoo.jett.schedule.dto.ScheduleResponse;
import com.choandyoo.jett.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Schedule", description = "일정 관련 API")
@RequestMapping("/schedule")
@RestController
@AllArgsConstructor

public class ScheduleController {
    private final ScheduleService scheduleService;
    @Operation(summary = "일정 추가", description = "특정 여행 일정 추가")
    @PostMapping("/{travelId}/add")
    public ResponseEntity<CustomApiResponse<String>> addSchedule(@PathVariable Long travelId , @RequestBody ScheduleRequest scheduleRequest) {
        scheduleService.addSchedule(travelId, scheduleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomApiResponse.onSuccess("일정이 성공적으로 추가되었습니다."));
    }


    @Operation(summary = "일정 조회", description = "선택 여행 일정 조회")
    @GetMapping("/lists/{travelId}")
    public ResponseEntity<CustomApiResponse<List<ScheduleResponse>>> getScheduleByTravelId(@PathVariable Long travelId) {
        List<ScheduleResponse> scheduleResponses = scheduleService.getAllSchedule(travelId);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(scheduleResponses));

    }
    @Operation(summary = "일정 삭제", description = "스케줄 일정 선택 여행 삭제")
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<CustomApiResponse<String>> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CustomApiResponse.onSuccess(null));
    }


}
