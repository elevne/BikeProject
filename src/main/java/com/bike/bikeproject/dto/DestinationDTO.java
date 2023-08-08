package com.bike.bikeproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

/**
 * Client 와 Destination 관련 정보를 주고받기 위해 사용하는 DTO
 * 업데이트/데이터 버전에 따라 Destination 과 @OneToOne 관계인
 * BikeStation 이 없을 수 있기에 null 값을 허용하는 Integer 래퍼 클래스 사용
 */
@Schema(description = "Destination (여행지, 식당, 카페) 정보를 담은 DTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DestinationDTO {

    @NotNull
    @Schema(description = "Destination 이름", example = "잠실 롯데월드")
    private String name;

    @NotNull
    @Range(min = 33L, max = 39L)
    @Schema(description = "Destination 위도 값", example = "37.3651")
    private double latitude;

    @NotNull
    @Range(min = 126L, max = 128L)
    @Schema(description = "Destination 경도 값", example = "127.1255")
    private double longitude;

    @Nullable
    @Schema(description = "가장 가까운 따릉이 정류소 ID", example = "12", nullable = true)
    private Integer nearestBikeStationId;

    @Nullable
    @Schema(description = "가장 가까운 따릉이 정류소 이름", example = "망원역 1번 출구 앞", nullable = true)
    private String nearestBikeStationName;

    @Nullable
    @Schema(description = "Destination 방문 순번", example = "3")
    private int order;
}