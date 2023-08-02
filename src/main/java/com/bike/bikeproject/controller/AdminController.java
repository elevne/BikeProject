package com.bike.bikeproject.controller;

import com.bike.bikeproject.util.PlaceBatchUtil;
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

    private final PlaceBatchUtil batchUtil;

    //  todo: Swagger API 작성
    @GetMapping("/batchInsert")
    public ResponseEntity<String> batchInsert(@RequestParam("type") String type) {
        PlaceType pt = PlaceType.ofDtype(type);
        batchUtil.batchInsert(pt);
        return ResponseEntity.ok("Batch Insert for type "+type+" succeeded");
    }

}
