package com.choandyoo.jett.travel.controller;

import com.choandyoo.jett.common.CustomApiResponse;
import com.choandyoo.jett.travel.service.TravelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Travel", description = "여행 관련 API")
@RequestMapping("/travel")
@RestController
@AllArgsConstructor
public class TravelController {
    private final TravelService travelService;

    @Operation(summary = "장소 검색", description = "키워드를 통해 장소 검색하기")
    @GetMapping("/kakao/searchKeyword")
    public ResponseEntity<CustomApiResponse<String>> searchKeyword(@RequestParam("query") String query, @RequestParam(value = "page", defaultValue = "1") String page) {
        String searchResult = travelService.searchKeyword(query, page);
        return ResponseEntity.status(HttpStatus.OK).body(CustomApiResponse.onSuccess(searchResult));
    }


}
