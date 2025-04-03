package com.jett.domain.schedule.entity;

import com.jett.domain.travel.entity.Travel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY) // Travel 엔티티와 다대일 관계 설정
    @JoinColumn(name = "travel_id", nullable = false) // 외래키 칼럼 지정
    private Travel travel; // 특정 여행에 속한 스케줄임을 나타냄

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private String placeUrl ;

    @Column
    private String placeName;

    @Column
    private String placeLocation;

    @Column
    private LocalDateTime createdAt;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;  // 위도

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude; // 경도

    @Column
    private LocalDate dayNum;

    @Column
    private LocalDateTime updatedAt;



}
