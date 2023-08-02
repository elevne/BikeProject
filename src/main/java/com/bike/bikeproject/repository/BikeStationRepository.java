package com.bike.bikeproject.repository;

import com.bike.bikeproject.entity.BikeStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeStationRepository extends JpaRepository<BikeStation, Integer> {

    BikeStation findFirstByStationNameContaining(String stationName);

}
