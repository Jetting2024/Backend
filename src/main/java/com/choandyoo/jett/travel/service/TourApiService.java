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

    @Value("${api.serviceKey}")
    private String serviceKey;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final List<String> VALID_PLACES = Arrays.asList(
            "서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종",
            "경기", "강원", "충청북도", "충청남도", "전라북도", "전라남도",
            "경상북도", "경상남도", "제주도"
    );
    public List<PopularPlaceResponse> getJsonResponse(String place) {
        List<PopularPlaceResponse> popularPlaces = new ArrayList<>();
        if (!VALID_PLACES.contains(place)) {
            throw new IllegalArgumentException("해당 지역은 지원하지 않습니다: " + place);
        }
        try {
            String encodedPlace = URLEncoder.encode(place, StandardCharsets.UTF_8);
            String urlString = "http://apis.data.go.kr/B551011/KorService1/searchKeyword1"
                    + "?MobileOS=ETC"
                    + "&MobileApp=MobileApp"
                    + "&_type=json"
                    + "&keyword=" + encodedPlace
                    + "&serviceKey=" + serviceKey;

            log.info("최종 API 요청 URL: {}", urlString);

            HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = connection.getResponseCode();
            log.info("응답 상태 코드: {}", responseCode);

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
                log.error("API 요청 실패, 응답 코드: {}", responseCode);
                return popularPlaces;
            }

            connection.disconnect();

            log.info("최종 응답 본문: \n{}", responseString);

            Map<String, Object> responseMap = objectMapper.readValue(responseString, Map.class);
            Map<String, Object> response = (Map<String, Object>) responseMap.get("response");
            if (response == null) return popularPlaces;

            Map<String, Object> body = (Map<String, Object>) response.get("body");
            if (body == null) return popularPlaces;

            Map<String, Object> items = (Map<String, Object>) body.get("items");
            if (items == null) return popularPlaces;

            List<Map<String, Object>> itemList = (List<Map<String, Object>>) items.get("item");
            if (itemList == null) return popularPlaces;

            for (Map<String, Object> item : itemList) {
                String title = (String) item.get("title");
                String address = (String) item.getOrDefault("addr1", "주소 없음");
                String imageUrl = item.get("firstimage") != null ? (String) item.get("firstimage") : "https://default-image-url.com/default.jpg";

                popularPlaces.add(new PopularPlaceResponse(title, address, imageUrl));
            }

        } catch (Exception e) {
            log.error("API 요청 중 오류 발생", e);
        }

        return popularPlaces;
    }
}
