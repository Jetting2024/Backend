package com.choandyoo.jett.travel.service;

import com.choandyoo.jett.member.entity.Member;
import com.choandyoo.jett.member.repository.MemberRepository;
import com.choandyoo.jett.travel.dto.request.TravelInviteRequest;
import com.choandyoo.jett.travel.dto.request.TravelRequest;
import com.choandyoo.jett.travel.dto.response.TravelResponse;
import com.choandyoo.jett.travel.entity.Travel;
import com.choandyoo.jett.travel.entity.TravelMember;
import com.choandyoo.jett.travel.repository.TravelMemberRepository;
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
    private final MemberRepository memberRepository;
    private final TravelMemberRepository travelMemberRepository;

    public List<TravelResponse> getAllTravelByUserId(Long userId) {
        return travelRepository.findById(userId).stream()
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
    public void addTravel(Long userId,TravelRequest travelRequest) {
        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. ID: " + userId));
        Travel travel = travelRequest.toSaveTravel();
        travelRepository.save(travel);
        TravelMember travelMember = TravelMember.builder()
                .travel(travel)
                .member(user)
                .build();
        travelMemberRepository.save(travelMember);

    }
    @Transactional
    public void deleteTravel(Long  travelId) {
        if (!travelRepository.existsById(travelId)) {
            throw new EntityNotFoundException("Travel not found with id: " + travelId);
        }
        travelRepository.deleteById(travelId);
    }
    @Transactional
    public void inviteTravel(TravelInviteRequest travelInviteRequest , Long travelId){
        Travel travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new EntityNotFoundException("Travel not found with id: " + travelId));
        String email = travelInviteRequest.getEmail();
        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 사용자가 없습니다."));
        TravelMember travelMember = TravelMember.builder()
                .member(member)  // 초대된 사용자
                .travel(travel)  // 해당 여행
                .build();
        travelMemberRepository.save(travelMember);

    }



}
