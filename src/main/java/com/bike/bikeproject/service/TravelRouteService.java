package com.bike.bikeproject.service;

import com.bike.bikeproject.dto.DestinationDTO;
import com.bike.bikeproject.vo.SuggestedRouteVO;

import java.util.List;

public interface TravelRouteService {

    SuggestedRouteVO getSuggestedRoute(DestinationDTO destinationDTO, List<Long> destinationIds) throws IllegalAccessException;

}
