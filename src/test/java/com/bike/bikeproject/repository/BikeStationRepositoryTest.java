package com.bike.bikeproject.repository;

import com.bike.bikeproject.entity.BikeStation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BikeStationRepositoryTest {

    @Autowired BikeStationRepository bikeStationRepository;

    @Test
    public void findStationNameContainingTest() {
        // given
        BikeStation inputData = BikeStation.builder()
                .id(9999)
                .latitude(9998d)
                .longitude(9997d)
                .stationName("Test station")
                .build();
        bikeStationRepository.save(inputData);
        // when
        BikeStation outputData = bikeStationRepository.findFirstByStationNameContaining("station");
        // then
        assertEquals(9999, outputData.getId());
        assertEquals(9998d, outputData.getLatitude());
        assertEquals(9997d, outputData.getLongitude());
        assertEquals("Test station", outputData.getStationName());
    }

}
