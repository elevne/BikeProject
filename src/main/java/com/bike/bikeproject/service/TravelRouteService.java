package com.bike.bikeproject.service;

import com.bike.bikeproject.dto.DestinationDTO;
import com.bike.bikeproject.entity.Destination;

import java.util.List;

public interface TravelRouteService {

    List<DestinationDTO> getSuggestedRoute(DestinationDTO destinationDTO, List<Long> destinations);

}
