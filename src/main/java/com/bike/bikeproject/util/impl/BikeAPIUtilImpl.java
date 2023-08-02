package com.bike.bikeproject.util.impl;

import com.bike.bikeproject.util.BikeApiUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BikeAPIUtilImpl implements BikeApiUtil {

    @Value("${bike.api.key}")
    private String bikeApiKey;

    private final String API_URL = "http://openapi.seoul.go.kr:8088/"+bikeApiKey+"/json/bikeList/";  // 뒤에 start_idx/end_idx/ 붙여서 GET 요청

    // todo 1. 정류소 정보 전부 받아서 넣기: 1-1: 정류소 정보 JSON -> Entity 매핑 / 1-2: 배치 INSERT
    // todo 2. 해당 정류소 현황 보기

}