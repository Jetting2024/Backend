package com.jett.domain.travelMember.repository;

import com.jett.domain.travelMember.entity.TravelMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TravelMemberRepository extends JpaRepository<TravelMember, Long> {
    Optional<TravelMember> findByMember_IdAndTravel_TravelId(Long memberId, Long travelId);

}
