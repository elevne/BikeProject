package com.bike.bikeproject.util;

import com.bike.bikeproject.exception.BikeAPIException;
import com.bike.bikeproject.util.impl.BikeAPIUtilImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

// todo: 이어서 작성하기
@RestClientTest(BikeAPIUtilImpl.class)
public class BikeAPIUtilTest {

    @Autowired BikeApiUtil bikeApiUtil;

    @Autowired MockRestServiceServer mockServer;

    @Value("${bike.api.key}")
    private String API_KEY;

    @Test
    @DisplayName("따릉이 API 호출 및 JsonObject 매핑 테스트")
    public void requestManyBikesAPITest() {
        // given
        int from = 1;
        int to = 10;
        String apiURL = "http://openapi.seoul.go.kr:8088/" + API_KEY + "/json/bikeList/" +
                String.valueOf(from) + "/" + String.valueOf(to);
        String response = "{\n" +
                "    \"rentBikeStatus\": {\n" +
                "        \"list_total_count\": 10,\n" +
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
                "                \"parkingBikeTotCnt\": \"5\",\n" +
                "                \"shared\": \"36\",\n" +
                "                \"stationLatitude\": \"37.55495071\",\n" +
                "                \"stationLongitude\": \"126.91083527\",\n" +
                "                \"stationId\": \"ST-5\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"rackTotCnt\": \"13\",\n" +
                "                \"stationName\": \"104. 합정역 1번출구 앞\",\n" +
                "                \"parkingBikeTotCnt\": \"4\",\n" +
                "                \"shared\": \"31\",\n" +
                "                \"stationLatitude\": \"37.55062866\",\n" +
                "                \"stationLongitude\": \"126.91498566\",\n" +
                "                \"stationId\": \"ST-6\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"rackTotCnt\": \"5\",\n" +
                "                \"stationName\": \"105. 합정역 5번출구 앞\",\n" +
                "                \"parkingBikeTotCnt\": \"1\",\n" +
                "                \"shared\": \"20\",\n" +
                "                \"stationLatitude\": \"37.55000687\",\n" +
                "                \"stationLongitude\": \"126.91482544\",\n" +
                "                \"stationId\": \"ST-7\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"rackTotCnt\": \"12\",\n" +
                "                \"stationName\": \"106. 합정역 7번출구 앞\",\n" +
                "                \"parkingBikeTotCnt\": \"3\",\n" +
                "                \"shared\": \"25\",\n" +
                "                \"stationLatitude\": \"37.54864502\",\n" +
                "                \"stationLongitude\": \"126.91282654\",\n" +
                "                \"stationId\": \"ST-8\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"rackTotCnt\": \"5\",\n" +
                "                \"stationName\": \"107. 신한은행 서교동지점\",\n" +
                "                \"parkingBikeTotCnt\": \"4\",\n" +
                "                \"shared\": \"80\",\n" +
                "                \"stationLatitude\": \"37.55751038\",\n" +
                "                \"stationLongitude\": \"126.91850281\",\n" +
                "                \"stationId\": \"ST-9\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"rackTotCnt\": \"10\",\n" +
                "                \"stationName\": \"108. 서교동 사거리\",\n" +
                "                \"parkingBikeTotCnt\": \"6\",\n" +
                "                \"shared\": \"60\",\n" +
                "                \"stationLatitude\": \"37.55274582\",\n" +
                "                \"stationLongitude\": \"126.91861725\",\n" +
                "                \"stationId\": \"ST-10\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"rackTotCnt\": \"10\",\n" +
                "                \"stationName\": \"109. 제일빌딩 앞\",\n" +
                "                \"parkingBikeTotCnt\": \"2\",\n" +
                "                \"shared\": \"20\",\n" +
                "                \"stationLatitude\": \"37.54769135\",\n" +
                "                \"stationLongitude\": \"126.91998291\",\n" +
                "                \"stationId\": \"ST-11\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"rackTotCnt\": \"10\",\n" +
                "                \"stationName\": \"111. 상수역 2번출구 앞\",\n" +
                "                \"parkingBikeTotCnt\": \"0\",\n" +
                "                \"shared\": \"0\",\n" +
                "                \"stationLatitude\": \"37.54787064\",\n" +
                "                \"stationLongitude\": \"126.92353058\",\n" +
                "                \"stationId\": \"ST-15\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"rackTotCnt\": \"10\",\n" +
                "                \"stationName\": \"112. 극동방송국 앞\",\n" +
                "                \"parkingBikeTotCnt\": \"4\",\n" +
                "                \"shared\": \"40\",\n" +
                "                \"stationLatitude\": \"37.54920197\",\n" +
                "                \"stationLongitude\": \"126.92320251\",\n" +
                "                \"stationId\": \"ST-16\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        mockServer.expect(requestTo(apiURL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
        // when
        JsonArray result = bikeApiUtil.requestSeoulBikeAPI(1, 10);
        // then
        assertEquals(10, result.size());
    }

    @Test
    @DisplayName("정류소 현황 정보 API 로 조회")
    public void requestOneBikeAPITest() {
        // given
        int idx1 = 1;
        int idx2 = 2;
        int idx3 = 3;
        List<Integer> indexes = List.of(idx1, idx2, idx3);
        String apiURL1 = "http://openapi.seoul.go.kr:8088/" + API_KEY + "/json/bikeList/" +
                String.valueOf(idx1) + "/" + String.valueOf(idx1);
        String apiURL2 = "http://openapi.seoul.go.kr:8088/" + API_KEY + "/json/bikeList/" +
                String.valueOf(idx2) + "/" + String.valueOf(idx2);
        String apiURL3 = "http://openapi.seoul.go.kr:8088/" + API_KEY + "/json/bikeList/" +
                String.valueOf(idx3) + "/" + String.valueOf(idx3);
        String response = "{\n" +
                "    \"rentBikeStatus\": {\n" +
                "        \"list_total_count\": 1,\n" +
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
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        mockServer.expect(requestTo(apiURL1))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
        mockServer.expect(requestTo(apiURL2))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
        mockServer.expect(requestTo(apiURL3))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
        // when
        JsonArray result = bikeApiUtil.requestSeoulBikeAPI(indexes);
        // then
        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("따릉이 API 예외처리 테스트")
    public void requestOneBikeAPIFailTest() {
        // given
        int idx1 = 1;
        List<Integer> indexes = List.of(idx1);
        String apiURL1 = "http://openapi.seoul.go.kr:8088/" + API_KEY + "/json/bikeList/" +
                String.valueOf(idx1) + "/" + String.valueOf(idx1);
        String response = "{\n" +
                "    \"rentBikeStatus\": {\n" +
                "        \"list_total_count\": 1,\n" +
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
                "            }\n" +
                "        ]\n" +
                "    }\n"; // JsonSyntaxException 던지게끔
        mockServer.expect(requestTo(apiURL1))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
        // when, then
        assertThrows(BikeAPIException.class, () -> {
            bikeApiUtil.requestSeoulBikeAPI(indexes);
        });
    }
}
