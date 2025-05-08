package com.jett.domain.travel.opendata.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "popular_place")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopularPlace {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String region;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String address;

  @Column(name = "image_url")
  private String imageUrl;
}
