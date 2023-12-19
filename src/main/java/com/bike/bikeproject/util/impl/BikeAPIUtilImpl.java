package com.bike.bikeproject.util.impl;

import com.bike.bikeproject.entity.BikeStation;
import com.bike.bikeproject.exception.BikeAPIException;
import com.bike.bikeproject.repository.BikeStationRepository;
import com.bike.bikeproject.util.BikeApiUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.spring.web.json.Json;

import javax.transaction.Transactional;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class BikeAPIUtilImpl implements BikeApiUtil {

    private final RestTemplate restTemplate;

    public BikeAPIUtilImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                // BufferingClientHttpRequestFactory: HTTP 요청을 버퍼링하는 데 사용 / 실제 요청은 내부적으로 다른 ClientHttpRequestFactory 사용
                // 생성된 요청은 버퍼에 저장 / 버퍼링은 주로 디버깅 및 로깅을 위해 사용
                // SimpleClientHttpRequestFactory: 실제 HTTP 요청을 만들고 수행하는 데 사용 / 단일 스레드 환경에서 사용하기 적합
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .setConnectTimeout(Duration.ofMillis(5000))
                .setReadTimeout(Duration.ofMillis(5000))
                .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
                .build();
    }

    @Value("${bike.api.key}")
    private String bikeApiKey;

    private final String API_URI = "http://openapi.seoul.go.kr:8088";

    // 뒤에 start_idx/end_idx/ 붙여서 GET 요청
    private final String PATH = "/json/bikeList/";

    // API 호출 시 한 번에 최대 1000 건 까지만 조회 가능
    public static final int API_MAX_COUNT = 1000;

    @Override
    public JsonArray requestSeoulBikeAPI(int startIdx, int endIdx) throws BikeAPIException {
        String path = "/" + bikeApiKey + PATH + String.valueOf(startIdx) + "/" + String.valueOf(endIdx);
        URI uri = UriComponentsBuilder
                .fromUriString(API_URI)
                .path(path)
                .encode()
                .build()
                .toUri();

        JsonObject stationsJsonObject;
        JsonArray stationsJsonArray;
        try {
            stationsJsonObject = (JsonObject) JsonParser.parseString(
                    Objects.requireNonNull(
                            restTemplate
                                    .getForEntity(uri, String.class)
                                    .getBody()
                    ));
            stationsJsonArray = stationsJsonObject
                    .getAsJsonObject("rentBikeStatus")
                    .getAsJsonArray("row");
        } catch (RestClientException | JsonSyntaxException e) {
            throw new BikeAPIException(e);
        }
        return stationsJsonArray;
    }

    @Override
    public JsonArray requestSeoulBikeAPI(List<Integer> indexes) throws BikeAPIException {
        JsonArray stationsJsonArray = new JsonArray();
        for (Integer idx : indexes) {
            stationsJsonArray.add(requestSeoulBikeAPI(idx));
        }
        return stationsJsonArray;
    }

    private JsonObject requestSeoulBikeAPI(int idx) throws BikeAPIException {
        String path = "/" + bikeApiKey + PATH + String.valueOf(idx) + "/" + String.valueOf(idx);
        URI uri = UriComponentsBuilder
                .fromUriString(API_URI)
                .path(path)
                .encode()
                .build()
                .toUri();

        JsonObject bikeStationInfo;
        try {
            JsonObject response = (JsonObject) JsonParser.parseString(
                    Objects.requireNonNull(
                            restTemplate
                                    .getForEntity(uri, String.class)
                                    .getBody()
                    ));
            bikeStationInfo = (JsonObject) response
                    .getAsJsonObject("rentBikeStatus")
                    .getAsJsonArray("row")
                    .get(0);
        } catch (RestClientException | JsonSyntaxException | NullPointerException | ClassCastException e) {
            throw new BikeAPIException(e);
        }
        return bikeStationInfo;
    }
}