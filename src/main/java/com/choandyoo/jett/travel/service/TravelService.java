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
import com.choandyoo.jett.travel.enums.Role;
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

    public List<TravelResponse> getAllTravel(Long userId) {
        List<Travel> travels = travelRepository.findByTravelMembers_Member_Id(userId);
        return travels.stream()
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
                .role(Role.ROLE_ADMIN)
                .build();
        travelMemberRepository.save(travelMember);

    }
    @Transactional
    public void deleteTravel(Long userId ,Long  travelId) {
        if (!travelRepository.existsById(travelId)) {
            throw new EntityNotFoundException("Travel not found with id: " + travelId);
        }
        TravelMember travelMember=travelMemberRepository.findByMember_IdAndTravel_TravelId(userId, travelId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없거나 그 유저에 대한 트래블 아이디가 틀립니다."));
        if (travelMember.getRole() != Role.ROLE_ADMIN) {
            throw new IllegalArgumentException("이 유저는 해당 여행에 대한 권한이 없습니다(여행 생성자만 추가,수정,삭제 가능.");
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
                .member(member)
                .travel(travel)
                .role(Role.ROLE_USER)
                .build();
        travelMemberRepository.save(travelMember);

    }



}
