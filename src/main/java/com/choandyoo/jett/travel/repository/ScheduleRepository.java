package com.choandyoo.jett.travel.repository;

import com.choandyoo.jett.travel.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByTravel_TravelId(Long travelId);

}
//public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
//    List<Schedule> findByTravel_TravelId(Long travelId);
//}