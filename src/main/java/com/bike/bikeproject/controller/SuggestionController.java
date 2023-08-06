package com.bike.bikeproject.controller;

import com.bike.bikeproject.dto.DestinationDTO;
import com.bike.bikeproject.service.TravelRouteService;
import com.bike.bikeproject.vo.SuggestedRouteVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bike/suggest")
@RequiredArgsConstructor
public class SuggestionController {

    private final TravelRouteService routeService;

    @Operation(summary = "TSP Route Suggestion", description = "TSP 알고리즘 기반 경로 추천 API (도착위치, 현재위치 동일)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    })
    @PostMapping("/tsp")
    public ResponseEntity<SuggestedRouteVO> getSuggestionWithTSP(
            @RequestBody @Valid DestinationDTO startAndEnd, @RequestParam("ids") List<Long> destinationIds, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return ResponseEntity.badRequest().body(null); }
        SuggestedRouteVO suggestedRouteVO = routeService.getSuggestedRoute(startAndEnd, destinationIds);
        return ResponseEntity.ok(suggestedRouteVO);
    }

}