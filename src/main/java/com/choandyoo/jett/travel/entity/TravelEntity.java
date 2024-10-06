package com.choandyoo.jett.travel.entity;

import com.choandyoo.jett.user.entity.UserEntity;
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
@Table(name = "travel")
public class TravelEntity {

    @Id
    @Column(name = "travel_id", nullable = false, unique = true)
    private Long travelId;

    @ManyToOne // TravelEntity는 여러 여행이 하나의 사용자에게 속할 수 있습니다.
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false) // 외래키 설정
    private UserEntity user; // 사용자 엔티티
//
//    @Column(name = "user_id")
//    private Long userId;



    @Column(name = "schedule_id", nullable = true)
    private Integer scheduleId;

    @Column(name = "travel_name", nullable = false)
    private String travelName;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
