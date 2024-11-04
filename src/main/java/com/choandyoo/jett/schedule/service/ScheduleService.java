package com.choandyoo.jett.schedule.service;

import com.choandyoo.jett.schedule.dto.ScheduleRequest;
import com.choandyoo.jett.schedule.dto.ScheduleResponse;
import com.choandyoo.jett.schedule.entity.Schedule;
import com.choandyoo.jett.schedule.repository.ScheduleRepository;
import com.choandyoo.jett.travel.dto.TravelRequest;
import com.choandyoo.jett.travel.entity.Travel;
import com.choandyoo.jett.travel.repository.TravelRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TravelRepository travelRepository;

    @Transactional
    public void addSchedule(Long travelId,ScheduleRequest scheduleRequest) {
        Travel travel=travelRepository.findById(travelId).orElseThrow(EntityNotFoundException::new);
        Schedule schedule = scheduleRequest.toSaveSchedule(travel,scheduleRequest);
        scheduleRepository.save(schedule);
    }

    public List<ScheduleResponse> getAllSchedule(Long travelId) {
        return scheduleRepository.findByTravel_TravelId(travelId).stream()
                .map(schedule -> ScheduleResponse.builder()
                        .scheduleId(schedule.getScheduleId())
                        .date(schedule.getDate())
                        .placeName(schedule.getPlaceName())
                        .placeLocation(schedule.getPlaceLocation())
                        .placeUrl(schedule.getPlaceUrl())
                        .contactNumber(schedule.getContactNumber())
                        .image(schedule.getImage())
                        .build())
                .collect(Collectors.toList());
    }
    public void deleteSchedule(Long scheduleId) {
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new EntityNotFoundException("Schedule not found with id: " + scheduleId);
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
