package com.jett.domain.travel.opendata.repository;

import com.jett.domain.travel.opendata.entity.PopularPlace;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopularPlaceRepository extends JpaRepository<PopularPlace, Long> {

  List<PopularPlace> findByRegion(String place);
}
