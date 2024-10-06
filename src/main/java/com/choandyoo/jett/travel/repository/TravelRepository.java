package com.choandyoo.jett.travel.repository;

import com.choandyoo.jett.travel.entity.TravelEntity;
import com.choandyoo.jett.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface TravelRepository extends JpaRepository<TravelEntity, Long> {
    List<TravelEntity> findByUserUserId(long userId); // 사용자 ID로 여행 정보를 조회
}
