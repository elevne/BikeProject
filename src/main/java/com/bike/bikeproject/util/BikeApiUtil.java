package com.bike.bikeproject.util;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * 서울 따릉이 API 호출용 인터페이스
 */
public interface BikeApiUtil {

    JsonObject requestSeoulBikeAPI(int startIdx, int endIdx);

    JsonObject requestSeoulBikeAPI(int idx);

    // todo: 위 메소드 이걸로 대체 + 테스트 코드 변경 + 컨트롤러 작성
    //JsonObject requestSeoulBikeAPI(List<Integer> indexes);

}
