package com.bike.bikeproject.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * 서울 따릉이 API 호출용 인터페이스
 */
public interface BikeApiUtil {

    JsonArray requestSeoulBikeAPI(int startIdx, int endIdx);

    JsonArray requestSeoulBikeAPI(List<Integer> indexes);

}
