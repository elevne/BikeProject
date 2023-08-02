package com.bike.bikeproject.repository;

import com.bike.bikeproject.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {

    void deleteAllByDtype(String dtype);

    List<Destination> findAllByDtype(String dtype);

}
