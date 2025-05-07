package com.jett.domain.travel.repository;

import com.jett.domain.travel.entity.Travel;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;


public interface TravelRepository extends JpaRepository<Travel, Long> {
    List<Travel> findByTravelMembers_Member_Id(Long userId);

    @Query("SELECT DISTINCT t FROM Travel t " +
        "JOIN FETCH t.travelMembers tm " +
        "JOIN FETCH tm.member m " +
        "WHERE m.id = :userId")
    List<Travel> findAllByUserIdWithMembers(@Param("userId") Long userId);


}
