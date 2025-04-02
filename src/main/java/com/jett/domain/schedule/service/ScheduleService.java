package com.jett.domain.schedule.service;

import com.jett.domain.schedule.dto.ScheduleRequest;
import com.jett.domain.schedule.dto.ScheduleResponse;
import java.util.List;

public interface ScheduleService {
  List <Long> addSchedule (Long userId, Long travelId, List<ScheduleRequest> scheduleRequests);

  List<ScheduleResponse> getAllSchedule(Long travelId);

  void deleteSchedule(Long userId,Long travelId,Long scheduleId);


}
