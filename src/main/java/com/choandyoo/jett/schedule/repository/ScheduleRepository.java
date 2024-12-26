package com.choandyoo.jett.schedule.repository;

import com.choandyoo.jett.schedule.entity.Schedule;
import com.choandyoo.jett.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByTravel_TravelId(Long travelId);
    boolean existsByTravelAndStartTimeBeforeAndEndTimeAfter(
            Travel travel, LocalDateTime endTime, LocalDateTime startTime
    );
}
