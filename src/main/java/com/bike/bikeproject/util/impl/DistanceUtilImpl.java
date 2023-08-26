package com.bike.bikeproject.util.impl;

import com.bike.bikeproject.dto.DestinationDTO;
import com.bike.bikeproject.util.DistanceUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DistanceUtilImpl implements DistanceUtil {

    public double[][] getDistanceMap(List<DestinationDTO> destinations) {
        int listSize = destinations.size();
        if (listSize <= 1) throw new IllegalArgumentException("DestinationDTO List Size must be bigger than 1");
        double[][] distanceMap = new double[listSize][listSize];
        for (int i = 0; i < destinations.size(); i++) {
            for (int j = i; j < destinations.size(); j++) {
                if (i == j) distanceMap[i][j] = 0;
                double distance = getDistance(destinations.get(i), destinations.get(j));
                distanceMap[i][j] = distance;
                distanceMap[j][i] = distance;
            }
        }
        return distanceMap;
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
        double EARTH_RADIUS = 6371.0d;
        double a = Math.pow(Math.sin(deltaLatitude / 2), 2)
                + Math.cos(fromLatitudeRadian) * Math.cos(toLatitudeRadian) * Math.pow(Math.sin(deltaLongitude / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }


}
