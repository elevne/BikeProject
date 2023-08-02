package com.bike.bikeproject.util;

import com.bike.bikeproject.entity.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * DestinationBatchUtil 에서 Destination 구체 타입 구분 용으로 사용되는 열거 타입
 */
@Getter
@RequiredArgsConstructor
public enum PlaceType {

    CAFE("data/cafe.txt", "C"),
    RESTAURANT("data/rest.txt", "R"),
    TRAVEL("data/trav.txt", "T");

    public static PlaceType ofDtype(String dtype) {
        Objects.requireNonNull(dtype);
        return Arrays.stream(values())
                .filter(p -> p.dtype.equals(dtype))
                .findAny()
                .orElseThrow(RuntimeException::new);  // todo: 적절한 예외로 바꿔 던지기
    }

    private final String filepath;
    private final String dtype;
}
