package com.jett.domain.invitation.servie;

import com.jett.domain.chat.service.ChatServiceImpl;
import com.jett.domain.invitation.component.RedisService;
import com.jett.domain.invitation.dto.InviteClickDto;
import com.jett.domain.invitation.dto.InviteStatusDto;
import com.jett.domain.member.entity.Member;
import com.jett.domain.member.repository.MemberRepository;
import com.jett.domain.travel.dto.request.TravelInviteRequest;
import com.jett.domain.travel.repository.TravelRepository;
import com.jett.domain.travel.service.TravelServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationService {
    private static final String INVITE_LINK_PREFIX = "travelId=%d";
    private final RedisService redisService;
    private final TravelRepository travelRepository;
    private final TravelServiceImpl travelServiceImpl;
    private final ChatServiceImpl chatServiceImpl;
    private final MemberRepository memberRepository;

    @Transactional
    public String generateInvitation(Long travelId) {
        String key = INVITE_LINK_PREFIX.formatted(travelId);
        String existingValue = redisService.getValues(key);

        if(existingValue == null || existingValue.isEmpty()) {
            String randomCode = UUID.randomUUID().toString();
            redisService.setValues(key, randomCode, RedisService.toTomorrow());
            return randomCode;
        }
        return existingValue;
    }

    @Transactional
    public boolean inviteClick(InviteClickDto inviteClickDto) {
        String validInvitation = redisService.getValues("travelId=" + inviteClickDto.getTravelId());

        if(validInvitation == null || validInvitation.isEmpty()) {
            new RuntimeException("Invalid or expired invitation");
        } else if(!validInvitation.equals(inviteClickDto.getInvitation())) {
            new RuntimeException("travelId and invitation do not match");
        }
        return true;
    }

    @Transactional
    public void inviteResponse(InviteStatusDto inviteStatusDto) {
        Long travelId = inviteStatusDto.getTravelId();
        Member member = memberRepository.findById(inviteStatusDto.getInviteeId()).orElseThrow(() -> new RuntimeException("no user"));
        TravelInviteRequest travelInviteRequest = TravelInviteRequest.builder()
                .email(member.getEmail())
                .build();
        travelServiceImpl.inviteTravel(travelInviteRequest, travelId);
        chatServiceImpl.addChatroomMember(member.getId(), travelId);
    }
}
