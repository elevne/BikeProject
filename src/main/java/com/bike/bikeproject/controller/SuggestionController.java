package com.bike.bikeproject.controller;

import com.bike.bikeproject.service.TravelRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/suggest")
@RequiredArgsConstructor
public class SuggestionController {

    private final TravelRouteService routeService;

}
