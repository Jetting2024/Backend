package com.choandyoo.jett.travel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long travelId;
    @Column
    private LocalDateTime travelDateTime;
    @Column
    private String road_address_name;
    @Column
    private String category_group_code;
    @Column
    private String phone;
    @Column
    private String place_name;


}
