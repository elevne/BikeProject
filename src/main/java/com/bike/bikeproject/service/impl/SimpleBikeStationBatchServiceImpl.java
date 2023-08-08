package com.bike.bikeproject.service.impl;

import com.bike.bikeproject.entity.BikeStation;
import com.bike.bikeproject.exception.BikeAPIException;
import com.bike.bikeproject.repository.BikeStationRepository;
import com.bike.bikeproject.service.SimpleBikeStationBatchService;
import com.bike.bikeproject.util.BikeApiUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.bike.bikeproject.util.impl.BikeAPIUtilImpl.API_MAX_COUNT;

@Service
@RequiredArgsConstructor
public class SimpleBikeStationBatchServiceImpl implements SimpleBikeStationBatchService {

    private final BikeStationRepository bikeStationRepository;

    private final BikeApiUtil bikeApiUtil;

    /**
     * 서울 따릉이 API 에서 정류소 정보 받아와 DB 에 저장
     */
    @Transactional
    public void batchInsertBikeStation() throws BikeAPIException {
        bikeStationRepository.deleteAllInBatch();

        boolean next = true;
        int i = 1;
        List<BikeStation> bikeStationList = new ArrayList<>();
        while (next) {
            JsonArray stations = bikeApiUtil.requestSeoulBikeAPI(i, i+API_MAX_COUNT-1);
            for (int j = 0; j < stations.size(); j++) {
                BikeStation bikeStation = jsonToBike((JsonObject) stations.get(j), i+j);
                bikeStationList.add(bikeStation);
            }
            if (stations.size() < API_MAX_COUNT) next = false;
            else i += API_MAX_COUNT;
        }

        bikeStationRepository.saveAll(bikeStationList);
    }

    private BikeStation jsonToBike(JsonObject json, Integer i) {
        return BikeStation.builder()
                .id(i)
                .latitude(Double.parseDouble(json.get("stationLatitude").toString().replaceAll("\"","")))
                .longitude(Double.parseDouble(json.get("stationLongitude").toString().replaceAll("\"","")))
                .stationName(json.get("stationName").toString().replaceAll("\"",""))
                .build();
    }

}
