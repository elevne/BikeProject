package com.bike.bikeproject.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

/**
 * DestinationBatchUtil 에서 Destination 구체 타입 구분 용으로 사용되는 열거 타입
 */
@Getter
@RequiredArgsConstructor
public enum DestinationType {

    CAFE("data/cafe.txt", "C"),
    RESTAURANT("data/rest.txt", "R"),
    TRAVEL("data/trav.txt", "T");

    public static DestinationType ofDtype(String dtype) {
        Objects.requireNonNull(dtype);
        return Arrays.stream(values())
                .filter(p -> p.dtype.equals(dtype))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("String variable 'dtype'(" + dtype + ") does not contain any of C/R/T"));
    }

    private final String filepath;
    private final String dtype;
}
