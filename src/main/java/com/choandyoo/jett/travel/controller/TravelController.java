package com.choandyoo.jett.travel.controller;

import com.choandyoo.jett.common.CustomApiResponse;
import com.choandyoo.jett.travel.dto.request.TravelInviteRequest;
import com.choandyoo.jett.travel.dto.request.TravelRequest;
import com.choandyoo.jett.travel.dto.response.TravelResponse;
import com.choandyoo.jett.travel.service.KakaoService;
import com.choandyoo.jett.travel.service.TravelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Travel", description = "여행 관련 API")
@RequestMapping("/travel")
@RestController
@AllArgsConstructor
@Slf4j
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
    @PostMapping("/{userId}")
    public ResponseEntity<CustomApiResponse<String>> addTravel(@RequestBody TravelRequest travelRequest, @PathVariable Long userId) {
        travelService.addTravel(userId,travelRequest);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess("여행 생성됌"));
    }
    @Operation(summary = "여행에 친구 초대",description = " 여행에 친구들 초대하기")
    @PostMapping("/invite/{travelId}")
    public ResponseEntity<CustomApiResponse<String>> inviteTravel(@RequestBody TravelInviteRequest travelInviteRequest, @PathVariable Long travelId) {
        travelService.inviteTravel(travelInviteRequest,travelId);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess("친구 초대됌"));
    }

    @Operation(summary = "그 유저에 대한 여행 조회", description = "그 유저에 대한 전체 모든 여행 조회")
    @GetMapping("/lists/{userId}")
    public ResponseEntity<CustomApiResponse<List<TravelResponse>>> checkTravelSchedule(@PathVariable Long userId) {
        List<TravelResponse> checkTravelResult = travelService.getAllTravel(userId);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(checkTravelResult));
    }
    @Operation(summary = "소프트 딜리트 여행 삭제",description = "여행 삭제")
    @DeleteMapping("/Hard/{userId}/{travelId}")
    public ResponseEntity<CustomApiResponse<String>> deleteTravel(@PathVariable Long userId,@PathVariable Long travelId) {
        travelService.deleteTravel(userId,travelId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CustomApiResponse.onSuccess(null));

    }

}



