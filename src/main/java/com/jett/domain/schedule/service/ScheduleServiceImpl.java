package com.jett.domain.schedule.service;


import com.jett.domain.schedule.dto.ScheduleRequest;
import com.jett.domain.schedule.dto.ScheduleResponse;
import com.jett.domain.schedule.entity.Schedule;
import com.jett.domain.schedule.repository.ScheduleRepository;
import com.jett.domain.travel.entity.Travel;
import com.jett.domain.travelMember.entity.TravelMember;
import com.jett.domain.travel.enums.Role;
import com.jett.domain.travelMember.repository.TravelMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TravelMemberRepository travelMemberRepository;

    @Override
    @Transactional
    public List<Long> addSchedule(Long userId, Long travelId, List<ScheduleRequest> scheduleRequests) {
        TravelMember travelMember = travelMemberRepository.findByMember_IdAndTravel_TravelId(userId, travelId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없거나 그 유저에 대한 트래블 아이디가 틀립니다."));
        if (travelMember.getRole() != Role.ROLE_ADMIN) {
            throw new IllegalArgumentException("이 유저는 해당 여행에 대한 권한이 없습니다 (여행 생성자만 일정추가, 일정수정, 여행과 일정삭제 가능).");
        }

        Travel travel = travelMember.getTravel();
        LocalDate travelStartTime = travel.getStartDate();
        LocalDate travelEndTime = travel.getEndDate();
        long fullDays = ChronoUnit.DAYS.between(travelStartTime, travelEndTime) + 1;

        List<Schedule> schedules = new ArrayList<>();
        List<Long> savedScheduleIds = new ArrayList<>();

        for (ScheduleRequest scheduleRequest : scheduleRequests) {
            LocalDate dayNum=scheduleRequest.getDayNum();
            long dayIndex = ChronoUnit.DAYS.between(travelStartTime, dayNum) + 1;

            if (dayIndex > fullDays || dayIndex < 1) {
                throw new IllegalArgumentException("일정이 여행 기간을 벗어났습니다.");
            }
            boolean isDuplicate = scheduleRepository.existsByTravelAndStartTimeBeforeAndEndTimeAfter(
                    travel, scheduleRequest.getEndTime(), scheduleRequest.getStartTime()
            );
            if (isDuplicate) {
                throw new IllegalArgumentException("해당 시간대에 겹치는 일정이 이미 존재합니다.");
            }
            Schedule schedule = scheduleRequest.toSaveSchedule(travel);
            schedules.add(schedule);
        }

        scheduleRepository.saveAll(schedules);

        for (Schedule schedule : schedules) {
            savedScheduleIds.add(schedule.getScheduleId());
        }

        return savedScheduleIds;
    }

    @Override
    @Transactional
    public List<ScheduleResponse> getAllSchedule(Long travelId) {
        return scheduleRepository.findByTravel_TravelId(travelId).stream()
                .map(schedule -> ScheduleResponse.builder()
                        .scheduleId(schedule.getScheduleId())
                        .startTime(schedule.getStartTime())
                        .endTime(schedule.getEndTime())
                        .placeName(schedule.getPlaceName())
                        .placeLocation(schedule.getPlaceLocation())
                        .placeUrl(schedule.getPlaceUrl())
                        .dayNum(schedule.getDayNum())
                        .latitude(schedule.getLatitude())
                        .longitude(schedule.getLongitude())
                        .build())
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public void deleteSchedule(Long userId,Long travelId,Long scheduleId) {
        TravelMember travelMember = travelMemberRepository.findByMember_IdAndTravel_TravelId(userId, travelId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없거나 그 유저에 대한 트래블 아이디가 틀립니다."));
        if (travelMember.getRole() != Role.ROLE_ADMIN) {
            throw new IllegalArgumentException("이 유저는 해당 여행에 대한 권한이 없습니다(여행 생성자만 일정추가,일정수정,여행과 일정삭제 가능.");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
