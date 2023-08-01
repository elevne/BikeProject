package com.bike.bikeproject.entity;

import javax.persistence.*;

@Entity
@Table(name = "place")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Place {

    @Id @Column(name = "place_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "ranking", nullable = false)
    private int ranking;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "time", nullable = false)
    private float time;

    @OneToOne
    @JoinColumn(name = "station_id")
    private BikeStation bikeStation;
}
