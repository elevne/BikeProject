package com.bike.bikeproject.controller;

import com.bike.bikeproject.util.BikeApiUtil;
import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bike/bikeStation")
@RequiredArgsConstructor
public class BikeStationController {

    private final BikeApiUtil bikeApiUtil;

    @Operation(summary = "Get Bike Station Info", description = "서울 따릉이 공공 API 활용 따릉이 정류소 정보 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    })
    @GetMapping("/currentSituation")
    public ResponseEntity<JsonObject> getCurrentSituationOfBikeStation(
            @RequestParam("indexes") List<Integer> indexes, BindingResult bindingResult
            ) {
        if (bindingResult.hasErrors()) { return ResponseEntity.badRequest().body(new JsonObject()); }

        return null;
    }

}
