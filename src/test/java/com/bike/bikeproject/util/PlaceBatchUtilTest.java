package com.bike.bikeproject.util;

import com.bike.bikeproject.entity.Cafe;
import com.bike.bikeproject.entity.Place;
import com.bike.bikeproject.repository.PlaceRepository;
import com.bike.bikeproject.util.impl.SimplePlaceBatchUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PlaceBatchUtilTest {

    @Autowired SimplePlaceBatchUtil placeBatchUtil;

    @Autowired PlaceRepository placeRepository;

    @Test
    @Transactional
    @DisplayName("간단 배치 입력 작업 테스트")
    public void batchInsertTest() {
        // given, when
        placeBatchUtil.batchInsert(PlaceType.CAFE);
        placeBatchUtil.batchInsert(PlaceType.RESTAURANT);
        placeBatchUtil.batchInsert(PlaceType.TRAVEL);
        List<Place> cafes = placeRepository.findAllByDtype("C");
        List<Place> restaurants = placeRepository.findAllByDtype("R");
        List<Place> travels = placeRepository.findAllByDtype("T");
        // then: 현재 데이터 파일에 있는 데이터 수
        assertEquals(cafes.size(), 41);
        assertEquals(restaurants.size(), 149);
        assertEquals(travels.size(), 66);
    }

}
