package com.bike.bikeproject.vo;

import com.bike.bikeproject.dto.DestinationDTO;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class SuggestedRouteVO {

    private List<DestinationDTO> suggestedRoute;

    private double totalDistance;

}
