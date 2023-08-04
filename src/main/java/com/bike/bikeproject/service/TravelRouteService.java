package com.bike.bikeproject.service;

import com.bike.bikeproject.dto.DestinationDTO;
import com.bike.bikeproject.dto.SuggestedRouteDTO;

import java.util.List;

public interface TravelRouteService {

    SuggestedRouteDTO getSuggestedRoute(DestinationDTO destinationDTO, List<Long> destinationIds);

}
