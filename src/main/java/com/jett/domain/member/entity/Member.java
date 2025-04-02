package com.jett.domain.member.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.jett.domain.chat.entity.ChatRoomMember;
import com.jett.domain.member.enums.Role;

import com.jett.domain.travelMember.entity.TravelMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime lastLoginDate;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<TravelMember> travelMembers;

    @OneToMany(mappedBy = "member")
    private List<ChatRoomMember> chatRoomMembers;

    public void updateLastLoginDate() {
        this.lastLoginDate = LocalDateTime.now();
   }
}
