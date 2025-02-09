package com.choandyoo.jett.travel.service;

import com.choandyoo.jett.travel.dto.response.PopularPlaceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourApiService {

    @Value("${api.serviceKey}") // ✅ application.yml에서 API 키 가져오기
    private String serviceKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ✅ 허용된 지역 리스트
    private static final List<String> VALID_PLACES = Arrays.asList(
            "서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종",
            "경기", "강원", "충청북도", "충청남도", "전라북도", "전라남도",
            "경상북도", "경상남도", "제주도"
    );

    public List<PopularPlaceResponse> getJsonResponse(String place) {
        List<PopularPlaceResponse> popularPlaces = new ArrayList<>();

        // ✅ 지역명 검증 (허용되지 않은 지역이면 예외 발생)
        if (!VALID_PLACES.contains(place)) {
            throw new IllegalArgumentException("해당 지역은 지원하지 않습니다: " + place);
        }

        try {
            String encodedPlace = URLEncoder.encode(place, StandardCharsets.UTF_8);

            // ✅ API 요청 URL 생성
            String urlString = "http://apis.data.go.kr/B551011/KorService1/searchKeyword1"
                    + "?MobileOS=ETC"
                    + "&MobileApp=MobileApp"
                    + "&_type=json"
                    + "&keyword=" + encodedPlace
                    + "&serviceKey=" + serviceKey;

            log.info("🚀 최종 API 요청 URL: {}", urlString);

            // ✅ API 요청
            HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            // ✅ 응답 처리
            int responseCode = connection.getResponseCode();
            log.info("✅ 응답 상태 코드: {}", responseCode);

            String responseString = "";
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    responseString = response.toString();
                }
            } else {
                log.error("🚨 API 요청 실패, 응답 코드: {}", responseCode);
                return popularPlaces; // 빈 리스트 반환
            }

            connection.disconnect();

            log.info("📌 최종 응답 본문: \n{}", responseString);

            // ✅ JSON 문자열을 Map으로 변환
            Map<String, Object> responseMap = objectMapper.readValue(responseString, Map.class);
            Map<String, Object> response = (Map<String, Object>) responseMap.get("response");
            if (response == null) return popularPlaces;

            Map<String, Object> body = (Map<String, Object>) response.get("body");
            if (body == null) return popularPlaces;

            Map<String, Object> items = (Map<String, Object>) body.get("items");
            if (items == null) return popularPlaces;

            List<Map<String, Object>> itemList = (List<Map<String, Object>>) items.get("item");
            if (itemList == null) return popularPlaces;

            // ✅ 필요한 데이터만 `PopularPlaceResponse`로 변환
            for (Map<String, Object> item : itemList) {
                String title = (String) item.get("title");
                String address = (String) item.getOrDefault("addr1", "주소 없음");
                String imageUrl = item.get("firstimage") != null ? (String) item.get("firstimage") : "https://default-image-url.com/default.jpg";

                popularPlaces.add(new PopularPlaceResponse(title, address, imageUrl));
            }

        } catch (Exception e) {
            log.error("🚨 API 요청 중 오류 발생", e);
        }

        return popularPlaces;
    }
}
