package com.bike.bikeproject.controller;

import com.bike.bikeproject.util.BikeApiUtil;
import com.bike.bikeproject.util.DestinationBatchUtil;
import com.bike.bikeproject.util.PlaceType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bike/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DestinationBatchUtil batchUtil;

    private final BikeApiUtil bikeApiUtil;

    //  todo: Swagger API 작성
    @GetMapping("/updateStations")
    public ResponseEntity<String> updateStations() {
        bikeApiUtil.batchInsertBikeStation();
        return ResponseEntity.ok("Batch Insert of Bike Station succeeded");
    }

    @GetMapping("/updateDestinations")
    public ResponseEntity<String> updateDestinations(@RequestParam("type") String type) {
        PlaceType pt = PlaceType.ofDtype(type);
        batchUtil.batchInsert(pt);
        return ResponseEntity.ok("Batch Insert Destination for type "+type+" succeeded");
    }

}
