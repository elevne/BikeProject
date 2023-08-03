package com.bike.bikeproject.service.impl;

import com.bike.bikeproject.dto.DestinationDTO;
import com.bike.bikeproject.repository.DestinationRepository;
import com.bike.bikeproject.service.TravelRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TravelRouteServiceImpl implements TravelRouteService {

    private final DestinationRepository destinationRepository;

    private final double EARTH_RADIUS = 6371.0d;

    @Override
    public List<DestinationDTO> getSuggestedRoute(DestinationDTO destinationDTO, List<Long> destinations) {
        return null;
    }

    private double getDistance(DestinationDTO from, DestinationDTO to) {
        double fromLatitudeRadian  = Math.toRadians(from.getLatitude());
        double fromLongitudeRadian = Math.toRadians(from.getLongitude());
        double toLatitudeRadian    = Math.toRadians(to.getLatitude());
        double toLongitudeRadian   = Math.toRadians(to.getLongitude());
        double deltaLatitude       = fromLatitudeRadian - toLatitudeRadian;
        double deltaLongitude      = fromLongitudeRadian - toLongitudeRadian;

        // Haversian Formula 를 이용하여 두 점의 위/경도 정보로 거리를 구할 수 있다.
        // a = sin²(Δφ/2) + cos(φ1) * cos(φ2) * sin²(Δλ/2)
        // c = 2 * atan2(√a, √(1-a))
        // distance = R * c
        double a = Math.pow(Math.sin(deltaLatitude / 2), 2)
                + Math.cos(fromLatitudeRadian) * Math.cos(toLatitudeRadian) * Math.pow(Math.sin(deltaLongitude / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }



}
