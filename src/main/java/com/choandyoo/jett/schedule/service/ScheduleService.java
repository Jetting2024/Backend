package com.choandyoo.jett.schedule.service;

import com.choandyoo.jett.schedule.repository.ScheduleRepository;
import com.choandyoo.jett.travel.dto.AllTravelResponseDto;
import com.choandyoo.jett.travel.dto.OnlyTravelResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;

    }
    public List<OnlyTravelResponseDto> getAllTravelsByUserId(Long travelId) {
        // 해당 userId에 대한 여행 목록을 조회하고 DTO로 변환
        return scheduleRepository.findByTravelTravelId(travelId)
                .stream()
                .map(schedule -> new OnlyTravelResponseDto(schedule.getDate(),schedule.getLocation()))
                .collect(Collectors.toList());
    }

}
