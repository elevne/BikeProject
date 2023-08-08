package com.bike.bikeproject.service;

import com.bike.bikeproject.entity.BikeStation;
import com.bike.bikeproject.repository.BikeStationRepository;
import com.bike.bikeproject.util.BikeApiUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SimpleBikeStationBatchServiceTest {

    @Autowired SimpleBikeStationBatchService simpleBikeStationBatchService;

    @Autowired BikeStationRepository bikeStationRepository;

    @MockBean BikeApiUtil bikeApiUtil;

    private String MOCK_API_RESPONSE = "{\n" +
            "    \"rentBikeStatus\": {\n" +
            "        \"list_total_count\": 3,\n" +
            "        \"RESULT\": {\n" +
            "            \"CODE\": \"INFO-000\",\n" +
            "            \"MESSAGE\": \"정상 처리되었습니다.\"\n" +
            "        },\n" +
            "        \"row\": [\n" +
            "            {\n" +
            "                \"rackTotCnt\": \"15\",\n" +
            "                \"stationName\": \"102. 망원역 1번출구 앞\",\n" +
            "                \"parkingBikeTotCnt\": \"2\",\n" +
            "                \"shared\": \"13\",\n" +
            "                \"stationLatitude\": \"37.55564880\",\n" +
            "                \"stationLongitude\": \"126.91062927\",\n" +
            "                \"stationId\": \"ST-4\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"rackTotCnt\": \"14\",\n" +
            "                \"stationName\": \"103. 망원역 2번출구 앞\",\n" +
            "                \"parkingBikeTotCnt\": \"7\",\n" +
            "                \"shared\": \"50\",\n" +
            "                \"stationLatitude\": \"37.55495071\",\n" +
            "                \"stationLongitude\": \"126.91083527\",\n" +
            "                \"stationId\": \"ST-5\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"rackTotCnt\": \"13\",\n" +
            "                \"stationName\": \"104. 합정역 1번출구 앞\",\n" +
            "                \"parkingBikeTotCnt\": \"1\",\n" +
            "                \"shared\": \"8\",\n" +
            "                \"stationLatitude\": \"37.55062866\",\n" +
            "                \"stationLongitude\": \"126.91498566\",\n" +
            "                \"stationId\": \"ST-6\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "}";
    @Test
    @DisplayName("따릉이 API 활용 정류소 정보 갱신 테스트")
    @Transactional
    public void batchInsertBikeStationTest() {
        // given
        JsonArray expected = ((JsonObject) JsonParser.parseString(MOCK_API_RESPONSE))
                        .getAsJsonObject("rentBikeStatus")
                        .getAsJsonArray("row");
        when(bikeApiUtil.requestSeoulBikeAPI(1, 1000)).thenReturn(expected);
        // when
        simpleBikeStationBatchService.batchInsertBikeStation();
        List<BikeStation> results = bikeStationRepository.findAll();
        BikeStation secondBikeStation = results.get(1);
        // then
        assertEquals(3, results.size());
        assertEquals(secondBikeStation.getStationName(), "103. 망원역 2번출구 앞");
        assertEquals(secondBikeStation.getLatitude(), 37.55495071D);
        assertEquals(secondBikeStation.getLongitude(), 126.91083527D);
    }

}
