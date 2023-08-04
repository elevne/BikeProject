package com.bike.bikeproject.dto;

import lombok.*;
import org.springframework.lang.Nullable;

/**
 * Client 와 Destination 관련 정보를 주고받기 위해 사용하는 DTO
 * 업데이트/데이터 버전에 따라 Destination 과 @OneToOne 관계인
 * BikeStation 이 없을 수 있기에 null 값을 허용하는 Integer 래펄 클래스 사용
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DestinationDTO {

    private String name;

    private double latitude;

    private double longitude;

    @Nullable
    private Integer nearestBikeStationId;

    @Nullable
    private String nearestBikeStationName;

    private int order;

}