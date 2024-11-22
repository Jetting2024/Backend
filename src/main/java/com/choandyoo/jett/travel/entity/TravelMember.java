package com.choandyoo.jett.travel.entity;

import com.choandyoo.jett.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import com.choandyoo.jett.travel.enums.Role;


@Entity
@Table(name = "travel_members")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 중간 테이블 ID를 자동 생성
    private Long id;

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Role role;

}