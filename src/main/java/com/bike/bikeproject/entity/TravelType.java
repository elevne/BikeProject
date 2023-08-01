package com.bike.bikeproject.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TravelType {

    CULTURE("문화관광"), SHOPPING("쇼핑"), HISTORY("역사관광"), RECREATION("휴양관광");

    private final String description;

}
