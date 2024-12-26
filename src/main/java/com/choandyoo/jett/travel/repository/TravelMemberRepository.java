package com.choandyoo.jett.travel.repository;

import com.choandyoo.jett.travel.entity.TravelMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelMemberRepository extends JpaRepository<TravelMember, Long> {

}
