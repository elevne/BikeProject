package com.bike.bikeproject.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlaceType {

    CAFE("data/cafe.txt"),
    RESTAURANT("data/rest.txt"),
    TRAVEL("data/trav.txt");

    private final String filepath;

}
