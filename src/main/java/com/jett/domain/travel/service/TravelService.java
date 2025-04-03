package com.jett.domain.travel.service;

import com.jett.domain.travel.dto.request.TravelInviteRequest;
import com.jett.domain.travel.dto.request.TravelRequest;
import com.jett.domain.travel.dto.response.TravelResponse;
import java.util.List;

public interface TravelService {
  List<TravelResponse> getAllTravel(Long userId);

  Long addTravel(Long userId, TravelRequest travelRequest);

  void deleteTravel(Long userId ,Long  travelId);

  void inviteTravel(TravelInviteRequest travelInviteRequest , Long travelId);

  TravelResponse checkOnlyTravelSchedule(Long userId, Long travelId);


}
