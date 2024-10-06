package com.choandyoo.jett.travel.service;


import com.choandyoo.jett.travel.dto.TravelResponseDto;
import com.choandyoo.jett.travel.repository.TravelRepository;
import com.choandyoo.jett.user.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelService {
    private final TravelRepository travelRepository;


    public TravelService(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }
    public List<TravelResponseDto> getAllTravelsByUserId(Long userId) {
        // 해당 userId에 대한 여행 목록을 조회하고 DTO로 변환
        return travelRepository.findByUserUserId(userId)
                .stream()
                .map(travel -> new TravelResponseDto(travel.getTravelId(),travel.getTravelName(), travel.getCreatedAt()))
                .collect(Collectors.toList());
    }

}
