package com.choandyoo.jett.travel.repository;

import com.choandyoo.jett.travel.dto.TravelResponse;
import com.choandyoo.jett.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TravelRepository extends JpaRepository<Travel, Long> {
    List<Travel> findByTravelMembers_Member_Id(Long memberId);

}
