package com.bike.bikeproject.entity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public enum TravelType {

    CULTURE("문화"), SHOPPING("쇼핑"), HISTORY("역사"), RECREATION("휴양");

    private final String description;

    public static TravelType getTravelType(String type) {
        Objects.requireNonNull(type);
        return Arrays.stream(values())
                .filter(t -> type.contains(t.description))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("String variable 'type'" + type + " does not contain any of 문화/쇼핑/역사/휴양"));
    }
}