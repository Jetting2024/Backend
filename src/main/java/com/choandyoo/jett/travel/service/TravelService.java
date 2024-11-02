package com.choandyoo.jett.travel.service;

import com.choandyoo.jett.travel.dto.TravelResponse;
import com.choandyoo.jett.travel.entity.Travel;
import com.choandyoo.jett.travel.repository.TravelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;

    public List<TravelResponse> getAllTravel() {
        // 모든 Travel 엔티티를 가져와 각 엔티티를 TravelResponse로 변환하여 리스트 반환
        return travelRepository.findAll().stream()
                .map(travel -> TravelResponse.builder()
                        .travelId(travel.getTravelId())
                        .travelName(travel.getTravelName())
                        .createdAt(travel.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

}