package com.jett.domain.schedule.controller;

import com.jett.domain.schedule.service.ScheduleService;
import com.jett.global.common.CustomApiResponse;
import com.jett.global.config.security.CustomUserDetails;
import com.jett.domain.schedule.dto.ScheduleRequest;
import com.jett.domain.schedule.dto.ScheduleResponse;
import com.jett.domain.schedule.service.ScheduleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Schedule", description = "일정 관련 API")
@RequestMapping("/schedule")
@RestController
@AllArgsConstructor

public class ScheduleController {
    private final ScheduleService scheduleService;
    @Operation(summary = "여행 일정 추가", description = "여행 일정 추가")
    @PostMapping("/{travelId}/add")
    public ResponseEntity<CustomApiResponse<List<Long>>> addSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long travelId , @RequestBody List<ScheduleRequest> scheduleRequests) {
        Long userId=customUserDetails.getId();
        List<Long> scheduleId= scheduleService.addSchedule(userId,travelId, scheduleRequests);
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomApiResponse.onSuccess(scheduleId));
    }


    @Operation(summary = "일정 조회", description = "선택 여행 일정 조회")
    @GetMapping("/lists/{travelId}")
    public ResponseEntity<CustomApiResponse<List<ScheduleResponse>>> getSchedule(@PathVariable Long travelId) {
        List<ScheduleResponse> scheduleResponses = scheduleService.getAllSchedule(travelId);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(scheduleResponses));

    }
    @Operation(summary = "일정 삭제", description = "스케줄 일정 선택 여행 삭제")
    @DeleteMapping("/{travelId}/{scheduleId}")
    public ResponseEntity<CustomApiResponse<String>> deleteSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable Long travelId,@PathVariable Long scheduleId) {
        Long userId=customUserDetails.getId();
        scheduleService.deleteSchedule(userId,travelId,scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CustomApiResponse.onSuccess("일정 삭제됌"));
    }


}

