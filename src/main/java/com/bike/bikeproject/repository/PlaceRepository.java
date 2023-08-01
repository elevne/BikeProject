package com.bike.bikeproject.repository;

import com.bike.bikeproject.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    void deleteAllByDtype(String dtype);

}
