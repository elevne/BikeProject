package com.bike.bikeproject.util.impl;

import com.bike.bikeproject.entity.BikeStation;
import com.bike.bikeproject.repository.BikeStationRepository;
import com.bike.bikeproject.util.BikeApiUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BikeAPIUtilImpl implements BikeApiUtil {

    private final BikeStationRepository bikeStationRepository;

    @Value("${bike.api.key}")
    private String bikeApiKey;

    private final String API_URI = "http://openapi.seoul.go.kr:8088";
    private final String PATH = "/json/bikeList/";  // 뒤에 start_idx/end_idx/ 붙여서 GET 요청

    // API 호출 시 한 번에 최대 1000 건 까지만 조회 가능
    private final int API_MAX_COUNT = 1000;

    // todo: 해당 정류소 현황 보기

    // todo: Batch 처리 잘 되고 있는지 MySQL 로그 설정 / 확인
    /**
     * 서울 따릉이 API 에서 정류소 정보 받아와 DB 에 저장
     */
    @Transactional
    public void batchInsertBikeStation() {
        bikeStationRepository.deleteAllInBatch();

        boolean next = true;
        int i = 1;
        List<BikeStation> bikeStationList = new ArrayList<>();
        while (next) {
            JsonObject stationsJson = seoulBikeAPI(i, i+API_MAX_COUNT-1);
            JsonArray stationsJsonArray =
                    stationsJson.getAsJsonObject("rentBikeStatus")
                                .getAsJsonArray("row");
            for (int j = 0; j < stationsJsonArray.size(); j++) {
                BikeStation bikeStation = jsonToBike((JsonObject) stationsJsonArray.get(j), i+j);
                bikeStationList.add(bikeStation);
            }

            if (stationsJsonArray.size() < API_MAX_COUNT) next = false;
            else i += API_MAX_COUNT;
        }

        bikeStationRepository.saveAll(bikeStationList);
    }

    private JsonObject seoulBikeAPI(int startIdx, int endIdx) {
        String path = "/" + bikeApiKey + PATH + String.valueOf(startIdx) + "/" + String.valueOf(endIdx);
        URI uri = UriComponentsBuilder
                .fromUriString(API_URI)
                .path(path)
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        return (JsonObject) JsonParser.parseString(
                restTemplate
                        .getForEntity(uri, String.class)
                        .getBody()
        );
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