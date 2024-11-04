package com.choandyoo.jett.schedule.repository;

import com.choandyoo.jett.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByTravel_TravelId(Long travelId);

}
