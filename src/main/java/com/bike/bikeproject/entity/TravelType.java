package com.bike.bikeproject.entity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public enum TravelType {

    CULTURE("문화관광"), SHOPPING("쇼핑"), HISTORY("역사관광"), RECREATION("휴양관광");

    private final String description;

    public static TravelType getTravelType(String type) {
        Objects.requireNonNull(type);
        if (type.contains("문화")) return TravelType.CULTURE;
        else if (type.contains("쇼핑")) return TravelType.SHOPPING;
        else if (type.contains("역사")) return TravelType.HISTORY;
        else if (type.contains("휴양")) return TravelType.RECREATION;
        else {
            log.warn("Invalid String type: {}", type);
            throw new RuntimeException();  // todo: 적절한 예외로 바꿔서 던지기
        }
    }

}
