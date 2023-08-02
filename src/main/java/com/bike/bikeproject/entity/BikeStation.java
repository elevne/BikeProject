package com.bike.bikeproject.entity;

import javax.persistence.*;

@Entity
@Table(name = "bike_station")
public class BikeStation {

    @Id @Column(name = "station_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "station_name", nullable = false)
    private String stationName;

}