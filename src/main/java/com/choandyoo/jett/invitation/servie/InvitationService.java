package com.choandyoo.jett.invitation.servie;

import com.choandyoo.jett.chat.service.ChatService;
import com.choandyoo.jett.config.CustomUserDetails;
import com.choandyoo.jett.invitation.component.RedisService;
import com.choandyoo.jett.invitation.dto.InviteClickDto;
import com.choandyoo.jett.invitation.dto.InviteStatusDto;
import com.choandyoo.jett.member.entity.Member;
import com.choandyoo.jett.member.repository.MemberRepository;
import com.choandyoo.jett.travel.dto.request.TravelInviteRequest;
import com.choandyoo.jett.travel.entity.Travel;
import com.choandyoo.jett.travel.repository.TravelRepository;
import com.choandyoo.jett.travel.service.TravelService;
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
    private final TravelService travelService;
    private final ChatService chatService;
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
        travelService.inviteTravel(travelInviteRequest, travelId);
        chatService.addChatroomMember(member.getId(), travelId);
    }
}
