package com.bike.bikeproject.repository;

import com.bike.bikeproject.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
