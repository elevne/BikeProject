package com.bike.bikeproject.vo;

import com.bike.bikeproject.dto.DestinationDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(description = "경로 추천 API 기반 추천 결과 VO")
@Getter
@AllArgsConstructor
public class SuggestedRouteVO {

    @Schema(description = "Destination 을 추천 순으로 담은 List")
    private List<DestinationDTO> suggestedRoute;

    @Schema(description = "총 이동 거리 (단위 km)", example = "33")
    private double totalDistance;

}
