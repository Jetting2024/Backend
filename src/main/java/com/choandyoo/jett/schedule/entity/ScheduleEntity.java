package com.choandyoo.jett.schedule.entity;

import com.choandyoo.jett.travel.entity.TravelEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="schedule")
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id", nullable = false, unique = true)
    private Long scheduleId;

    @ManyToOne
    @JoinColumn(name = "travel_id", nullable = false)
    private TravelEntity travel;

//    @Column(name = "travel_id")
//    private Long travelId;

//    @Column(name = "user_id", nullable = false)
//    private Long userId;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(length = 50)
    private String location;

    @Column
    private Integer sequence;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


}
