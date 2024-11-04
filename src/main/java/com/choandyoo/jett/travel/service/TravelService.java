package com.choandyoo.jett.travel.service;

import com.choandyoo.jett.travel.dto.TravelRequest;
import com.choandyoo.jett.travel.dto.TravelResponse;
import com.choandyoo.jett.travel.entity.Travel;
import com.choandyoo.jett.travel.repository.TravelRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    public void addTravel(TravelRequest travelRequest) {
        // travelRequest 객체에서 toSaveTravel을 호출하여 Travel 객체 생성
        Travel travel = travelRequest.toSaveTravel();
        travelRepository.save(travel);
    }
    @Transactional
    public void deleteTravel(Long  travelId) {
        if (!travelRepository.existsById(travelId)) {
            throw new EntityNotFoundException("Travel not found with id: " + travelId);
        }
        travelRepository.deleteById(travelId);
    }


}
