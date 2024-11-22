package com.choandyoo.jett.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.choandyoo.jett.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmail(String email);

}
