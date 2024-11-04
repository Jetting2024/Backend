package com.choandyoo.jett.travel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@SQLDelete(sql = "UPDATE travel SET deleted = true WHERE travelId = ?")
@Where(clause = "deleted = false")
@Table(name = "travel")
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelId;

    @Column
    private String travelName;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column(name = "deleted", nullable = false)
    private Boolean isDeleted = false;
}