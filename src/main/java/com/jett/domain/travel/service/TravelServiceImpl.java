package com.jett.domain.travel.service;

import com.jett.domain.member.entity.Member;
import com.jett.domain.member.repository.MemberRepository;
import com.jett.domain.travel.dto.request.TravelInviteRequest;
import com.jett.domain.travel.dto.request.TravelRequest;
import com.jett.domain.travel.dto.response.TravelResponse;
import com.jett.domain.travel.entity.Travel;
import com.jett.domain.travelMember.entity.TravelMember;
import com.jett.domain.travelMember.repository.TravelMemberRepository;
import com.jett.domain.travel.repository.TravelRepository;
import com.jett.domain.travel.enums.Role;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class TravelServiceImpl implements TravelService {
    private final TravelRepository travelRepository;
    private final MemberRepository memberRepository;
    private final TravelMemberRepository travelMemberRepository;

    @Override
    @Transactional
    public List<TravelResponse> getAllTravel(Long userId) {
        List<Travel> travels = travelRepository.findByTravelMembers_Member_Id(userId);
        return travels.stream()
                .map(travel -> TravelResponse.builder()
                        .travelId(travel.getTravelId())
                        .travelName(travel.getTravelName())
                        .startDate(travel.getStartDate())
                        .endDate(travel.getEndDate())
                        .participants(travel.getTravelMembers().stream()
                                .map(travelMember -> travelMember.getMember().getName())  // TravelMember를 통해 참여자 이름 추출
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public Long addTravel(Long userId, TravelRequest travelRequest) {
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
        return travel.getTravelId();

    }
    @Override
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
    @Override
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
    @Override
    @Transactional
    public TravelResponse checkOnlyTravelSchedule(Long userId, Long travelId) {
        if (!travelRepository.existsById(travelId)) {
            throw new EntityNotFoundException("Travel not found with id: " + travelId);
        }
        Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new EntityNotFoundException("Travel not found with id: " + travelId));
        return TravelResponse.builder()
            .travelId(travel.getTravelId())
            .travelName(travel.getTravelName())
            .startDate(travel.getStartDate())
            .endDate(travel.getEndDate())
            .participants(travel.getTravelMembers().stream()
                .map(travelMember -> travelMember.getMember().getName())
                .collect(Collectors.toList()))
            .build();
    }


}
