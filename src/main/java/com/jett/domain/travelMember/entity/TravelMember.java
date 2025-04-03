package com.jett.domain.travelMember.entity;

import com.jett.domain.member.entity.Member;
import com.jett.domain.travel.entity.Travel;
import com.jett.domain.travel.enums.Role;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;


    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}