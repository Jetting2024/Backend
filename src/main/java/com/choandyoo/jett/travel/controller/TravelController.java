package com.choandyoo.jett.travel.controller;

import com.choandyoo.jett.common.CustomApiResponse;
import com.choandyoo.jett.config.CustomUserDetails;
import com.choandyoo.jett.travel.dto.TravelRequest;
import com.choandyoo.jett.travel.dto.TravelResponse;
import com.choandyoo.jett.travel.service.KakaoService;
import com.choandyoo.jett.travel.service.TravelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Travel", description = "여행 관련 API")
@RequestMapping("/travel")
@RestController
@AllArgsConstructor
public class TravelController {
    private final KakaoService kakaoService;
    private final TravelService travelService;

    @Operation(summary = "장소 검색", description = "키워드를 통해 장소 검색하기")
    @GetMapping("/kakao/searchKeyword")
    public ResponseEntity<CustomApiResponse<String>> searchKeyword(@RequestParam("query") String query, @RequestParam(value = "page", defaultValue = "1") String page) {
        String searchResult = kakaoService.searchKeyword(query, page);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(searchResult));
    }
    @Operation(summary = "여행 생성",description = " 여행 생성하기")
    @PostMapping()
    public ResponseEntity<CustomApiResponse<String>> addTravel(@RequestBody TravelRequest travelRequest) {
        travelService.addTravel(travelRequest);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess("여행 생성됌"));
    }

    @Operation(summary = "여행 조회", description = "그 유저에 대한 전체 모든 여행 일정조회")
    @GetMapping("/lists")
    public ResponseEntity<CustomApiResponse<List<TravelResponse>>> checkTravelSchedule(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getId();
        List<TravelResponse> checkTravelResult = travelService.getAllTravelByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(checkTravelResult));
    }
    @Operation(summary = "소프트 딜리트 여행 삭제",description = "여행 삭제")
    @DeleteMapping("/Hard/{travelId}")
    public ResponseEntity<CustomApiResponse<String>> deleteTravel(@PathVariable Long travelId) {
        travelService.deleteTravel(travelId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CustomApiResponse.onSuccess(null));

    }
    //여행조회 테스트 하기
    @Operation(summary = "여행 조회", description = "특정 유저에 대한 여행 일정 조회(jwt 사용안한테스트)")
    @GetMapping("/test/{userId}")
    public ResponseEntity<List<TravelResponse>> getTravelByUserId(@PathVariable Long userId) {
        List<TravelResponse> travelList = travelService.Test_getAllTravelByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(travelList);
    }



}



