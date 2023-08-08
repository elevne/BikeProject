package com.bike.bikeproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "bike_station")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BikeStation implements Persistable<Integer> {

    @Id @Column(name = "station_id")
    private Integer id;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "station_name", nullable = false)
    private String stationName;

    // INSERT 작업 시 매 ROW 마다 SELECT 문이 발생하는 것 방지
    @Override
    public boolean isNew() {
        return true;
    }
}