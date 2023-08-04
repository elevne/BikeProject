package com.bike.bikeproject.util;

import com.bike.bikeproject.entity.BikeStation;
import com.bike.bikeproject.repository.BikeStationRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class BikeAPIUtilTest {

    @Autowired BikeApiUtil bikeApiUtil;

    @Autowired BikeStationRepository bikeStationRepository;

    @Test
    @DisplayName("따릉이 API 호출 및 데이터 최신화 테스트")
    public void bikeStationBatchInsertTest() {
        // given
        bikeApiUtil.batchInsertBikeStation();
        // when
        List<BikeStation> bikeStationList = bikeStationRepository.findAll();
        // then
        // todo: 테스트 코드 작성 어떻게 할지 생각해 보기
    }

}
