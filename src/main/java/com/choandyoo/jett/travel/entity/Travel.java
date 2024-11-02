package com.choandyoo.jett.travel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelId;

    @Column
    private String travelName;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

}