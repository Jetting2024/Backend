package com.choandyoo.jett.travel.entity;

import com.choandyoo.jett.member.entity.Member;
import com.choandyoo.jett.travel.enums.Role;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "Travel_Member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 중간 테이블 ID를 자동 생성
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;


    @ManyToOne // 하나의 Travel에 속하는 여러 회원
    @JoinColumn(name = "travel_id")  // Travel 엔티티와 연결
    private Travel travel;

    @ManyToOne
    @JoinColumn(name = "member_id")  // Member 엔티티와 연결
    private Member member;

}