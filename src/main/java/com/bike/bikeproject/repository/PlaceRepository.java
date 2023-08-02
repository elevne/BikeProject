package com.bike.bikeproject.repository;

import com.bike.bikeproject.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    void deleteAllByDtype(String dtype);

    List<Place> findAllByDtype(String dtype);

}
