package com.bike.bikeproject.util;

import com.bike.bikeproject.entity.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum PlaceType {

    CAFE("data/cafe.txt", "C",
            (List<String> dataList) -> {
                return Cafe.builder()
                        .latitude(Double.parseDouble(dataList.get(1)))
                        .longitude(Double.parseDouble(dataList.get(2)))
                        .ranking(Integer.parseInt(dataList.get(5)))
                        .name(dataList.get(6).replaceAll("\"", ""))
                        .time(1.0f)
                        .bikeStation(null)  // todo: nearest 값으로 BikeStation 정보 가져오는 메소드 필요
                        .build();
    }),
    RESTAURANT("data/rest.txt", "R",
            (List<String> dataList) -> {
                return Restaurant.builder()
                        .latitude(Double.parseDouble(dataList.get(1)))
                        .longitude(Double.parseDouble(dataList.get(2)))
                        .ranking(Integer.parseInt(dataList.get(6)))
                        .name(dataList.get(4).replaceAll("\"", ""))
                        .time(1.0f)
                        .bikeStation(null)
                        .build();
            }),
    TRAVEL("data/trav.txt", "T",
            (List<String> dataList) -> {
                return Travel.builder()
                        .latitude(Double.parseDouble(dataList.get(1)))
                        .longitude(Double.parseDouble(dataList.get(2)))
                        .ranking(Integer.parseInt(dataList.get(4)))
                        .name(dataList.get(7).replaceAll("\"", ""))
                        .time(Float.parseFloat(dataList.get(6)))
                        .category(TravelType.getTravelType(dataList.get(5)))
                        .bikeStation(null)
                        .build();
            });

    public static PlaceType ofDtype(String dtype) {
        return Arrays.stream(values())
                .filter(p -> p.dtype.equals(dtype))
                .findFirst()
                .orElseThrow(RuntimeException::new);  // todo: 적절한 예외로 바꿔 던지기
    }

    private final String filepath;
    private final String dtype;
    private final Function<List<String>, Place> function;
}
