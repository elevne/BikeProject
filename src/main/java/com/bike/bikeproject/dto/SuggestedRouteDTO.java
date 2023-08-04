package com.bike.bikeproject.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SuggestedRouteDTO {

    private List<DestinationDTO> suggestedRoute;

    private double totalDistance;

}
