package com.choandyoo.jett.travel.service;

import com.choandyoo.jett.travel.dto.TravelRequest;
import com.choandyoo.jett.travel.dto.TravelResponse;
import com.choandyoo.jett.travel.entity.Travel;
import com.choandyoo.jett.travel.repository.TravelRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class TravelService {
    private final TravelRepository travelRepository;

    public List<TravelResponse> Test_getAllTravelByUserId(Long userId) {
        // 특정 사용자가 참여한 여행을 조회하고, TravelResponse로 변환
        return travelRepository.findByTravelMembers_Member_Id(userId).stream()
                .map(travel -> TravelResponse.builder()
                        .travelId(travel.getTravelId())
                        .travelName(travel.getTravelName())
                        .createdAt(travel.getCreatedAt())
                        .participants(travel.getTravelMembers().stream()
                                .map(travelMember -> travelMember.getMember().getName())  // TravelMember를 통해 참여자 이름 추출
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
    public List<TravelResponse> getAllTravelByUserId(Long userId) {
        // 특정 사용자가 참여한 여행을 조회하고, TravelResponse로 변환
        return travelRepository.findByTravelMembers_Member_Id(userId).stream()
                .map(travel -> TravelResponse.builder()
                        .travelId(travel.getTravelId())
                        .travelName(travel.getTravelName())
                        .createdAt(travel.getCreatedAt())
                        .participants(travel.getTravelMembers().stream()
                                .map(travelMember -> travelMember.getMember().getName())  // TravelMember를 통해 참여자 이름 추출
                                .collect(Collectors.toList()))
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
