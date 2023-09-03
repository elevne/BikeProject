package com.bike.bikeproject.controller;

import com.bike.bikeproject.service.SimpleBikeStationBatchService;
import com.bike.bikeproject.service.SimpleDestinationBatchService;
import com.bike.bikeproject.util.DestinationType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "ADMIN", description = "ADMIN 전용 데이터 갱신 API")
@RestController
@RequestMapping("/bike/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SimpleDestinationBatchService destinationBatchService;

    private final SimpleBikeStationBatchService bikeStationBatchService;

    @Operation(tags = "ADMIN",
            summary = "Update Bike Station Info", description = "따릉이 API 이용 정류소 정보를 최신정보로 갱신")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "UPDATE SUCCESS"),
            @ApiResponse(responseCode = "403", description = "ONLY ADMIN CAN ACCESS TO THE API"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping("/updateStations")
    public ResponseEntity<String> updateStations() {
        bikeStationBatchService.batchInsertBikeStation();
        return ResponseEntity.ok("Batch Insert of Bike Station succeeded");
    }

    @Operation(tags = "ADMIN",
            summary = "Update Destinations Info", description = "여행지/식당/카페 정보 갱신 (전처리 완료한 새로운 txt 파일 필요)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "UPDATE SUCCESS"),
            @ApiResponse(responseCode = "403", description = "ONLY ADMIN CAN ACCESS TO THE API"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping("/updateDestinations")
    public ResponseEntity<String> updateDestinations(@RequestParam("type") String type) throws IOException, IllegalArgumentException {
        DestinationType pt = DestinationType.ofDtype(type);
        destinationBatchService.batchInsert(pt);
        return ResponseEntity.ok("Batch Insert Destination for type "+type+" succeeded");
    }

}
