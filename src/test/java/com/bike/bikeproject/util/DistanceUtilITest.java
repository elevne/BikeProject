package com.bike.bikeproject.util;

import com.bike.bikeproject.dto.DestinationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class DistanceUtilITest {

    @Autowired DistanceUtil distanceUtil;
    private List<DestinationDTO> destinationDTOList = new ArrayList<>();
    private DestinationDTO destinationDTO1;
    private DestinationDTO destinationDTO2;
    private DestinationDTO destinationDTO3;

    @BeforeEach
    public void setUp() {
        destinationDTOList.clear();
        destinationDTO1 = new DestinationDTO("D1", 11, 11, null, null, 0);
        destinationDTO2 = new DestinationDTO("D1", 12, 12, null, null, 0);
        destinationDTO3 = new DestinationDTO("D1", 13, 13, null, null, 0);
        destinationDTOList.add(destinationDTO1);
        destinationDTOList.add(destinationDTO2);
        destinationDTOList.add(destinationDTO3);
    }

    @Test
    @DisplayName("DistanceMap 메소드 테스트")
    public void getDistanceMapTest() {
        // given
        double[][] distanceMap = distanceUtil.getDistanceMap(destinationDTOList);
        // when, then
        assertEquals(distanceMap[0][1], distanceMap[1][0]);
        assertEquals(distanceMap[1][2], distanceMap[2][1]);
        assertEquals(distanceMap[0][0], 0);
        assertEquals(distanceMap[1][1], 0);
        assertEquals(distanceMap[2][2], 0);
    }

    @Test
    @DisplayName("DistanceMap 메소드 예외 테스트")
    public void getDistanceMapExceptionTest() {
        // given
        destinationDTOList.clear();
        // when, then
        assertThrows(IllegalArgumentException.class, () -> {
            distanceUtil.getDistanceMap(destinationDTOList);
        });
    }

}
