package com.bike.bikeproject.util;

import com.bike.bikeproject.dto.DestinationDTO;

import java.util.List;

public interface DistanceUtil {

    double[][] getDistanceMap(List<DestinationDTO> destinations);

}
