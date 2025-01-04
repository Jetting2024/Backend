package com.choandyoo.jett.invitation.servie;

import com.choandyoo.jett.invitation.component.RedisService;
import com.choandyoo.jett.travel.repository.TravelRepository;
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

}
