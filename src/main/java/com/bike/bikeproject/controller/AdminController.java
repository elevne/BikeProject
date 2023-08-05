package com.bike.bikeproject.controller;

import com.bike.bikeproject.service.SimpleBikeStationBatchService;
import com.bike.bikeproject.service.SimpleDestinationBatchService;
import com.bike.bikeproject.util.DestinationType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/bike/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SimpleDestinationBatchService destinationBatchService;

    private final SimpleBikeStationBatchService bikeStationBatchService;

    @Operation(summary = "Update Bike Station Info", description = "따릉이 API 이용 정류소 정보를 최신정보로 갱신")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "UPDATE SUCCESS"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/updateStations")
    public ResponseEntity<String> updateStations() {
        bikeStationBatchService.batchInsertBikeStation();
        return ResponseEntity.ok("Batch Insert of Bike Station succeeded");
    }

    @Operation(summary = "Update Destinations Info", description = "여행지/식당/카페 정보 갱신 (전처리 완료한 새로운 txt 파일 필요)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "UPDATE SUCCESS"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/updateDestinations")
    public ResponseEntity<String> updateDestinations(@RequestParam("type") String type) throws IOException, IllegalArgumentException {
        DestinationType pt = DestinationType.ofDtype(type);
        destinationBatchService.batchInsert(pt);
        return ResponseEntity.ok("Batch Insert Destination for type "+type+" succeeded");
    }

}
