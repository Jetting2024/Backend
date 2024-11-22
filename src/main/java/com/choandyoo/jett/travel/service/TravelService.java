package com.choandyoo.jett.travel.service;


import com.choandyoo.jett.member.entity.Member;
import com.choandyoo.jett.member.repository.MemberRepository;
import com.choandyoo.jett.member.service.MemberService;
import com.choandyoo.jett.travel.dto.request.TravelInviteRequest;
import com.choandyoo.jett.travel.dto.request.TravelRequest;
import com.choandyoo.jett.travel.dto.response.TravelResponse;
import com.choandyoo.jett.travel.entity.Travel;
import com.choandyoo.jett.travel.entity.TravelMember;
import com.choandyoo.jett.travel.enums.Role;
import com.choandyoo.jett.travel.repository.TravelMemberRepository;
import com.choandyoo.jett.travel.repository.TravelRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class TravelService {
    private final TravelRepository travelRepository;
    private final MemberRepository memberRepository;
    private final TravelMemberRepository travelMemberRepository;

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
    @Transactional
    public void inviteFriendsToTravel(Long userId,Long travelId,TravelInviteRequest travelInviteRequest){
        List<String> friendEmails = travelInviteRequest.getFriendEmails();
        for (String email : friendEmails) {
            Optional<Member> optionalMember = memberRepository.findMemberByEmail(email);
            optionalMember.ifPresent(member -> {
                // Travel 객체를 찾고, TravelMember 객체를 빌더 패턴을 통해 생성
                Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new RuntimeException("Travel not found"));

                Role role = member.getId().equals(userId) ? Role.ROLE_ADMIN : Role.ROLE_USER;

                TravelMember travelMember = TravelMember.builder()
                        .travel(travel)
                        .member(member)
                        .role(role)  // 초대된 사람에게는 ROLE_USER, 초대자는 ROLE_ADMIN
                        .build();

                travelMemberRepository.save(travelMember);  // TravelMember 저장
            });
        }

    }


}
