package com.choandyoo.jett.schedule.service;


import com.choandyoo.jett.schedule.dto.ScheduleRequest;
import com.choandyoo.jett.schedule.dto.ScheduleResponse;
import com.choandyoo.jett.schedule.entity.Schedule;
import com.choandyoo.jett.schedule.repository.ScheduleRepository;
import com.choandyoo.jett.travel.entity.Travel;
import com.choandyoo.jett.travel.entity.TravelMember;
import com.choandyoo.jett.travel.enums.Role;
import com.choandyoo.jett.travel.repository.TravelMemberRepository;
import com.choandyoo.jett.travel.repository.TravelRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TravelRepository travelRepository;
    private final TravelMemberRepository travelMemberRepository;

    @Transactional
    public void addSchedule(Long userId,Long travelId,ScheduleRequest scheduleRequest) {
        TravelMember travelMember = travelMemberRepository.findByMember_IdAndTravel_TravelId(userId, travelId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없거나 그 유저에 대한 트래블 아이디가 틀립니다."));
        if (travelMember.getRole() != Role.ROLE_ADMIN) {
            throw new IllegalArgumentException("이 유저는 해당 여행에 대한 권한이 없습니다(여행 생성자만 일정추가,일정수정,여행과 일정삭제 가능.");
        }
        Travel travel = travelMember.getTravel();
        boolean isDuplicate = scheduleRepository.existsByTravelAndStartTimeBeforeAndEndTimeAfter(
                travel, scheduleRequest.getEndTime(), scheduleRequest.getStartTime()
        );

        if (isDuplicate) {
            throw new IllegalArgumentException("해당 시간대에 겹치는 일정이 이미 존재합니다.");
        }
        Schedule schedule = scheduleRequest.toSaveSchedule(travel);
        scheduleRepository.save(schedule);
    }

    public List<ScheduleResponse> getAllSchedule(Long travelId) {
        return scheduleRepository.findByTravel_TravelId(travelId).stream()
                .map(schedule -> ScheduleResponse.builder()
                        .scheduleId(schedule.getScheduleId())
                        .startTime(schedule.getStartTime())
                        .endTime(schedule.getEndTime())
                        .placeName(schedule.getPlaceName())
                        .placeLocation(schedule.getPlaceLocation())
                        .build())
                .collect(Collectors.toList());
    }
    public void deleteSchedule(Long userId,Long travelId,Long scheduleId) {
        TravelMember travelMember = travelMemberRepository.findByMember_IdAndTravel_TravelId(userId, travelId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없거나 그 유저에 대한 트래블 아이디가 틀립니다."));
        if (travelMember.getRole() != Role.ROLE_ADMIN) {
            throw new IllegalArgumentException("이 유저는 해당 여행에 대한 권한이 없습니다(여행 생성자만 일정추가,일정수정,여행과 일정삭제 가능.");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
