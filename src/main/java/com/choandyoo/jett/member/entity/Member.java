package com.choandyoo.jett.member.entity;

import java.time.LocalDateTime;

import com.choandyoo.jett.member.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public void updateLastLoginDate() {
        this.lastLoginDate = LocalDateTime.now();
   }
}
