package com.choandyoo.jett.travel.repository;

import com.choandyoo.jett.travel.entity.TravelMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TravelMemberRepository extends JpaRepository<TravelMember, Long> {
    Optional<TravelMember> findByMember_IdAndTravel_TravelId(Long memberId, Long travelId);

}
