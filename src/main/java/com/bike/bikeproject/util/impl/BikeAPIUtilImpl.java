package com.bike.bikeproject.util.impl;

import com.bike.bikeproject.util.BikeApiUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BikeAPIUtilImpl implements BikeApiUtil {

    @Value("${bike.api.key}")
    private String bikeApiKey;

}