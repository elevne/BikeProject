package com.bike.bikeproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "bike_station")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BikeStation {

    @Id @Column(name = "station_id")
    private Integer id;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "station_name", nullable = false)
    private String stationName;

}