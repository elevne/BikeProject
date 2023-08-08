package com.bike.bikeproject.service;

import com.bike.bikeproject.entity.Destination;
import com.bike.bikeproject.repository.DestinationRepository;
import com.bike.bikeproject.service.impl.SimpleDestinationBatchServiceImpl;
import com.bike.bikeproject.util.DestinationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SimpleDestinationBatchServiceTest {

    @Autowired
    SimpleDestinationBatchServiceImpl placeBatchUtil;

    @Autowired
    DestinationRepository destinationRepository;

    // todo: 이 테스트 어떻게 개선할 수 있는지 알아보기
    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("간단 배치 입력 작업 테스트")
    public void batchInsertTest() throws IOException {
        // given, when
        placeBatchUtil.batchInsert(DestinationType.CAFE);
        placeBatchUtil.batchInsert(DestinationType.RESTAURANT);
        placeBatchUtil.batchInsert(DestinationType.TRAVEL);
        List<Destination> cafes = destinationRepository.findAllByDtype("C");
        List<Destination> restaurants = destinationRepository.findAllByDtype("R");
        List<Destination> travels = destinationRepository.findAllByDtype("T");
        // then: 현재 데이터 파일에 있는 데이터 수
        assertEquals(cafes.size(), 41);
        assertEquals(restaurants.size(), 149);
        assertEquals(travels.size(), 66);
    }

}
