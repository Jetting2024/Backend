package com.choandyoo.jett.travel.controller;

import com.choandyoo.jett.common.CustomApiResponse;
import com.choandyoo.jett.config.CustomUserDetails;
import com.choandyoo.jett.travel.dto.request.TravelInviteRequest;
import com.choandyoo.jett.travel.dto.request.TravelRequest;
import com.choandyoo.jett.travel.dto.response.PopularPlaceResponse;
import com.choandyoo.jett.travel.dto.response.TravelResponse;
import com.choandyoo.jett.travel.service.KakaoService;
import com.choandyoo.jett.travel.service.TourApiService;
import com.choandyoo.jett.travel.service.TravelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Travel", description = "여행 관련 API")
@RequestMapping("/travel")
@RestController
@AllArgsConstructor

public class TravelController {

  private final KakaoService kakaoService;
  private final TravelService travelService;
  private final TourApiService tourApiService;

  @Operation(summary = "장소 검색", description = "키워드를 통해 장소 검색하기")
  @GetMapping("/kakao/searchKeyword")
  public ResponseEntity<CustomApiResponse<String>> searchKeyword(
      @RequestParam("query") String query,
      @RequestParam(value = "page", defaultValue = "1") String page) {
    String searchResult = kakaoService.searchKeyword(query, page);
    return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(searchResult));
  }

  @Operation(summary = "여행 생성", description = " 여행 생성하기")
  @PostMapping()
  public ResponseEntity<CustomApiResponse<Long>> addTravel(@RequestBody TravelRequest travelRequest,
      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    Long userId = customUserDetails.getId();
    Long travelId = travelService.addTravel(userId, travelRequest);
    return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(travelId));
  }

  @Operation(summary = "여행에 친구 초대", description = " 여행에 친구들 초대하기")
  @PostMapping("/invite/{travelId}")
  public ResponseEntity<CustomApiResponse<String>> inviteTravel(
      @RequestBody TravelInviteRequest travelInviteRequest, @PathVariable Long travelId) {
    travelService.inviteTravel(travelInviteRequest, travelId);
    return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess("친구 초대됌"));
  }

  @Operation(summary = "그 유저에 대한 여행 조회", description = "그 유저에 대한 전체 모든 여행 조회")
  @GetMapping("/lists")
  public ResponseEntity<CustomApiResponse<List<TravelResponse>>> checkTravelSchedule(
      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    Long userId = customUserDetails.getId();
    List<TravelResponse> checkTravelResult = travelService.getAllTravel(userId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(CustomApiResponse.onSuccess(checkTravelResult));
  }

  @Operation(summary = "그 유저와 여행번호에 대한 단일 대한 여행 조회", description = "그 유저와 여행아디에 대한 단일 여행 조회")
  @GetMapping("/onlyList/{travelId}")
  public ResponseEntity<CustomApiResponse<TravelResponse>> checkOnlyTravelSchedule(
      @AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long travelId) {
    Long userId = customUserDetails.getId();
    TravelResponse checkTravelResult = travelService.checkOnlyTravelSchedule(userId,travelId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(CustomApiResponse.onSuccess(checkTravelResult));
  }

  @Operation(summary = "소프트 딜리트 여행 삭제", description = "여행 삭제")
  @DeleteMapping("/Hard/{travelId}")
  public ResponseEntity<CustomApiResponse<String>> deleteTravel(
      @AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long travelId) {
    Long userId = customUserDetails.getId();
    travelService.deleteTravel(userId, travelId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CustomApiResponse.onSuccess("여행 삭제됌"));

  }

  @Operation(summary = "지역별 인기여행지 조회", description = "지역을 입력받을 후 해당하는 위치 관광지 조회")
  @GetMapping("/popularLists")
  public ResponseEntity<CustomApiResponse<List<PopularPlaceResponse>>> getPopularPlace(
      @RequestParam String place) {
    try {
      List<PopularPlaceResponse> popularResults = tourApiService.getJsonResponse(place);
      return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(popularResults));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(CustomApiResponse.onFailure(e.getMessage(), null));
    }
  }

}
