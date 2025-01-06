package com.choandyoo.jett.invitation.servie;

import com.choandyoo.jett.chat.service.ChatService;
import com.choandyoo.jett.config.CustomUserDetails;
import com.choandyoo.jett.invitation.component.RedisService;
import com.choandyoo.jett.travel.dto.request.TravelInviteRequest;
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

    @Transactional
    public String generateInvitation(Long travelId) {
        boolean isPresent = travelRepository.findById(travelId).isPresent();
        if(!isPresent) {
            new RuntimeException("해당 여행 일정이 존재하지 않습니다.");
        }

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
    public void respondToInvite(Long travelId, String invitation, CustomUserDetails customUserDetails) {
        String validInvitation = redisService.getValues("travelId=" + travelId.toString());
        Long userId = customUserDetails.getId();

        boolean isPresent = travelRepository.findById(travelId).isPresent();
        if(!isPresent) {
            new RuntimeException("the travel do not exist.");
        } else if (validInvitation == null || validInvitation.isEmpty()) {
            new RuntimeException("Invalid or expired invitation");
        } else if(validInvitation.equals(invitation)) {
            new RuntimeException("travelId and invitation do not match.");
        }
        TravelInviteRequest travelInviteRequest = TravelInviteRequest.builder()
                .email(customUserDetails.getUsername())
                .build();
        travelService.inviteTravel(travelInviteRequest, travelId);
        chatService.addChatroomMember(userId, travelId);
    }
}
