package com.choandyoo.jett.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.choandyoo.jett.user.entity.UserEntity;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> { // UserEntity의 기본키 타입 적고 jpa가 제공하는 인터페이스로 crud
    Optional<UserEntity> findMemberByEmail(String email);

}
