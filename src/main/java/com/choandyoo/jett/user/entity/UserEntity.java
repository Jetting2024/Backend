package com.choandyoo.jett.user.entity;

import com.choandyoo.jett.travel.entity.TravelEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name= "user")

public class UserEntity {
    @Id //이 필드가 기본키라고 알려줌
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키의 값을 자동으로 생성하게 하고 GenerationType 이라는 전략을 쓴다.
    @Column(name = "user_id", nullable = false, unique = true) // 기본 키 설정
    private Long userId;

    @Column(length = 40,nullable = false)
    private String name;

    @Column(length = 256,nullable = false)
    private String email;

    @Column(length = 256,nullable = false)
    private String password;

    @Column(name="created_At",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now(); // createdAt와 updatedAt을 동일하게 초기화
        this.deletedAt = null; // 필요에 따라 null로 설정
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
