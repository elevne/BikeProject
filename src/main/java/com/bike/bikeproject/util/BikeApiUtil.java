package com.bike.bikeproject.util;

import com.google.gson.JsonObject;

/**
 * 서울 따릉이 API 호출용 인터페이스
 */
public interface BikeApiUtil {

    JsonObject requestSeoulBikeAPI(int startIdx, int endIdx);

    JsonObject requestSeoulBikeAPI(int idx);

}
