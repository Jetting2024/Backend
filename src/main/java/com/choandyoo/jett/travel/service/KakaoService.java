package com.choandyoo.jett.travel.service;

import com.choandyoo.jett.config.KakaoOAuth2Config;
import lombok.AllArgsConstructor;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class KakaoService {

    private final KakaoOAuth2Config kakaoOAuth2Config;

    public String searchKeyword(String query, String page) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "KakaoAK " + kakaoOAuth2Config.getClientId());

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        String uri = "https://dapi.kakao.com/v2/local/search/keyword.json?"
                + "query=" + query
                + "&page=" + page
                + "&size=7";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, entity, Object.class
        ) ;

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
        List<Map<String, Object>> list = (List<Map<String, Object>>) responseBody.get("documents");

        return list.toString();
    }


}
