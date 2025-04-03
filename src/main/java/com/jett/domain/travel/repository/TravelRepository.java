package com.jett.domain.travel.repository;

import com.jett.domain.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TravelRepository extends JpaRepository<Travel, Long> {
    List<Travel> findByTravelMembers_Member_Id(Long userId);


}
